package com.emily.apicraft.block.entity;

import cofh.core.block.entity.SecurableBlockEntity;
import cofh.core.network.packet.client.TileControlPacket;
import cofh.core.util.control.RedstoneControlModule;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.lib.inventory.ItemStorageCoFH;
import com.emily.apicraft.capabilities.BeeProviderCapability;
import com.emily.apicraft.client.gui.elements.BreedingProcessStorage;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.inventory.BeeHousingItemInv;
import com.emily.apicraft.items.BeeItem;
import com.emily.apicraft.items.subtype.BeeTypes;
import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.Tags;
import net.minecraft.core.BlockPos;
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

import java.util.*;

import static cofh.lib.util.constants.NBTTags.TAG_BLOCK_ENTITY;

public abstract class AbstractBeeHousingBlockEntity extends SecurableBlockEntity
        implements IBeeHousing, ITickableTile.IServerTickable, ITickableTile.IClientTickable, MenuProvider {
    // region Fields
    // Constants
    protected static final int MAX_BREEDING_PROGRESS = 100;
    protected static final int TICK_PERIOD = 600;
    // Inventory
    protected final BeeHousingItemInv inventory;
    protected Stack<ItemStack> pendingStack = new Stack<>();
    // Logic
    protected int tickThrottle = 0;
    protected BreedingProcessStorage processStorage = new BreedingProcessStorage(0, 0);
    // State
    protected boolean isActive = false;
    // Climate
    protected Biome biome = null;
    protected int baseTemperature = 0;
    protected int baseHumidity = 0;
    protected int actualTemperature = 0;
    protected int actualHumidity = 0;

    // endregion

    public AbstractBeeHousingBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state, int outputCount, int frameCount, int augmentCount) {
        super(tileEntityTypeIn, pos, state);
        inventory = new BeeHousingItemInv(this, outputCount, frameCount, augmentCount);
    }

    // region BeeKeeping
    protected boolean canWork(){
        boolean hasSpace = addPendingProducts();
        if(!hasSpace){
            return false;
        }
        ItemStack queenStack = inventory.getQueen();
        Optional<BeeTypes> typeOptional = queenStack.getItem() instanceof BeeItem ? Optional.of(((BeeItem) queenStack.getItem()).getBeeType()) : Optional.empty();
        if(typeOptional.isEmpty()){
            return false;
        }
        BeeTypes type = typeOptional.get();
        if(type == BeeTypes.DRONE){
            ItemStack droneStack = inventory.getDrone();
            return droneStack.getItem() instanceof BeeItem && ((BeeItem) droneStack.getItem()).getBeeType() == BeeTypes.DRONE;
        }
        else if(type == BeeTypes.QUEEN){
            if(!isQueenAlive()){
                // TODO add queen death logic
                Optional<Bee> dyingQueenOptional = BeeProviderCapability.get(queenStack).getBeeIndividual();
                if(dyingQueenOptional.isPresent()){

                }
                inventory.getQueenSlot().extractItem(0, 1, false);
                processStorage.clear();
                processStorage.setCapacity(0);
                return false;
            }
        }
        return true;
    }

    protected void doWork(){
        if(inventory.getQueen().isEmpty()){
            return;
        }
        if(inventory.getQueen().getItem() instanceof BeeItem beeItem){
            if(beeItem.getBeeType() == BeeTypes.DRONE){
                tickBreed();
            }
            else if(beeItem.getBeeType() == BeeTypes.QUEEN){
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

    protected boolean isQueenAlive(){
        if(inventory.getQueen().isEmpty()){
            return false;
        }
        CompoundTag tag = inventory.getQueen().getTag();
        if(tag == null){
            return false;
        }
        CompoundTag beeTag = tag.getCompound(Tags.TAG_BEE);
        if(beeTag == null){
            return false;
        }
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

            queen.age(inventory.applyLifespanModifier(10));
            BeeProviderCapability.get(inventory.getQueen()).setBeeIndividual(queen);
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
    }

    @Override
    public ItemStack createItemStackTag(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTagElement(TAG_BLOCK_ENTITY);
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
    }

    @Override
    public void tickClient(){

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
    // endregion

    // region GuiPacket
    @Override
    public FriendlyByteBuf getGuiPacket(FriendlyByteBuf buffer) {
        super.getGuiPacket(buffer);
        buffer.writeInt(actualTemperature);
        buffer.writeInt(actualHumidity);
        processStorage.writeToBuffer(buffer);
        return buffer;
    }

    @Override
    public void handleGuiPacket(FriendlyByteBuf buffer) {
        super.handleGuiPacket(buffer);
        actualTemperature = buffer.readInt();
        actualHumidity = buffer.readInt();
        processStorage.readFromBuffer(buffer);
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
        this.actualTemperature = baseTemperature;
        this.actualHumidity = baseHumidity;
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
    // endregion
}
