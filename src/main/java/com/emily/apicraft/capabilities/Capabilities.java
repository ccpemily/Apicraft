package com.emily.apicraft.capabilities;

import com.emily.apicraft.interfaces.capabilities.IBeeProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class Capabilities {
    public static final Capability<IBeeProvider> BEE_PROVIDER = CapabilityManager.get(new CapabilityToken<>(){});
}
