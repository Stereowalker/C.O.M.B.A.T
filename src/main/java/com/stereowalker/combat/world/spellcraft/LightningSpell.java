package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class LightningSpell extends Spell {


	protected LightningSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		BlockPos blockpos = new BlockPos(location);
        if (caster.level.canSeeSky(blockpos)) {
           LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(caster.level);
           lightningboltentity.moveTo(Vec3.atBottomCenterOf(blockpos));
           lightningboltentity.setCause(caster instanceof ServerPlayer ? (ServerPlayer)caster : null);
           caster.level.addFreshEntity(lightningboltentity);
           return true;
        }
        return false;
	}

}
