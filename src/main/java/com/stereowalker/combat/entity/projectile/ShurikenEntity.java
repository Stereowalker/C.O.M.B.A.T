package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.item.CItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ShurikenEntity extends AbstractArrowEntity {
	public ShurikenEntity(EntityType<? extends ShurikenEntity> p_i50158_1_, World p_i50158_2_) {
		super(p_i50158_1_, p_i50158_2_);
	}

	public ShurikenEntity(World worldIn, LivingEntity shooter) {
		super(CEntityType.SHURIKEN, shooter, worldIn);
	}

	public ShurikenEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.SHURIKEN, x, y, z, worldIn);
	}

	protected ItemStack getArrowStack() {
		return new ItemStack(CItems.SHURIKEN);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}