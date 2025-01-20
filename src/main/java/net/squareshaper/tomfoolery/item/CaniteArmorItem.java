package net.squareshaper.tomfoolery.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.squareshaper.tomfoolery.Tomfoolery;
import net.squareshaper.tomfoolery.registry.ModArmorMaterials;
import net.squareshaper.tomfoolery.registry.ModComponents;

import java.util.List;
import java.util.Map;

import static java.lang.Math.*;

public class CaniteArmorItem extends ToggleableArmorItem {
    public CaniteArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    private final Map<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>>()
            )
                    .put(ModArmorMaterials.IRON_CANITE, List.of(
                                    new StatusEffectInstance(StatusEffects.SPEED, 400, 0, false, false),
                                    new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false)
                            )
                    ).put(
                            ModArmorMaterials.DIAMOND_CANITE, List.of(
                                    new StatusEffectInstance(StatusEffects.SPEED, 400, 0, false, false),
                                    new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false),
                                    new StatusEffectInstance(StatusEffects.STRENGTH, 400, 0, false, false)
                            )
                    )
                    .build();

    @Override
    public String getBootsTooltip() {
        return "tooltip.tomfoolery.lunge_ability";
    }

    @Override
    public String getHelmetTooltip() {
        return "tooltip.tomfoolery.sniff_ability";
    }

    public static boolean getSniffing(ClientPlayerEntity player) {
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return helmet.getItem() instanceof CaniteArmorItem && Tomfoolery.tryGetItemBooleanComponent(ModComponents.ABILITY_ENABLED, helmet, false)
                && player.isSneaking();
    }

    public static int getSniffDistance() {
        return 3000;
    }

    @Override
    public int leggingsEffect(World world, ItemStack leggings, PlayerEntity player) {

        //did the ClientPlayerEntityMixin enable trotting? if the component isn't there, set it to 'false'
        int trotting = Tomfoolery.tryGetItemIntegerComponent(ModComponents.TROTTING_ABILITY, leggings, 0);
        //if it was set to true...
        if (trotting < 0) {
            //...and we're still sprinting
            if (player.isSprinting()) {
                //do 'anything' so we can see if it works
                float yaw = player.getHeadYaw();
                float speed = 0.06f;
                float xForce = (float) cos((yaw / 180) * PI + PI * 0.5);
                float zForce = (float) sin((yaw / 180) * PI + PI * 0.5);
                float downwardsForce = 0;
                player.addVelocity(xForce * speed, downwardsForce, zForce * speed);

                //speeds:
                //normal sprint: 15.11
                //sprint jumping: 11.89
                // 0.1f:
                //normal trot: 8.63
                //trot jumping: 4.18
                // 0.04f:
                //normal trot: 11.57
                //trot jumping: 6.68
                // 0.06f:
                //normal trot: 10.46
                //trot jumping: 5.47

            } else {
                //set it to false, if the player stopped sprinting but the ability is still toggled on
                leggings.set(ModComponents.TROTTING_ABILITY, 0);
            }
        }
        //don't go on cooldown (cooldown of 0 ticks)
        return 0;
    }

    public void setTrot(PlayerEntity player, ItemStack leggings, boolean trot) {
        if (leggings.getItem() instanceof CaniteArmorItem) {
            int trotting = Tomfoolery.tryGetItemIntegerComponent(ModComponents.TROTTING_ABILITY, leggings, 0);
            if (!player.isTouchingWater() && !player.isSubmergedInWater() && player.isSprinting()
                    && player.getHungerManager().getFoodLevel() > 6.0F && !player.isUsingItem() && !player.isFallFlying()
                    && !player.hasVehicle()) {
                if (Tomfoolery.tryGetItemBooleanComponent(ModComponents.ABILITY_ENABLED, leggings, false)) {
                    if (trot) {
                        if (trotting == 0) {
                            player.sendMessage(Text.literal("piksvin"));
                            for (int i = 0; i < 20; i++) {
                                player.getWorld().addParticle(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(),
                                        0.1, 0.1, 0.1);
                            }
                            leggings.set(ModComponents.TROTTING_ABILITY, -1);
                        }
                    }
                    if (trotting > 0) {
                        leggings.set(ModComponents.TROTTING_ABILITY, trotting - 1);
                    }
                }
            } else {
                leggings.set(ModComponents.TROTTING_ABILITY, CaniteArmorItem.getTrotWaitTime());
            }
        }
    }

    public static int getTrotWaitTime() {
        return 7;
    }

    @Override
    public String getLeggingsTooltip() {
        return "tooltip.tomfoolery.trot_ability";
    }

    @Override
    public int bootsEffect(World world, ItemStack boots, PlayerEntity player) {
        int timer = Tomfoolery.tryGetItemIntegerComponent(ModComponents.LUNGE_ABILITY_TIMER, boots, 0);
        boolean jumping = Tomfoolery.tryGetItemBooleanComponent(ModComponents.JUMPING, boots, false);
        boolean jumped = Tomfoolery.tryGetItemBooleanComponent(ModComponents.JUMPED, boots, false);
        boolean wasOnground = Tomfoolery.tryGetItemBooleanComponent(ModComponents.WAS_ON_GROUND, boots, false);


        //stolen from my mixin
        if (Tomfoolery.tryGetItemBooleanComponent(ModComponents.ABILITY_ENABLED, boots, false)) {
            if (jumping) {
                if (wasOnground) {
//                    player.sendMessage(Text.literal("starting timer..."));
                    boots.set(ModComponents.LUNGE_ABILITY_TIMER, this.getLungeTimer());

                } else if (!jumped && canLunge(boots, player)) {
                    if (player.getWorld().isClient()) {
                        player.sendMessage(Text.literal("LUNGING!!!"));
                    }
                    lunge(player);
                    boots.set(ModComponents.LUNGE_ABILITY_TIMER, -1);
                }

            }
        }

        boots.set(ModComponents.WAS_ON_GROUND, player.isOnGround());

        if (timer > 0) {
            boots.set(ModComponents.LUNGE_ABILITY_TIMER, timer - 1);
        } else if (timer < 0) {
            boots.set(ModComponents.LUNGE_ABILITY_TIMER, 0);
            player.sendMessage(Text.literal("--going on cooldown--"));
            return 200;
        }

        return 0;
    }

    public int getLungeTimer() {
        return 200;
    }

    public static boolean canLunge(ItemStack boots, PlayerEntity player) {
        return Tomfoolery.tryGetItemIntegerComponent(ModComponents.LUNGE_ABILITY_TIMER, boots, 0) > 0 && !player.getItemCooldownManager().isCoolingDown(boots.getItem());
    }

    public static void lunge(PlayerEntity player) {
        float yaw = player.getYaw();
        //north = -z
        //west = -x
        float xForce = (float) cos((yaw / 180) * PI + PI * 0.5);
        float zForce = (float) sin((yaw / 180) * PI + PI * 0.5);
        float speed = 3f;
        float downwardsForce = 0;
        player.addVelocity(xForce * speed, downwardsForce, zForce * speed);
    }

    public void setLunge(PlayerEntity player, ItemStack boots, boolean jumping) {
        boolean jumped = Tomfoolery.tryGetItemBooleanComponent(ModComponents.JUMPING, boots, false);
        boots.set(ModComponents.JUMPED, jumped);
        boots.set(ModComponents.JUMPING, jumping);
    }
}
