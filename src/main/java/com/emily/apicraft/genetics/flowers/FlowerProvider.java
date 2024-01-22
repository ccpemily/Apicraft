package com.emily.apicraft.genetics.flowers;

import com.emily.apicraft.Apicraft;
import net.minecraft.resources.ResourceLocation;

public class FlowerProvider {
    private final ResourceLocation name;

    public FlowerProvider(String name){
        this.name = new ResourceLocation(Apicraft.MOD_ID, name);
    }

    public ResourceLocation getFlowerType(){
        return name;
    }
}
