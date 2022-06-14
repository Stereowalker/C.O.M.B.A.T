package com.stereowalker.combat.tags;

import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class BiomeCTags {
	public static final TagKey<Biome> IS_WARM_OCEAN = BiomeTags.create("combat:is_warm_ocean");
	public static final TagKey<Biome> HAS_ETHERION_TOWER = BiomeTags.create("combat:has_structure/etherion_tower");
	public static final TagKey<Biome> HAS_ETHERION_TOWER_ACROTLEST = BiomeTags.create("combat:has_structure/etherion_tower_acrotlest");
	public static final TagKey<Biome> ACROTLEST_MINESHAFT_NORMAL = BiomeTags.create("combat:has_structure/acrotlest_mineshaft_normal");
	public static final TagKey<Biome> ACROTLEST_PORTAL = BiomeTags.create("combat:has_structure/acrotlest_portal");
	public static final TagKey<Biome> OVERWORLD_PORTAL = BiomeTags.create("combat:has_structure/overworld_portal");
	public static final TagKey<Biome> MAGIC_STONE_DEPOSIT = BiomeTags.create("combat:has_structure/magic_stone_deposit");
}
