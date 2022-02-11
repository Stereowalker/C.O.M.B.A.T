package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class SoulArrow extends AbstractArrow {

   public SoulArrow(EntityType<? extends SoulArrow> type, Level worldIn) {
      super(type, worldIn);
   }

   public SoulArrow(Level worldIn, LivingEntity shooter) {
      super(CEntityType.SOUL_ARROW, shooter, worldIn);
   }

   public SoulArrow(Level worldIn, double x, double y, double z) {
      super(CEntityType.SOUL_ARROW, x, y, z, worldIn);
   }

   @Override
   protected ItemStack getPickupItem() {
      return new ItemStack(Items.AIR);
   }
   
   @Override
   public Packet<?> getAddEntityPacket() {
   		return NetworkHooks.getEntitySpawningPacket(this);
   }
}