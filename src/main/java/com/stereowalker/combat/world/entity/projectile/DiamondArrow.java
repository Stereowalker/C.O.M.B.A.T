package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class DiamondArrow extends AbstractCustomArrow {

	public DiamondArrow(EntityType<? extends DiamondArrow> entityIn, Level worldIn) {
		super(entityIn, worldIn);
	}

	public DiamondArrow(Level worldIn, double x, double y, double z) {
		super(CEntityType.DIAMOND_ARROW, worldIn, y, z, x);
		this.setBaseDamage(5.0D);
	}

	public DiamondArrow(Level worldIn, LivingEntity shooter) {
		super(CEntityType.DIAMOND_ARROW, worldIn, shooter);
		this.setBaseDamage(5.0D);
	}

	@Override
	public Item arrowItem() {
		return CItems.DIAMOND_ARROW;
	}

	@Override
	public Item tippedArrowItem() {
		return CItems.DIAMOND_TIPPED_ARROW;
	}

}
