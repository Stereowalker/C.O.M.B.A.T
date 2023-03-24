package com.stereowalker.rankup.world.stat;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.stat.Stat;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class Stats {
	public static final List<Stat> STATS = new ArrayList<Stat>();
	public static final ResourceKey<Stat> VITALITY_KEY = ResourceKey.create(CombatRegistries.STATS_REGISTRY, new ResourceLocation("combat:vitality"));
	public static final Stat VITALITY = register(VITALITY_KEY, new VitalityStat());
	
	public static Stat register(ResourceKey<Stat> name, Stat stat) {
		stat.setRegistryName(name.location());
		STATS.add(stat);
		return stat;
	}
	
	public static void registerAll(IForgeRegistry<Stat> registry) {
		for(Stat stat : STATS) {
			registry.register(stat);
			Combat.debug("Leveled Stat: \""+stat.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Leveled Stats Registered");
	}
}
