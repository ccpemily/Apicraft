package com.emily.apicraft.inventory.storage;

import cofh.lib.common.inventory.ItemStorageCoFH;
import com.emily.apicraft.items.FrameItem;

public class ItemStorageFrame extends ItemStorageCoFH {
    public ItemStorageFrame(int maxFrameTier){
        super((stack -> stack.getItem() instanceof FrameItem frame && frame.getType().tier <= maxFrameTier));
    }
}
