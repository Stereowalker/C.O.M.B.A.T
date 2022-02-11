package com.stereowalker.rankup.skill;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerSkillsPacket;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fmllegacy.network.NetworkDirection;

@EventBusSubscriber
public class SkillsEvents {

	public static void restoreStats(Player player, Player original) {
		PlayerSkills.getOrCreateRankSkillNBT(player);
		for (Skill skill : CombatRegistries.SKILLS) {
			PlayerSkills.setSkill(player, skill, PlayerSkills.hasSkill(original, skill));
		}
		PlayerSkills.setStartingSkill(player, PlayerSkills.getStartingSkill(original));
	}
	
	public static void tickSkillUpdate(LivingEntity entity) {
		//Send to Client
		if (!entity.level.isClientSide && entity instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer)entity;
			Combat.getInstance().channel.sendTo(new ClientboundPlayerSkillsPacket(player), player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
		}
		//Add on spawn
		if(entity instanceof Player) {
			Player player = (Player)entity;
			PlayerSkills.addSkillsOnSpawn(player);
		}
		//TIck skill
		if (entity instanceof Player) {
			Player playerEntity = (Player) entity;
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
		if (event.getEntityLiving() instanceof Player) {
			Player player = (Player) event.getEntityLiving();
			for (Skill skill : CombatRegistries.SKILLS) {
				if (PlayerSkills.hasSkill(player, skill)) {
					skill.onAttackEntity(player, event.getTarget());
				}
			}
		}
	}
}
