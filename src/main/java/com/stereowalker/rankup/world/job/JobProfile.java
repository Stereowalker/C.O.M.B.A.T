package com.stereowalker.rankup.world.job;

import com.stereowalker.rankup.api.job.Job;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;

public class JobProfile {
	int experience;
	int level;

	public JobProfile(int experienceIn, int level) {
		this.experience = experienceIn;
		this.level = level;
	}

	/**
	 * @param points the points to set
	 */
	public void setExperience(int points) {
		this.experience = points;
	}
	
	public void addExperience(int points) {
		this.experience+=points;
	}
	public void setLevel(int points) {
		this.level = points;
	}
	
	public void addLevel(int points) {
		this.level+=points;
	}
	
	public static void setExperience(LivingEntity entity, ResourceKey<Job> job, int points) {
		JobProfile jobProfile = PlayerJobs.getJobProfile(entity, job);
		jobProfile.setExperience(points);
		PlayerJobs.setJobProfile(entity, job, jobProfile);
	}

	public void giveJob() {
		this.level = 1;
	}
	
	public static void giveJob(LivingEntity entity, ResourceKey<Job> job) {
		JobProfile jobProfile = PlayerJobs.getJobProfile(entity, job);
		jobProfile.giveJob();
		PlayerJobs.setJobProfile(entity, job, jobProfile);
	}
	
	/**
	 * @return the points
	 */
	public int getExperience() {
		return experience;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean hasJob() {
		return this.level >= 1;
	}

	/**
	 * Reads the water data for the player.
	 */
	public void read(CompoundTag compound) {
		if (compound.contains("experience", 99)) {
			this.experience = compound.getInt("experience");
			this.level = compound.getInt("level");
		}
	}

	/**
	 * Writes the water data for the player.
	 */
	public void write(CompoundTag compound) {
		compound.putInt("experience", this.experience);
		compound.putInt("level", this.level);
	}
}
