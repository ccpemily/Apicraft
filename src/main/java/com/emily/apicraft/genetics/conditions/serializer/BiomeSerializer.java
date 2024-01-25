package com.emily.apicraft.genetics.conditions.serializer;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.genetics.conditions.ConditionBiome;
import com.emily.apicraft.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.utils.JsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class BiomeSerializer implements IConditionSerializer<ConditionBiome> {
    @Override
    public ConditionBiome fromJson(ResourceLocation id, JsonObject jsonObject) {
        Set<ResourceLocation> biomes = new HashSet<>();
        Set<TagKey<Biome>> tags = new HashSet<>();
        if(jsonObject.has(JsonUtils.BIOMES)){
            JsonElement elem = jsonObject.get(JsonUtils.BIOMES);
            if(elem.isJsonArray()){
                for (var item : elem.getAsJsonArray()){
                    String biomeName = item.getAsString();
                    biomes.add(new ResourceLocation(biomeName));
                }
            }
            else{
                String biomeName = elem.getAsString();
                biomes.add(new ResourceLocation(biomeName));
            }
        }
        if(jsonObject.has(JsonUtils.TAGS)){
            JsonElement elem = jsonObject.get(JsonUtils.TAGS);
            if(elem.isJsonArray()){
                for (var item : elem.getAsJsonArray()){
                    String tagName = item.getAsString();
                    TagKey<Biome> key = TagKey.create(Registries.BIOME, new ResourceLocation(tagName));
                    tags.add(key);
                }
            }
            else{
                String tagName = elem.getAsString();
                TagKey<Biome> key = TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(tagName));
                tags.add(key);
            }
        }
        if(!biomes.isEmpty() || !tags.isEmpty()){
            return new ConditionBiome(biomes, tags);
        }
        return null;
    }

    @Override
    public JsonObject toJson(ConditionBiome condition) {
        JsonObject object = new JsonObject();
        JsonObject value = new JsonObject();
        object.addProperty(JsonUtils.TYPE, condition.getType().getResourceLocation().toString());
        JsonArray biomes = new JsonArray();
        for(var b : condition.getAcceptedBiomeNames()){
            biomes.add(b.toString());
        }
        JsonArray tags = new JsonArray();
        for(var t : condition.getAcceptedTags()){
            tags.add(t.location().toString());
        }
        value.add(JsonUtils.BIOMES, biomes);
        value.add(JsonUtils.TAGS, tags);
        object.add(JsonUtils.VALUE, value);
        return object;
    }

    @Nullable
    @Override
    public ConditionBiome fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        Set<ResourceLocation> biomes = new HashSet<>();
        Set<TagKey<Biome>> tags = new HashSet<>();
        int blockCount = buffer.readInt();
        for(int i = 0; i < blockCount; i++){
            ResourceLocation loc = buffer.readResourceLocation();
            biomes.add(loc);
        }
        int tagCount = buffer.readInt();
        for(int i = 0; i < blockCount; i++){
            ResourceLocation loc = buffer.readResourceLocation();
            TagKey<Biome> tag = TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), loc);
            tags.add(tag);
        }
        return new ConditionBiome(biomes, tags);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionBiome condition) {
        buffer.writeInt(condition.getAcceptedBiomeNames().size());
        condition.getAcceptedBiomeNames().forEach(buffer::writeResourceLocation);
        buffer.writeInt(condition.getAcceptedTags().size());
        condition.getAcceptedTags().forEach(tag -> buffer.writeResourceLocation(tag.location()));
    }
}
