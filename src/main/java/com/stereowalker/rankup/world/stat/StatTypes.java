package com.stereowalker.rankup.world.stat;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.stat.StatType;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class StatTypes {
	public static final ResourceKey<StatType> DEFAULT_KEY = ResourceKey.create(CombatRegistries.STAT_TYPES_REGISTRY, new ResourceLocation("combat:default"));
	public static final ResourceKey<StatType> VITALITY_KEY = ResourceKey.create(CombatRegistries.STAT_TYPES_REGISTRY, new ResourceLocation("combat:vitality"));
	
	public static void registerAll(RegisterHelper<StatType> registry) {
		registry.register(DEFAULT_KEY, new DefaultStat());
		registry.register(VITALITY_KEY, new VitalityStat());
	}
}
