package com.stereowalker.combat.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class ReinforcedLongbowItem extends LongbowItem implements IReinforcedItem {
	private IItemTier tier;

	public ReinforcedLongbowItem(Properties builder, IItemTier tier) {
		super(builder.defaultMaxDamage(282 + tier.getMaxUses()));
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
		return CItems.LONGBOW;
	}

	@Override
	public Ingredient getUpgradeItem() {
		return tier.getRepairMaterial();
	}

}
