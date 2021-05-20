package com.stereowalker.rankup;

import com.google.common.collect.ImmutableMap;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.network.RankupNetRegistry;
import com.stereowalker.rankup.resource.StatsManager;
import com.stereowalker.rankup.stat.StatSettings;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Rankup {
	
	public static final StatsManager statsManager = new StatsManager();
	
	public ImmutableMap<Stat,StatSettings> CLIENT_STATS = ImmutableMap.of();
	
	public Rankup() {
		RankupNetRegistry.registerMessages();
	}
}
