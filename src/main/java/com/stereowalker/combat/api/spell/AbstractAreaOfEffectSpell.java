package com.stereowalker.combat.api.spell;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

public abstract class AbstractAreaOfEffectSpell extends TargetedSpell implements IExtensionSpell {
	int range;
	
	protected AbstractAreaOfEffectSpell(SpellCategory category, Rank tier, float cost, int cooldown, int range, boolean isBeneficialSpell, int castTime) {
		super(category, tier, CastType.AREA_OF_EFFECT, cost, cooldown, isBeneficialSpell, castTime);
		this.range = range;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		List<Entity> affectedEntities = caster.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(caster.getPosition().offset(Direction.NORTH, range).offset(Direction.WEST, range).offset(Direction.UP, range), caster.getPosition().offset(Direction.SOUTH, range).offset(Direction.EAST, range).offset(Direction.DOWN, range)));
		for (Entity living: affectedEntities) {
			SpellInstance spell = new SpellInstance(this, strength, location, hand, caster.getUniqueID());
			spell.executeExtensionSpell(caster, living);
		}
		return !affectedEntities.isEmpty();
	}

}
