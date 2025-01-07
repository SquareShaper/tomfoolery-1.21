package net.squareshaper.tomfoolery.mixin.canite.sniff;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.squareshaper.tomfoolery.item.CaniteArmorItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;


//all Credits for this mixin go to MoriyaShiine's
//original: https://github.com/MoriyaShiine/enchancement/blob/main/src/main/java/moriyashiine/enchancement/mixin/enchantmenteffectcomponenttype/entityxray/client/MinecraftClientMixin.java
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @ModifyReturnValue(method = "hasOutline", at = @At("RETURN"))
    private boolean enchancement$entityXray(boolean original, Entity entity) {
        if (!original && player != null && entity instanceof LivingEntity living) {
            float distance = 10;
            //set this to true if I ever add an ability that hides your scent xD
            boolean entityIsHidden = false;
            boolean sniffing = CaniteArmorItem.getSniffing(player);
            if (entity.distanceTo(player) < distance && !entityIsHidden && !living.canSee(player) && sniffing) {
                return true;
            }
        }
        return original;
    }
}
