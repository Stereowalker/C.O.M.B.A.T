package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.api.spell.AbstractProjectileSpell;
import com.stereowalker.combat.entity.projectile.SpellEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SpellRenderer extends AbstractMagicProjectileRenderer<SpellEntity> {
	
	public SpellRenderer(EntityRendererManager manager) {
		super(manager);
	}
	
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	public ResourceLocation getEntityTexture(SpellEntity entity) {
		return ((AbstractProjectileSpell)entity.getSpell().getSpell()).projectileTexture();	
	}
}
