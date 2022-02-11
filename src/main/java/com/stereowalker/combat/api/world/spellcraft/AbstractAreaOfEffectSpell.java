package com.stereowalker.combat.api.world.spellcraft;

import java.util.List;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractAreaOfEffectSpell extends TargetedSpell implements IExtensionSpell {
	int range;
	
	protected AbstractAreaOfEffectSpell(SpellCategory category, Rank tier, float cost, int cooldown, int range, boolean isBeneficialSpell, int castTime) {
		super(category, tier, CastType.AREA_OF_EFFECT, cost, cooldown, isBeneficialSpell, castTime);
		this.range = range;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		List<Entity> affectedEntities = caster.level.getEntitiesOfClass(Entity.class, new AABB(caster.blockPosition().relative(Direction.NORTH, range).relative(Direction.WEST, range).relative(Direction.UP, range), caster.blockPosition().relative(Direction.SOUTH, range).relative(Direction.EAST, range).relative(Direction.DOWN, range)));
		for (Entity living: affectedEntities) {
			SpellInstance spell = new SpellInstance(this, strength, location, hand, caster.getUUID());
			spell.executeExtensionSpell(caster, living);
		}
		return !affectedEntities.isEmpty();
	}

}
