package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.item.CItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class WoodenArrowEntity extends AbstractCustomArrowEntity {

	public WoodenArrowEntity(EntityType<? extends WoodenArrowEntity> entityIn, World worldIn) {
		super(entityIn, worldIn);
	}

	public WoodenArrowEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.WOODEN_ARROW, worldIn, y, z, x);
		this.setDamage(1.0D);
	}

	public WoodenArrowEntity(World worldIn, LivingEntity shooter) {
		super(CEntityType.WOODEN_ARROW, worldIn, shooter);
		this.setDamage(1.0D);
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
