package net.squareshaper.tomfoolery.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.squareshaper.tomfoolery.Tomfoolery;

public class ModItemGroups {
    public static final ItemGroup TOMFOOLERY_ITEMGROUP = Registry.register(Registries.ITEM_GROUP,
            Tomfoolery.id("tomfoolery"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.TOKEN))
                    .displayName(Text.translatable("itemgroup.tomfoolery.tomfoolery"))
                    .entries((displayContext, entries) -> {
                    entries.add(ModItems.TOKEN);
                    }).build());

    public static void registerModItemGroups() {
        Tomfoolery.LOGGER.info("Registering Mod Item Groups for " + Tomfoolery.MOD_ID + "...");
    }
}
