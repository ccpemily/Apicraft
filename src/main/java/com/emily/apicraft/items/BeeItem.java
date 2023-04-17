package com.emily.apicraft.items;

import cofh.core.item.ItemCoFH;
import com.emily.apicraft.capabilities.BeeProviderCapability;
import com.emily.apicraft.client.ClientSetupEvents;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.Chromosomes;
import com.emily.apicraft.interfaces.items.IBeeItem;
import com.emily.apicraft.items.creativetab.CreativeTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

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
    public boolean isFoil(ItemStack stack) {
        if(!stack.hasTag()){
            return false;
        }
        else {
            Chromosomes.Species species = BeeProviderCapability.get(stack).getBeeSpeciesDirectly(true);
            return species.isFoil();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(!stack.hasTag()){
            return;
        }
        Optional<Bee> beeOptional = BeeProviderCapability.get(stack).getBeeIndividual();
        if(beeOptional.isEmpty()){
            return;
        }
        Bee bee = beeOptional.get();
        bee.analyze(); // debug
        if(bee.isAnalyzed()){
            if(!Screen.hasShiftDown()){
                bee.addTooltip(components);
            }
            else{
                components.add(Component.translatable("bee.tooltip.tmi").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
            }
        }
        else{
            components.add(Component.translatable("bee.tooltip.unknown").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, level, components, flag);
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
