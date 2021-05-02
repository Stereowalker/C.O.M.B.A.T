package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.item.CItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class QuartzArrowEntity extends AbstractCustomArrowEntity {

	public QuartzArrowEntity(EntityType<? extends QuartzArrowEntity> entityIn, World worldIn) {
		super(entityIn, worldIn);
	}

	public QuartzArrowEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.QUARTZ_ARROW, worldIn, y, z, x);
		this.setDamage(2.0D);
	}

	public QuartzArrowEntity(World worldIn, LivingEntity shooter) {
		super(CEntityType.QUARTZ_ARROW, worldIn, shooter);
		this.setDamage(2.0D);
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
