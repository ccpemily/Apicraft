package com.emily.apicraft.core.datagen.providers.localization;

import com.emily.apicraft.Apicraft;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;

public class ZhCNLanguageProvider extends LanguageProvider {
    public static final HashMap<String, String> BEE_TYPES = new HashMap<>(){{
        put("drone", "工蜂");
        put("queen", "蜂后");
        put("larva", "幼虫");
    }};
    public static final HashMap<String, String> ALLELE_NAMES = new HashMap<>(){{

    }};
    public static final HashMap<String, String> BEE_TOOLTIPS = new HashMap<>(){{

    }};
    public ZhCNLanguageProvider(DataGenerator gen, String locale) {
        super(gen.getPackOutput(), Apicraft.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        for(String key : BEE_TYPES.keySet()){
            add("bee.type." + key, BEE_TYPES.get(key));
        }
    }
}
