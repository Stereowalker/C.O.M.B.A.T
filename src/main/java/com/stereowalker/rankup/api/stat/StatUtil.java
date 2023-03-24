package com.stereowalker.rankup.api.stat;

import com.stereowalker.combat.api.registries.CombatRegistries;

public class StatUtil {

	public static Stat getStatFromId(int id) {
		if(id <= CombatRegistries.STATS.getValues().size()) {
			return (Stat) CombatRegistries.STATS.getValues().toArray()[id];
		}
		return null;
	}

	public static Stat getStatFromName(String name) {
		for(Stat stat : CombatRegistries.STATS) {
			if(stat.getKey().contentEquals(name)) {
				return stat;
			}
		}
		return null;
	}
}
