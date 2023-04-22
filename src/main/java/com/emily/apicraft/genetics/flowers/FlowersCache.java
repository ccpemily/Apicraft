package com.emily.apicraft.genetics.flowers;

import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.BeeGenome;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.block.IBeeHousing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlowersCache implements INBTSerializable<CompoundTag> {
    private static final int FLOWER_CHECK_INTERVAL = 200;

    private int currentTick = 0;
    @Nullable
    private FlowerData flowerData = null;
    private List<BlockPos> coordinates = new ArrayList<>();
    private boolean dirty = false;

    @Override
    public CompoundTag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }

    public void fromNetwork(FriendlyByteBuf buffer){

    }

    public void writeToNetwork(FriendlyByteBuf buffer){

    }

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

    public void update(IBeeHousing housing, Bee queen){
        if(flowerData == null){
            flowerData = new FlowerData(housing, queen);
            coordinates.clear();
        }
        Level level = housing.getBeeHousingLevel();
        if(level == null){
            return;
        }
        currentTick++;
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
        int ticksPerCheck = 1 + flowerCount * flowerCount;
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
