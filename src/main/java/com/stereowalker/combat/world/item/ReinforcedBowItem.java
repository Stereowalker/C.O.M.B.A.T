package com.stereowalker.combat.world.item;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ReinforcedBowItem extends BowItem implements ReinforcedTool {
	private Tier tier;

	public ReinforcedBowItem(Properties builder, Tier tier) {
		super(builder.defaultDurability(384 + tier.getUses()));
		this.tier = tier;
	}

	@Override
	public int getEnchantmentValue() {
		return tier.getEnchantmentValue();
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return this.tier.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public Item getBaseItem() {
		return Items.BOW;
	}

	@Override
	public Ingredient getUpgradeItem() {
		return tier.getRepairIngredient();
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
