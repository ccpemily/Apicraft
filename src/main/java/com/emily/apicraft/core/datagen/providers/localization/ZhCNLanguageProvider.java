package com.emily.apicraft.core.datagen.providers.localization;

import com.emily.apicraft.Apicraft;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Locale;

import static com.mojang.logging.LogUtils.getLogger;

public class ZhCNLanguageProvider extends LanguageProvider {
    public static final HashMap<String, String> BeeTypes = new HashMap<>(){{
        put("drone", "工蜂");
        put("queen", "蜂后");
        put("larva", "幼虫");
    }};
    public static final HashMap<String, String> AlleleNames = new HashMap<>(){{

    }};
    public static final HashMap<String, String> BeeTooltips = new HashMap<>(){{

    }};
    public ZhCNLanguageProvider(DataGenerator gen, String locale) {
        super(gen, Apicraft.MODID, locale);
    }

    @Override
    protected void addTranslations() {
    }
}
