package com.emily.apicraft.genetics.flowers;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.BeeGenome;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.block.beehouse.IBeeHousing;
import com.emily.apicraft.utils.Tags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.*;

public class FlowersCache implements INBTSerializable<CompoundTag> {
    private static final int FLOWER_CHECK_INTERVAL = 100;

    private int currentTick;
    @Nullable
    private FlowerData flowerData = null;
    private final List<BlockPos> coordinates = new ArrayList<>();
    private boolean dirty = false;

    public FlowersCache(){
        currentTick = new Random().nextInt(FLOWER_CHECK_INTERVAL);
    }

    // region NBTTag
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag list = new ListTag();
        int i = 0;
        for(BlockPos pos : coordinates){
            CompoundTag coordinateTag = new CompoundTag();
            coordinateTag.putInt("x", pos.getX());
            coordinateTag.putInt("y", pos.getY());
            coordinateTag.putInt("z", pos.getZ());
            list.add(i, coordinateTag);
            i++;
        }
        tag.put(Tags.TAG_FLOWERS_CACHE, list);
        return tag;
    }

    public void writeToTag(CompoundTag nbt){
        ListTag list = new ListTag();
        int i = 0;
        for(BlockPos pos : coordinates){
            CompoundTag coordinateTag = new CompoundTag();
            coordinateTag.putInt("x", pos.getX());
            coordinateTag.putInt("y", pos.getY());
            coordinateTag.putInt("z", pos.getZ());
            list.add(i, coordinateTag);
            i++;
        }
        Apicraft.LOGGER.debug("Wrote flowers NBT data: " + list);
        nbt.put(Tags.TAG_FLOWERS_CACHE, list);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ListTag list = nbt.getList(Tags.TAG_FLOWERS_CACHE, Tag.TAG_COMPOUND);
        if(list.size() <= 0){
            return;
        }
        for(int i = 0; i < list.size(); i++){
            CompoundTag coordinateTag = list.getCompound(i);
            coordinates.add(new BlockPos(coordinateTag.getInt("x"), coordinateTag.getInt("y"), coordinateTag.getInt("z")));
        }
        dirty = true;
        Apicraft.LOGGER.debug("Read flowers data from NBT: " + this.serializeNBT());
    }
    // endregion

    // region Network
    public void fromNetwork(FriendlyByteBuf buffer){
        coordinates.clear();
        int size = buffer.readInt();
        while(size > 0){
            int x = buffer.readInt();
            int y = buffer.readInt();
            int z = buffer.readInt();
            coordinates.add(new BlockPos(x, y, z));
            size--;
        }
        Apicraft.LOGGER.debug("Received flower cache packet: " + serializeNBT().toString());
    }

    public void writeToNetwork(FriendlyByteBuf buffer){
        int size = coordinates.size();
        buffer.writeInt(size);
        for(BlockPos pos : coordinates){
            buffer.writeInt(pos.getX());
            buffer.writeInt(pos.getY());
            buffer.writeInt(pos.getZ());
        }
        Apicraft.LOGGER.debug("Sent flower cache packet: " + serializeNBT().toString());
        dirty = false;
    }
    // endregion

    // region Callback
    public void onNewQueen(IBeeHousing housing, Bee queen){
        if (this.flowerData != null) {
            BeeGenome genome = queen.getGenome();
            ResourceLocation flowerType = ((Alleles.AcceptedFlowers) genome.getAllele(AlleleTypes.ACCEPTED_FLOWERS, true)).getValue().getFlowerType();
            if (!this.flowerData.type.equals(flowerType)
                    || !this.flowerData.territory.equals(((Alleles.Territory) genome.getAllele(AlleleTypes.TERRITORY, true)).getValue())) {
                flowerData = new FlowerData(housing, queen);
                coordinates.clear();
            }
        }
    }

    public void forceLookForFlowers(Bee queen, IBeeHousing housing) {
        if (flowerData != null) {
            coordinates.clear();
            flowerData.resetIterator(housing, queen);
            Level world = housing.getBeeHousingLevel();
            while (flowerData.areaIterator.hasNext()) {
                BlockPos.MutableBlockPos blockPos = flowerData.areaIterator.next();
                if (flowerData.predicate.predicate(world, blockPos)) {
                    coordinates.add(blockPos.immutable());
                    dirty = true;
                }
            }
        }
    }
    // endregion

    public List<BlockPos> getFlowersPos(){
        return Collections.unmodifiableList(coordinates);
    }

    public void update(IBeeHousing housing, Bee queen){
        if(flowerData == null){
            flowerData = new FlowerData(housing, queen);
            coordinates.clear();
        }
        Level level = housing.getBeeHousingLevel();
        if(level == null){
            return;
        }
        if(!coordinates.isEmpty() && currentTick % FLOWER_CHECK_INTERVAL == 0){
            Iterator<BlockPos> iterator = coordinates.iterator();
            while(iterator.hasNext()){
                BlockPos pos = iterator.next();
                if(!flowerData.predicate.predicate(level, pos)){
                    iterator.remove();
                    dirty = true;
                }
            }
        }

        int flowerCount = coordinates.size();
        int ticksPerCheck = flowerCount * flowerCount + 1;
        if(currentTick % ticksPerCheck == 0){
            if(flowerData.areaIterator.hasNext()){
                BlockPos.MutableBlockPos pos = flowerData.areaIterator.next();
                if(flowerData.predicate.predicate(level, pos)){
                    coordinates.add(pos.immutable());
                    dirty = true;
                }
            }
            else{
                flowerData.resetIterator(housing, queen);
            }
        }
        currentTick++;
    }

    public boolean isDirty() {
        return dirty;
    }

    private static class FlowerData {
        public final ResourceLocation type;
        public final Vec3i territory;
        public final FlowerManager.AcceptFlowerPredicate predicate;
        public Iterator<BlockPos.MutableBlockPos> areaIterator;

        public FlowerData(IBeeHousing housing, Bee queen){
            this.type = ((Alleles.AcceptedFlowers) queen.getGenome().getAllele(AlleleTypes.ACCEPTED_FLOWERS, true)).getProvider().getFlowerType();
            this.territory = ((Alleles.Territory) queen.getGenome().getAllele(AlleleTypes.TERRITORY, true)).getValue();
            this.predicate = FlowerManager.getPredicate(type);
            this.areaIterator = FlowerManager.getSurfaceIterator(housing, queen);
        }

        public void resetIterator(IBeeHousing beeHousing, Bee queen) {
            this.areaIterator = FlowerManager.getSurfaceIterator(beeHousing, queen);
        }
    }
}
