package com.stereowalker.rankup.skill;

import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class PerseveranceSkill extends Skill {

	public PerseveranceSkill(Builder builder) {
		super(builder);
	}

	@Override
	public void playerTick(LivingEntity entity) {
		float health = entity.getHealth();
		float maxHealth = entity.getMaxHealth();
		float percentage = Mth.clamp((health/maxHealth)*100.0F, 0.0F, 100.0F);
		boolean flag1 = this == Skills.PERSEVERANCE_1 && !PlayerSkills.hasSkill(entity, Skills.PERSEVERANCE_2);
		boolean flag2 = this == Skills.PERSEVERANCE_2 && !PlayerSkills.hasSkill(entity, Skills.PERSEVERANCE_3);
		boolean flag3 = this == Skills.PERSEVERANCE_3;
		if (flag1 || flag2 || flag3) {
			if (percentage < 20.0F && getLevel() == 1) {
				entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40));
			}
			if (percentage < 30.0F && getLevel() == 2) {
				entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40));
			}
			if (percentage < 40.0F && getLevel() == 3) {
				entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40));
			}
		}
		super.playerTick(entity);
	}

}
