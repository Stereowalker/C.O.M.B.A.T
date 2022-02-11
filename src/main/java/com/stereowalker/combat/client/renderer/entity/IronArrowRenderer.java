package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.projectile.IronArrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronArrowRenderer extends ArrowRenderer<IronArrow> {
   public static final ResourceLocation RES_ARROW = Combat.getInstance().location("textures/entity/projectiles/iron_arrow.png");

   public IronArrowRenderer(EntityRendererProvider.Context p_173964_) {
      super(p_173964_);
   }

   @Override
   public ResourceLocation getTextureLocation(IronArrow entity) {
      return RES_ARROW;
   }
}