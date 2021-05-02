package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.item.CItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ObsidianArrowEntity extends AbstractCustomArrowEntity {

	public ObsidianArrowEntity(EntityType<? extends ObsidianArrowEntity> entityIn, World worldIn) {
		super(entityIn, worldIn);
	}

	public ObsidianArrowEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.OBSIDIAN_ARROW, worldIn, y, z, x);
		this.setDamage(4.0D);
	}

	public ObsidianArrowEntity(World worldIn, LivingEntity shooter) {
		super(CEntityType.OBSIDIAN_ARROW, worldIn, shooter);
		this.setDamage(4.0D);
	}

	@Override
	public Item arrowItem() {
		return CItems.OBSIDIAN_ARROW;
	}

	@Override
	public Item tippedArrowItem() {
		return CItems.OBSIDIAN_TIPPED_ARROW;
	}

}
