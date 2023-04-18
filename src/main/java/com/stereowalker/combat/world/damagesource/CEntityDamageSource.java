package com.stereowalker.combat.world.damagesource;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class CEntityDamageSource extends CDamageSource {
   @Nullable
   protected final Entity damageSourceEntity;
   /** Whether this EntityDamageSource is from an entity wearing Thorns-enchanted armor. */
   private boolean isThornsDamage;

   public CEntityDamageSource(String damageTypeIn, @Nullable Entity damageSourceEntityIn) {
      super(damageTypeIn);
      this.damageSourceEntity = damageSourceEntityIn;
   }

   /**
    * Sets this EntityDamageSource as originating from Thorns armor
    */
   public CEntityDamageSource setThorns() {
      this.isThornsDamage = true;
      return this;
   }

   public boolean isThorns() {
      return this.isThornsDamage;
   }

   @Override
   @Nullable
   public Entity getEntity() {
      return this.damageSourceEntity;
   }

   @Override
   public Component getLocalizedDeathMessage(LivingEntity entityLivingBaseIn) {
      ItemStack itemstack = this.damageSourceEntity instanceof LivingEntity ? ((LivingEntity)this.damageSourceEntity).getMainHandItem() : ItemStack.EMPTY;
      String s = "death.attack." + this.msgId;
      return !itemstack.isEmpty() && itemstack.hasCustomHoverName() ? Component.translatable(s + ".item", entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName(), itemstack.getDisplayName()) : Component.translatable(s, entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName());
   }

   @Override
   public boolean scalesWithDifficulty() {
      return this.damageSourceEntity != null && this.damageSourceEntity instanceof LivingEntity && !(this.damageSourceEntity instanceof Player);
   }

   @Override
   @Nullable
   public Vec3 getSourcePosition() {
      return this.damageSourceEntity != null ? this.damageSourceEntity.position() : null;
   }
}