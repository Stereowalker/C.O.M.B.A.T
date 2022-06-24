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
	
//	public static final Stat AGILITY = register("agility", new AgilityStat());
//	public static final Stat MAGIC = register("magic", new Stat("0476ba86-f7ee-4f60-9323-688f69709b9d"));
//	public static final Stat DEFENCE = register("defence", new Stat("1ce15570-ccb7-4936-9fb6-bb75369e8518"));
//	public static final Stat STRENGTH = register("strength", new Stat("caaa7df7-f495-4615-a43d-aa87d1d75c46"));
//	public static final Stat LUCK = register("luck", new Stat("0831eba4-f4ec-4b73-b3cb-e6834a9795d7"));
	
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
