package com.emily.apicraft.items.subtype;

import com.emily.apicraft.interfaces.genetics.IBeeModifierProvider;
import net.minecraft.core.Vec3i;

import java.util.Locale;
import java.util.function.Function;

public enum FrameTypes {
    UNTREATED(1, 10),
    IMPREGNATED(2, 15, 2.0f, 2.0f, 1.0f, 1.0f, 1),
    PROVEN(3, 20, 4.0f, 4.0f, 2.0f, 1.0f, 2);

    public final int tier;
    public final int maxUse;
    public final float productivityModifier;
    public final float lifespanModifier;
    public final float mutationModifier;
    public final float territoryModifier;
    public final int fertilityModifier;


    FrameTypes(int tier, int maxUse){
        this(tier, maxUse, 1.0f, 1.0f, 1.0f, 1.0f, 0);
    }

    FrameTypes(int tier, int maxUse, float productivityModifier, float lifespanModifier, float mutationModifier, float territoryModifier, int fertilityModifier){
        this.tier = tier;
        this.maxUse = maxUse;
        this.productivityModifier = productivityModifier;
        this.lifespanModifier = lifespanModifier;
        this.mutationModifier = mutationModifier;
        this.territoryModifier = territoryModifier;
        this.fertilityModifier = fertilityModifier;
    }

    public String getName(){
        return "frame_" + this.name().toLowerCase(Locale.ENGLISH);
    }


}
