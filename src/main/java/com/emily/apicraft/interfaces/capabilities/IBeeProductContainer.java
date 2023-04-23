package com.emily.apicraft.interfaces.capabilities;

import com.emily.apicraft.bee.BeeProductData;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Optional;

@AutoRegisterCapability
public interface IBeeProductContainer {
    Optional<BeeProductData> getProductData();
    void setProductData(BeeProductData data);
}
