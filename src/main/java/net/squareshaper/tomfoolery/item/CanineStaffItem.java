package net.squareshaper.tomfoolery.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.squareshaper.tomfoolery.Tomfoolery;
import net.squareshaper.tomfoolery.registry.ModItems;

import java.util.List;

public class CanineStaffItem extends Item {
    public CanineStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        if (block == Blocks.GOLD_BLOCK) {
            List<ItemEntity> ingots = Tomfoolery.getItemsNear(world, blockPos, Items.IRON_INGOT, context.getPlayer(), 1f);

            float particleVelocity = 0.1f;
            int particleCount = 10;

            if (!ingots.isEmpty()) {
                if (!world.isClient()) {
                    ItemEntity firstIngot = ingots.getFirst();
                    int ingotCount = firstIngot.getStack().getCount();
                    firstIngot.remove(Entity.RemovalReason.DISCARDED);
                    ItemEntity caniteIngot = new ItemEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 1,
                            blockPos.getZ() + 0.5, new ItemStack(ModItems.CANITE_INGOT, ingotCount));
                    caniteIngot.setVelocity(0, 1, 0);
                    world.spawnEntity(caniteIngot);
                }

                for (int i = 0; i <= particleCount; i++) {
                    world.addParticle(ParticleTypes.HAPPY_VILLAGER, blockPos.getX() + 0.5, blockPos.getY() + 1.5,
                            blockPos.getZ() + 0.5,
                            world.getRandom().nextBetween(-1, 1) * particleVelocity,
                            world.getRandom().nextBetween(-1, 1) * particleVelocity,
                            world.getRandom().nextBetween(-1, 1) * particleVelocity);
                }
                world.playSound(context.getPlayer(), blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                        SoundEvents.ENTITY_WOLF_HOWL, SoundCategory.PLAYERS);
                context.getPlayer().getItemCooldownManager().set(this, 80);
                return ActionResult.SUCCESS;
            } else {
                for (int i = 0; i <= particleCount; i++) {
                    world.addParticle(ParticleTypes.FLAME, blockPos.getX() + 0.5, blockPos.getY() + 1.5,
                            blockPos.getZ() + 0.5,
                            world.getRandom().nextBetween(-1, 1) * particleVelocity,
                            world.getRandom().nextBetween(-1, 1) * particleVelocity,
                            world.getRandom().nextBetween(-1, 1) * particleVelocity);
                }
                world.playSound(context.getPlayer(), blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                        SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS);
                return ActionResult.FAIL;
            }

        }

        return super.useOnBlock(context);
    }
}
