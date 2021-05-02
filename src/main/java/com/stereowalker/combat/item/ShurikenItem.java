package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.projectile.ShurikenEntity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShurikenItem extends Item {

	public ShurikenItem(Item.Properties builder) {
		super(builder);
	}

	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		ShurikenEntity entityShuriken = new ShurikenEntity(worldIn, playerIn);
		entityShuriken.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.5F + (float)2 * 0.5F, 1.0F);
		if (playerIn.abilities.isCreativeMode) {
			entityShuriken.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
		}
		itemstack.shrink(1);
		worldIn.addEntity(entityShuriken);
		return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	}
}