package com.emily.apicraft.items;

import cofh.core.item.ItemCoFH;
import com.emily.apicraft.bee.BeeProductData;
import com.emily.apicraft.capabilities.implementation.BeeProductContainerCapability;
import com.emily.apicraft.interfaces.capabilities.IBeeProductContainer;
import com.emily.apicraft.interfaces.genetics.IBeeModifierProvider;
import com.emily.apicraft.items.creativetab.CreativeTabs;
import com.emily.apicraft.items.subtype.FrameTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FrameItem extends ItemCoFH implements IBeeModifierProvider {
    private final FrameTypes type;
    private final float productivityModifier;
    private final float lifespanModifier;
    private final float mutationModifier;
    private final float territoryModifier;
    private final int fertilityModifier;
    public FrameItem(FrameTypes type) {
        super(new Properties().tab(CreativeTabs.TAB_ITEMS).durability(type.maxUse).setNoRepair());
        this.type = type;
        this.productivityModifier = type.productivityModifier;
        this.lifespanModifier = type.lifespanModifier;
        this.mutationModifier = type.mutationModifier;
        this.territoryModifier = type.territoryModifier;
        this.fertilityModifier = type.fertilityModifier;
    }

    public FrameTypes getType() {
        return type;
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0xf5eac1;
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Optional<BeeProductData> data =  BeeProductContainerCapability.get(stack).getProductData();
        data.ifPresent(beeProductData ->
                components.add(Component.translatable("tooltip.frame.product_stored", beeProductData.getTotalStored(), this.type.maxUse))
        );
        List<Component> modifiers = new ArrayList<>();
        if(type.lifespanModifier != 1.0f){
            modifiers.add(Component.translatable("tooltip.bee_modifier.lifespan.mul", 1 / type.lifespanModifier));
        }
        if(type.productivityModifier != 1.0f){
            modifiers.add(Component.translatable("tooltip.bee_modifier.productivity.mul", type.productivityModifier));
        }
        if(type.fertilityModifier != 0){
            if(type.fertilityModifier > 0){
                modifiers.add(Component.translatable("tooltip.bee_modifier.fertility.add", type.fertilityModifier));
            }
            else {
                modifiers.add(Component.translatable("tooltip.bee_modifier.fertility.sub", -type.fertilityModifier));
            }
        }
        if(type.mutationModifier != 1.0f){
            modifiers.add(Component.translatable("tooltip.bee_modifier.mutation.mul", type.mutationModifier));
        }
        if(type.territoryModifier != 1.0f){
            modifiers.add(Component.translatable("tooltip.bee_modifier.territory.mul", type.territoryModifier));
        }
        if(!modifiers.isEmpty()){
            if(Screen.hasShiftDown()){
                components.add(Component.translatable("tooltip.bee_modifier.title"));
                components.addAll(modifiers);
            }
            else{
                components.add(Component.translatable("bee.tooltip.tmi").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
            }
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if(this.allowedIn(group)){
            ItemStack stack = new ItemStack(this);
            stack.setDamageValue(this.type.maxUse);
            IBeeProductContainer container = BeeProductContainerCapability.get(stack);
            container.setProductData(new BeeProductData(this.type.maxUse));
            items.add(stack);
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag){
        return new BeeProductContainerCapability(stack);
    }

    // region IBeeModifierProvider
    @Override
    public float applyProductivityModifier(float val) {
        return val * this.productivityModifier;
    }

    @Override
    public int applyLifespanModifier(int val) {
        return (int) Math.ceil(val * this.lifespanModifier);
    }

    @Override
    public float applyMutationModifier(float val) {
        return val * this.mutationModifier;
    }

    @Override
    public Vec3i applyTerritoryModifier(Vec3i val) {
        return new Vec3i(
                (int) Math.ceil(val.getX() * this.territoryModifier),
                (int) Math.ceil(val.getY() * this.territoryModifier),
                (int) Math.ceil(val.getZ() * this.territoryModifier)
        );
    }

    @Override
    public int applyFertilityModifier(int val) {
        return Math.max(val + this.fertilityModifier, 0);
    }
    // endregion
}
