package com.stereowalker.rankup.world.stat;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.stat.Stat;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class Stats {
	public static final ResourceKey<Stat> VITALITY_KEY = ResourceKey.create(CombatRegistries.STATS_REGISTRY, new ResourceLocation("combat:vitality"));
	
	public static void registerAll(RegisterHelper<Stat> registry) {
		registry.register(VITALITY_KEY, new VitalityStat());
	}
}
