package net.squareshaper.tomfoolery;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.squareshaper.tomfoolery.datagen.ModBlockTagProvider;
import net.squareshaper.tomfoolery.datagen.ModLootTableProvider;
import net.squareshaper.tomfoolery.datagen.ModModelProvider;
import net.squareshaper.tomfoolery.datagen.ModRecipeProvider;
import net.squareshaper.tomfoolery.registry.ModRegistryDataGenerator;

public class TomfooleryDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModRegistryDataGenerator::new);
	}
}