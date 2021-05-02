package com.stereowalker.rankup.skill;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.network.server.SPlayerSkillsPacket;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.NetworkDirection;

@EventBusSubscriber
public class SkillsEvents {

	public static void restoreStats(PlayerEntity player, PlayerEntity original) {
		PlayerSkills.getOrCreateRankSkillNBT(player);
		for (Skill skill : CombatRegistries.SKILLS) {
			PlayerSkills.setSkill(player, skill, PlayerSkills.hasSkill(original, skill));
		}
		PlayerSkills.setStartingSkill(player, PlayerSkills.getStartingSkill(original));
	}
	
	public static void tickSkillUpdate(LivingEntity entity) {
		//Send to Client
		if (!entity.world.isRemote && entity instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)entity;
			Combat.getInstance().channel.sendTo(new SPlayerSkillsPacket(player), player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		}
		//Add on spawn
		if(entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)entity;
			PlayerSkills.addSkillsOnSpawn(player);
		}
		//TIck skill
		if (entity instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) entity;
			for (Skill skill : CombatRegistries.SKILLS) {
				if (PlayerSkills.hasSkill(playerEntity, skill)) {
					skill.playerTick(playerEntity);
				}
			}
			if (!PlayerSkills.hasSkill(playerEntity, PlayerSkills.getStartingSkill(playerEntity)) && PlayerSkills.getStartingSkill(playerEntity) != Skills.EMPTY) {
				PlayerSkills.grantSkill(playerEntity, PlayerSkills.getStartingSkill(playerEntity), true);
			}
		}
	}
	
	@SubscribeEvent
	public static void attackSkillUpdate(AttackEntityEvent event) {
		if (event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			for (Skill skill : CombatRegistries.SKILLS) {
				if (PlayerSkills.hasSkill(player, skill)) {
					skill.onAttackEntity(player, event.getTarget());
				}
			}
		}
	}
}
