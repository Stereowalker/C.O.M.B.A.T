package com.stereowalker.combat.world.level.levelgen.structure;

import com.stereowalker.combat.Combat;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public interface CombatStructureSets {
	ResourceKey<StructureSet> ETHERION_TOWERS = createKey("etherion_towers");
	ResourceKey<StructureSet> ACROTLEST_MINESHAFTS = createKey("acrotlest_mineshafts");
	ResourceKey<StructureSet> ACROTLEST_PORTALS = createKey("acrotlest_portals");
	ResourceKey<StructureSet> MAGIC_STONE_DEPOSITS = createKey("magic_stone_deposits");

	private static ResourceKey<StructureSet> createKey(String p_209873_) {
		return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(Combat.MODID, p_209873_));
	}
}
