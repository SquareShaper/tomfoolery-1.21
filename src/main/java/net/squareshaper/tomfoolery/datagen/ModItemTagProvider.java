package net.squareshaper.tomfoolery.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.squareshaper.tomfoolery.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.MAGIC_SWORD);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.MAGIC_SWORD);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.DIAMOND_CANITE_CHESTPLATE)
                .add(ModItems.IRON_CANITE_CHESTPLATE);
    }
}
