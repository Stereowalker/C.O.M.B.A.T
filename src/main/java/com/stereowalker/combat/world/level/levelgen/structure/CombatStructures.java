package com.stereowalker.combat.world.level.levelgen.structure;

import com.stereowalker.combat.Combat;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public interface CombatStructures {
	ResourceKey<ConfiguredStructureFeature<?, ?>> ETHERION_TOWER = createKey("etherion_tower");
	ResourceKey<ConfiguredStructureFeature<?, ?>> ETHERION_TOWER_DESERT = createKey("etherion_tower_desert");
	ResourceKey<ConfiguredStructureFeature<?, ?>> ETHERION_TOWER_BADLANDS = createKey("etherion_tower_badlands");
	ResourceKey<ConfiguredStructureFeature<?, ?>> ETHERION_TOWER_ACROTLEST = createKey("etherion_tower_acrotlest");
	ResourceKey<ConfiguredStructureFeature<?, ?>> ACROTLEST_MINESHAFT_NORMAL = createKey("acrotlest_mineshaft");
	ResourceKey<ConfiguredStructureFeature<?, ?>> ACROTLEST_PORTAL = createKey("acrotlest_portal");
	ResourceKey<ConfiguredStructureFeature<?, ?>> OVERWORLD_PORTAL = createKey("overworld_portal");
	ResourceKey<ConfiguredStructureFeature<?, ?>> MAGIC_STONE_DEPOSIT = createKey("magic_stone_deposit");

	private static ResourceKey<ConfiguredStructureFeature<?, ?>> createKey(String p_209873_) {
		return ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Combat.MODID, p_209873_));
	}
}
