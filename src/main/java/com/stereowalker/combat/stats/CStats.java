package com.stereowalker.combat.stats;

import com.stereowalker.combat.Combat;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class CStats {
//	public static final List<StatType<?>> STATTYPES = new ArrayList<StatType<?>>();

	public static final ResourceLocation SHOTS_FIRED = registerCustom("shots_fired", IStatFormatter.DEFAULT);
	public static final ResourceLocation GUNS_RELOADED = registerCustom("guns_reloaded", IStatFormatter.DEFAULT);
	public static final ResourceLocation SPELLS_CASTED = registerCustom("spells_casted", IStatFormatter.DEFAULT);
	public static final ResourceLocation INTERACT_WITH_WOODCUTTER = registerCustom("interact_with_woodcutter", IStatFormatter.DEFAULT);

//		private static ResourceLocation registerCustom(String key, IStatFormatter formatter) {
//			ResourceLocation resourcelocation = Combat.getInstance().location(key);
//			StatType<ResourceLocation> statType = new StatType<>(Registry.CUSTOM_STAT);
//			statType.setRegistryName(resourcelocation);
//			Stats.CUSTOM.get(resourcelocation, formatter);
//			CStats.STATTYPES.add(statType);
//			return resourcelocation;
//		}

	private static ResourceLocation registerCustom(String key, IStatFormatter formatter) {
		ResourceLocation resourcelocation = Combat.getInstance().location(key);
		Registry.register(Registry.CUSTOM_STAT, key, resourcelocation);
		Stats.CUSTOM.get(resourcelocation, formatter);
		return resourcelocation;
	}
//
//	public static <T> StatType<T> register(String name, Registry<T> registry) {
//		StatType<T> statType = new StatType<>(registry);
//		statType.setRegistryName(Combat.location(name));
//		CStats.STATTYPES.add(statType);
//		return statType;
//	}
//
//	public static void registerAll(IForgeRegistry<StatType<?>> registry) {
//		for(StatType<?> statType : STATTYPES) {
//			registry.register(statType);
//			Combat.debug("StatType: \""+statType.getRegistryName().toString()+"\" registered");
//		}
//		Combat.debug("All StatTypes Registered");
//	}

}
