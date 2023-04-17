package com.emily.apicraft.items;

import cofh.core.item.ItemCoFH;
import com.emily.apicraft.items.creativetab.CreativeTabs;
import net.minecraft.world.item.Item;

public class PortableAnalyzer extends ItemCoFH {
    public PortableAnalyzer() {
        super(new Item.Properties().stacksTo(1).tab(CreativeTabs.TAB_ITEMS));
    }
}
