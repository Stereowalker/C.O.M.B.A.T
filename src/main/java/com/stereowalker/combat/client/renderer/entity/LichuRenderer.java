package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.entity.model.LichuModel;
import com.stereowalker.combat.entity.monster.LichuEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LichuRenderer extends MobRenderer<LichuEntity, LichuModel<LichuEntity>> {
   private static final ResourceLocation LICHU_TEXTURES = Combat.getInstance().location("textures/entity/lichu.png");

   public LichuRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new LichuModel<>(), 0.7F);
   }

   /**
    * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
    */
   public ResourceLocation getEntityTexture(LichuEntity entity) {
      return LICHU_TEXTURES;
   }
}
