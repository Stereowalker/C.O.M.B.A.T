package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.entity.projectile.ObsidianArrowEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ObsidianArrowRenderer extends ArrowRenderer<ObsidianArrowEntity> {
   public static final ResourceLocation RES_ARROW = Combat.getInstance().location("textures/entity/projectiles/obsidian_arrow.png");

   public ObsidianArrowRenderer(EntityRendererManager manager) {
      super(manager);
   }

   /**
    * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
    */
   public ResourceLocation getEntityTexture(ObsidianArrowEntity entity) {
      return RES_ARROW;
   }
}