package com.stereowalker.combat.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.item.ItemFilters;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;

@Mixin(Item.class)
public abstract class ItemMixin extends net.minecraftforge.registries.ForgeRegistryEntry<Item> implements IItemProvider, net.minecraftforge.common.extensions.IForgeItem {

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Overwrite
	public UseAction getUseAction(ItemStack stack) {
		if (Config.BATTLE_COMMON.swordBlocking.get() && (ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(stack) || ItemFilters.DOUBLE_EDGE_STRAIGHT_WEAPONS.test(stack))) {
			return UseAction.BLOCK;
		} else {
			return stack.getItem().isFood() ? UseAction.EAT : UseAction.NONE;
		}
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	@Overwrite
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (this.isFood()) {
			ItemStack itemstack = playerIn.getHeldItem(handIn);
			if (playerIn.canEat(this.getFood().canEatWhenFull())) {
				playerIn.setActiveHand(handIn);
				return ActionResult.resultConsume(itemstack);
			} else {
				return ActionResult.resultFail(itemstack);
			}
		} else {
			ItemStack itemstack = playerIn.getHeldItem(handIn);
			if (Config.BATTLE_COMMON.swordBlocking.get() && (ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(itemstack) || ItemFilters.DOUBLE_EDGE_STRAIGHT_WEAPONS.test(itemstack))) {
				playerIn.setActiveHand(handIn);
				return ActionResult.resultConsume(itemstack);
			} else {
				return ActionResult.resultPass(playerIn.getHeldItem(handIn));
			}
		}
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Overwrite
	public int getUseDuration(ItemStack stack) {
		if (stack.getItem().isFood()) {
			return this.getFood().isFastEating() ? 16 : 32;
		} else if (Config.BATTLE_COMMON.swordBlocking.get() && (ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(stack) || ItemFilters.DOUBLE_EDGE_STRAIGHT_WEAPONS.test(stack))) {
			return 40;
		} else {
			return 0;
		}
	}

	@Nullable
	@Shadow
	public Food getFood() {return null;}

	@Shadow
	public boolean isFood() {return true;}
}
