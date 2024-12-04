package net.squareshaper.tomfoolery.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.squareshaper.tomfoolery.Tomfoolery;

public class ModEffects {

    private static RegistryEntry<StatusEffect> registerEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Tomfoolery.id(name), statusEffect);
    }

    public static void registerModEffects() {
        Tomfoolery.LOGGER.info("Registering Status Effects for " + Tomfoolery.MOD_ID + "...");
    }
}
