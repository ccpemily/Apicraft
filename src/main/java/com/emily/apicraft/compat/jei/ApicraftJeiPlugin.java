package com.emily.apicraft.compat.jei;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.capabilities.implementation.BeeProviderCapability;
import com.emily.apicraft.genetics.Bee;
import com.emily.apicraft.genetics.alleles.Alleles;
import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.ItemUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@JeiPlugin
public class ApicraftJeiPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(Apicraft.MOD_ID);
    }

    @Override
    public void registerItemSubtypes(@NotNull ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(Registries.ITEMS.get(ItemUtils.BEE_DRONE_ID), BeeSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(Registries.ITEMS.get(ItemUtils.BEE_PRINCESS_ID), BeeSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(Registries.ITEMS.get(ItemUtils.BEE_QUEEN_ID), BeeSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(Registries.ITEMS.get(ItemUtils.BEE_LARVA_ID), BeeSubtypeInterpreter.INSTANCE);
    }



    private static class BeeSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
        public static final BeeSubtypeInterpreter INSTANCE = new BeeSubtypeInterpreter();

        private BeeSubtypeInterpreter(){}

        @Override
        public @NotNull String apply(@NotNull ItemStack stack, @NotNull UidContext context) {
            if(!stack.hasTag()){
                return IIngredientSubtypeInterpreter.NONE;
            }
            Bee bee = BeeProviderCapability.get(stack).getBeeIndividual().orElse(Bee.getPure(Alleles.Species.FOREST));
            return bee.getGenome().getSpecies().toString();
        }
    }
}
