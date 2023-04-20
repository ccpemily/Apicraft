package com.emily.apicraft.items.subtype;

import com.emily.apicraft.interfaces.genetics.IBeeModifierProvider;
import net.minecraft.core.Vec3i;

import java.util.Locale;
import java.util.function.Function;

public enum FrameTypes {
    UNTREATED(10), IMPREGNATED(15), PROVEN(20);

    public final int maxUse;
    public final float productivityModifier;
    public final float lifespanModifier;
    public final float mutationModifier;
    public final float territoryModifier;
    public final int fertilityModifier;


    FrameTypes(int maxUse){
        this(maxUse, 1.0f, 1.0f, 1.0f, 1.0f, 0);
    }

    FrameTypes(int maxUse, float productivityModifier, float lifespanModifier, float mutationModifier, float territoryModifier, int fertilityModifier){
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
