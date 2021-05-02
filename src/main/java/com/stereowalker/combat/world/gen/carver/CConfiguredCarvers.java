package com.stereowalker.combat.world.gen.carver;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CConfiguredCarvers {
	public static final List<Pair<String,ConfiguredCarver<?>>> CONFIGURED_CARVERS = new ArrayList<Pair<String,ConfiguredCarver<?>>>();
	
	public static final ConfiguredCarver<ProbabilityConfig> ACROTLEST_CAVE = func_243773_a("acrotlest_cave", CWorldCarver.ACROTLEST_CAVE.func_242761_a(new ProbabilityConfig(0.14285715F)));
	public static final ConfiguredCarver<ProbabilityConfig> ACROTLEST_CANYON = func_243773_a("acrotlest_canyon", CWorldCarver.ACROTLEST_CANYON.func_242761_a(new ProbabilityConfig(0.02F)));

	private static <WC extends ICarverConfig> ConfiguredCarver<WC> func_243773_a(String name, ConfiguredCarver<WC> configuredCarver) {
		CONFIGURED_CARVERS.add(Pair.of(name, configuredCarver));
		return configuredCarver;
	}
}
