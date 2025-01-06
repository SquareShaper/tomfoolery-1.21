package net.squareshaper.tomfoolery.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.squareshaper.tomfoolery.registry.ModArmorMaterials;
import net.squareshaper.tomfoolery.registry.ModComponents;

import java.util.List;
import java.util.Map;

public class CaniteArmorItem extends ArmorItem {
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
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        //save in the armor, right-click on the armor to toggle (remember tooltips!)
        boolean canineRush = true;

        if (!world.isClient()) {
            if (entity instanceof PlayerEntity player) {
                if (wearingFullSuitOfArmor(player)) {
                    if (canineRush) {
                        checkArmorEffects(player);
                    }
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void checkArmorEffects(PlayerEntity player) {
        for (Map.Entry<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            RegistryEntry<ArmorMaterial> material = entry.getKey();
            List<StatusEffectInstance> effects = entry.getValue();

            if (wearingCorrectArmor(player, material)) {
                addStatusEffects(player, effects);
            }
        }
    }

    private boolean wearingFullSuitOfArmor(PlayerEntity player) {
        ItemStack helmet = player.getInventory().getArmorStack(0);
        ItemStack chestplate = player.getInventory().getArmorStack(1);
        ItemStack leggings = player.getInventory().getArmorStack(2);
        ItemStack boots = player.getInventory().getArmorStack(3);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean wearingCorrectArmor(PlayerEntity player, RegistryEntry<ArmorMaterial> material) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem helmet = (ArmorItem) player.getInventory().getArmorStack(0).getItem();
        ArmorItem chestplate = (ArmorItem) player.getInventory().getArmorStack(1).getItem();
        ArmorItem leggings = (ArmorItem) player.getInventory().getArmorStack(2).getItem();
        ArmorItem boots = (ArmorItem) player.getInventory().getArmorStack(3).getItem();

        return helmet.getMaterial() == material && chestplate.getMaterial() == material && leggings.getMaterial() == material && boots.getMaterial() == material;
    }

    private void addStatusEffects(PlayerEntity player, List<StatusEffectInstance> effects) {
        boolean hasEffect = effects.stream().allMatch(statusEffectInstance -> player.hasStatusEffect(statusEffectInstance.getEffectType()));

        if (!hasEffect) {
            for (StatusEffectInstance effect : effects) {
                player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.shouldShowParticles()));
            }
        }

        //reapply status effects if they are about to run out
        for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
            for (StatusEffectInstance effect : effects) {
                if (effectInstance.getEffectType() == effect.getEffectType()) {
                    if (effectInstance.getAmplifier() <= effect.getAmplifier()) {
                        if (effectInstance.getDuration() <= 220) {
                            player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.shouldShowParticles()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (!player.getWorld().isClient()) {
            if (clickType == ClickType.RIGHT) {
                Boolean enabled = stack.get(ModComponents.ABILITY_ENABLED);
                if (enabled != null) {
                    stack.set(ModComponents.ABILITY_ENABLED, stack.get(ModComponents.ABILITY_ENABLED));
                } else {
                    stack.set(ModComponents.ABILITY_ENABLED, true);
                }
                return true;
            }
        }

        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack superStack = super.getDefaultStack();
        superStack.set(ModComponents.ABILITY_ENABLED, false);
        return superStack;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Boolean abilityEnabled = stack.get(ModComponents.ABILITY_ENABLED);
        if (abilityEnabled != null) {
            if (abilityEnabled) {
                tooltip.add(Text.translatable("tooltip.tomfoolery.ability_enabled"));
            } else {
                tooltip.add(Text.translatable("tooltip.tomfoolery.ability_disabled").formatted(Formatting.GRAY));
            }
        } else {
            stack.set(ModComponents.ABILITY_ENABLED, false);
        }

        super.appendTooltip(stack, context, tooltip, type);
    }
}
