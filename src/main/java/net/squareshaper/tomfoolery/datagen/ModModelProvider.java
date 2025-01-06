package net.squareshaper.tomfoolery.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.squareshaper.tomfoolery.registry.ModItems;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.TOKEN, Models.GENERATED);
        itemModelGenerator.register(ModItems.MAGIC_SWORD, Models.HANDHELD);

        //Canite
        generateCaniteModels(itemModelGenerator);
    }

    private void generateCaniteModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CANITE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.CANINE_STAFF, Models.HANDHELD);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.IRON_CANITE_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.IRON_CANITE_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.IRON_CANITE_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.IRON_CANITE_BOOTS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.DIAMOND_CANITE_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.DIAMOND_CANITE_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.DIAMOND_CANITE_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.DIAMOND_CANITE_BOOTS);
    }
}
