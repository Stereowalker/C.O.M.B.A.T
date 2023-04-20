package com.stereowalker.rankup.events;

import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.network.protocol.game.ClientboundEntityStatsPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundNearbyPlayerStatsPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerJobsPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerStatsPacket;
import com.stereowalker.rankup.skill.SkillsEvents;
import com.stereowalker.rankup.world.job.JobEvents;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.rankup.world.stat.StatEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class RankEvents {

	public static void living(LivingEntity living) {
		SkillsEvents.tickSkillUpdate(living);
		if (!living.level.isClientSide) {
			if (Combat.RPG_CONFIG.enableLevelingSystem) {
				StatEvents.registerEntityStats(living);
				StatEvents.statUpdate(living);
				if (living instanceof ServerPlayer) {
					ServerPlayer player = (ServerPlayer)living;
					JobEvents.registerEntityStats(player);
					JobEvents.jobUpdate(player, player.getLevel().getServer().getPlayerList());
					new ClientboundPlayerJobsPacket(player).send(player);
				}
			}
			if (living instanceof ServerPlayer player) {
				new ClientboundPlayerStatsPacket(player).send(player);
				for (ServerPlayer other : living.level.getEntitiesOfClass(ServerPlayer.class, living.getBoundingBox().inflate(40))) {
					new ClientboundNearbyPlayerStatsPacket(other).send(player);
				}
			} else if (!(living instanceof Player)){
				for (ServerPlayer player : living.level.getEntitiesOfClass(ServerPlayer.class, living.getBoundingBox().inflate(40))) {
					new ClientboundEntityStatsPacket(living).send(player);
				}
			}
		}
	}
	
	public static void playerJoin(Player player) {
		if (player instanceof ServerPlayer) {
			StatEvents.sendStatsToClient((ServerPlayer) player);
		}
	}
	
	public static void syncToClientOnSave(LevelAccessor level) {
		if (level instanceof ServerLevel) {
			for (ServerPlayer player : ((ServerLevel)level).players()) {
				StatEvents.sendStatsToClient(player);
			}
		}
	}

	public static void clonePlayer(Player thisPlayer, Player thatPlayer, boolean keepEverything) {
		SkillsEvents.restoreStats(thisPlayer, thatPlayer);
		JobEvents.restoreJobs(thisPlayer, thatPlayer, !keepEverything);
	}

	@SubscribeEvent
	public static void appendExperienceStorage(PlayerXpEvent.PickupXp event) {
	}

	public static void createPlayer(Entity entity, Level level, boolean loadedFromDisk) {
		if (!level.isClientSide) {
			if (entity instanceof LivingEntity) {
				if (Combat.RPG_CONFIG.enableLevelingSystem) {
					StatEvents.registerEntityStats((LivingEntity)entity);
					StatEvents.statUpdate((LivingEntity)entity);
					if (entity instanceof ServerPlayer) {
						JobEvents.registerEntityStats((ServerPlayer)entity);
					}
				}
			}
			if (entity instanceof Player && !PlayerAttributeLevels.hasInitPlayer((Player)entity)) {
				StatEvents.initializeAllStats((LivingEntity)entity);
				PlayerAttributeLevels.setPlayerInitialization((Player)entity, true);
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
