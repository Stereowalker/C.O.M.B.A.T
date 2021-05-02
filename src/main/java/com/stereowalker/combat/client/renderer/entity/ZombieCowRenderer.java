package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.entity.monster.ZombieCowEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieCowRenderer extends MobRenderer<ZombieCowEntity, CowModel<ZombieCowEntity>> {
   private static final ResourceLocation COW_TEXTURES = Combat.getInstance().location("textures/entity/zombie_cow.png");

   public ZombieCowRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new CowModel<>(), 0.7F);
   }

   /**
    * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
    */
   public ResourceLocation getEntityTexture(ZombieCowEntity entity) {
      return COW_TEXTURES;
   }
}