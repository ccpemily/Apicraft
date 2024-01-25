package com.emily.apicraft.items;

import cofh.core.common.item.ItemCoFH;
import com.emily.apicraft.capabilities.implementation.BeeProviderCapability;
import com.emily.apicraft.client.ClientSetupEvents;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.SpeciesData;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.items.subtype.BeeTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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
        super((type == BeeTypes.QUEEN || type == BeeTypes.PRINCESS) ? new Properties().stacksTo(1) : new Properties());
        this.type = type;
        ClientSetupEvents.addColorable(this);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack){
        Alleles.Species species = BeeProviderCapability.get(stack).getBeeSpeciesDirectly(true);
        return Component.translatable(species.getLocalizationKey()).append(Component.translatable(this.getBeeType().getName()));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        if(!stack.hasTag()){
            return false;
        }
        else {
            SpeciesData species = BeeProviderCapability.get(stack).getBeeSpeciesDirectly(true).getValue();
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
        if(Screen.hasShiftDown()){
            bee.addTooltip(components);
        }
        else{
            components.add(Component.translatable("bee.tooltip.tmi").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
        super.appendHoverText(stack, level, components, flag);
    }

    // region IColorableItem
    @Override
    public int getColor(ItemStack stack, int tintIndex){
        if(!stack.hasTag()){
            return tintIndex == 0 ? 0x000000 : 0xffffff;
        }
        else{
            return BeeProviderCapability.get(stack).getBeeSpeciesDirectly(true).getValue().getColor(tintIndex);
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
