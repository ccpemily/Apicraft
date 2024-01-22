package com.emily.apicraft.block.entity.beehousing;

import cofh.core.common.block.entity.SecurableBlockEntity;
import cofh.core.common.network.packet.client.TileControlPacket;
import cofh.core.common.network.packet.client.TileStatePacket;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.lib.util.constants.BlockStatePropertiesCoFH;
import com.emily.apicraft.Apicraft;
import com.emily.apicraft.capabilities.implementation.BeeProviderCapability;
import com.emily.apicraft.client.gui.elements.BreedingProcessStorage;
import com.emily.apicraft.client.particles.ParticleRenderer;
import com.emily.apicraft.core.lib.ErrorStates;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.genetics.flowers.FlowersCache;
import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.inventory.BeeHousingItemInv;
import com.emily.apicraft.items.BeeItem;
import com.emily.apicraft.items.subtype.BeeTypes;
import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.Tags;
//import com.mojang.math.;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

import static cofh.lib.util.constants.NBTTags.TAG_BLOCK_ENTITY;

public abstract class AbstractBeeHousingBlockEntity extends SecurableBlockEntity
        implements IBeeHousing, ITickableTile.IServerTickable, ITickableTile.IClientTickable, MenuProvider {
    // region Fields
    // Constants
    protected static final int MAX_BREEDING_PROGRESS = 100;
    protected static final int TICK_PERIOD = 600;
    protected static final int CLIENT_TICK_PERIOD = 600;
    // Inventory
    protected final BeeHousingItemInv inventory;
    protected Stack<ItemStack> pendingStack = new Stack<>();
    // Logic
    protected boolean isBreeding = false;
    protected int tickThrottle = 0;
    protected int clientTick = 0;
    protected FlowersCache flowersCache = new FlowersCache();
    @Nullable
    protected Bee currentQueen;
    protected QueenCanWorkCache canWorkCache = new QueenCanWorkCache();
    protected BreedingProcessStorage processStorage = new BreedingProcessStorage(0, 0, () -> isBreeding);
    protected ErrorStates errorState = ErrorStates.NONE;
    // State
    protected boolean isActive = false;
    // Climate
    protected Biome biome = null;
    protected int baseTemperature = 0;
    protected int baseHumidity = 0;
    protected int actualTemperature = 0;
    protected int actualHumidity = 0;

    // endregion

    public AbstractBeeHousingBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state, int outputCount, int frameCount, int augmentCount, int maxFrameTier) {
        super(tileEntityTypeIn, pos, state);
        inventory = new BeeHousingItemInv(this, outputCount, frameCount, augmentCount, maxFrameTier);
    }

    // region BeeKeeping
    protected void setActive(boolean active){
        if(isActive != active){
            Apicraft.LOGGER.debug("Turning state from " + isActive + " to " + active);
            if(getBlockState().hasProperty(BlockStatePropertiesCoFH.ACTIVE) && level != null){
                level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockStatePropertiesCoFH.ACTIVE, active));
            }
            isActive = active;
            onStateUpdate();
        }
    }

    protected boolean canWork(){
        boolean hasSpace = addPendingProducts();
        if(!hasSpace){
            errorState = ErrorStates.INVENTORY_FULL;
            return false; // Has no space
        }
        ItemStack queenStack = inventory.getQueen();
        if(queenStack.isEmpty()){
            processStorage.clear();
            processStorage.setCapacity(0);
            currentQueen = null;
            errorState = ErrorStates.NO_QUEEN;
            return false; // No queen or princess drone
        }
        Optional<BeeTypes> typeOptional = queenStack.getItem() instanceof BeeItem ? Optional.of(((BeeItem) queenStack.getItem()).getBeeType()) : Optional.empty();
        if(typeOptional.isEmpty()){
            errorState = ErrorStates.ILLEGAL_STATE;
            return false; // Invalid bee type (exceptional)
        }
        BeeTypes type = typeOptional.get();
        if(type == BeeTypes.DRONE){
            setActive(false);
            ItemStack droneStack = inventory.getDrone();
            if(droneStack.getItem() instanceof BeeItem && ((BeeItem) droneStack.getItem()).getBeeType() == BeeTypes.DRONE){
                errorState = ErrorStates.NONE;
                return true;
            }
            errorState = ErrorStates.NO_DRONE;
            return false; // Has drone to mate
        }
        else if(type == BeeTypes.QUEEN){
            Optional<Bee> queenOptional = BeeProviderCapability.get(queenStack).getBeeIndividual();
            if(queenOptional.isEmpty()){
                errorState = ErrorStates.ILLEGAL_STATE;
                currentQueen = null;
                return false; // Invalid bee individual (exceptional)
            }
            if(!isQueenAlive()){
                queenOptional.ifPresent(bee -> pendingStack.addAll(bee.getOffspring(this)));
                inventory.getQueenSlot().extractItem(0, 1, false);
                processStorage.clear();
                processStorage.setCapacity(0);
                currentQueen = null;
                errorState = ErrorStates.NO_QUEEN;
                return false; // Queen dead
            }
            if(currentQueen == null || !currentQueen.isGeneticEqual(queenOptional.get())){
                currentQueen = queenOptional.get();
                processStorage.setCapacity(currentQueen.getMaxHealth());
                processStorage.clear();
                processStorage.modify(currentQueen.getHealth());
                flowersCache.onNewQueen(this, currentQueen);
                canWorkCache.clear();
            }
            // Check for bee working condition
            boolean canWork =  canWorkCache.queenCanWork(currentQueen, this);
            // Check for flowers
            flowersCache.update(this, currentQueen);
            boolean hasFlower = flowersCache.getFlowersPos().size() > 0;
            boolean dirty = flowersCache.isDirty();
            if(!hasFlower){
                // Flower error comes first
                errorState = ErrorStates.NO_FLOWER;
            }
            if(isActive && dirty){
                onStateUpdate();
            }
            return hasFlower && canWork;
        }
        errorState = ErrorStates.ILLEGAL_STATE;
        return false;
    }

    protected void doWork(){
        if(inventory.getQueen().isEmpty()){
            isBreeding = false;
            return;
        }
        if(inventory.getQueen().getItem() instanceof BeeItem beeItem){
            if(beeItem.getBeeType() == BeeTypes.DRONE){
                tickBreed();
            }
            else if(beeItem.getBeeType() == BeeTypes.QUEEN){
                isBreeding = false;
                setActive(true);
                tickWork();
            }
        }
    }

    protected boolean addPendingProducts(){
        boolean hasSpace = true;
        while(!pendingStack.isEmpty()){
            ItemStack next = pendingStack.pop();
            next = inventory.tryAddItemOutput(next);
            if(!next.isEmpty()){
                pendingStack.push(next);
                hasSpace = false;
                break;
            }
        }
        return hasSpace;
    }

    protected void doProduction(){
        if(currentQueen != null && level != null){
            boolean active = level.random.nextBoolean();
            Alleles.Species species = active ? currentQueen.getGenome().getSpecies() : currentQueen.getGenome().getInactiveSpecies();
            boolean special = currentQueen.canProduceSpecial(this, active);

            float chanceBase = applyProductivityModifier(currentQueen.getGenome().getProductivity());
            int count = (int) Math.floor(chanceBase);
            float chance = chanceBase - count;

            if(level.random.nextFloat() < chance){
                count++;
            }
            for(int i = 0; i < count; i++){
                inventory.addFrameProduct(species, special);
            }
        }
    }

    protected boolean isQueenAlive(){
        if(inventory.getQueen().isEmpty()){
            return false;
        }
        CompoundTag tag = inventory.getQueen().getTag();
        if(tag == null){
            return false;
        }
        CompoundTag beeTag = tag.getCompound(Tags.TAG_BEE);
        return beeTag.getInt(Tags.TAG_HEALTH) > 0;

    }
    protected void tickBreed(){
        ItemStack princessStack = inventory.getQueen();
        ItemStack droneStack = inventory.getDrone();
        if(princessStack.getItem() instanceof BeeItem beeItem){
            if(beeItem.getBeeType() != BeeTypes.DRONE){
                processStorage.clear();
                processStorage.setCapacity(0);
                return;
            }
            processStorage.setCapacity(MAX_BREEDING_PROGRESS);
            isBreeding = true;
            if(!processStorage.isFull()){
                processStorage.modify(1);
            }
            if(processStorage.isFull()){
                Optional<Bee> princessOptional = BeeProviderCapability.get(princessStack).getBeeIndividual();
                Optional<Bee> droneOptional = BeeProviderCapability.get(droneStack).getBeeIndividual();
                if(princessOptional.isEmpty() || droneOptional.isEmpty()){
                    return;
                }
                Bee princess = princessOptional.get();
                Bee drone = droneOptional.get();
                princess.mate(drone.getGenome());
                ItemStack queenStack = new ItemStack(Registries.ITEMS.get("bee_queen"));
                BeeProviderCapability.get(queenStack).setBeeIndividual(princess);
                inventory.getQueenSlot().setItemStack(queenStack);
                inventory.getDroneSlot().extractItem(1, 1, false);
                processStorage.setCapacity(princess.getMaxHealth());
                processStorage.modify(princess.getHealth());
                isBreeding = false;
            }
        }
    }
    protected void tickWork(){
        Optional<Bee> queenOptional = BeeProviderCapability.get(inventory.getQueen()).getBeeIndividual();
        if(queenOptional.isEmpty()){
            processStorage.clear();
            processStorage.setCapacity(0);
            return;
        }
        tickThrottle++;
        if(tickThrottle >= TICK_PERIOD){
            tickThrottle = 0;
            Bee queen = queenOptional.get();
            doProduction();
            queen.age(inventory.applyLifespanModifier(1));
            BeeProviderCapability.get(inventory.getQueen()).setBeeIndividual(queen);
            markChunkUnsaved();
        }
        processStorage.setCapacity(queenOptional.get().getMaxHealth());
        processStorage.clear();
        processStorage.modify(queenOptional.get().getHealth());
    }
    // endregion

    // region NBTTag
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        isActive = nbt.getBoolean(Tags.TAG_STATE);
        tickThrottle = nbt.getInt(Tags.TAG_THROTTLE);
        processStorage.deserializeNBT(nbt);
        inventory.read(nbt);
        ListTag list = nbt.getList(Tags.TAG_PENDING_PRODUCT, Tag.TAG_COMPOUND);
        for(int i = 0; i < list.size(); i++){
            pendingStack.add(ItemStack.of(list.getCompound(i)));
        }
        errorState = ErrorStates.values()[nbt.getInt(Tags.TAG_ERROR_STATE)];
        flowersCache.deserializeNBT(nbt);
        if(isActive){
            TileStatePacket.sendToClient(this);
        }
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putBoolean(Tags.TAG_STATE, isActive);
        nbt.putInt(Tags.TAG_THROTTLE, tickThrottle);
        processStorage.writeToTag(nbt);
        inventory.write(nbt);
        Stack<ItemStack> copy = new Stack<>();
        copy.addAll(pendingStack);
        ListTag list = new ListTag();
        while(!copy.isEmpty()){
            CompoundTag itemTag = new CompoundTag();
            copy.pop().save(itemTag);
            list.add(itemTag);
        }
        nbt.put(Tags.TAG_PENDING_PRODUCT, list);
        nbt.putInt(Tags.TAG_ERROR_STATE, errorState.ordinal());
        flowersCache.writeToTag(nbt);
    }

    @Override
    public ItemStack createItemStackTag(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTagElement(TAG_BLOCK_ENTITY);
        nbt.putBoolean(Tags.TAG_STATE, false);
        nbt.putInt(Tags.TAG_THROTTLE, tickThrottle);
        processStorage.writeToTag(nbt);
        inventory.write(nbt);
        Stack<ItemStack> copy = new Stack<>();
        copy.addAll(pendingStack);
        ListTag list = new ListTag();
        while(!copy.isEmpty()){
            CompoundTag itemTag = new CompoundTag();
            copy.pop().save(itemTag);
            list.add(itemTag);
        }
        nbt.put(Tags.TAG_PENDING_PRODUCT, list);
        nbt.putInt(Tags.TAG_ERROR_STATE, errorState.ordinal());
        //flowersCache.writeToTag(nbt);
        return super.createItemStackTag(stack);
    }
    // endregion

    // region ITickable
    @Override
    public void tickServer() {
        updateClimateState();

        if(canWork()){
            doWork();
        }
        else{
            setActive(false);
        }
    }

    @Override
    public void tickClient(){
        if(isActive && !Minecraft.getInstance().isPaused() && currentQueen != null){
            //Apicraft.LOGGER.debug("Client tick: " + clientTick);
            if(clientTick % 4 == 0){
                ParticleRenderer.addBeeHiveFX(this, currentQueen.getGenome(), flowersCache.getFlowersPos());
            }
            if(clientTick% 50 == 3 && level != null){
                doPollenFX(level, pos().getX(), pos().getY(), pos().getZ());
            }
        }
        clientTick++;
        clientTick %= CLIENT_TICK_PERIOD;
    }

    @OnlyIn(Dist.CLIENT)
    public static void doPollenFX(Level world, double x, double y, double z) {
        double fxX = x + 0.5F;
        double fxY = y + 0.25F;
        double fxZ = z+ 0.5F;
        float distanceFromCenter = 0.6F;
        float leftRightSpreadFromCenter = distanceFromCenter * (world.random.nextFloat() - 0.5F);
        float upSpread = world.random.nextFloat() * 6F / 16F;
        fxY += upSpread;

        ParticleRenderer.addEntityHoneyDustFX(world, fxX - distanceFromCenter, fxY, fxZ + leftRightSpreadFromCenter);
        ParticleRenderer.addEntityHoneyDustFX(world, fxX + distanceFromCenter, fxY, fxZ + leftRightSpreadFromCenter);
        ParticleRenderer.addEntityHoneyDustFX(world, fxX + leftRightSpreadFromCenter, fxY, fxZ - distanceFromCenter);
        ParticleRenderer.addEntityHoneyDustFX(world, fxX + leftRightSpreadFromCenter, fxY, fxZ + distanceFromCenter);
    }
    // endregion

    // region IBeeHousing
    @Override
    public Level getBeeHousingLevel() {
        return level;
    }

    @Override
    public BlockPos getBeeHousingPos() {
        return pos();
    }

    @Override
    public Optional<Biome> getBeeHousingBiome() {
        if(biome == null){
            biome = hasLevel() ? Objects.requireNonNull(getLevel()).getBiome(pos()).get() : null;
        }
        return biome != null ? Optional.of(biome) : Optional.empty();
    }
    @Override
    public BeeHousingItemInv getBeeHousingInv() {
        return inventory;
    }

    public String getBeeHousingOwnerName(){
        return hasSecurity() ? securityControl.getOwnerName() : "_";
    }
    @Override
    public ErrorStates getErrorState(){
        return errorState;
    }
    @Override
    public void setErrorState(ErrorStates errorState){
        this.errorState = errorState;
    }

    public void clearCachedValue(){
        if (!Objects.requireNonNull(this.getLevel()).isClientSide()) {
            canWorkCache.clear();
            canWork();
            if (currentQueen!= null) {
                flowersCache.forceLookForFlowers(currentQueen, this);
            }
        }
    }

    @Override
    public Vector3d getBeeFXCoordinates() {
        return new Vector3d(getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5);
    }
    // endregion

    // region GuiPacket
    @Override
    public FriendlyByteBuf getGuiPacket(FriendlyByteBuf buffer) {
        super.getGuiPacket(buffer);
        buffer.writeInt(actualTemperature);
        buffer.writeInt(actualHumidity);
        processStorage.writeToBuffer(buffer);
        buffer.writeBoolean(isBreeding);
        buffer.writeInt(errorState.ordinal());
        return buffer;
    }

    @Override
    public void handleGuiPacket(FriendlyByteBuf buffer) {
        super.handleGuiPacket(buffer);
        actualTemperature = buffer.readInt();
        actualHumidity = buffer.readInt();
        processStorage.readFromBuffer(buffer);
        isBreeding = buffer.readBoolean();
        errorState = ErrorStates.values()[buffer.readInt()];
    }
    // endregion

    // region StatePacket
    @Override
    public FriendlyByteBuf getStatePacket(FriendlyByteBuf buffer){
        super.getStatePacket(buffer);
        buffer.writeBoolean(isActive);
        Apicraft.LOGGER.debug("Sent state packet: Active = " + isActive);
        if(isActive){
            buffer.writeItem(inventory.getQueen());
            flowersCache.writeToNetwork(buffer);
        }
        return buffer;
    }

    @Override
    public void handleStatePacket(FriendlyByteBuf buffer){
        super.handleStatePacket(buffer);
        isActive = buffer.readBoolean();
        Apicraft.LOGGER.debug("Received state packet: Active = " + isActive);
        if(isActive){
            ItemStack queenStack = buffer.readItem();
            Apicraft.LOGGER.debug("Received item stack: " + queenStack);
            currentQueen = BeeProviderCapability.get(queenStack).getBeeIndividual().orElse(null);
            if(currentQueen != null){
                Apicraft.LOGGER.debug("Received bee: " + currentQueen.writeToTag(new CompoundTag()).toString());
            }
            flowersCache.fromNetwork(buffer);
        }

    }
    // endregion

    // region ITileCallback

    @Override
    public void onInventoryChanged(int slot) {
        if (inventory.isAugmentSlot(slot)) {
            //updateAugmentState();
        }
        markChunkUnsaved();
    }
    @Override
    public void onControlUpdate() {
        callNeighborStateChange();
        TileControlPacket.sendToClient(this);
        markChunkUnsaved();
    }

    public void onStateUpdate() {
        callNeighborStateChange();
        TileStatePacket.sendToClient(this);
        markChunkUnsaved();
    }
    // endregion

    // region IClimateProvider
    protected void tryUpdateBiome(){
        if(biome == null){
            biome = hasLevel() ? Objects.requireNonNull(getLevel()).getBiome(pos()).get() : null;
        }
        else{
            this.baseTemperature = (int) Math.floor(biome.getBaseTemperature() * 100);
            this.baseHumidity = (int) Math.floor(biome.getDownfall() * 100);
        }
    }

    protected void updateClimateState(){
        tryUpdateBiome();
        this.actualTemperature = this.applyTemperatureModifier(baseTemperature);
        this.actualHumidity = this.applyHumidityModifier(baseHumidity);
    }

    @Override
    public int getExactTemperature() {
        return actualTemperature;
    }

    @Override
    public int getExactHumidity() {
        return actualHumidity;
    }
    // endregion

    // region IBeeBreeder

    @Override
    public BreedingProcessStorage getBreedingProcess() {
        return processStorage;
    }

    // endregion

    // region Augments
    @Override
    public float applyProductivityModifier(float val) {
        return inventory.applyProductivityModifier(val);
    }

    @Override
    public int applyLifespanModifier(int val) {
        return inventory.applyLifespanModifier(val);
    }

    @Override
    public float applyMutationModifier(float val) {
        return inventory.applyMutationModifier(val);
    }

    @Override
    public Vec3i applyTerritoryModifier(Vec3i val) {
        return inventory.applyTerritoryModifier(val);
    }

    @Override
    public int applyFertilityModifier(int val) {
        return inventory.applyFertilityModifier(val);
    }

    @Override
    public int applyTemperatureModifier(int val) {
        return val;
    }

    @Override
    public int applyHumidityModifier(int val) {
        return val;
    }
    // endregion

    protected static class QueenCanWorkCache {
        private static final int ticksPerCheckQueenCanWork = 10;

        private boolean queenCanWorkCached = false;
        private int queenCanWorkCooldown = 0;

        public boolean queenCanWork(Bee queen, IBeeHousing beeHousing) {
            if (queenCanWorkCooldown <= 0) {
                queenCanWorkCached = queen.canWork(beeHousing);
                queenCanWorkCooldown = ticksPerCheckQueenCanWork;
            } else {
                queenCanWorkCooldown--;
            }

            return queenCanWorkCached;
        }

        public void clear() {
            queenCanWorkCached = false;
            queenCanWorkCooldown = 0;
        }
    }
}
