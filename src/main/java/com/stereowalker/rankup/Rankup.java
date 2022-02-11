package com.stereowalker.rankup;

import com.google.common.collect.ImmutableMap;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.network.protocol.game.RankupNetRegistry;
import com.stereowalker.rankup.resource.StatsManager;
import com.stereowalker.rankup.world.stat.StatSettings;

public class Rankup {
	
	public static final StatsManager statsManager = new StatsManager();
	
	public ImmutableMap<Stat,StatSettings> CLIENT_STATS = ImmutableMap.of();
	
	public Rankup() {
		RankupNetRegistry.registerMessages();
	}
}
