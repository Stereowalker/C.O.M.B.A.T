package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.world.entity.misc.Meteor;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class MeteorSpell extends Spell {

	protected MeteorSpell(Rank tier, float cost, int cooldown, int castTime) {
		super(SpellCategory.EARTH, tier, CastType.BLOCK, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		if (location != null) {
			if(!caster.level.isClientSide) {
				if(this.getRank() == Rank.NOVICE) {
					Meteor meteor = new Meteor(caster.level, Meteor.MeteorSize.SMALL, location.x, location.y + 200.0F, location.z);
					return caster.level.addFreshEntity(meteor);
				}
				if(this.getRank() == Rank.APPRENTICE) {
					Meteor meteor = new Meteor(caster.level, Meteor.MeteorSize.MEDIUM, location.x, location.y + 200.0F, location.z);
					return caster.level.addFreshEntity(meteor);
				}
				if(this.getRank() == Rank.ADVANCED) {
					Meteor meteor = new Meteor(caster.level, Meteor.MeteorSize.LARGE, location.x, location.y + 200.0F, location.z);
					return caster.level.addFreshEntity(meteor);
				}
				if(this.getRank() == Rank.GOD) {
					for (int i = 0; i < 30; i++) {
						Meteor meteor0 = new Meteor(caster.level, Meteor.MeteorSize.SMALL, location.x + (caster.level.random.nextInt(20) - 10), location.y + 200.0F + caster.level.random.nextInt(200), location.z + (caster.level.random.nextInt(20) - 10));
						Meteor meteor1 = new Meteor(caster.level, Meteor.MeteorSize.MEDIUM, location.x + (caster.level.random.nextInt(20) - 10), location.y + 200.0F + caster.level.random.nextInt(200), location.z + (caster.level.random.nextInt(20) - 10));
						Meteor meteor2 = new Meteor(caster.level, Meteor.MeteorSize.LARGE, location.x + (caster.level.random.nextInt(20) - 10), location.y + 200.0F + caster.level.random.nextInt(200), location.z + (caster.level.random.nextInt(20) - 10));
						int j = caster.level.random.nextInt(3);
						if(j == 0) {
							caster.level.addFreshEntity(meteor0);
						}
						if(j == 1) {
							caster.level.addFreshEntity(meteor1);
						}
						if(j == 2) {
							caster.level.addFreshEntity(meteor2);
						}

					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Component getFailedMessage(LivingEntity caster) {
		return new TranslatableComponent("No location was selected");
	}

}
