package net.squareshaper.tomfoolery.entity.projectile;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.squareshaper.tomfoolery.cca.MagicProjectile;
import net.squareshaper.tomfoolery.cca.ModEntityComponents;
import org.jetbrains.annotations.Nullable;

public class CustomProjectile extends PersistentProjectileEntity {

    public CustomProjectile(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(EntityType.TRIDENT, owner, world, stack, shotFrom);
    }

    public CustomProjectile(EntityType<? extends PersistentProjectileEntity> entityEntityType, World world) {
        super(entityEntityType, world);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return Items.GLOW_BERRIES.getDefaultStack();
    }

    @Override
    protected void onHit(LivingEntity target) {
        target.addVelocity(0, 3, 0);
//        super.onHit(target);
    }

    @Override
    protected float getDragInWater() {
        return 1;
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return true;
    }

    @Override
    protected double getGravity() {
        return 0;
    }

    @Override
    public void tick() {
        MagicProjectile component = ModEntityComponents.MAGIC_PROJECTILE.get(this);
        component.decrementAge();
        int age = component.getAge();
        if (age <= 0) {
            this.kill();
            return;
        }
        super.tick();
    }

    @Override
    protected void onSwimmingStart() {
        this.setGlowing(true);
        super.onSwimmingStart();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        assert MinecraftClient.getInstance().player != null;
        World world = this.getWorld();
        if (!world.isClient()){
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
            world.createExplosion(this.getOwner(), x, y, z, 4, World.ExplosionSourceType.TNT);
        }

        this.kill();
    }


}
