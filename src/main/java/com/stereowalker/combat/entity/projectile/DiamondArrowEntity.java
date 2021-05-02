package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.item.CItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class DiamondArrowEntity extends AbstractCustomArrowEntity {

	public DiamondArrowEntity(EntityType<? extends DiamondArrowEntity> entityIn, World worldIn) {
		super(entityIn, worldIn);
	}

	public DiamondArrowEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.DIAMOND_ARROW, worldIn, y, z, x);
		this.setDamage(5.0D);
	}

	public DiamondArrowEntity(World worldIn, LivingEntity shooter) {
		super(CEntityType.DIAMOND_ARROW, worldIn, shooter);
		this.setDamage(5.0D);
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
