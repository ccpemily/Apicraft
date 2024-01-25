package com.emily.apicraft.items;

import cofh.core.common.item.ItemCoFH;
import com.emily.apicraft.apiculture.beeproduct.BeeProductData;
import com.emily.apicraft.capabilities.implementation.BeeProductFrameCapability;
import com.emily.apicraft.apiculture.IBeeModifierProvider;
import com.emily.apicraft.items.subtype.FrameTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
        super(new Properties().durability(type.maxUse).setNoRepair());
        this.type = type;
        this.productivityModifier = type.productivityModifier;
        this.lifespanModifier = type.lifespanModifier;
        this.mutationModifier = type.mutationModifier;
        this.territoryModifier = type.territoryModifier;
        this.fertilityModifier = type.fertilityModifier;
    }

    protected FrameItem(FrameTypes type, boolean ignoreModifiers){
        super(new Properties().durability(type.maxUse).setNoRepair());
        this.type = type;
        if(ignoreModifiers){
            this.productivityModifier = 1.0f;
            this.lifespanModifier = 1.0f;
            this.mutationModifier = 1.0f;
            this.territoryModifier = 1.0f;
            this.fertilityModifier = 0;
        }
        else {
            this.productivityModifier = type.productivityModifier;
            this.lifespanModifier = type.lifespanModifier;
            this.mutationModifier = type.mutationModifier;
            this.territoryModifier = type.territoryModifier;
            this.fertilityModifier = type.fertilityModifier;
        }

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
        return getMaxDamage(stack) > 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Optional<BeeProductData> data =  BeeProductFrameCapability.get(stack).getProductData();
        if(getMaxDamage(stack) > 0){
            data.ifPresent(beeProductData ->
                    components.add(Component.translatable("tooltip.frame.product_stored", beeProductData.getTotalStored(), this.type.maxUse).withStyle(ChatFormatting.GOLD))
            );
        }
        List<Component> modifiers = new ArrayList<>();
        if(type.lifespanModifier != 1.0f){
            modifiers.add(Component.translatable("tooltip.bee_modifier.lifespan.mul", 1 / type.lifespanModifier).withStyle(ChatFormatting.GRAY));
        }
        if(type.productivityModifier != 1.0f){
            modifiers.add(Component.translatable("tooltip.bee_modifier.productivity.mul", type.productivityModifier).withStyle(ChatFormatting.GRAY));
        }
        if(type.fertilityModifier != 0){
            if(type.fertilityModifier > 0){
                modifiers.add(Component.translatable("tooltip.bee_modifier.fertility.add", type.fertilityModifier).withStyle(ChatFormatting.GRAY));
            }
            else {
                modifiers.add(Component.translatable("tooltip.bee_modifier.fertility.sub", -type.fertilityModifier).withStyle(ChatFormatting.GRAY));
            }
        }
        if(type.mutationModifier != 1.0f){
            modifiers.add(Component.translatable("tooltip.bee_modifier.mutation.mul", type.mutationModifier).withStyle(ChatFormatting.GRAY));
        }
        if(type.territoryModifier != 1.0f){
            modifiers.add(Component.translatable("tooltip.bee_modifier.territory.mul", type.territoryModifier).withStyle(ChatFormatting.GRAY));
        }
        if(Screen.hasShiftDown()){
            components.add(Component.translatable("tooltip.bee_modifier.title"));
            if(!modifiers.isEmpty()){
                components.addAll(modifiers);
            }
            else{
                components.add(Component.translatable("tooltip.bee_modifier.empty").withStyle(ChatFormatting.GRAY));
            }
        }
        else{
            components.add(Component.translatable("bee.tooltip.tmi").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag){
        return new BeeProductFrameCapability(stack);
    }

    // region IBeeModifierProvider
    @Override
    public float applyProductivityModifier(float val) {
        return val * this.productivityModifier;
    }

    @Override
    public float applyLifespanModifier(float val) {
        return val * this.lifespanModifier;
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
