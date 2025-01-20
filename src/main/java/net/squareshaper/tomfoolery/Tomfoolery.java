package net.squareshaper.tomfoolery;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.squareshaper.tomfoolery.item.CaniteArmorItem;
import net.squareshaper.tomfoolery.networking.NetworkingConstants;
import net.squareshaper.tomfoolery.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Tomfoolery implements ModInitializer {
	public static final String MOD_ID = "tomfoolery";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String name) {
		return Identifier.of(MOD_ID, name);
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ModBlocks.registerModBlocks();
		ModEffects.registerModEffects();
		ModFoodComponents.registerModFoodComponents();
		ModItemGroups.registerModItemGroups();
		ModItems.registerModItems();
		ModComponents.registerModComponents();

		PayloadTypeRegistry.playC2S().register(NetworkingConstants.TrotPayload.ID, NetworkingConstants.TrotPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(NetworkingConstants.LungePayload.ID, NetworkingConstants.LungePayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(NetworkingConstants.TrotPayload.ID, ((payload, context) -> {
			ItemStack leggings = context.player().getInventory().getArmorStack(1);
			if (!leggings.isEmpty() && leggings.getItem() instanceof CaniteArmorItem caniteLeggings) {
				caniteLeggings.setTrot(context.player(), leggings, payload.Trot());
			}
		}));

		ServerPlayNetworking.registerGlobalReceiver(NetworkingConstants.LungePayload.ID, ((payload, context) -> {
			ItemStack boots = context.player().getInventory().getArmorStack(0);
			if (!boots.isEmpty() && boots.getItem() instanceof CaniteArmorItem caniteLeggings) {
				caniteLeggings.setLunge(context.player(), boots, payload.Lunge());
			}
		}));
	}

	public static List<ItemEntity> getItemsNear(World world, BlockPos position, Item item, PlayerEntity user, Float range) {
		List<Entity> Entities = world.getOtherEntities(user, new Box(position).expand(range), itemEntity -> itemEntity instanceof ItemEntity);
		List<ItemEntity> itemEntities = new ArrayList<>();
		for (Entity entity:Entities) {
			if (entity instanceof ItemEntity itemEntity) {
				if (itemEntity.getStack().getItem() == item) {
					itemEntities.add(itemEntity);
				}
			}
		}
		return itemEntities;
	}

	public static Boolean tryGetItemBooleanComponent(ComponentType<Boolean> component, ItemStack stack, Boolean defaultValue) {
		Boolean enabled = stack.get(component);
		if (enabled != null) {
			return enabled;
		} else {
			stack.set(component, defaultValue);
			return defaultValue;
		}
	}

	public static Integer tryGetItemIntegerComponent(ComponentType<Integer> component, ItemStack stack, Integer defaultValue) {
		Integer number = stack.get(component);
		if (number != null) {
			return number;
		} else {
			stack.set(component, defaultValue);
			return defaultValue;
		}
	}
}