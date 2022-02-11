package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.entity.projectile.Shuriken;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ShurikenItem extends Item {

	public ShurikenItem(Properties builder) {
		super(builder);
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		Shuriken entityShuriken = new Shuriken(worldIn, playerIn);
		entityShuriken.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 2.5F + (float)2 * 0.5F, 1.0F);
		if (playerIn.getAbilities().instabuild) {
			entityShuriken.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
		}
		itemstack.shrink(1);
		worldIn.addFreshEntity(entityShuriken);
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
	}
}