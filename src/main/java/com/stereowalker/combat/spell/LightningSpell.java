package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class LightningSpell extends Spell {


	protected LightningSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		BlockPos blockpos = new BlockPos(location);
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
