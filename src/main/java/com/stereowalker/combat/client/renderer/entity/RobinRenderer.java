package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.entity.layers.RobinClothingLayer;
import com.stereowalker.combat.client.renderer.entity.model.RobinModel;
import com.stereowalker.combat.entity.boss.RobinEntity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RobinRenderer extends BipedRenderer<RobinEntity, RobinModel<RobinEntity>> {
   private static final ResourceLocation SKELETON_TEXTURES = Combat.getInstance().location("textures/entity/arch_robin/arch_robin.png");

   public RobinRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new RobinModel<>(), 0.5F);
      this.addLayer(new HeldItemLayer<>(this));
      this.addLayer(new RobinClothingLayer<>(this));
      this.addLayer(new BipedArmorLayer<>(this, new RobinModel<RobinEntity>(0.5F, true), new RobinModel<RobinEntity>(1.0F, true)));
   }

   /**
    * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
    */
   public ResourceLocation getEntityTexture(RobinEntity entity) {
      return SKELETON_TEXTURES;
   }
}