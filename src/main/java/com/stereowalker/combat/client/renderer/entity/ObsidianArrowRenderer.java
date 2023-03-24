package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.projectile.ObsidianArrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ObsidianArrowRenderer extends ArrowRenderer<ObsidianArrow> {
   public static final ResourceLocation RES_ARROW = Combat.getInstance().location("textures/entity/projectiles/obsidian_arrow.png");

   public ObsidianArrowRenderer(EntityRendererProvider.Context p_173917_) {
      super(p_173917_);
   }

   @Override
   public ResourceLocation getTextureLocation(ObsidianArrow entity) {
      return RES_ARROW;
   }
}