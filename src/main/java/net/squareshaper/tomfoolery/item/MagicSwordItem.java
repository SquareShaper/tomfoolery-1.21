package net.squareshaper.tomfoolery.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.squareshaper.tomfoolery.cca.ModEntityComponents;
import net.squareshaper.tomfoolery.entity.projectile.CustomProjectile;

import java.util.List;

public class MagicSwordItem extends ToolItem {
    private static final double yeetStrength = 1;
    private static final int projectileAge = 20;
    // ^ fifty blocks reach with a speed of 3
    public MagicSwordItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(ToolComponent.Rule.ofAlwaysDropping(List.of(Blocks.BLACKSTONE), 15.0F), ToolComponent.Rule.of(BlockTags.PICKAXE_MINEABLE, 1.5F)), 1.0F, 2
        );
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int baseAttackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(
                                BASE_ATTACK_DAMAGE_MODIFIER_ID, ((float)baseAttackDamage + material.getAttackDamage()), EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Vec3d eyePos = attacker.getEyePos();
        Vec3d targetPos = target.getPos();
        Vec3d direction = eyePos.subtract(targetPos);
        direction = direction.normalize();
        direction = direction.multiply(yeetStrength);
        target.addVelocity(direction);
        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        PersistentProjectileEntity projectile = new CustomProjectile(world, user, Items.GLOW_BERRIES.getDefaultStack(),
                user.getEquippedStack(EquipmentSlot.MAINHAND));

        world.spawnEntity(projectile);
        projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0, 3, 0);
        ModEntityComponents.MAGIC_PROJECTILE.get(projectile).setAge(projectileAge);

        user.getItemCooldownManager().set(this, 20);
        return TypedActionResult.success(user.getEquippedStack(EquipmentSlot.MAINHAND), true);
    }
}
