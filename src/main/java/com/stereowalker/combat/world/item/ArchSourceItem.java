package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.entity.projectile.ArchArrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ArchSourceItem extends Item {
	ArchType artype;
   public ArchSourceItem(Item.Properties builder, ArchType type) {
      super(builder);
      this.artype = type;
   }

   public AbstractArrow createArrow(Level world, LivingEntity livingEntity, ArchType type) {
	   ArchArrow entitytippedarrow = new ArchArrow(world, livingEntity, type);
	   return entitytippedarrow;
   }
   
   public ArchType getARType() {
	return artype;
}
   
   public enum ArchType {
	   EXPLOSIVE,
	   FLAME,
	   FREEZE,
	   TELEPORT;
   }
}