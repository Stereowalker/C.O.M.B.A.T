package com.stereowalker.rankup.stat;

import com.google.common.collect.Maps;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.config.Config;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

public class PlayerAttributeLevels {
	public static StatProfile getStatPoints(LivingEntity entity, Stat stat) {
		StatProfile newStatProfile = new StatProfile(0, Maps.newHashMap());
		if(entity != null) {
			CompoundNBT compound = getRankNBT(entity); 
			if (compound != null && compound.contains(stat.getRegistryName().getPath(), 10)) {
				newStatProfile.read(compound.getCompound(stat.getRegistryName().getPath()));
				return newStatProfile;
			}
		}
		return newStatProfile;
	}

	public static int getExperience(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = entity.getPersistentData().getCompound("combat:RankUp"); 
			return compound.getInt("experience");
		}
		return 0;
	}

	public static int getLevel(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = entity.getPersistentData().getCompound("combat:RankUp"); 
			return compound.getInt("level");
		}
		return 0;
	}

	public static boolean hasInitPlayer(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = entity.getPersistentData().getCompound("combat:RankUp"); 
			return compound.getBoolean("init_player");
		}
		return false;
	}

	public static void setStatProfile(LivingEntity entity, Stat stat, StatProfile statPoints) {
		CompoundNBT compound = entity.getPersistentData().getCompound("combat:RankUp"); 
		CompoundNBT nbt = new CompoundNBT();
		statPoints.write(nbt);
		compound.put(stat.getRegistryName().getPath(), nbt);
	}

	public static void setExperience(LivingEntity entity, int experience) {
		CompoundNBT compound = entity.getPersistentData().getCompound("combat:RankUp"); 
		compound.putInt("experience", experience);
	}

	public static void setLevel(LivingEntity entity, int level) {
		CompoundNBT compound = entity.getPersistentData().getCompound("combat:RankUp"); 
		compound.putInt("level", level);
	}

	public static void setPlayerInitialization(LivingEntity entity, boolean value) {
		CompoundNBT compound = entity.getPersistentData().getCompound("combat:RankUp"); 
		compound.putBoolean("init_player", value);
	}

	public static boolean addStatPoints(LivingEntity entity, Stat level, int amount) {
		StatProfile statProfile = getStatPoints(entity, level);
		statProfile.setPoints(statProfile.getPoints()+amount);
		setStatProfile(entity, level, statProfile);
		return true;
	}

	public static boolean addLevel(LivingEntity entity) {
		setLevel(entity, getLevel(entity)+1);
		return true;
	}

	public static void addLevelsOnSpawn(LivingEntity entity) {
		CompoundNBT compound;
		compound = getOrCreateRankNBT(entity);
		if(entity.isAlive()) {
			if (!entity.world.isRemote) {
				for (Stat stat : CombatRegistries.STATS) {
					if (!compound.contains(stat.getRegistryName().getPath())) {
						if (entity instanceof PlayerEntity)
							setStatProfile(entity, stat, new StatProfile(Rankup.statsManager.STATS.get(stat).getDefaultPoints(), Maps.newHashMap()));
						else
							setStatProfile(entity, stat, new StatProfile(StatEvents.calculatePointsFromBase(entity, stat), Maps.newHashMap()));

					}
				}
			}

			if (!compound.contains("experience")) {
				setExperience(entity, 0);
			}

			if (!compound.contains("level")) {
				setLevel(entity, 1);
				if (!(entity instanceof PlayerEntity)) {
					if (!Config.RPG_COMMON.keepMobsAtLevelOne.get() && entity.world instanceof ServerWorld) {
						BlockPos position = entity.getPosition();
						BlockPos spawn = ((ServerWorld)entity.world).getSpawnPoint();

						double distance = position.distanceSq(spawn);

						int level = 0;
						boolean continueFlag = true;
						while (continueFlag) {
							if (distance > distance(level, entity.world.getGameRules().getInt(GameRules.SPAWN_RADIUS))) {
								level++;
							} else {
								continueFlag = false;
							}
						}

						for (int i = 0; i < level; i++) {
							StatEvents.levelUp(entity, false);
						}
					}
				}
			}
		}
	}

	public static double distance(int range, int spawnRadius) {
		return (spawnRadius + Config.RPG_COMMON.distanceForLevelIncrease.get() + (Config.RPG_COMMON.distanceForLevelIncreaseStep.get() * range)) * (spawnRadius + Config.RPG_COMMON.distanceForLevelIncrease.get() + (Config.RPG_COMMON.distanceForLevelIncreaseStep.get() * range));
	}

	public static CompoundNBT getRankNBT(Entity entity) {
		return entity.getPersistentData().getCompound("combat:RankUp");
	}

	public static void setRankNBT(CompoundNBT nbt, Entity entity) {
		entity.getPersistentData().put("combat:RankUp", nbt);
	}

	public static CompoundNBT getOrCreateRankNBT(LivingEntity player) {
		if (!player.getPersistentData().contains("combat:RankUp", 10)) {
			player.getPersistentData().put("combat:RankUp", new CompoundNBT());
		}
		return player.getPersistentData().getCompound("combat:RankUp");
	}
}
