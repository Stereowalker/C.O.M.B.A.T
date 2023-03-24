package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ThrownSpear extends AbstractThrownItem {
	public ThrownSpear(EntityType<? extends ThrownSpear> p_i50148_1_, Level worldIn) {
		super(p_i50148_1_, worldIn);
	}

	public ThrownSpear(Level worldIn, LivingEntity entityIn, ItemStack thrownStackIn) {
		super(CEntityType.SPEAR, worldIn, entityIn, thrownStackIn);
	}

	@OnlyIn(Dist.CLIENT)
	public ThrownSpear(Level worldIn, double velocityX, double velocityY, double velocityZ) {
		super(CEntityType.SPEAR, worldIn, velocityY, velocityZ, velocityX);
	}
}