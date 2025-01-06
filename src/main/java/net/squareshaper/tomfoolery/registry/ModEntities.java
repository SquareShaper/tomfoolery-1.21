package net.squareshaper.tomfoolery.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.squareshaper.tomfoolery.Tomfoolery;

public class ModEntities {
//    public static final EntityType<CustomProjectile> MAGIC_PROJECTILE = register("magic_projectile", EntityType.Builder.create(CustomProjectile::new, SpawnGroup.MISC)
//            .dimensions(1, 1));

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, Tomfoolery.id(id), type.build(id));
    }
}
