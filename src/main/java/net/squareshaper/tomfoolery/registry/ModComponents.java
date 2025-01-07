package net.squareshaper.tomfoolery.registry;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.squareshaper.tomfoolery.Tomfoolery;


public class ModComponents {

    public static final ComponentType<Integer> TOKEN_COUNT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Tomfoolery.id("token_count"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    //Armor abilities
    public static final ComponentType<Boolean> ABILITY_ENABLED = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Tomfoolery.id("ability_enabled"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Integer> ABILITY_COOLDOWN = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Tomfoolery.id("ability_cooldown"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static void registerModComponents() {
        Tomfoolery.LOGGER.info("Registering Components for " + Tomfoolery.MOD_ID + "...");
    }
}
