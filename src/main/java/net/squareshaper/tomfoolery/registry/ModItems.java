package net.squareshaper.tomfoolery.registry;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.squareshaper.tomfoolery.Tomfoolery;
import net.squareshaper.tomfoolery.item.CanineStaffItem;
import net.squareshaper.tomfoolery.item.CaniteArmorItem;
import net.squareshaper.tomfoolery.item.MagicSwordItem;
import net.squareshaper.tomfoolery.item.TokenItem;

public class ModItems {

    public static Item TOKEN = registerItem("token", new TokenItem(new Item.Settings().maxCount(16)));
    public static Item MAGIC_SWORD = registerItem("magic_sword", new MagicSwordItem(ToolMaterials.NETHERITE,
            new Item.Settings().attributeModifiers(
                    MagicSwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 6, -2.2f))));

    // Canite stuff
    public static Item CANINE_STAFF = registerItem("canine_staff", new CanineStaffItem(new Item.Settings().maxCount(1)));
    public static Item CANITE_INGOT = registerItem("canite_ingot", new Item(new Item.Settings().maxCount(64)));
    public static Item IRON_CANITE_HELMET = registerItem("iron_canite_helmet", new CaniteArmorItem(ModArmorMaterials.IRON_CANITE,
            ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
    public static Item IRON_CANITE_CHESTPLATE = registerItem("iron_canite_chestplate", new CaniteArmorItem(ModArmorMaterials.IRON_CANITE,
            ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
    public static Item IRON_CANITE_LEGGINGS = registerItem("iron_canite_leggings", new CaniteArmorItem(ModArmorMaterials.IRON_CANITE,
            ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
    public static Item IRON_CANITE_BOOTS = registerItem("iron_canite_boots", new CaniteArmorItem(ModArmorMaterials.IRON_CANITE,
            ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));
    public static Item DIAMOND_CANITE_HELMET = registerItem("diamond_canite_helmet", new CaniteArmorItem(ModArmorMaterials.DIAMOND_CANITE,
            ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(33))));
    public static Item DIAMOND_CANITE_CHESTPLATE = registerItem("diamond_canite_chestplate", new CaniteArmorItem(ModArmorMaterials.DIAMOND_CANITE,
            ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(33))));
    public static Item DIAMOND_CANITE_LEGGINGS = registerItem("diamond_canite_leggings", new CaniteArmorItem(ModArmorMaterials.DIAMOND_CANITE,
            ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(33))));
    public static Item DIAMOND_CANITE_BOOTS = registerItem("diamond_canite_boots", new CaniteArmorItem(ModArmorMaterials.DIAMOND_CANITE,
            ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(33))));
    //15 for iron, 33 for diamond and 37 for netherite (max damage multiplier


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Tomfoolery.id(name), item);
    }

    public static void registerModItems() {
        Tomfoolery.LOGGER.info("Registering Items for " + Tomfoolery.MOD_ID + "...");
    }
}
