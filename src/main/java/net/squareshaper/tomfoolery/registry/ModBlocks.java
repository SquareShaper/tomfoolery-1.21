package net.squareshaper.tomfoolery.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.squareshaper.tomfoolery.Tomfoolery;


public class ModBlocks {

    //Helper functions
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Tomfoolery.id(name), block);
    }

    private static Block registerBlockNoItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Tomfoolery.id(name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Tomfoolery.id(name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Tomfoolery.LOGGER.info("Registering Blocks for " + Tomfoolery.MOD_ID + "...");
    }
}
