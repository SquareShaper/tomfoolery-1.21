package net.squareshaper.tomfoolery.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.squareshaper.tomfoolery.registry.ModComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ToolToDiamondMixin {
    @Inject(at = @At("HEAD"), method = "onStackClicked", cancellable = true)
    public void onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (clickType == ClickType.LEFT && stack.getItem() == Items.DIAMOND) {
            ItemStack slotStack = slot.getStack();
            if (!slotStack.isEmpty()) {
                Integer tokens = slotStack.get(ModComponents.TOKEN_COUNT);
                if (tokens != null) {
                    stack.decrement(1);
                    slotStack.setDamage(0);
                    if (tokens > 1) {
                        slotStack.set(ModComponents.TOKEN_COUNT, tokens-1);
                    }
                    else {
                        slotStack.remove(ModComponents.TOKEN_COUNT);
                    }
                    cir.setReturnValue(null);
                }
            }
        }
    }
}
