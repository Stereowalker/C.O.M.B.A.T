package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.projectile.AbstractCustomArrowEntity;
import com.stereowalker.combat.entity.projectile.DiamondArrowEntity;
import com.stereowalker.combat.entity.projectile.GoldenArrowEntity;
import com.stereowalker.combat.entity.projectile.IronArrowEntity;
import com.stereowalker.combat.entity.projectile.ObsidianArrowEntity;
import com.stereowalker.combat.entity.projectile.QuartzArrowEntity;
import com.stereowalker.combat.entity.projectile.WoodenArrowEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TieredArrowItem extends ArrowItem {
	ArrowType type;

	public TieredArrowItem(Properties builder, ArrowType typeIn) {
		super(builder);
		this.type = typeIn;
	}

	public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		AbstractCustomArrowEntity arrowentity;
		if (type == ArrowType.WOODEN) {
			arrowentity = new WoodenArrowEntity(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else if (type == ArrowType.GOLDEN) {
			arrowentity = new GoldenArrowEntity(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else if (type == ArrowType.QUARTZ) {
			arrowentity = new QuartzArrowEntity(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else if (type == ArrowType.IRON) {
			arrowentity = new IronArrowEntity(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else if (type == ArrowType.OBSIDIAN) {
			arrowentity = new ObsidianArrowEntity(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else if (type == ArrowType.DIAMOND) {
			arrowentity = new DiamondArrowEntity(worldIn, shooter);
			arrowentity.setPotionEffect(stack);
		} else {
			arrowentity = new WoodenArrowEntity(worldIn, shooter);
		}
		return arrowentity;
	}
	
	

	public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.PlayerEntity player) {
		int enchant = net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.enchantment.Enchantments.INFINITY, bow);
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
