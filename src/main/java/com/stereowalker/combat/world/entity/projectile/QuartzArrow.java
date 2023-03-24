package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class QuartzArrow extends AbstractCustomArrow {

	public QuartzArrow(EntityType<? extends QuartzArrow> entityIn, Level worldIn) {
		super(entityIn, worldIn);
	}

	public QuartzArrow(Level worldIn, double x, double y, double z) {
		super(CEntityType.QUARTZ_ARROW, worldIn, y, z, x);
		this.setBaseDamage(2.0D);
	}

	public QuartzArrow(Level worldIn, LivingEntity shooter) {
		super(CEntityType.QUARTZ_ARROW, worldIn, shooter);
		this.setBaseDamage(2.0D);
	}

	@Override
	public Item arrowItem() {
		return CItems.QUARTZ_ARROW;
	}

	@Override
	public Item tippedArrowItem() {
		return CItems.QUARTZ_TIPPED_ARROW;
	}

}
