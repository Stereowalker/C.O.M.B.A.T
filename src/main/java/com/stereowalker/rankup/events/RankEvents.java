package com.stereowalker.rankup.events;

import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.skill.SkillsEvents;
import com.stereowalker.rankup.world.job.JobEvents;
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
			if (Combat.RPG_CONFIG.enableLevelingSystem) {
				StatEvents.registerEntityStats(event.getEntityLiving());
				StatEvents.statUpdate(event.getEntityLiving());
				if (event.getEntityLiving() instanceof ServerPlayer) {
					ServerPlayer player = (ServerPlayer)event.getEntityLiving();
					JobEvents.registerEntityStats(player);
					JobEvents.jobUpdate(player, player.getLevel().getServer().getPlayerList());
					JobEvents.playerToClient(player);
				}
			}
			if (event.getEntityLiving() instanceof ServerPlayer) {
				StatEvents.playerToClient((ServerPlayer) event.getEntityLiving());
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
		JobEvents.restoreJobs(event.getPlayer(), event.getOriginal(), event.isWasDeath());
	}

	@SubscribeEvent
	public static void addReload(AddReloadListenerEvent event) {
		event.addListener(Rankup.statsManager);
	}

	@SubscribeEvent
	public static void appendExperienceStorage(PlayerXpEvent.PickupXp event) {
	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void createPlayer(EntityJoinWorldEvent event) {
		if (!event.getWorld().isClientSide) {
			if (event.getEntity() instanceof LivingEntity) {
				if (Combat.RPG_CONFIG.enableLevelingSystem) {
					StatEvents.registerEntityStats((LivingEntity)event.getEntity());
					StatEvents.statUpdate((LivingEntity)event.getEntity());
					if (event.getEntity() instanceof ServerPlayer) {
						JobEvents.registerEntityStats((ServerPlayer)event.getEntity());
					}
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
