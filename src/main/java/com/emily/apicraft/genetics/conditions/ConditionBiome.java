package com.emily.apicraft.genetics.conditions;

import com.emily.apicraft.block.beehouse.IBeeHousing;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Supplier;

public class ConditionBiome implements IBeeCondition{
    Set<ResourceLocation> acceptedBiomeNames = new HashSet<>();
    Set<TagKey<Biome>> acceptedTags = new HashSet<>();

    public ConditionBiome(Collection<ResourceLocation> acceptedBiomeNames, Collection<TagKey<Biome>> acceptedTags){
        this.acceptedBiomeNames.addAll(acceptedBiomeNames);
        this.acceptedTags.addAll(acceptedTags);
    }

    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        Optional<Biome> biomeOptional = beeHousing.getBeeHousingBiome();
        if(biomeOptional.isEmpty()){
            return 0;
        }
        else{
            Biome biome = biomeOptional.get();
            Registry<Biome> registry = beeHousing.getBeeHousingLevel().registryAccess().registryOrThrow(Registries.BIOME);
            for(var biomeName : acceptedBiomeNames){
                Biome other = registry.get(biomeName);
                Biome modded = ForgeRegistries.BIOMES.getValue(biomeName);
                if(other == biome || modded == biome){
                    return chance;
                }
            }
            for(TagKey<Biome> biomeTagKey : acceptedTags){
                var biomeHoldersOptional = registry.getTag(biomeTagKey);
                if(biomeHoldersOptional.isPresent()){
                    var biomeHolders = biomeHoldersOptional.get();
                    for(var holder : biomeHolders){
                        if(holder.get() == biome){
                            return chance;
                        }
                    }
                }
                if(Objects.requireNonNull(ForgeRegistries.BIOMES.tags()).getTag(biomeTagKey).contains(biome)){
                    return chance;
                }
            }
        }
        return 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        List<Component> components = new ArrayList<>();
        components.add(Component.translatable("tooltip.condition.biome"));
        boolean first = true;
        if(!acceptedBiomeNames.isEmpty()){
            components.add(Component.literal("  ").append(Component.translatable("tooltip.accept_biomes").withStyle(ChatFormatting.DARK_GRAY)));
            MutableComponent component = Component.literal("   ");
            for(var b : acceptedBiomeNames){
                if(!first){
                    component.append(", ");
                }
                else {
                    first = false;
                }
                if(b == null){
                    throw new IllegalArgumentException();
                } else {
                    String namespace = b.getNamespace();
                    String path = b.getPath();
                    component.append(Component.translatable("biome." + namespace + "." + path).withStyle(ChatFormatting.YELLOW));
                }
            }
            first = true;
            components.add(component);
        }

        if(!acceptedTags.isEmpty()){
            components.add(Component.literal("  ").append(Component.translatable("tooltip.accept_biome_tags").withStyle(ChatFormatting.DARK_GRAY)));
            for(var t : acceptedTags){
                components.add(Component.literal("   #" + t.location()).withStyle(ChatFormatting.GRAY));
            }
        }
        return components;
    }

    @Override
    public IConditionType<?> getType() {
        return Conditions.REQUIRE_BIOME.get();
    }

    public Collection<ResourceLocation> getAcceptedBiomeNames() {
        return acceptedBiomeNames;
    }

    public Collection<TagKey<Biome>> getAcceptedTags(){
        return acceptedTags;
    }
}
