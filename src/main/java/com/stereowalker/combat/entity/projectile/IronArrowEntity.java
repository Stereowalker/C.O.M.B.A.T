package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.item.CItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class IronArrowEntity extends AbstractCustomArrowEntity {

	public IronArrowEntity(EntityType<? extends IronArrowEntity> entityIn, World worldIn) {
		super(entityIn, worldIn);
	}

	public IronArrowEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.IRON_ARROW, worldIn, y, z, x);
		this.setDamage(3.0D);
	}

	public IronArrowEntity(World worldIn, LivingEntity shooter) {
		super(CEntityType.IRON_ARROW, worldIn, shooter);
		this.setDamage(3.0D);
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
