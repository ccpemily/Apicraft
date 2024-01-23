package com.emily.apicraft.genetics.conditions.serializer;

import com.emily.apicraft.genetics.conditions.ConditionRequireBlock;
import com.emily.apicraft.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.utils.JsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.emily.apicraft.registry.Registries.BLOCKS;

public class RequireBlockSerializer implements IConditionSerializer<ConditionRequireBlock> {
    @Override
    public ConditionRequireBlock fromJson(ResourceLocation id, JsonObject jsonObject) {
        Set<Block> blocks = new HashSet<>();
        Set<TagKey<Block>> tags = new HashSet<>();
        if(jsonObject.has(JsonUtils.BLOCKS)){
            JsonElement elem = jsonObject.get(JsonUtils.BLOCKS);
            if(elem.isJsonArray()){
                for (var item : elem.getAsJsonArray()){
                    String blockName = item.getAsString();
                    Block block = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(blockName));
                    if(block != null){
                        blocks.add(block);
                    }
                }
            }
            else{
                String blockName = elem.getAsString();
                Block block = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(blockName));
                if(block != null){
                    blocks.add(block);
                }
            }
        }
        if(jsonObject.has(JsonUtils.TAGS)){
            JsonElement elem = jsonObject.get(JsonUtils.TAGS);
            if(elem.isJsonArray()){
                for (var item : elem.getAsJsonArray()){
                    String tagName = item.getAsString();
                    TagKey<Block> key = TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation(tagName));
                    tags.add(key);
                }
            }
            else{
                String tagName = elem.getAsString();
                TagKey<Block> key = TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation(tagName));
                tags.add(key);
            }
        }
        if(!blocks.isEmpty() || !tags.isEmpty()){
            return new ConditionRequireBlock(blocks, tags);
        }
        return null;
    }

    @Nullable
    @Override
    public ConditionRequireBlock fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {

        return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionRequireBlock condition) {

    }
}
