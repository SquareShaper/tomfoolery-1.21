package net.squareshaper.tomfoolery.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.squareshaper.tomfoolery.Tomfoolery;
import net.squareshaper.tomfoolery.registry.ModArmorMaterials;
import net.squareshaper.tomfoolery.registry.ModComponents;

import java.util.List;
import java.util.Map;

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
        return "tooltip.tomfoolery.waterskip_ability";
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

    @Override
    public int bootsEffect(World world, ItemStack stack, PlayerEntity player) {
        int timer = Tomfoolery.tryGetItemIntegerComponent(ModComponents.LUNGE_ABILITY_TIMER, stack, 0);
        if (timer > 0) {
            stack.set(ModComponents.LUNGE_ABILITY_TIMER, timer-1);
        }
        else if (timer < 0) {
            stack.set(ModComponents.LUNGE_ABILITY_TIMER, 0);
            return 400;
        }
        return 0;
    }

    public static boolean canLunge(ItemStack boots) {
        return Tomfoolery.tryGetItemIntegerComponent(ModComponents.LUNGE_ABILITY_TIMER, boots, 0) > 0;
    }

    public static void lunge(PlayerEntity player) {
        player.addVelocity(0, 2, 0);
    }
}
