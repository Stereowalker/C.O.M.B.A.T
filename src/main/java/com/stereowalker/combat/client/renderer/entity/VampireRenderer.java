package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.VampireModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.monster.Vampire;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VampireRenderer extends MobRenderer<Vampire, VampireModel<Vampire>> {
	private static final ResourceLocation VAMPIRE_TEXTURE = Combat.getInstance().location("textures/entity/vampire.png");

	public VampireRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_, new VampireModel<>((p_173964_.bakeLayer(CModelLayers.VAMPIRE))), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(Vampire pEntity) {
		return VAMPIRE_TEXTURE;
	}
}
