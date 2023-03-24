package com.stereowalker.combat.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ReinforcedTowerShieldItem extends ShieldCItem implements ReinforcedTool {
	private Tier tier;

	public ReinforcedTowerShieldItem(Properties builder, Tier tier) {
		super(builder.defaultDurability(336 + tier.getUses()));
		this.tier = tier;
	}
	
	@Override
	public int getEnchantmentValue() {
		return tier.getEnchantmentValue()/* super.getEnchantmentValue() */;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return this.tier.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public Item getBaseItem() {
		return Items.SHIELD;
	}

	@Override
	public Ingredient getUpgradeItem() {
		return tier.getRepairIngredient();
	}

}
