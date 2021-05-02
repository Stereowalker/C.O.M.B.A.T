package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpearEntity extends AbstractThrowableItemEntity {
	public SpearEntity(EntityType<? extends SpearEntity> p_i50148_1_, World worldIn) {
		super(p_i50148_1_, worldIn);
	}

	public SpearEntity(World worldIn, LivingEntity entityIn, ItemStack thrownStackIn) {
		super(CEntityType.SPEAR, worldIn, entityIn, thrownStackIn);
	}

	@OnlyIn(Dist.CLIENT)
	public SpearEntity(World worldIn, double velocityX, double velocityY, double velocityZ) {
		super(CEntityType.SPEAR, worldIn, velocityY, velocityZ, velocityX);
	}
}