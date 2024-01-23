package com.emily.apicraft.recipes.conditions;

import com.emily.apicraft.Apicraft;
import com.emily.apicraft.genetics.conditions.ConditionHumidity;
import com.emily.apicraft.genetics.conditions.ConditionOwnerName;
import com.emily.apicraft.genetics.conditions.ConditionRequireBlock;
import com.emily.apicraft.genetics.conditions.ConditionTemperature;
import com.emily.apicraft.genetics.conditions.serializer.HumiditySerializer;
import com.emily.apicraft.genetics.conditions.serializer.RequireBlockSerializer;
import com.emily.apicraft.genetics.conditions.serializer.RequirePlayerSerializer;
import com.emily.apicraft.genetics.conditions.serializer.TemperatureSerializer;
import com.emily.apicraft.interfaces.genetics.conditions.IConditionSerializer;
import com.emily.apicraft.registry.Registries;
import net.minecraftforge.registries.RegistryObject;

public class ConditionSerializers {
    private ConditionSerializers(){}

    public static void register(){}

    public static final RegistryObject<IConditionSerializer<ConditionTemperature>> TEMPERATURE_SERIALIZER = Registries.CONDITION_SERIALIZERS.register("temperature", TemperatureSerializer::new);
    public static final RegistryObject<IConditionSerializer<ConditionHumidity>> HUMIDITY_SERIALIZER = Registries.CONDITION_SERIALIZERS.register("humidity", HumiditySerializer::new);
    public static final RegistryObject<IConditionSerializer<ConditionRequireBlock>> REQUIRE_BLOCK_SERIALIZER = Registries.CONDITION_SERIALIZERS.register("require_block", RequireBlockSerializer::new);
    public static final RegistryObject<IConditionSerializer<ConditionOwnerName>> REQUIRE_PLAYER_SERIALIZER = Registries.CONDITION_SERIALIZERS.register("require_player", RequirePlayerSerializer::new);
}
