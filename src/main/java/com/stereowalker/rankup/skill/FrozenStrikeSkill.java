package com.stereowalker.rankup.skill;

import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class FrozenStrikeSkill extends Skill {

	public FrozenStrikeSkill(Builder builder) {
		super(builder);
	}
	
	@Override
	public void onAttackEntity(Player player, Entity target) {
		super.onAttackEntity(player, target);
		SpellUtil.setIce(target, 10);
	}

}
