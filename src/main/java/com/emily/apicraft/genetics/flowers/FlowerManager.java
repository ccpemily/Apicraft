package com.emily.apicraft.genetics.flowers;

import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.AlleleTypes;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.block.beehouse.IBeeHousing;
import com.google.common.collect.AbstractIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.tags.ITag;

import javax.annotation.Nullable;
import java.util.*;

public class FlowerManager {
    private static final Map<ResourceLocation, Set<Block>> acceptedBlocks = new HashMap<>();
    private static final Map<ResourceLocation, Set<BlockState>> acceptedStates = new HashMap<>();
    private static final Map<ResourceLocation, Set<ITag<Block>>> acceptedTags = new HashMap<>();

    private static final Block[] standardFlowers = new Block[]{
            Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID,
            Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP,
            Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP,
            Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.WITHER_ROSE,
            Blocks.LILY_OF_THE_VALLEY,
    };
    private static final Block[] pottedStandardFlowers = new Block[]{
            Blocks.POTTED_POPPY, Blocks.POTTED_BLUE_ORCHID, Blocks.POTTED_ALLIUM,
            Blocks.POTTED_AZURE_BLUET, Blocks.POTTED_RED_TULIP, Blocks.POTTED_ORANGE_TULIP,
            Blocks.POTTED_WHITE_TULIP, Blocks.POTTED_PINK_TULIP, Blocks.POTTED_OXEYE_DAISY,
            Blocks.POTTED_CORNFLOWER, Blocks.POTTED_LILY_OF_THE_VALLEY, Blocks.POTTED_WITHER_ROSE,
    };

    static {
        for(Block block : standardFlowers){
            registerAcceptedFlower(Alleles.AcceptedFlowers.VANILLA.getValue().getFlowerType(), block);
        }
        for(Block block : pottedStandardFlowers){
            registerAcceptedFlower(Alleles.AcceptedFlowers.VANILLA.getValue().getFlowerType(), block);
        }
        registerAcceptedFlower(Alleles.AcceptedFlowers.END.getValue().getFlowerType(), Blocks.DRAGON_EGG);
        registerAcceptedFlower(Alleles.AcceptedFlowers.END.getValue().getFlowerType(), Blocks.CHORUS_PLANT);
        registerAcceptedFlower(Alleles.AcceptedFlowers.END.getValue().getFlowerType(), Blocks.CHORUS_FLOWER);
        registerAcceptedFlower(Alleles.AcceptedFlowers.JUNGLE.getValue().getFlowerType(), Blocks.VINE);
        registerAcceptedFlower(Alleles.AcceptedFlowers.VANILLA.getValue().getFlowerType(), Blocks.FERN);
        registerAcceptedFlower(Alleles.AcceptedFlowers.WHEAT.getValue().getFlowerType(), Blocks.WHEAT);
        registerAcceptedFlower(Alleles.AcceptedFlowers.GOURD.getValue().getFlowerType(), Blocks.PUMPKIN_STEM);
        registerAcceptedFlower(Alleles.AcceptedFlowers.GOURD.getValue().getFlowerType(), Blocks.MELON_STEM);
        registerAcceptedFlower(Alleles.AcceptedFlowers.NETHER.getValue().getFlowerType(), Blocks.NETHER_WART);
        registerAcceptedFlower(Alleles.AcceptedFlowers.CACTI.getValue().getFlowerType(), Blocks.CACTUS);
        registerAcceptedFlower(Alleles.AcceptedFlowers.CACTI.getValue().getFlowerType(), Blocks.POTTED_CACTUS);
        registerAcceptedFlower(Alleles.AcceptedFlowers.MUSHROOMS.getValue().getFlowerType(), Blocks.POTTED_RED_MUSHROOM);
        registerAcceptedFlower(Alleles.AcceptedFlowers.MUSHROOMS.getValue().getFlowerType(), Blocks.POTTED_BROWN_MUSHROOM);
    }

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
        return new SpiralBlockPosIterator(housing.getBeeHousingLevel(), housing.getBeeHousingPos(), area.getX(), area.getY());
    }


    private static class SpiralBlockPosIterator extends AbstractIterator<BlockPos.MutableBlockPos> {
        private final BlockPos center;
        private final Level level;
        private final int diameter;
        private int currentDiameter;
        private int currentDirection;
        private final int maxH;
        private final int minH;

        @Nullable
        private BlockPos.MutableBlockPos mutableBlockPos;

        public SpiralBlockPosIterator(Level level, BlockPos center, int diameter, int depth){
            this.level = level;
            this.center = center;
            this.diameter = diameter;
            currentDiameter = 3;
            currentDirection = 0;
            minH = -depth / 2 + center.getY();
            maxH = minH + depth;
        }

        @Nullable
        @Override
        protected BlockPos.MutableBlockPos computeNext(){
            return nextPos();
        }

        protected BlockPos.MutableBlockPos nextPos(){
            if(this.mutableBlockPos == null){
                mutableBlockPos = new BlockPos.MutableBlockPos(center.getX(), maxH, center.getZ());
                int y = Math.min(maxH, level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, mutableBlockPos).getY() + 1);
                mutableBlockPos.setY(y);
                return mutableBlockPos;
            } else if(currentDiameter > diameter){
                //Apicraft.LOGGER.debug("End of data");
                return endOfData();
            } else {
                int x = mutableBlockPos.getX() - center.getX();
                int y = mutableBlockPos.getY();
                int z = mutableBlockPos.getZ() - center.getZ();

                if(y > minH && y > -64){
                    y--;
                } else {
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

                    mutableBlockPos.set(center.getX() + x, y, center.getZ() + z);
                    y = Math.min(maxH, level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, mutableBlockPos).getY());
                }

                return mutableBlockPos.set(center.getX() + x, y, center.getZ() + z);
            }
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


        @SuppressWarnings("deprecation")
        public boolean predicate(Level level, BlockPos pos){
            if(level.hasChunkAt(pos)){
                BlockState state = level.getBlockState(pos);
                //Apicraft.LOGGER.debug("Checking for blockstate: " + pos + ", state: " + state);
                if(!state.isAir()){
                    Block block = state.getBlock();

                    return acceptedBlocks.contains(block) || acceptedBlockStates.contains(state) || acceptedBlockTags.stream().anyMatch(tag -> tag.contains(block));
                }
            }
            return false;
        }
    }
}
