package com.stereowalker.rankup.world.stat;

import com.google.common.collect.Maps;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;

public class PlayerAttributeLevels {
	public static StatProfile getStatPoints(LivingEntity entity, Stat stat) {
		StatProfile newStatProfile = new StatProfile(0, Maps.newHashMap());
		if(entity != null) {
			CompoundTag compound = getRankNBT(entity); 
			if (compound != null && compound.contains(stat.getRegistryName().getPath(), 10)) {
				newStatProfile.read(compound.getCompound(stat.getRegistryName().getPath()));
				return newStatProfile;
			}
		}
		return newStatProfile;
	}

	public static int getExperience(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = entity.getPersistentData().getCompound("combat:RankUp"); 
			return compound.getInt("experience");
		}
		return 0;
	}

	public static int getLevel(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = entity.getPersistentData().getCompound("combat:RankUp"); 
			return compound.getInt("level");
		}
		return 0;
	}

	public static int getUpgradePoints(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = entity.getPersistentData().getCompound("combat:RankUp"); 
			return compound.getInt("upgrade_points");
		}
		return 0;
	}

	public static boolean hasInitPlayer(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = entity.getPersistentData().getCompound("combat:RankUp"); 
			return compound.getBoolean("init_player");
		}
		return false;
	}

	public static void setStatProfile(LivingEntity entity, Stat stat, StatProfile statPoints) {
		CompoundTag compound = entity.getPersistentData().getCompound("combat:RankUp"); 
		CompoundTag nbt = new CompoundTag();
		statPoints.write(nbt);
		compound.put(stat.getRegistryName().getPath(), nbt);
	}

	public static void setExperience(LivingEntity entity, int experience) {
		CompoundTag compound = entity.getPersistentData().getCompound("combat:RankUp"); 
		compound.putInt("experience", experience);
	}

	public static void setLevel(LivingEntity entity, int level) {
		CompoundTag compound = entity.getPersistentData().getCompound("combat:RankUp"); 
		compound.putInt("level", level);
	}

	public static void setUpgradePoints(LivingEntity entity, int upgradePoints) {
		CompoundTag compound = entity.getPersistentData().getCompound("combat:RankUp"); 
		compound.putInt("upgrade_points", upgradePoints);
	}

	public static void setPlayerInitialization(LivingEntity entity, boolean value) {
		CompoundTag compound = entity.getPersistentData().getCompound("combat:RankUp"); 
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

	public static boolean addUpgradePoints(LivingEntity entity, int points) {
		if (getUpgradePoints(entity)+points >= 0) {
			setUpgradePoints(entity, getUpgradePoints(entity)+points);
			return true;
		} else {
			return false;
		}
	}

	public static void addLevelsOnSpawn(LivingEntity entity) {
		CompoundTag compound;
		compound = getOrCreateRankNBT(entity);
		if(entity.isAlive()) {
			if (!entity.level.isClientSide) {
				for (Stat stat : CombatRegistries.STATS) {
					if (!compound.contains(stat.getRegistryName().getPath())) {
						if (entity instanceof Player)
							setStatProfile(entity, stat, new StatProfile(Rankup.statsManager.STATS.get(stat).getDefaultPoints(), Maps.newHashMap()));
						else
							setStatProfile(entity, stat, new StatProfile(StatEvents.calculatePointsFromBase(entity, stat), Maps.newHashMap()));

					}
				}
			}

			if (!compound.contains("experience")) {
				setExperience(entity, 0);
			}
			
			if (!compound.contains("upgrade_points")) {
				setUpgradePoints(entity, 0);
			}

			if (!compound.contains("level")) {
				setLevel(entity, 1);
				if (!(entity instanceof Player)) {
					if (!Combat.RPG_CONFIG.keepMobsAtLevelOne && entity.level instanceof ServerLevel) {
						BlockPos position = entity.blockPosition();
						BlockPos spawn = ((ServerLevel)entity.level).getSharedSpawnPos();

						double distance = position.distSqr(spawn);

						int level = 0;
						boolean continueFlag = true;
						while (continueFlag) {
							if (distance > distance(level, entity.level.getGameRules().getInt(GameRules.RULE_SPAWN_RADIUS))) {
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
		return (spawnRadius + Combat.RPG_CONFIG.distanceForLevelIncrease + (Combat.RPG_CONFIG.distanceForLevelIncreaseStep * range)) * (spawnRadius + Combat.RPG_CONFIG.distanceForLevelIncrease + (Combat.RPG_CONFIG.distanceForLevelIncreaseStep * range));
	}

	public static CompoundTag getRankNBT(Entity entity) {
		return entity.getPersistentData().getCompound("combat:RankUp");
	}

	public static void setRankNBT(CompoundTag nbt, Entity entity) {
		entity.getPersistentData().put("combat:RankUp", nbt);
	}

	public static CompoundTag getOrCreateRankNBT(LivingEntity player) {
		if (!player.getPersistentData().contains("combat:RankUp", 10)) {
			player.getPersistentData().put("combat:RankUp", new CompoundTag());
		}
		return player.getPersistentData().getCompound("combat:RankUp");
	}
}
