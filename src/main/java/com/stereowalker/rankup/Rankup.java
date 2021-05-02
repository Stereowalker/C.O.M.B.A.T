package com.stereowalker.rankup;

import com.stereowalker.rankup.network.RankupNetRegistry;
import com.stereowalker.rankup.resource.StatsManager;

public class Rankup {
	
	public static final StatsManager statsManager = new StatsManager();
	
	public Rankup() {
		RankupNetRegistry.registerMessages();
	}
}
