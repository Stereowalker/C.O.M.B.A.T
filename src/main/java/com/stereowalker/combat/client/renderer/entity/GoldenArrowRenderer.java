package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.projectile.GoldenArrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GoldenArrowRenderer extends ArrowRenderer<GoldenArrow> {
	public static final ResourceLocation RES_ARROW = Combat.getInstance().location("textures/entity/projectiles/golden_arrow.png");

	public GoldenArrowRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_);
	}

	@Override
	public ResourceLocation getTextureLocation(GoldenArrow entity) {
		return RES_ARROW;
	}
}