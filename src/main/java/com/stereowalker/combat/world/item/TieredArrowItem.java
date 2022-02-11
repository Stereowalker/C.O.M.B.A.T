package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.entity.projectile.AbstractCustomArrow;
import com.stereowalker.combat.world.entity.projectile.DiamondArrow;
import com.stereowalker.combat.world.entity.projectile.GoldenArrow;
import com.stereowalker.combat.world.entity.projectile.IronArrow;
import com.stereowalker.combat.world.entity.projectile.ObsidianArrow;
import com.stereowalker.combat.world.entity.projectile.QuartzArrow;
import com.stereowalker.combat.world.entity.projectile.WoodenArrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TieredArrowItem extends ArrowItem {
	ArrowType type;

	public TieredArrowItem(Properties builder, ArrowType typeIn) {
		super(builder);
		this.type = typeIn;
	}

	@Override
	public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		AbstractCustomArrow arrowentity;
		if (type == ArrowType.WOODEN) {
			arrowentity = new WoodenArrow(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else if (type == ArrowType.GOLDEN) {
			arrowentity = new GoldenArrow(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else if (type == ArrowType.QUARTZ) {
			arrowentity = new QuartzArrow(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else if (type == ArrowType.IRON) {
			arrowentity = new IronArrow(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else if (type == ArrowType.OBSIDIAN) {
			arrowentity = new ObsidianArrow(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else if (type == ArrowType.DIAMOND) {
			arrowentity = new DiamondArrow(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else {
			arrowentity = new WoodenArrow(worldIn, shooter);
		}
		return arrowentity;
	}

	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.world.entity.player.Player player) {
		int enchant = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantments.INFINITY_ARROWS, bow);
		return enchant <= 0 ? false : this.getClass() == TieredArrowItem.class;
	}

	public enum ArrowType {
		WOODEN,
		GOLDEN,
		FLINT,
		QUARTZ,
		IRON,
		OBSIDIAN,
		DIAMOND;
	}

}
