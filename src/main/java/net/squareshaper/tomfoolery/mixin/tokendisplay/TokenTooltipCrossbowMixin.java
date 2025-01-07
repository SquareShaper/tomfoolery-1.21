package net.squareshaper.tomfoolery.mixin.tokendisplay;

import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.squareshaper.tomfoolery.item.TokenItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CrossbowItem.class)
public class TokenTooltipCrossbowMixin {
    @Inject(at = @At("HEAD"), method = "appendTooltip")
    private void appendTokenTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo info) {
        List<Text> tooltipsToAdd = TokenItem.doTokenTooltip(stack);
        tooltip.addAll(tooltipsToAdd);
    }
}
