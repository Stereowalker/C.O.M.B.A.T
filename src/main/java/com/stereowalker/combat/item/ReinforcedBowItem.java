package com.stereowalker.combat.item;

import net.minecraft.item.BowItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class ReinforcedBowItem extends BowItem implements IReinforcedItem{
	private IItemTier tier;

	public ReinforcedBowItem(Properties builder, IItemTier tier) {
		super(builder.defaultMaxDamage(384 + tier.getMaxUses()));
		this.tier = tier;
	}

	@Override
	public int getItemEnchantability() {
		return tier.getEnchantability();
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.tier.getRepairMaterial().test(repair) || super.getIsRepairable(toRepair, repair);
	}

	@Override
	public Item getBaseItem() {
		return Items.BOW;
	}

	@Override
	public Ingredient getUpgradeItem() {
		return tier.getRepairMaterial();
	}
	
//	@Override
//	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//		if (!worldIn.isRemote) {
//			Item randomItem = (Item) ForgeRegistries.ITEMS.getValues().toArray()[random.nextInt(ForgeRegistries.ITEMS.getValues().size())];
//			playerIn.sendMessage(new StringTextComponent(""+randomItem));
//			playerIn.addItemStackToInventory(new ItemStack(randomItem));
//		}
//		return super.onItemRightClick(worldIn, playerIn, handIn);
//	}

}
