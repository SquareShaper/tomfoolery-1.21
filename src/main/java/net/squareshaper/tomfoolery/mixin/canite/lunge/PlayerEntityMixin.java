package net.squareshaper.tomfoolery.mixin.canite.lunge;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    //problem is, jump is only called when on ground - I need to inject into tick
    @Inject(method = "tick", at = @At("HEAD"))
    public void tomfoolery$triggerLunge(CallbackInfo ci) {
//        PlayerEntity player = (PlayerEntity) (Object) this;
//        ItemStack boots = player.getInventory().getArmorStack(0);
//        if (boots.getItem() instanceof CaniteArmorItem) {
//            if (Tomfoolery.tryGetItemBooleanComponent(ModComponents.ABILITY_ENABLED, boots, false)) {
//                if (CaniteArmorItem.canLunge(boots, player) && this.jumping) {
//                    CaniteArmorItem.lunge(player);
//                    boots.set(ModComponents.LUNGE_ABILITY_TIMER, -1);
//                }
//                if (player.isOnGround() && this.jumping) {
//                    if (!player.getItemCooldownManager().isCoolingDown(boots.getItem())) {
//                        boots.set(ModComponents.LUNGE_ABILITY_TIMER, 200);
//                    }
//                }
//            }
//        }
    }
}


