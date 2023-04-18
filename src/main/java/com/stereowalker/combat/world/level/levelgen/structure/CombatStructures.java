package com.stereowalker.combat.world.level.levelgen.structure;

import com.stereowalker.combat.Combat;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

public interface CombatStructures {
	ResourceKey<Structure> ETHERION_TOWER = createKey("etherion_tower");
	ResourceKey<Structure> ETHERION_TOWER_DESERT = createKey("etherion_tower_desert");
	ResourceKey<Structure> ETHERION_TOWER_BADLANDS = createKey("etherion_tower_badlands");
	ResourceKey<Structure> ETHERION_TOWER_ACROTLEST = createKey("etherion_tower_acrotlest");
	ResourceKey<Structure> ACROTLEST_MINESHAFT_NORMAL = createKey("acrotlest_mineshaft");
	ResourceKey<Structure> ACROTLEST_PORTAL = createKey("acrotlest_portal");
	ResourceKey<Structure> OVERWORLD_PORTAL = createKey("overworld_portal");
	ResourceKey<Structure> MAGIC_STONE_DEPOSIT = createKey("magic_stone_deposit");

	private static ResourceKey<Structure> createKey(String p_209873_) {
		return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Combat.MODID, p_209873_));
	}
}
