package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.projectile.MjolnirLightning;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MjolnirLightningRenderer extends AbstractMagicProjectileRenderer<MjolnirLightning> {
	public static final ResourceLocation RES_MJOLNIR_LIGHTNING = Combat.getInstance().location("textures/entity/projectiles/mjolnir_lightning.png");

	public MjolnirLightningRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_);
	}

	@Override
	public ResourceLocation getTextureLocation(MjolnirLightning entity) {
		return RES_MJOLNIR_LIGHTNING;
	}
}
