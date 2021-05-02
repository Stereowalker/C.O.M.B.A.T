package com.stereowalker.combat.spell;

import java.util.function.Supplier;

import com.stereowalker.combat.api.spell.AbstractAreaOfEffectSpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class AOEEffectSpell extends AbstractAreaOfEffectSpell {
	Supplier<Effect> effect;
	int amplifier;

	protected AOEEffectSpell(SpellCategory category, Rank tier, float cost, int cooldown, int range, Supplier<Effect> effect, int amplifier, boolean isBeneficialSpell, int castTime) {
		super(category, tier, cost, cooldown, range, isBeneficialSpell, castTime);
		this.effect = effect;
		this.amplifier = amplifier;
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vector3d location, Hand hand) {
		if (target instanceof LivingEntity) {
			return ((LivingEntity)target).addPotionEffect(new EffectInstance(this.effect.get(), MathHelper.ceil(600*strength), this.amplifier, false, true));
		}
		return false;
	}
}
