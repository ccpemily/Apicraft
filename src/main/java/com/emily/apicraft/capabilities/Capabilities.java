package com.emily.apicraft.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class Capabilities {
    public static final Capability<IBeeProvider> BEE_PROVIDER = CapabilityManager.get(new CapabilityToken<>(){});
    public static final Capability<IBeeProductContainer> PRODUCT_DATA_PROVIDER = CapabilityManager.get(new CapabilityToken<>(){});
}
