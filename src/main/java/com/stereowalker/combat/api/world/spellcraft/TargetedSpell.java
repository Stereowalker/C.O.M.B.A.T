package com.stereowalker.combat.api.world.spellcraft;

public abstract class TargetedSpell extends Spell {
	private boolean isBeneficialSpell;

	protected TargetedSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, boolean isBeneficialSpell, int castTime) {
		super(category, tier, type, cost, cooldown, castTime);
		this.isBeneficialSpell = isBeneficialSpell;
	}
	public boolean isBeneficialSpell() {
		return isBeneficialSpell;
	}
}
