package com.emily.apicraft.inventory.storage;

import cofh.lib.inventory.ItemStorageCoFH;
import com.emily.apicraft.items.FrameItem;

public class ItemStorageFrame extends ItemStorageCoFH {
    public ItemStorageFrame(){
        super((stack -> stack.getItem() instanceof FrameItem));
    }
}
