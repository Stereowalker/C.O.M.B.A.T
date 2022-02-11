package com.stereowalker.combat.world.spellcraft;

import java.util.function.Supplier;

import com.stereowalker.combat.api.world.spellcraft.AbstractAreaOfEffectSpell;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class AOEEffectSpell extends AbstractAreaOfEffectSpell {
	Supplier<MobEffect> effect;
	int amplifier;

	protected AOEEffectSpell(SpellCategory category, Rank tier, float cost, int cooldown, int range, Supplier<MobEffect> effect, int amplifier, boolean isBeneficialSpell, int castTime) {
		super(category, tier, cost, cooldown, range, isBeneficialSpell, castTime);
		this.effect = effect;
		this.amplifier = amplifier;
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vec3 location, InteractionHand hand) {
		if (target instanceof LivingEntity) {
			return ((LivingEntity)target).addEffect(new MobEffectInstance(this.effect.get(), Mth.ceil(600*strength), this.amplifier, false, true));
		}
		return false;
	}
}
