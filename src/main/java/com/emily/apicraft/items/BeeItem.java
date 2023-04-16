package com.emily.apicraft.items;

import cofh.core.item.ItemCoFH;
import cofh.lib.api.item.IColorableItem;
import com.emily.apicraft.capabilities.BeeProviderCapability;
import com.emily.apicraft.capabilities.Capabilities;
import com.emily.apicraft.capabilities.EmptyBeeProvider;
import com.emily.apicraft.core.client.ClientSetupEvents;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.BeeGenome;
import com.emily.apicraft.genetics.Chromosomes;
import com.emily.apicraft.interfaces.capabilities.IBeeProvider;
import com.emily.apicraft.interfaces.items.IBeeItem;
import com.emily.apicraft.items.creativetab.CreativeTabs;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BeeItem extends ItemCoFH implements IBeeItem {
    private final BeeTypes type;

    public BeeItem(BeeTypes type) {
        super(type == BeeTypes.QUEEN ? new Properties().tab(CreativeTabs.TAB_BEES).stacksTo(1) : new Properties().tab(CreativeTabs.TAB_BEES));
        this.type = type;
        this.showInGroups = () -> false;
        ClientSetupEvents.addColorable(this);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack){
        Chromosomes.Species species = BeeProviderCapability.get(stack).getBeeSpeciesDirectly(true);
        return Component.translatable("chromosomes." + species.toString()).append(Component.translatable(this.getBeeType().getName()));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if(this.allowedIn(group)){
            for(Chromosomes.Species species : Chromosomes.Species.values()){
                ItemStack stack = new ItemStack(this);
                BeeProviderCapability.get(stack).setBeeIndividual(Bee.getPure(species));
                items.add(stack);
            }
        }
    }

    // region IColorableItem
    @Override
    public int getColor(ItemStack stack, int tintIndex){
        if(!stack.hasTag()){
            return tintIndex == 0 ? 0x000000 : 0xffffff;
        }
        else{
            return BeeProviderCapability.get(stack).getBeeSpeciesDirectly(true).getColor(tintIndex);
        }
    }
    // endregion

    // region IBeeInfoProvider
    @Override
    public BeeTypes getBeeType() {
        return type;
    }
    // endregion
}
