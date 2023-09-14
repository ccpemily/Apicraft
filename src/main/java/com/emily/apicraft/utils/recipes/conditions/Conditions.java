package com.emily.apicraft.utils.recipes.conditions;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.genetics.conditions.*;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionType;
import com.emily.apicraft.registry.Registries;
import net.minecraftforge.registries.RegistryObject;

public class Conditions {
    private Conditions(){}

    public static void register(){}

    public static final RegistryObject<IConditionType<ConditionTemperature>> TEMPERATURE = Registries.CONDITION_TYPES.register("temperature", () -> new ConditionType<>(Apicraft.MODID, "temperature"));
    public static final RegistryObject<IConditionType<ConditionHumidity>> HUMIDITY = Registries.CONDITION_TYPES.register("humidity", () -> new ConditionType<>(Apicraft.MODID, "humidity"));
    public static final RegistryObject<IConditionType<ConditionRequireBlock>> REQUIRE_BLOCK = Registries.CONDITION_TYPES.register("require_block", () -> new ConditionType<>(Apicraft.MODID, "require_block"));
    public static final RegistryObject<IConditionType<ConditionOwnerName>> REQUIRE_PLAYER = Registries.CONDITION_TYPES.register("require_player", () -> new ConditionType<>(Apicraft.MODID, "require_player"));
}
