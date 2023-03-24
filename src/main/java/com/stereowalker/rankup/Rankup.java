package com.stereowalker.rankup;

import com.google.common.collect.ImmutableMap;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.resource.StatsManager;
import com.stereowalker.rankup.world.stat.StatSettings;

import net.minecraft.resources.ResourceKey;

public class Rankup {
	
	public static final StatsManager statsManager = new StatsManager();
	
	public ImmutableMap<ResourceKey<Stat>,StatSettings> CLIENT_STAT_SETTINGS = ImmutableMap.of();
	
	public Rankup() {
	}
}
