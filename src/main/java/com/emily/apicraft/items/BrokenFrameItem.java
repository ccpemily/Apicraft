package com.emily.apicraft.items;

import com.emily.apicraft.bee.BeeProductData;
import com.emily.apicraft.capabilities.implementation.BeeProductFrameCapability;
import com.emily.apicraft.items.subtype.FrameTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class BrokenFrameItem extends FrameItem{
    public BrokenFrameItem(FrameTypes type) {
        super(type, true);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Optional<BeeProductData> data =  BeeProductFrameCapability.get(stack).getProductData();
        data.ifPresent(beeProductData ->
                components.add(Component.translatable("tooltip.frame.product_stored", beeProductData.getTotalStored(), this.getType().maxUse))
        );

        components.add(Component.translatable("tooltip.bee_modifier.title"));
        components.add(Component.translatable("tooltip.bee_modifier.empty"));
    }
}
