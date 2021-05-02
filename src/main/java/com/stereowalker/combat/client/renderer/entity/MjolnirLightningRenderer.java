package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.entity.projectile.MjolnirLightningEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class MjolnirLightningRenderer extends AbstractMagicProjectileRenderer<MjolnirLightningEntity> {
	public static final ResourceLocation RES_MJOLNIR_LIGHTNING = Combat.getInstance().location("textures/entity/projectiles/mjolnir_lightning.png");

	   public MjolnirLightningRenderer(EntityRendererManager manager) {
	      super(manager);
	   }

	   public ResourceLocation getEntityTexture(MjolnirLightningEntity entity) {
	      return RES_MJOLNIR_LIGHTNING;
	   }
}
