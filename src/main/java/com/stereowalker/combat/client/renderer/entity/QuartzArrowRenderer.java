package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.projectile.QuartzArrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QuartzArrowRenderer extends ArrowRenderer<QuartzArrow> {
	public static final ResourceLocation RES_ARROW = Combat.getInstance().location("textures/entity/projectiles/quartz_arrow.png");

	public QuartzArrowRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_);
	}

	@Override
	public ResourceLocation getTextureLocation(QuartzArrow entity) {
		return RES_ARROW;
	}
}