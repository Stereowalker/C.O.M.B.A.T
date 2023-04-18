package com.stereowalker.rankup.skill;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerSkillsPacket;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.PlayerSkills.SkillGrantReason;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.unionlib.api.insert.InsertCanceller;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SkillsEvents {

	public static void restoreStats(Player player, Player original) {
		PlayerSkills.getOrCreateRankSkillNBT(player);
		for (Skill skill : CombatRegistries.SKILLS.get()) {
			PlayerSkills.setSkill(player, skill, PlayerSkills.hasSkill(original, skill));
		}
		PlayerSkills.setStartingSkill(player, PlayerSkills.getStartingSkill(original));
	}

	public static void tickSkillUpdate(LivingEntity entity) {
		//Send to Client
		if (!entity.level.isClientSide && entity instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer)entity;
			new ClientboundPlayerSkillsPacket(player).send(player);
		}
		//Add on spawn
		if(entity instanceof Player) {
			Player player = (Player)entity;
			PlayerSkills.addSkillsOnSpawn(player);
		}
		//TIck skill
		if (entity instanceof Player) {
			Player playerEntity = (Player) entity;
			for (Skill skill : CombatRegistries.SKILLS.get()) {
				if (PlayerSkills.hasSkill(playerEntity, skill)) {
					skill.playerTick(playerEntity);
				}
			}
			if (!PlayerSkills.hasSkill(playerEntity, PlayerSkills.getStartingSkill(playerEntity)) && PlayerSkills.getStartingSkill(playerEntity) != Skills.EMPTY) {
				PlayerSkills.grantSkill(playerEntity, PlayerSkills.getStartingSkill(playerEntity), SkillGrantReason.STARTER);
			}
		}
	}

	public static void attackSkillUpdate(Player player, Entity target, InsertCanceller canceller) {
		for (Skill skill : CombatRegistries.SKILLS.get()) {
			if (PlayerSkills.hasSkill(player, skill)) {
				skill.onAttackEntity(player, target);
			}
		}
	}
}
