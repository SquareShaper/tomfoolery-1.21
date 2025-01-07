package net.squareshaper.tomfoolery.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.squareshaper.tomfoolery.registry.ModArmorMaterials;

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
    public int bootsEffect(World world, ItemStack stack, PlayerEntity player) {
        if (player.isSneaking()) {
            player.addVelocity(0, 1, 0);
            return 600;
        }
        else {
            return 0;
        }
    }
}
