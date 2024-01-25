package com.emily.apicraft.genetics.branches;

import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.genetics.alleles.IAlleleType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public interface IBeeBranch {
    ResourceLocation location();
    HashMap<IAlleleType, IAllele<?>> getTemplate();
    String getLocalizationKey();
}
