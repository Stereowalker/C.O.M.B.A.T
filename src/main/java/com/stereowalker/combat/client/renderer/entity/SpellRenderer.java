package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.api.world.spellcraft.AbstractProjectileSpell;
import com.stereowalker.combat.world.entity.projectile.ProjectileSpell;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SpellRenderer extends AbstractMagicProjectileRenderer<ProjectileSpell> {

	public SpellRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_);
	}

	@Override
	public ResourceLocation getTextureLocation(ProjectileSpell entity) {
		return ((AbstractProjectileSpell)entity.getSpell().getSpell()).projectileTexture();	
	}
}
