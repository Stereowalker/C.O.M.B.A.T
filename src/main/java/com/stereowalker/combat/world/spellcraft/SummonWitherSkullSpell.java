package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SummonWitherSkullSpell extends Spell {

	protected SummonWitherSkullSpell(SpellCategory category, Rank tier, float cost, int cooldown, int castTime) {
		super(category, tier, CastType.SELF, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		if (caster.level instanceof ServerLevel) {
			double d0 = caster.getX();
			double d1 = caster.getEyeY();
			double d2 = caster.getZ();
			double d3 = location.x - d0;
			double d4 = location.y - d1;
			double d5 = location.z - d2;
			WitherSkull witherskull = new WitherSkull(caster.level, caster, d3, d4, d5) {
				@Override
				protected void onHit(HitResult pResult) {
					super.onHit(pResult);
					if (!this.level.isClientSide) {
						Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner()) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
						this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float) strength, false, explosion$blockinteraction);
						this.discard();
					}

				}
			};
			witherskull.setOwner(caster);
			witherskull.setDangerous(false);

			witherskull.setPosRaw(d0, d1, d2);
			caster.level.addFreshEntity(witherskull);
		}
		return true;
	}
}
