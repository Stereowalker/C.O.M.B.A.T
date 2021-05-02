package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.AbstractAreaOfEffectSpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class ThunderClapSpell extends AbstractAreaOfEffectSpell {

	protected ThunderClapSpell(SpellCategory category, Rank tier, float cost, int cooldown, int range, boolean isBeneficialSpell, int castTime) {
		super(category, tier, cost, cooldown, range, isBeneficialSpell, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vector3d location, Hand hand) {
		BlockPos blockpos = target.getPosition();
        if (caster.world.canSeeSky(blockpos)) {
           LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(caster.world);
           lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(blockpos));
           lightningboltentity.setCaster(caster instanceof ServerPlayerEntity ? (ServerPlayerEntity)caster : null);
           caster.world.addEntity(lightningboltentity);
           return true;
        }
        return false;
	}

}
