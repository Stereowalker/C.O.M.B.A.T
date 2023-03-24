package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class GoldenArrow extends AbstractCustomArrow {

	public GoldenArrow(EntityType<? extends GoldenArrow> entityIn, Level worldIn) {
		super(entityIn, worldIn);
	}

	public GoldenArrow(Level worldIn, double x, double y, double z) {
		super(CEntityType.GOLDEN_ARROW, worldIn, y, z, x);
		this.setBaseDamage(1.0D);
	}

	public GoldenArrow(Level worldIn, LivingEntity shooter) {
		super(CEntityType.GOLDEN_ARROW, worldIn, shooter);
		this.setBaseDamage(1.0D);
	}

	@Override
	public Item arrowItem() {
		return CItems.GOLDEN_ARROW;
	}

	@Override
	public Item tippedArrowItem() {
		return CItems.GOLDEN_TIPPED_ARROW;
	}

}
