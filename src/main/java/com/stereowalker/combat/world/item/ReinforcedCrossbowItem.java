package com.stereowalker.combat.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class ReinforcedCrossbowItem extends CrossbowItem implements ReinforcedTool {
	private Tier tier;

	public ReinforcedCrossbowItem(Properties builder, Tier tier) {
		super(builder.defaultDurability(326 + tier.getUses()));
		this.tier = tier;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (isCharged(itemstack)) {
			performShooting(worldIn, playerIn, handIn, itemstack, func_220013_l(itemstack), 1.0F);
			setCharged(itemstack, false);
			return InteractionResultHolder.consume(itemstack);
		} else if (!playerIn.getProjectile(itemstack).isEmpty()) {
			if (!isCharged(itemstack)) {
				this.startSoundPlayed = false;
				this.midLoadSoundPlayed = false;
				playerIn.startUsingItem(handIn);
			}

			return InteractionResultHolder.consume(itemstack);
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
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
	public boolean useOnRelease(ItemStack stack) {
		return stack.is(this);
	}

	private static float func_220013_l(ItemStack p_220013_0_) {
		return p_220013_0_.getItem() instanceof ReinforcedCrossbowItem && containsChargedProjectile(p_220013_0_, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
	}

	@Override
	public Item getBaseItem() {
		return Items.CROSSBOW;
	}

	@Override
	public Ingredient getUpgradeItem() {
		return tier.getRepairIngredient();
	}

}
