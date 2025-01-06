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
                    entries.add(ModItems.MAGIC_SWORD);
                    entries.add(ModItems.CANINE_STAFF);
                    entries.add(ModItems.CANITE_INGOT);
                    entries.add(ModItems.IRON_CANITE_HELMET);
                    entries.add(ModItems.IRON_CANITE_CHESTPLATE);
                    entries.add(ModItems.IRON_CANITE_LEGGINGS);
                    entries.add(ModItems.IRON_CANITE_BOOTS);
                    entries.add(ModItems.DIAMOND_CANITE_HELMET);
                    entries.add(ModItems.DIAMOND_CANITE_CHESTPLATE);
                    entries.add(ModItems.DIAMOND_CANITE_LEGGINGS);
                    entries.add(ModItems.DIAMOND_CANITE_BOOTS);
                    }).build());

    public static void registerModItemGroups() {
        Tomfoolery.LOGGER.info("Registering Mod Item Groups for " + Tomfoolery.MOD_ID + "...");
    }
}
