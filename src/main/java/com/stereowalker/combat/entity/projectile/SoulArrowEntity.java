package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SoulArrowEntity extends AbstractArrowEntity {

   public SoulArrowEntity(EntityType<? extends SoulArrowEntity> type, World worldIn) {
      super(type, worldIn);
   }

   public SoulArrowEntity(World worldIn, LivingEntity shooter) {
      super(CEntityType.SOUL_ARROW, shooter, worldIn);
   }

   public SoulArrowEntity(World worldIn, double x, double y, double z) {
      super(CEntityType.SOUL_ARROW, x, y, z, worldIn);
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(Items.AIR);
   }
   
   @Override
   public IPacket<?> createSpawnPacket() {
   		return NetworkHooks.getEntitySpawningPacket(this);
   }
}