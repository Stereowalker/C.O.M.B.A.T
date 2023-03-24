package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.projectile.Bullet;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BulletRenderer extends ArrowRenderer<Bullet> {
	public static final ResourceLocation RES_ARROW = Combat.getInstance().location("textures/entity/projectiles/bullet.png");

	public BulletRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_);
	}

	@Override
	public ResourceLocation getTextureLocation(Bullet entity) {
		return RES_ARROW;
	}
}