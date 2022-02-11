package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class IronArrow extends AbstractCustomArrow {

	public IronArrow(EntityType<? extends IronArrow> entityIn, Level worldIn) {
		super(entityIn, worldIn);
	}

	public IronArrow(Level worldIn, double x, double y, double z) {
		super(CEntityType.IRON_ARROW, worldIn, y, z, x);
		this.setBaseDamage(3.0D);
	}

	public IronArrow(Level worldIn, LivingEntity shooter) {
		super(CEntityType.IRON_ARROW, worldIn, shooter);
		this.setBaseDamage(3.0D);
	}

	@Override
	public Item arrowItem() {
		return CItems.IRON_ARROW;
	}

	@Override
	public Item tippedArrowItem() {
		return CItems.IRON_TIPPED_ARROW;
	}

}
