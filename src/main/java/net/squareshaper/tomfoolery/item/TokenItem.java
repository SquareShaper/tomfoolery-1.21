package net.squareshaper.tomfoolery.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.squareshaper.tomfoolery.registry.ModComponents;

import java.util.List;

public class TokenItem extends Item {
    public TokenItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType == ClickType.LEFT) {
            ItemStack slotStack = slot.getStack();
            if (!slotStack.isEmpty()) {
                if (slotStack.isIn(ItemTags.DURABILITY_ENCHANTABLE)) {
                    stack.decrement(1);
                    Integer tokens = slotStack.get(ModComponents.TOKEN_COUNT);
                    if (tokens != null) {
                        slotStack.set(ModComponents.TOKEN_COUNT, tokens + 1);
                    } else {
                        slotStack.set(ModComponents.TOKEN_COUNT, 1);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Integer Tokens = stack.get(ModComponents.TOKEN_COUNT);
        if (Tokens != null) {
            tooltip.add(Text.literal("Tokens:" + Tokens));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    public static List<Text> doTokenTooltip(ItemStack stack) {
        List<Text> tooltips = new java.util.ArrayList<>();
        Integer Tokens = stack.get(ModComponents.TOKEN_COUNT);
        if (Tokens != null) {
            tooltips.add(Text.literal("Tokens: " + Tokens));
        }
        return tooltips;
    }
}
