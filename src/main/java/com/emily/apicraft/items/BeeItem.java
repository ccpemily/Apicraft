package com.emily.apicraft.items;

import cofh.core.item.ItemCoFH;
import cofh.lib.api.item.IColorableItem;
import net.minecraft.world.item.ItemStack;

public class BeeItem extends ItemCoFH implements IColorableItem {
    private final BeeTypes type;

    public BeeItem(Properties builder, BeeTypes type) {
        super(builder);
        this.type = type;
    }

    // region IColorableItem
    @Override
    public int getColor(ItemStack stack, int tintIndex){
        return tintIndex == 0 ? 0x000000 : 0xffffff;
    }
    // endregion
}
