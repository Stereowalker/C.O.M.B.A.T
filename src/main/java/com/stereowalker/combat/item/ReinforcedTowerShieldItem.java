package com.stereowalker.combat.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class ReinforcedTowerShieldItem extends ShieldCItem implements IReinforcedItem {
	private IItemTier tier;

	public ReinforcedTowerShieldItem(Properties builder, IItemTier tier) {
		super(builder.defaultMaxDamage(336 + tier.getMaxUses()));
		this.tier = tier;
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
	public Item getBaseItem() {
		return Items.SHIELD;
	}

	@Override
	public Ingredient getUpgradeItem() {
		return tier.getRepairMaterial();
	}

}
