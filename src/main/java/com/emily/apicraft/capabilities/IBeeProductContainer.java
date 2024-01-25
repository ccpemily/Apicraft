package com.emily.apicraft.capabilities;

import com.emily.apicraft.apiculture.beeproduct.BeeProductData;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Optional;

@AutoRegisterCapability
public interface IBeeProductContainer {
    Optional<BeeProductData> getProductData();
    void setProductData(BeeProductData data);
}
