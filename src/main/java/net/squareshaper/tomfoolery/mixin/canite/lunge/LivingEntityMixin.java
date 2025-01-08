package net.squareshaper.tomfoolery.mixin.canite.lunge;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.squareshaper.tomfoolery.Tomfoolery;
import net.squareshaper.tomfoolery.item.CaniteArmorItem;
import net.squareshaper.tomfoolery.registry.ModComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    //problem is, jump is only called when on ground - I need to inject into tickMovement
    @Inject(method = "jump", at = @At("HEAD"))
    public void tomfoolery$triggerLunge(CallbackInfo ci) {
        LivingEntity thisObject = (LivingEntity) (Object) this;
        if (thisObject.isPlayer()) {
            PlayerEntity player = (PlayerEntity) thisObject;
            ItemStack boots = player.getInventory().getArmorStack(0);
            if (boots.getItem() instanceof CaniteArmorItem) {
                if (!player.getWorld().isClient()) {
                    if (Tomfoolery.tryGetItemBooleanComponent(ModComponents.ABILITY_ENABLED, boots, false)) {
                        if (CaniteArmorItem.canLunge(boots)) {
                            CaniteArmorItem.lunge(player);
                            boots.set(ModComponents.LUNGE_ABILITY_TIMER, -1);
                        }
                    }
                }
            }
        }
    }
}
