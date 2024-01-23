package com.emily.apicraft.capabilities.empty;

import com.emily.apicraft.bee.BeeProductData;
import com.emily.apicraft.capabilities.IBeeProductContainer;

import java.util.Optional;

public class EmptyBeeProductContainer implements IBeeProductContainer {
    private static final EmptyBeeProductContainer instance = new EmptyBeeProductContainer();
    public static EmptyBeeProductContainer getInstance(){
        return instance;
    }
    @Override
    public Optional<BeeProductData> getProductData() {
        return Optional.empty();
    }

    @Override
    public void setProductData(BeeProductData data) {

    }
}
