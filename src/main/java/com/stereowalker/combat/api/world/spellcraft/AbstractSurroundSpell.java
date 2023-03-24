package com.stereowalker.combat.api.world.spellcraft;

import com.stereowalker.combat.world.entity.magic.SurroundSpellCircle;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractSurroundSpell extends TargetedSpell implements IExtensionSpell {
	private int effectInterval;

	protected AbstractSurroundSpell(SpellCategory category, Rank tier, float cost, int cooldown, int effectInterval, boolean isBeneficialSpell, int castTime) {
		super(category, tier, CastType.SURROUND, cost, cooldown, isBeneficialSpell, castTime);
		this.effectInterval = effectInterval;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		SurroundSpellCircle circle = new SurroundSpellCircle(caster, caster.level, caster.blockPosition().getX(), caster.blockPosition().getY(), caster.blockPosition().getZ());
		circle.setSpell(new SpellInstance(this, strength, location, hand, caster.getUUID()));
		return caster.level.addFreshEntity(circle);
	}

	public int getEffectInterval() {
		return effectInterval;
	}

}
