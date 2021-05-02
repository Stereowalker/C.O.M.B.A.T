package com.stereowalker.rankup.skill;

import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class FrozenStrikeSkill extends Skill {

	public FrozenStrikeSkill(Builder builder) {
		super(builder);
	}
	
	@Override
	public void onAttackEntity(PlayerEntity player, Entity target) {
		super.onAttackEntity(player, target);
		SpellUtil.setIce(target, 10);
	}

}
