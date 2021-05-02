package com.stereowalker.combat.api.spell;

import com.stereowalker.combat.entity.magic.TrapSpellCircleEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public abstract class AbstractTrapSpell extends TargetedSpell implements IExtensionSpell {

	protected AbstractTrapSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, boolean isBeneficialSpell, int castTime) {
		super(category, tier, type, cost, cooldown, isBeneficialSpell, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		TrapSpellCircleEntity circle = new TrapSpellCircleEntity(caster, caster.world, location.x, location.y, location.z);
		circle.setSpell(new SpellInstance(this, strength, location, hand, caster.getUniqueID()));
		return caster.world.addEntity(circle);
	}
}

