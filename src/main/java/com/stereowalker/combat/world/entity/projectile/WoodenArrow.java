package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class WoodenArrow extends AbstractCustomArrow {

	public WoodenArrow(EntityType<? extends WoodenArrow> entityIn, Level worldIn) {
		super(entityIn, worldIn);
	}

	public WoodenArrow(Level worldIn, double x, double y, double z) {
		super(CEntityType.WOODEN_ARROW, worldIn, y, z, x);
		this.setBaseDamage(1.0D);
	}

	public WoodenArrow(Level worldIn, LivingEntity shooter) {
		super(CEntityType.WOODEN_ARROW, worldIn, shooter);
		this.setBaseDamage(1.0D);
	}

	@Override
	public Item arrowItem() {
		return CItems.WOODEN_ARROW;
	}

	@Override
	public Item tippedArrowItem() {
		return CItems.WOODEN_TIPPED_ARROW;
	}

}
