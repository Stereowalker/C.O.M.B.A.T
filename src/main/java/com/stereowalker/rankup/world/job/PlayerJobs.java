package com.stereowalker.rankup.world.job;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.job.Job;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PlayerJobs {
	public static JobProfile getJobProfile(LivingEntity entity, ResourceKey<Job> key) {
		JobProfile newStatProfile = new JobProfile(0, 0);
		if(entity != null) {
			CompoundTag compound = getRankNBT(entity); 
			if (compound != null && compound.contains(key.location().toString(), 10)) {
				newStatProfile.read(compound.getCompound(key.location().toString()));
				return newStatProfile;
			}
		}
		return newStatProfile;
	}

	public static void setJobProfile(LivingEntity entity, ResourceKey<Job> key, JobProfile statPoints) {
		CompoundTag nbt = new CompoundTag();
		statPoints.write(nbt);
		getRankNBT(entity).put(key.location().toString(), nbt);
	}

	public static void addLevelsOnSpawn(LivingEntity entity) {
		CompoundTag compound;
		compound = getOrCreateRankNBT(entity);
		if(entity.isAlive()) {
			if (!entity.level.isClientSide && entity instanceof Player) {
				entity.getLevel().registryAccess().registryOrThrow(CombatRegistries.JOBS_REGISTRY).entrySet().forEach((job) -> {
					if (!compound.contains(job.getKey().location().toString())) {
						setJobProfile(entity, job.getKey(), new JobProfile(0, 0));
					}
				});
			}
		}
	}

	public static CompoundTag getRankNBT(Entity entity) {
		return entity.getPersistentData().getCompound("combat:rankup_jobs");
	}

	public static void setRankNBT(CompoundTag nbt, Entity entity) {
		entity.getPersistentData().put("combat:rankup_jobs", nbt);
	}

	public static CompoundTag getOrCreateRankNBT(LivingEntity player) {
		if (!player.getPersistentData().contains("combat:rankup_jobs", 10)) {
			player.getPersistentData().put("combat:rankup_jobs", new CompoundTag());
		}
		return player.getPersistentData().getCompound("combat:rankup_jobs");
	}
}
