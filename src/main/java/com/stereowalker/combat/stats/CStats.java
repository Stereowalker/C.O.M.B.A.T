package com.stereowalker.combat.stats;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraftforge.registries.IForgeRegistry;

public class CStats {
	private static final List<StatType<?>> STATTYPES = new ArrayList<StatType<?>>();
	private static final List<ResourceLocation> CUSTOMSTATS = new ArrayList<ResourceLocation>();

	public static final ResourceLocation SHOTS_FIRED = registerCustom("shots_fired", StatFormatter.DEFAULT);
	public static final ResourceLocation GUNS_RELOADED = registerCustom("guns_reloaded", StatFormatter.DEFAULT);
	public static final ResourceLocation SPELLS_CASTED = registerCustom("spells_casted", StatFormatter.DEFAULT);
	public static final ResourceLocation INTERACT_WITH_WOODCUTTER = registerCustom("interact_with_woodcutter", StatFormatter.DEFAULT);
	public static final ResourceLocation DAMAGE_BLOCKED_BY_WEAPON = registerCustom("damage_blocked_by_weapon", StatFormatter.DIVIDE_BY_TEN);

	private static ResourceLocation registerCustom(String key, StatFormatter formatter) {
		ResourceLocation resourcelocation = Combat.getInstance().location(key);
		Registry.register(Registry.CUSTOM_STAT, key, resourcelocation);
		Stats.CUSTOM.get(resourcelocation, formatter);
		CUSTOMSTATS.add(resourcelocation);
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
		Combat.debug("All StatTypes Registered");
	}

}
