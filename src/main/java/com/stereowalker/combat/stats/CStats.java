package com.stereowalker.combat.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.stereowalker.combat.Combat;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraftforge.registries.IForgeRegistry;

public class CStats {
	private static final List<StatType<?>> STATTYPES = new ArrayList<StatType<?>>();
	private static final Map<ResourceLocation,StatFormatter> CUSTOMSTATS = new HashMap<ResourceLocation,StatFormatter>();

	public static final ResourceLocation SHOTS_FIRED = registerCustom("shots_fired", StatFormatter.DEFAULT);
	public static final ResourceLocation MOBS_KILLED_WITH_BOW = registerCustom("mobs_killed_with_bow", StatFormatter.DEFAULT);
	public static final ResourceLocation GUNS_RELOADED = registerCustom("guns_reloaded", StatFormatter.DEFAULT);
	public static final ResourceLocation SPELLS_CASTED = registerCustom("spells_casted", StatFormatter.DEFAULT);
	public static final ResourceLocation INTERACT_WITH_WOODCUTTER = registerCustom("interact_with_woodcutter", StatFormatter.DEFAULT);
	public static final ResourceLocation DAMAGE_BLOCKED_BY_WEAPON = registerCustom("damage_blocked_by_weapon", StatFormatter.DIVIDE_BY_TEN);

	private static ResourceLocation registerCustom(String key, StatFormatter formatter) {
		ResourceLocation resourcelocation = Combat.getInstance().location(key);
		CUSTOMSTATS.put(resourcelocation, formatter);
		return resourcelocation;
	}
//
//	public static <T> StatType<T> register(String name, Registry<T> registry) {
//		StatType<T> statType = new StatType<>(registry);
//		statType.setRegistryName(Combat.location(name));
//		CStats.STATTYPES.add(statType);
//		return statType;
//	}

	public static void registerAll(IForgeRegistry<StatType<?>> registry) {
		for(StatType<?> statType : STATTYPES) {
			registry.register(statType);
			Combat.debug("StatType: \""+statType.getRegistryName().toString()+"\" registered");
		}
		for(Entry<ResourceLocation, StatFormatter> statType : CUSTOMSTATS.entrySet()) {
			Registry.register(Registry.CUSTOM_STAT, statType.getKey().toString(), statType.getKey());
			Stats.CUSTOM.get(statType.getKey(), statType.getValue());
			Combat.debug("Custom Stat: \""+statType.getKey()+"\" registered");
		}
		Combat.debug("All StatTypes Registered");
	}

}
