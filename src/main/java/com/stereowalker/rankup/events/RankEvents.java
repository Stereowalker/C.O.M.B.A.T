package com.stereowalker.rankup.events;

import com.stereowalker.combat.config.Config;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.skill.SkillsEvents;
import com.stereowalker.rankup.stat.PlayerAttributeLevels;
import com.stereowalker.rankup.stat.StatEvents;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class RankEvents {

	@SubscribeEvent
	public static void living(LivingUpdateEvent event) {
		SkillsEvents.tickSkillUpdate(event.getEntityLiving());
		if (Config.RPG_COMMON.enableLevelingSystem.get()) {
			StatEvents.registerEntityStats(event.getEntityLiving());
			StatEvents.statUpdate(event.getEntityLiving());
		}
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof ServerPlayerEntity) {
			StatEvents.sendToClient((ServerPlayerEntity) event.getEntityLiving());
		}
	}

	@SubscribeEvent
	public static void clonePlayer(PlayerEvent.Clone event) {
		StatEvents.restoreStats(event.getPlayer(), event.getOriginal(), event.isWasDeath());
		SkillsEvents.restoreStats(event.getPlayer(), event.getOriginal());
	}

	@SubscribeEvent
	public static void addReload(AddReloadListenerEvent event) {
		event.addListener(Rankup.statsManager);
	}

	@SubscribeEvent
	public static void appendExperienceStorage(PlayerXpEvent.PickupXp event) {
	}

	@SubscribeEvent
	public static void increaseExperience(LivingExperienceDropEvent event) {
		//In order to prevent players from getting experience from other players
		if (event.getAttackingPlayer() != null && !(event.getEntityLiving() instanceof PlayerEntity)) {
			int i = event.getDroppedExperience();
			System.out.println("Previous XP = "+i);

			i *= Math.max((PlayerAttributeLevels.getLevel(event.getEntityLiving()))-(PlayerAttributeLevels.getLevel(event.getAttackingPlayer())), 1);
			System.out.println("Multiplier = "+Math.max((PlayerAttributeLevels.getLevel(event.getEntityLiving()))-(PlayerAttributeLevels.getLevel(event.getAttackingPlayer())), 1));
			System.out.println("New XP = "+i);

			PlayerAttributeLevels.setExperience(event.getAttackingPlayer(), PlayerAttributeLevels.getExperience(event.getAttackingPlayer())+i);
		}
		//Make players take all the experence from players that theyve killed
		if (event.getAttackingPlayer() != null && (event.getEntityLiving() instanceof PlayerEntity) && Config.RPG_COMMON.takeXpFromKilledPlayers.get()) {
			PlayerAttributeLevels.setExperience(event.getAttackingPlayer(), PlayerAttributeLevels.getExperience(event.getAttackingPlayer())+PlayerAttributeLevels.getExperience(event.getEntityLiving()));
			PlayerAttributeLevels.setExperience(event.getEntityLiving(), 0);
		}
	}

	@SubscribeEvent
	public static void createPlayer(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			if (Config.RPG_COMMON.enableLevelingSystem.get()) {
				StatEvents.registerEntityStats((LivingEntity)event.getEntity());
				StatEvents.statUpdate((LivingEntity)event.getEntity());
			}
		}
		if (event.getEntity() instanceof PlayerEntity && !PlayerAttributeLevels.hasInitPlayer((PlayerEntity)event.getEntity())) {
			StatEvents.aad((LivingEntity)event.getEntity());
			PlayerAttributeLevels.setPlayerInitialization((PlayerEntity)event.getEntity(), true);
		}
	}

	@SubscribeEvent
	public static void deletePlayer(LivingDeathEvent event) {
		if (!event.isCanceled() && event.getEntity() instanceof PlayerEntity) {
			PlayerAttributeLevels.setPlayerInitialization((PlayerEntity)event.getEntity(), false);
		}
	}
}
