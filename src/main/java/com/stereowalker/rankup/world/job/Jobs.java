package com.stereowalker.rankup.world.job;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.stats.CStats;
import com.stereowalker.rankup.api.job.Job;
import com.stereowalker.rankup.skill.Skills;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class Jobs {
	public static final ResourceKey<Job> ARCHER_KEY = ResourceKey.create(CombatRegistries.JOBS_REGISTRY, new ResourceLocation("combat:archer"));
	
	public static void registerAll(RegisterHelper<Job> registry) {
		Map<ResourceKey<Job>,Job> JOBS = new HashMap<ResourceKey<Job>,Job>();
		JOBS.put(ARCHER_KEY, new Job(CStats.MOBS_KILLED_WITH_BOW, 10, 1, new int[]{100,300,600,1000}, Lists.newArrayList(Skills.ARCHERS_ELBOW, Skills.ARROW_SAVINGS), false));
		for(Entry<ResourceKey<Job>, Job> job : JOBS.entrySet()) {
			registry.register(job.getKey(), job.getValue());
			Combat.debug("Job: \""+job.getKey().location().toString()+"\" registered");
		}
		Combat.debug("All Jobs Registered");
	}
}
