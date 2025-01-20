package net.squareshaper.tomfoolery.cca;


import net.minecraft.entity.player.PlayerEntity;
import net.squareshaper.tomfoolery.Tomfoolery;
import net.squareshaper.tomfoolery.entity.projectile.CustomProjectile;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class ModEntityComponents implements EntityComponentInitializer {
    public static final ComponentKey<MagicProjectile> MAGIC_PROJECTILE =
            ComponentRegistry.getOrCreate(Tomfoolery.id("magic_projectile"), MagicProjectile.class);
//    public static final ComponentKey<TrotTimerComponent> TROT_TIMER =
//            ComponentRegistry.getOrCreate(Tomfoolery.id("trot_timer"), TrotTimerComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.registerFor(CustomProjectile.class, MAGIC_PROJECTILE, world -> new MagicProjectile());
//        entityComponentFactoryRegistry.registerFor(PlayerEntity.class, TROT_TIMER, world -> new TrotTimerComponent());
    }
}
