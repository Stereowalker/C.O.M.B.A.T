package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.entity.model.VampireModel;
import com.stereowalker.combat.entity.monster.VampireEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class VampireRenderer extends MobRenderer<VampireEntity, VampireModel<VampireEntity>> {
	private static final ResourceLocation VAMPIRE_TEXTURE = Combat.getInstance().location("textures/entity/vampire.png");

	public VampireRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new VampireModel<>(0.0F), 0.5F);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	public ResourceLocation getEntityTexture(VampireEntity entity) {
		return VAMPIRE_TEXTURE;
	}
}
