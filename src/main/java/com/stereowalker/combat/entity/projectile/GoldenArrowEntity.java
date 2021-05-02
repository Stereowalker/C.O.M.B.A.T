package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.item.CItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class GoldenArrowEntity extends AbstractCustomArrowEntity {

	public GoldenArrowEntity(EntityType<? extends GoldenArrowEntity> entityIn, World worldIn) {
		super(entityIn, worldIn);
	}

	public GoldenArrowEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.GOLDEN_ARROW, worldIn, y, z, x);
		this.setDamage(1.0D);
	}

	public GoldenArrowEntity(World worldIn, LivingEntity shooter) {
		super(CEntityType.GOLDEN_ARROW, worldIn, shooter);
		this.setDamage(1.0D);
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
