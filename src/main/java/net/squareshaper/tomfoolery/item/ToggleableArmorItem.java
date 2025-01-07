package net.squareshaper.tomfoolery.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
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
import net.squareshaper.tomfoolery.Tomfoolery;
import net.squareshaper.tomfoolery.registry.ModComponents;

import java.util.List;

public class ToggleableArmorItem extends ArmorItem {
    public ToggleableArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            boolean enabled = Tomfoolery.tryGetItemBooleanComponent(ModComponents.ABILITY_ENABLED, stack, false);

            int cooldown = checkCooldown(stack);

            //always count down the cooldown
            if (cooldown > 0) {
                stack.set(ModComponents.ABILITY_COOLDOWN, cooldown - 1);

                // if it isn't set to be on visible cooldown (like enderpearls and chorus fruit) activate the Minecraft cooldown visuals
                if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                    player.getItemCooldownManager().set(stack.getItem(), cooldown);
                }
            } else {
                if (enabled && player.getInventory().getArmorStack(slot) == stack) {
                    switch (slot) {
                        case 3:
                            cooldown = helmetEffect(world, stack, player);
                            break;
                        case 2:
                            cooldown = chestplateEffect(world, stack, player);
                            break;
                        case 1:
                            cooldown = leggingsEffect(world, stack, player);
                            break;
                        case 0:
                            cooldown = bootsEffect(world, stack, player);
                            break;
                        default:
                            break;
                    }
                }
                stack.set(ModComponents.ABILITY_COOLDOWN, cooldown);
            }

        }

        //full suit effect?
//        if (!world.isClient()) {
//            if (entity instanceof PlayerEntity player) {
//                if (wearingFullSuitOfArmor(player)) {
//                    if (canineRush) {
//                        checkArmorEffects(player);
//                    }
//                }
//            }
//        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public int checkCooldown(ItemStack stack) {
        return Tomfoolery.tryGetItemIntegerComponent(ModComponents.ABILITY_COOLDOWN, stack, 0);
    }

    //override these - the return value should be the cooldown you want to wait until your ability activates again, or checks again - for most this will be 0 when the prerequisites aren't met, and a higher number(like 600) when it actually triggers
    public int helmetEffect(World world, ItemStack stack, PlayerEntity player) {
        return checkCooldown(stack);
    }

    public int chestplateEffect(World world, ItemStack stack, PlayerEntity player) {
        return checkCooldown(stack);
    }

    public int leggingsEffect(World world, ItemStack stack, PlayerEntity player) {
        return checkCooldown(stack);
    }

    public int bootsEffect(World world, ItemStack stack, PlayerEntity player) {
        return checkCooldown(stack);
    }

//    private void checkArmorEffects(PlayerEntity player) {
//        for (Map.Entry<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
//            RegistryEntry<ArmorMaterial> material = entry.getKey();
//            List<StatusEffectInstance> effects = entry.getValue();
//
//            if (wearingCorrectArmor(player, material)) {
//                addStatusEffects(player, effects);
//            }
//        }
//    }

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

        if (clickType == ClickType.RIGHT) {
            Boolean enabled = Tomfoolery.tryGetItemBooleanComponent(ModComponents.ABILITY_ENABLED, stack, true);
            stack.set(ModComponents.ABILITY_ENABLED, !enabled);
            return true;
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
        Boolean abilityEnabled = Tomfoolery.tryGetItemBooleanComponent(ModComponents.ABILITY_ENABLED, stack, false);
        Integer abilityCooldown = Tomfoolery.tryGetItemIntegerComponent(ModComponents.ABILITY_COOLDOWN, stack, 0);

        if (abilityEnabled) {
            tooltip.add(Text.translatable("tooltip.tomfoolery.ability_enabled"));
        } else {
            tooltip.add(Text.translatable("tooltip.tomfoolery.ability_disabled").formatted(Formatting.GRAY));
        }
        tooltip.add(Text.translatable("tooltip.tomfoolery.ability_cooldown", abilityCooldown));

        super.appendTooltip(stack, context, tooltip, type);
    }
}
