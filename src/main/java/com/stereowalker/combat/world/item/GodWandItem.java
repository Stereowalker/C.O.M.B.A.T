package com.stereowalker.combat.world.item;

import com.stereowalker.combat.api.world.spellcraft.Rank;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GodWandItem extends AbstractMagicCastingItem implements LegendaryGear, Vanishable {

	public GodWandItem(Properties properties) {
		super(properties, Rank.GOD, 0.5D, 0.5D);
	}
	
	@Override
	public boolean canCast(Player playerEntity, ItemStack stack) {
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return getRarity();
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		legendaryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public void legendaryAbilityTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot) {
		
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

}
