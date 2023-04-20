package com.emily.apicraft.items;

import cofh.core.item.ItemCoFH;
import cofh.lib.api.item.IColorableItem;
import com.emily.apicraft.client.ClientSetupEvents;
import com.emily.apicraft.items.creativetab.CreativeTabs;
import com.emily.apicraft.items.subtype.BeeCombTypes;
import net.minecraft.world.item.ItemStack;

public class BeeCombItem extends ItemCoFH implements IColorableItem {
    private final BeeCombTypes type;

    public BeeCombItem(BeeCombTypes type) {
        super(new Properties().tab(CreativeTabs.TAB_ITEMS));
        this.type = type;
        ClientSetupEvents.addColorable(this);
    }

    @Override
    public int getColor(ItemStack item, int colorIndex) {
        return type.getColor(colorIndex);
    }

    public BeeCombTypes getType(){
        return type;
    }
}
