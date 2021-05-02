package com.stereowalker.combat.item;

import com.stereowalker.combat.api.spell.Rank;

import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GodWandItem extends AbstractMagicCastingItem implements ILegendaryGear, IVanishable {

	public GodWandItem(Properties properties) {
		super(properties, Rank.GOD, 0.5D, 0.5D);
	}
	
	@Override
	public boolean canCast(PlayerEntity playerEntity, ItemStack stack) {
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return getRarity();
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		legendaryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public void legendaryAbilityTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot) {
		
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

}
