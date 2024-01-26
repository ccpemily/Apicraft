package com.emily.apicraft.genetics.branches;

import com.emily.apicraft.genetics.alleles.IAllele;
import com.emily.apicraft.genetics.alleles.IAlleleType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Locale;

public class BeeBranch implements IBeeBranch{
    private final ResourceLocation loc;
    private final HashMap<IAlleleType, IAllele<?>> template;

    BeeBranch(ResourceLocation loc){
        this.loc = loc;
        this.template = new HashMap<>();
    }
    BeeBranch(ResourceLocation loc, BeeBranchProperties properties){
        this.loc = loc;
        this.template = properties.getTemplate();
    }

    public HashMap<IAlleleType, IAllele<?>> getTemplate(){
        return template;
    }

    public ResourceLocation location(){
        return loc;
    }
    public String getLocalizationKey(){
        return "branch." + this.loc.getPath().toLowerCase(Locale.ENGLISH);
    }
}
