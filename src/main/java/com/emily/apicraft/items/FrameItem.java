package com.emily.apicraft.items;

import cofh.core.item.ItemCoFH;
import com.emily.apicraft.capabilities.BeeProviderCapability;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.interfaces.genetics.IBeeModifierProvider;
import com.emily.apicraft.items.creativetab.CreativeTabs;
import com.emily.apicraft.items.subtype.FrameTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

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
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if(this.allowedIn(group)){
            ItemStack stack = new ItemStack(this);
            stack.setDamageValue(this.type.maxUse);
            items.add(stack);
        }
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
