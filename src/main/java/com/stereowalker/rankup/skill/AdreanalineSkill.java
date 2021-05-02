package com.stereowalker.rankup.skill;

import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

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
				entity.addPotionEffect(new EffectInstance(Effects.SPEED, 20));
			}
			if (health <= 4.0F && getLevel() == 2) {
				entity.addPotionEffect(new EffectInstance(Effects.SPEED, 20));
				entity.addPotionEffect(new EffectInstance(Effects.STRENGTH, 20));
			}
		}
		super.playerTick(entity);
	}
}
