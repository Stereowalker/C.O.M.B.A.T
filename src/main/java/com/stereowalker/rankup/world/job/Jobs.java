package com.stereowalker.rankup.world.job;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.stats.CStats;
import com.stereowalker.rankup.api.job.Job;
import com.stereowalker.rankup.skill.Skills;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class Jobs {
	public static final List<Job> JOBS = new ArrayList<Job>();
	public static final ResourceKey<Job> ARCHER_KEY = ResourceKey.create(CombatRegistries.JOBS_REGISTRY, new ResourceLocation("combat:archer"));
	
	public static Job register(ResourceKey<Job> name, Job job) {
		job.setRegistryName(name.location());
		JOBS.add(job);
		return job;
	}
	
	public static void registerAll(IForgeRegistry<Job> registry) {
		register(ARCHER_KEY, new Job(CStats.MOBS_KILLED_WITH_BOW, 10, 1, new int[]{100,300,600,1000}, Lists.newArrayList(Skills.ARCHERS_ELBOW, Skills.ARROW_SAVINGS), false));
		for(Job job : JOBS) {
			registry.register(job);
			Combat.debug("Job: \""+job.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Jobs Registered");
	}
}
