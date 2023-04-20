package com.emily.apicraft.inventory.storage;

import cofh.lib.inventory.ItemStorageCoFH;
import com.emily.apicraft.items.BeeItem;
import com.emily.apicraft.items.subtype.BeeTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemStorageBee extends ItemStorageCoFH {
    private final List<BeeTypes> filter = new ArrayList<>();

    public ItemStorageBee(){
        super((stack -> stack.getItem() instanceof BeeItem));
    }

    public ItemStorageBee(BeeTypes... types){
        super();
        filter.addAll(Arrays.asList(types));
        validator = stack -> {
            if(stack.getItem() instanceof BeeItem beeItem){
                return filter.contains(beeItem.getBeeType());
            }
            return false;
        };
    }
}
