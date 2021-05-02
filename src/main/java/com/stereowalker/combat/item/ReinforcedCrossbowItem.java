package com.stereowalker.combat.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ReinforcedCrossbowItem extends CrossbowItem implements IReinforcedItem {
	private IItemTier tier;

	public ReinforcedCrossbowItem(Properties builder, IItemTier tier) {
		super(builder.defaultMaxDamage(326 + tier.getMaxUses()));
		this.tier = tier;
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (isCharged(itemstack)) {
			fireProjectiles(worldIn, playerIn, handIn, itemstack, func_220013_l(itemstack), 1.0F);
			setCharged(itemstack, false);
			return ActionResult.resultConsume(itemstack);
		} else if (!playerIn.findAmmo(itemstack).isEmpty()) {
			if (!isCharged(itemstack)) {
				this.isLoadingStart = false;
				this.isLoadingMiddle = false;
				playerIn.setActiveHand(handIn);
			}

			return ActionResult.resultConsume(itemstack);
		} else {
			return ActionResult.resultFail(itemstack);
		}
	}

	@Override
	public int getItemEnchantability() {
		return tier.getEnchantability()/* super.getItemEnchantability() */;
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.tier.getRepairMaterial().test(repair) || super.getIsRepairable(toRepair, repair);
	}

	@Override
	public boolean isCrossbow(ItemStack stack) {
		return true;
	}

	private static float func_220013_l(ItemStack p_220013_0_) {
		return p_220013_0_.getItem() instanceof ReinforcedCrossbowItem && hasChargedProjectile(p_220013_0_, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
	}

	@Override
	public Item getBaseItem() {
		return Items.CROSSBOW;
	}

	@Override
	public Ingredient getUpgradeItem() {
		return tier.getRepairMaterial();
	}

}
