package com.stereowalker.rankup.events;

import com.stereowalker.old.combat.config.Config;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.skill.SkillsEvents;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.rankup.world.stat.StatEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class RankEvents {

	@SubscribeEvent
	public static void living(LivingUpdateEvent event) {
		SkillsEvents.tickSkillUpdate(event.getEntityLiving());
		if (!event.getEntityLiving().level.isClientSide) {
			if (Config.RPG_COMMON.enableLevelingSystem.get()) {
				StatEvents.registerEntityStats(event.getEntityLiving());
				StatEvents.statUpdate(event.getEntityLiving());
			}
			if (event.getEntityLiving() instanceof ServerPlayer) {
				StatEvents.sendPlayerToClient((ServerPlayer) event.getEntityLiving());
			} else if (!(event.getEntityLiving() instanceof Player)){
				for (ServerPlayer player : event.getEntityLiving().level.getEntitiesOfClass(ServerPlayer.class, event.getEntityLiving().getBoundingBox().inflate(40))) {
					StatEvents.sendEntityToClient(player, event.getEntityLiving());
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getPlayer() instanceof ServerPlayer) {
			StatEvents.sendStatsToClient((ServerPlayer) event.getPlayer());
		}
	}
	
	@SubscribeEvent
	public static void playerJoin(WorldEvent.Save event) {
		if (event.getWorld() instanceof ServerLevel) {
			for (ServerPlayer player : ((ServerLevel)event.getWorld()).players()) {
				StatEvents.sendStatsToClient(player);
			}
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
		if (event.getAttackingPlayer() != null && !(event.getEntityLiving() instanceof Player)) {
			int i = event.getDroppedExperience();

			i *= Math.max((PlayerAttributeLevels.getLevel(event.getEntityLiving()))-(PlayerAttributeLevels.getLevel(event.getAttackingPlayer())), 1);

			PlayerAttributeLevels.setExperience(event.getAttackingPlayer(), PlayerAttributeLevels.getExperience(event.getAttackingPlayer())+i);
		}
		//Make players take all the experence from players that theyve killed
		if (event.getAttackingPlayer() != null && (event.getEntityLiving() instanceof Player) && Config.RPG_COMMON.takeXpFromKilledPlayers.get()) {
			PlayerAttributeLevels.setExperience(event.getAttackingPlayer(), PlayerAttributeLevels.getExperience(event.getAttackingPlayer())+PlayerAttributeLevels.getExperience(event.getEntityLiving()));
			PlayerAttributeLevels.setExperience(event.getEntityLiving(), 0);
		}
	}

	@SubscribeEvent
	public static void createPlayer(EntityJoinWorldEvent event) {
		if (!event.getWorld().isClientSide) {
			if (event.getEntity() instanceof LivingEntity) {
				if (Config.RPG_COMMON.enableLevelingSystem.get()) {
					StatEvents.registerEntityStats((LivingEntity)event.getEntity());
					StatEvents.statUpdate((LivingEntity)event.getEntity());
				}
			}
			if (event.getEntity() instanceof Player && !PlayerAttributeLevels.hasInitPlayer((Player)event.getEntity())) {
				StatEvents.initializeAllStats((LivingEntity)event.getEntity());
				PlayerAttributeLevels.setPlayerInitialization((Player)event.getEntity(), true);
			}
		}
	}

	@SubscribeEvent
	public static void deletePlayer(LivingDeathEvent event) {
		if (!event.isCanceled() && event.getEntity() instanceof Player) {
			PlayerAttributeLevels.setPlayerInitialization((Player)event.getEntity(), false);
		}
	}
}
