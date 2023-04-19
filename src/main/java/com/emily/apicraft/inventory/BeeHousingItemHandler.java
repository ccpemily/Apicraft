package com.emily.apicraft.inventory;

import cofh.lib.api.IStorageCallback;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.inventory.SimpleItemInv;
import com.emily.apicraft.inventory.storage.ItemStorageBee;
import com.emily.apicraft.inventory.storage.ItemStorageFrame;
import com.emily.apicraft.items.BeeTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BeeHousingItemHandler extends SimpleItemInv {
    protected ItemStorageBee queenInv;
    protected ItemStorageBee droneInv;
    protected List<ItemStorageCoFH> outputInv;
    protected List<ItemStorageFrame> frameInv;

    public BeeHousingItemHandler(@Nullable IStorageCallback callback) {
        super(callback);
        queenInv = new ItemStorageBee(BeeTypes.QUEEN, BeeTypes.DRONE);
        droneInv = new ItemStorageBee(BeeTypes.DRONE);

    }
}
