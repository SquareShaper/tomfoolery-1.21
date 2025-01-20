package net.squareshaper.tomfoolery.mixin.canite.trot;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.squareshaper.tomfoolery.networking.NetworkingConstants;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow
    @Final
    protected MinecraftClient client;

    @Inject(method = "tick", at = @At("HEAD"))
    public void activateTrot(CallbackInfo ci) {
        ClientPlayNetworking.send(new NetworkingConstants.TrotPayload(this.client.options.sprintKey.isPressed()));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void checkJump(CallbackInfo ci) {
        ClientPlayNetworking.send(new NetworkingConstants.LungePayload(this.client.options.jumpKey.isPressed()));
    }
}