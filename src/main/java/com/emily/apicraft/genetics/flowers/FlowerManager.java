package com.emily.apicraft.genetics.flowers;

import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.google.common.collect.AbstractIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.tags.ITag;

import javax.annotation.Nullable;
import java.util.*;

public class FlowerManager {
    private static final Map<ResourceLocation, Set<Block>> acceptedBlocks = new HashMap<>();
    private static final Map<ResourceLocation, Set<BlockState>> acceptedStates = new HashMap<>();
    private static final Map<ResourceLocation, Set<ITag<Block>>> acceptedTags = new HashMap<>();

    private FlowerManager(){}

    public static void registerAcceptedFlower(ResourceLocation type, Block block){
        if(acceptedBlocks.containsKey(type)){
            acceptedBlocks.get(type).add(block);
        }
        else {
            acceptedBlocks.put(type, new HashSet<>(Collections.singleton(block)));
            acceptedStates.put(type, new HashSet<>());
            acceptedTags.put(type, new HashSet<>());
        }
    }

    public static void registerAcceptedFlower(ResourceLocation type, BlockState state){
        if(acceptedStates.containsKey(type)){
            acceptedStates.get(type).add(state);
        }
        else {
            acceptedBlocks.put(type, new HashSet<>());
            acceptedStates.put(type, new HashSet<>(Collections.singleton(state)));
            acceptedTags.put(type, new HashSet<>());
        }
    }

    public static void registerAcceptedFlower(ResourceLocation type, ITag<Block> tag){
        if(acceptedTags.containsKey(type)){
            acceptedTags.get(type).add(tag);
        }
        else {
            acceptedBlocks.put(type, new HashSet<>());
            acceptedTags.put(type, new HashSet<>(Collections.singleton(tag)));
            acceptedStates.put(type, new HashSet<>());
        }
    }

    public static AcceptFlowerPredicate getPredicate(ResourceLocation type){
        return new AcceptFlowerPredicate(acceptedBlocks.get(type), acceptedStates.get(type), acceptedTags.get(type));
    }

    private static Vec3i getArea(IBeeHousing housing, Bee bee){
        return housing.applyTerritoryModifier(((Alleles.Territory) bee.getGenome().getAllele(AlleleTypes.TERRITORY, true)).getValue());
    }

    public static Iterator<BlockPos.MutableBlockPos> getSurfaceIterator(IBeeHousing housing, Bee bee){
        Vec3i area = getArea(housing, bee);
        return new SpiralSurfaceIterator(housing.getBeeHousingLevel(), housing.getBeeHousingPos(), area.getX(), area.getY());
    }


    private static class SpiralBlockPosIterator extends AbstractIterator<BlockPos.MutableBlockPos> {
        protected final BlockPos center;
        private final int diameter;
        private int currentDiameter;
        private int currentDirection;

        @Nullable
        BlockPos.MutableBlockPos mutableBlockPos;

        public SpiralBlockPosIterator(BlockPos center, int diameter){
            this.center = center;
            this.diameter = diameter;
            currentDiameter = 3;
            currentDirection = 0;
        }

        @Nullable
        @Override
        protected BlockPos.MutableBlockPos computeNext(){
            return nextPos();
        }

        protected BlockPos.MutableBlockPos nextPos(){
            if(this.mutableBlockPos == null){
                mutableBlockPos = new BlockPos.MutableBlockPos(center.getX(), center.getY(), center.getZ());
                return mutableBlockPos;
            } else if(currentDiameter > diameter){
                return endOfData();
            } else {
                int x = mutableBlockPos.getX() - center.getX();
                int z = mutableBlockPos.getZ() - center.getZ();

                switch (currentDirection){
                    case 0 -> {
                        x++;
                        if(x == currentDiameter / 2){
                            currentDirection++;
                        }
                    }
                    case 1 -> {
                        z++;
                        if(z == currentDiameter / 2){
                            currentDirection++;
                        }
                    }
                    case 2 -> {
                        x--;
                        if(x == -currentDiameter / 2){
                            currentDirection++;
                        }
                    }
                    case 3 -> {
                        z--;
                        if(z == -currentDiameter / 2){
                            currentDirection = 0;
                            currentDiameter += 2;
                        }
                    }
                }
                return mutableBlockPos.set(x, center.getY(), z);
            }
        }
    }
    private static class SpiralSurfaceIterator extends SpiralBlockPosIterator{
        private final Level level;
        private final int maxDepth;


        public SpiralSurfaceIterator(Level level, BlockPos center, int diameter, int heightRange) {
            super(center, diameter);
            this.level = level;
            this.maxDepth = heightRange / 2;
        }

        @Nullable
        @Override
        protected BlockPos.MutableBlockPos computeNext(){
            BlockPos.MutableBlockPos next = nextPos();
            int y = Math.max(Math.min(center.getY() + maxDepth, level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, next.getX(), next.getZ())), center.getY() - maxDepth);
            return next.setY(y);
        }
    }
    public static class AcceptFlowerPredicate {
        private final Set<Block> acceptedBlocks;
        private final Set<BlockState> acceptedBlockStates;
        private final Set<ITag<Block>> acceptedBlockTags;

        public AcceptFlowerPredicate(Set<Block> acceptedBlocks, Set<BlockState> acceptedBlockStates, Set<ITag<Block>> acceptedBlockTags){
            this.acceptedBlocks = acceptedBlocks;
            this.acceptedBlockStates = acceptedBlockStates;
            this.acceptedBlockTags = acceptedBlockTags;
        }

        public boolean predicate(Level level, BlockPos pos){
            if(level.hasChunk(pos.getX(), pos.getZ())){
                BlockState state = level.getBlockState(pos);
                if(!state.isAir()){
                    Block block = state.getBlock();
                    return acceptedBlocks.contains(block) || acceptedBlockStates.contains(state) || acceptedBlockTags.stream().anyMatch(tag -> tag.contains(block));
                }
            }
            return false;
        }
    }
}
