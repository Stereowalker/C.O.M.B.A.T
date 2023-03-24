package com.stereowalker.rankup.skill;

import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class AdreanalineSkill extends Skill {

	public AdreanalineSkill(Builder builder) {
		super(builder);
	}

	@Override
	public void playerTick(LivingEntity entity) {
		boolean flag1 = this == Skills.ADRENALINE_RUSH_1 && !PlayerSkills.hasSkill(entity, Skills.ADRENALINE_RUSH_2);
		boolean flag2 = this == Skills.ADRENALINE_RUSH_2/* && !PlayerSkills.hasSkill(entity, Skills.PERSEVERANCE_3) */;
//		boolean flag3 = this == Skills.PERSEVERANCE_3;
		if (flag1 || flag2/* || flag3 */) {
			float health = entity.getHealth();
			if (health <= 4.0F && getLevel() == 1) {
				entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20));
			}
			if (health <= 4.0F && getLevel() == 2) {
				entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20));
				entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20));
			}
		}
		super.playerTick(entity);
	}
}
