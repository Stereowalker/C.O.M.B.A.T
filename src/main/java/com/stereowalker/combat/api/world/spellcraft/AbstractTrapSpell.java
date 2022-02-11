package com.stereowalker.combat.api.world.spellcraft;

import com.stereowalker.combat.world.entity.magic.TrapSpellCircle;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractTrapSpell extends TargetedSpell implements IExtensionSpell {

	protected AbstractTrapSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, boolean isBeneficialSpell, int castTime) {
		super(category, tier, type, cost, cooldown, isBeneficialSpell, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		TrapSpellCircle circle = new TrapSpellCircle(caster, caster.level, location.x, location.y, location.z);
		circle.setSpell(new SpellInstance(this, strength, location, hand, caster.getUUID()));
		return caster.level.addFreshEntity(circle);
	}
}

