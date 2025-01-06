package net.squareshaper.tomfoolery.datagen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.squareshaper.tomfoolery.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.MAGIC_SWORD, 1)
                .pattern("  #")
                .pattern(" # ")
                .pattern("X  ")
                .input('#', Items.QUARTZ)
                .input('X', Items.BREEZE_ROD)
                .criterion(hasItem(Items.BREEZE_ROD), conditionsFromItem(Items.BREEZE_ROD))
                .offerTo(recipeExporter);

        //Canite
        registerCaniteRecipes(recipeExporter);
    }

    private void registerCaniteRecipes(RecipeExporter recipeExporter){
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CANINE_STAFF, 1)
                .pattern("  X")
                .pattern(" # ")
                .pattern("#  ")
                .input('#', Items.BONE)
                .input('X', Items.GOLD_INGOT)
                .criterion(hasItem(Items.BONE), conditionsFromItem(Items.BONE))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.IRON_CANITE_HELMET, 1)
                .pattern("###")
                .pattern("# #")
                .input('#', ModItems.CANITE_INGOT)
                .criterion(hasItem(ModItems.CANITE_INGOT), conditionsFromItem(ModItems.CANITE_INGOT))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.IRON_CANITE_CHESTPLATE, 1)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .input('#', ModItems.CANITE_INGOT)
                .criterion(hasItem(ModItems.CANITE_INGOT), conditionsFromItem(ModItems.CANITE_INGOT))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.IRON_CANITE_LEGGINGS, 1)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .input('#', ModItems.CANITE_INGOT)
                .criterion(hasItem(ModItems.CANITE_INGOT), conditionsFromItem(ModItems.CANITE_INGOT))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.IRON_CANITE_BOOTS, 1)
                .pattern("# #")
                .pattern("# #")
                .input('#', ModItems.CANITE_INGOT)
                .criterion(hasItem(ModItems.CANITE_INGOT), conditionsFromItem(ModItems.CANITE_INGOT))
                .offerTo(recipeExporter);
    }
}
