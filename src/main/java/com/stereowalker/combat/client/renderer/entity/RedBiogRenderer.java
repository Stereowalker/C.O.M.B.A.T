package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.entity.model.BiogModel;
import com.stereowalker.combat.entity.monster.BiogEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RedBiogRenderer extends MobRenderer<BiogEntity, BiogModel<BiogEntity>> {
   private static final ResourceLocation BIOG_TEXTURES = Combat.getInstance().location("textures/entity/red_biog.png");

   public RedBiogRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new BiogModel<>(), 0.7F);
   }

   /**
    * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
    */
   public ResourceLocation getEntityTexture(BiogEntity entity) {
      return BIOG_TEXTURES;
   }
}
