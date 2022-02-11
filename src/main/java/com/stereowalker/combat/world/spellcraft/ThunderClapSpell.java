package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.AbstractAreaOfEffectSpell;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ThunderClapSpell extends AbstractAreaOfEffectSpell {

	protected ThunderClapSpell(SpellCategory category, Rank tier, float cost, int cooldown, int range, boolean isBeneficialSpell, int castTime) {
		super(category, tier, cost, cooldown, range, isBeneficialSpell, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vec3 location, InteractionHand hand) {
		BlockPos blockpos = target.blockPosition();
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
