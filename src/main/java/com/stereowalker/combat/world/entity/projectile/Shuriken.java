package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class Shuriken extends AbstractArrow {
	public Shuriken(EntityType<? extends Shuriken> p_i50158_1_, Level p_i50158_2_) {
		super(p_i50158_1_, p_i50158_2_);
	}

	public Shuriken(Level worldIn, LivingEntity shooter) {
		super(CEntityType.SHURIKEN, shooter, worldIn);
	}

	public Shuriken(Level worldIn, double x, double y, double z) {
		super(CEntityType.SHURIKEN, x, y, z, worldIn);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(CItems.SHURIKEN);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}