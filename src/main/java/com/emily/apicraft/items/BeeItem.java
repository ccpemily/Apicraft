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
import com.emily.apicraft.items.creativetab.CreativeTabs;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class BeeItem extends ItemCoFH implements IColorableItem {
    private final BeeTypes type;

    public BeeItem(BeeTypes type) {
        super(type == BeeTypes.QUEEN ? new Properties().tab(CreativeTabs.TAB_BEES).stacksTo(1) : new Properties().tab(CreativeTabs.TAB_BEES));
        this.type = type;
        this.showInGroups = () -> true;
        ClientSetupEvents.addColorable(this);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!showInGroups.get()) {
            return;
        }
        if(this.allowedIn(group)){
            for(Chromosomes.Species species : Chromosomes.Species.values()){
                ItemStack stack = new ItemStack(this);
                IBeeProvider provider = stack.getCapability(Capabilities.BEE_PROVIDER).orElse(EmptyBeeProvider.getInstance());
                provider.setBeeIndividual(new Bee(BeeGenome.defaultGenome(species)));
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
            IBeeProvider provider = stack.getCapability(Capabilities.BEE_PROVIDER).orElse(EmptyBeeProvider.getInstance());
            return provider.getBeeSpeciesDirectly().getColor(tintIndex);
        }
    }
    // endregion

    // region CapabilityProvider
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag){
        return new BeeProviderCapability(stack);
    }
    // endregion
}
