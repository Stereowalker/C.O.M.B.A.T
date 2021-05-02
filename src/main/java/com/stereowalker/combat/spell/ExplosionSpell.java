package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion.Mode;

public class ExplosionSpell extends Spell {

	protected ExplosionSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		if (location != null) {
			if(!caster.world.isRemote) {
				caster.world.createExplosion((Entity)null, location.x, location.y, location.z, MathHelper.ceil(strength), false, Mode.BREAK);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public ITextComponent getFailedMessage(LivingEntity caster) {
		return new TranslationTextComponent("No location was selected");
	}
}
