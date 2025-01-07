package net.squareshaper.tomfoolery.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
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
        ItemStack helmet = player.getInventory().getArmorStack(0);
        if (helmet.getItem().getComponents() instanceof CaniteArmorItem && Boolean.TRUE.equals(helmet.get(ModComponents.ABILITY_ENABLED))) {
            return true;
        }
        return true;
    }
}
