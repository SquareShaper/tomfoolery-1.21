package net.squareshaper.tomfoolery.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.squareshaper.tomfoolery.registry.ModComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class TokenTooltipMixin {
    @Inject(at = @At("HEAD"), method = "appendTooltip")
    private void appendTokenTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo info) {
        Integer tokens = stack.get(ModComponents.TOKEN_COUNT);
        if (tokens != null) {
            tooltip.add(Text.literal("Tokens: " + tokens));
        }
    }
}
