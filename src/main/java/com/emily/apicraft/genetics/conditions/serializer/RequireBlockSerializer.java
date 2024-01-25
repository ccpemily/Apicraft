package com.emily.apicraft.genetics.conditions.serializer;

import com.emily.apicraft.genetics.conditions.ConditionRequireBlock;
import com.emily.apicraft.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.utils.JsonUtils;
import com.google.gson.JsonArray;
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

    @Override
    public JsonObject toJson(ConditionRequireBlock condition) {
        JsonObject object = new JsonObject();
        JsonObject value = new JsonObject();
        object.addProperty(JsonUtils.TYPE, condition.getType().getResourceLocation().toString());
        JsonArray blocks = new JsonArray();
        for(var b : condition.getAcceptedBlocks()){
            blocks.add(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(b)).toString());
        }
        JsonArray tags = new JsonArray();
        for(var t : condition.getAcceptedTags()){
            tags.add(t.location().toString());
        }
        value.add(JsonUtils.BLOCKS, blocks);
        value.add(JsonUtils.TAGS, tags);
        object.add(JsonUtils.VALUE, value);
        return object;
    }

    @Nullable
    @Override
    public ConditionRequireBlock fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        Set<Block> blocks = new HashSet<>();
        Set<TagKey<Block>> tags = new HashSet<>();
        int blockCount = buffer.readInt();
        for(int i = 0; i < blockCount; i++){
            ResourceLocation loc = buffer.readResourceLocation();
            Block block = ForgeRegistries.BLOCKS.getValue(loc);
            blocks.add(block);
        }
        int tagCount = buffer.readInt();
        for(int i = 0; i < blockCount; i++){
            ResourceLocation loc = buffer.readResourceLocation();
            TagKey<Block> tag = BlockTags.create(loc);
            tags.add(tag);
        }
        return new ConditionRequireBlock(blocks, tags);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionRequireBlock condition) {
        buffer.writeInt(condition.getAcceptedBlocks().size());
        condition.getAcceptedBlocks().forEach(block -> buffer.writeResourceLocation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block))));
        buffer.writeInt(condition.getAcceptedTags().size());
        condition.getAcceptedTags().forEach(tag -> buffer.writeResourceLocation(tag.location()));
    }
}
