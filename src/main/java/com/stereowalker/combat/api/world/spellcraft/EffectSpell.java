package com.stereowalker.combat.api.world.spellcraft;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EffectSpell extends Spell {
	MobEffect effect;
	int amplifier;

	public EffectSpell(SpellCategory category, Rank tier, float cost, MobEffect effect, int amplifierIn, int castTime) {
		super(category, tier, CastType.SELF, cost, tier == Rank.GOD ? 150 : 60, castTime);
		this.effect = effect;
		this.amplifier = amplifierIn;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		if(this.getRank() == Rank.GOD) return caster.addEffect(new MobEffectInstance(effect, 60 * 20, amplifier, false, false));
		else return caster.addEffect(new MobEffectInstance(effect, Mth.ceil(60 * 20 * strength), amplifier, false, false));
	}
	
//	@Override
//	public boolean canExecute() {
//		return !getCaster().isPotionActive(effect);
//	}
	
	@Override
	public Component getFailedMessage(LivingEntity caster) {
		return Component.translatable("The effect is already active");
	}

}
