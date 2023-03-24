package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ObsidianArrow extends AbstractCustomArrow {

	public ObsidianArrow(EntityType<? extends ObsidianArrow> entityIn, Level worldIn) {
		super(entityIn, worldIn);
	}

	public ObsidianArrow(Level worldIn, double x, double y, double z) {
		super(CEntityType.OBSIDIAN_ARROW, worldIn, y, z, x);
		this.setBaseDamage(4.0D);
	}

	public ObsidianArrow(Level worldIn, LivingEntity shooter) {
		super(CEntityType.OBSIDIAN_ARROW, worldIn, shooter);
		this.setBaseDamage(4.0D);
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
