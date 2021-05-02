package com.stereowalker.combat.api.spell;

import com.stereowalker.combat.entity.magic.SurroundSpellCircleEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public abstract class AbstractSurroundSpell extends TargetedSpell implements IExtensionSpell {
	private int effectInterval;

	protected AbstractSurroundSpell(SpellCategory category, Rank tier, float cost, int cooldown, int effectInterval, boolean isBeneficialSpell, int castTime) {
		super(category, tier, CastType.SURROUND, cost, cooldown, isBeneficialSpell, castTime);
		this.effectInterval = effectInterval;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		SurroundSpellCircleEntity circle = new SurroundSpellCircleEntity(caster, caster.world, caster.getPosition().getX(), caster.getPosition().getY(), caster.getPosition().getZ());
		circle.setSpell(new SpellInstance(this, strength, location, hand, caster.getUniqueID()));
		return caster.world.addEntity(circle);
	}

	public int getEffectInterval() {
		return effectInterval;
	}

}
