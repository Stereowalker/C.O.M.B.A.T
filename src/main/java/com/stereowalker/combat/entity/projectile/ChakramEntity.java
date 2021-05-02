package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChakramEntity extends AbstractThrowableItemEntity {
	public ChakramEntity(EntityType<? extends ChakramEntity> p_i50148_1_, World worldIn) {
		super(p_i50148_1_, worldIn);
	}

	public ChakramEntity(World worldIn, LivingEntity entityIn, ItemStack thrownStackIn) {
		super(CEntityType.CHAKRAM, worldIn, entityIn, thrownStackIn);
	}

	@OnlyIn(Dist.CLIENT)
	public ChakramEntity(World worldIn, double velocityX, double velocityY, double velocityZ) {
		super(CEntityType.CHAKRAM, worldIn, velocityY, velocityZ, velocityX);
	}
}