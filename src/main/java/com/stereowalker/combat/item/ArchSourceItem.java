package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.projectile.ArchArrowEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ArchSourceItem extends Item {
	ArchType artype;
   public ArchSourceItem(Item.Properties builder, ArchType type) {
      super(builder);
      this.artype = type;
   }

   public AbstractArrowEntity createArrow(World world, LivingEntity livingEntity, ArchType type) {
	   ArchArrowEntity entitytippedarrow = new ArchArrowEntity(world, livingEntity, type);
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