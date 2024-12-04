package net.squareshaper.tomfoolery.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.squareshaper.tomfoolery.Tomfoolery;
import net.squareshaper.tomfoolery.item.TokenItem;

public class ModItems {
    public static Item TOKEN = registerItem("token", new TokenItem(new Item.Settings().maxCount(16)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Tomfoolery.id(name), item);
    }

    public static void registerModItems() {
        Tomfoolery.LOGGER.info("Registering Items for " + Tomfoolery.MOD_ID + "...");
    }
}
