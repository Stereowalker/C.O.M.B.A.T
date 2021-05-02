package com.stereowalker.combat.api.spell;

import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EffectSpell extends Spell{
	Effect effect;
	int amplifier;

	public EffectSpell(SpellCategory category, Rank tier, float cost, Effect effect, int amplifierIn, int castTime) {
		super(category, tier, CastType.SELF, cost, tier == Rank.GOD ? 150 : 60, castTime);
		this.effect = effect;
		this.amplifier = amplifierIn;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		if(this.getRank() == Rank.GOD) return caster.addPotionEffect(new EffectInstance(effect, 60 * 20, amplifier, false, false));
		else return caster.addPotionEffect(new EffectInstance(effect, MathHelper.ceil(60 * 20 * strength), amplifier, false, false));
	}
	
//	@Override
//	public boolean canExecute() {
//		return !getCaster().isPotionActive(effect);
//	}
	
	@Override
	public ITextComponent getFailedMessage(LivingEntity caster) {
		return new TranslationTextComponent("The effect is already active");
	}

}
