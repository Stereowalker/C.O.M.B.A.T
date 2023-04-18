package com.stereowalker.combat.data.worldgen.placement;

import java.util.List;

import com.stereowalker.combat.data.worldgen.features.COreFeatures;
import com.stereowalker.old.combat.config.Config;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class COrePlacements {
//	public static final Holder<PlacedFeature> DEAD_ORE_COPPER = register("dead_ore_copper", COreFeatures.DEAD_ORE_COPPER, OrePlacements.commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(96))));
//	public static final Holder<PlacedFeature> DEAD_ORE_COAL = register("dead_ore_coal", COreFeatures.DEAD_ORE_COAL, OrePlacements.commonOrePlacement(20, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127))));
//	public static final Holder<PlacedFeature> DEAD_ORE_IRON = register("dead_ore_iron", COreFeatures.DEAD_ORE_IRON, OrePlacements.commonOrePlacement(20, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63))));
//	public static final Holder<PlacedFeature> DEAD_ORE_GOLD = register("dead_ore_gold", COreFeatures.DEAD_ORE_GOLD, OrePlacements.commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(31))));
//	public static final Holder<PlacedFeature> DEAD_ORE_REDSTONE = register("dead_ore_redstone", COreFeatures.DEAD_ORE_REDSTONE, OrePlacements.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15))));
//	public static final Holder<PlacedFeature> DEAD_ORE_DIAMOND = register("dead_ore_diamond", COreFeatures.DEAD_ORE_DIAMOND, OrePlacements.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15))));
//	public static final Holder<PlacedFeature> DEAD_ORE_LAPIS = register("dead_ore_lapis", COreFeatures.DEAD_ORE_LAPIS, OrePlacements.commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(30))));
//	public static final Holder<PlacedFeature> DEAD_ORE_PASQUEM = register("dead_ore_pasquem", COreFeatures.DEAD_ORE_PASQUEM, OrePlacements.commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));
//
//
//	public static Holder<PlacedFeature> register(String p_206510_, Holder<? extends ConfiguredFeature<?, ?>> p_206511_, List<PlacementModifier> p_206512_) {
//		return PlacementUtils.register("combat"+p_206510_, p_206511_, p_206512_);
//	}
//
//	public static Holder<PlacedFeature> register(String p_206514_, Holder<? extends ConfiguredFeature<?, ?>> p_206515_, PlacementModifier... p_206516_) {
//		return PlacementUtils.register("combat"+p_206514_, p_206515_, p_206516_);
//	}

	public static final ResourceKey<PlacedFeature> ORE_PELGAN = PlacementUtils.createKey("combat:ore_plegan");
	public static final ResourceKey<PlacedFeature> ORE_LOZYNE = PlacementUtils.createKey("combat:ore_lozyne");
	public static final ResourceKey<PlacedFeature> ORE_SERABLE = PlacementUtils.createKey("combat:ore_serable");
	public static final ResourceKey<PlacedFeature> ORE_PYRANITE = PlacementUtils.createKey("combat:ore_pyranite");
	public static final ResourceKey<PlacedFeature> ORE_RUBY = PlacementUtils.createKey("combat:ore_ruby");
	public static final ResourceKey<PlacedFeature> ORE_TRIDOX = PlacementUtils.createKey("combat:ore_tridox");
	public static final ResourceKey<PlacedFeature> ORE_PASQUEM = PlacementUtils.createKey("combat:ore_pasquem");
	public static final ResourceKey<PlacedFeature> ORE_LIMESTONE = PlacementUtils.createKey("combat:ore_limestone");
	public static final ResourceKey<PlacedFeature> ORE_CASSITERITE = PlacementUtils.createKey("combat:ore_cassiterite");
	public static final ResourceKey<PlacedFeature> ORE_TRIDOX_MAGIC = PlacementUtils.createKey("combat:ore_tridox_magic");
	public static final ResourceKey<PlacedFeature> ORE_YELLOW_CLUSTER = PlacementUtils.createKey("combat:ore_yellow_cluster");
	public static final ResourceKey<PlacedFeature> ORE_YELLOW_CLUSTER_MAGIC = PlacementUtils.createKey("combat:ore_yellow_cluster_magic");

	public static void bootstrap(BootstapContext<PlacedFeature> p_256238_) {
		HolderGetter<ConfiguredFeature<?, ?>> holdergetter = p_256238_.lookup(Registries.CONFIGURED_FEATURE);
		Holder<ConfiguredFeature<?, ?>> holder = holdergetter.getOrThrow(COreFeatures.ORE_YELLOW_CLUSTER_MAGIC);
		Holder<ConfiguredFeature<?, ?>> holder1 = holdergetter.getOrThrow(COreFeatures.ORE_YELLOW_CLUSTER);
		Holder<ConfiguredFeature<?, ?>> holder2 = holdergetter.getOrThrow(COreFeatures.ORE_TRIDOX_MAGIC);
		Holder<ConfiguredFeature<?, ?>> holder3 = holdergetter.getOrThrow(COreFeatures.ORE_CASSITERITE);
		Holder<ConfiguredFeature<?, ?>> holder4 = holdergetter.getOrThrow(COreFeatures.ORE_LIMESTONE);
		Holder<ConfiguredFeature<?, ?>> holder5 = holdergetter.getOrThrow(COreFeatures.ORE_PASQUEM);
		Holder<ConfiguredFeature<?, ?>> holder6 = holdergetter.getOrThrow(COreFeatures.ORE_TRIDOX);
		Holder<ConfiguredFeature<?, ?>> holder7 = holdergetter.getOrThrow(COreFeatures.ORE_RUBY);
		Holder<ConfiguredFeature<?, ?>> holder8 = holdergetter.getOrThrow(COreFeatures.ORE_PYRANITE);
		Holder<ConfiguredFeature<?, ?>> holder9 = holdergetter.getOrThrow(COreFeatures.ORE_SERABLE);
		Holder<ConfiguredFeature<?, ?>> holder10 = holdergetter.getOrThrow(COreFeatures.ORE_LOZYNE);
		Holder<ConfiguredFeature<?, ?>> holder11 = holdergetter.getOrThrow(COreFeatures.ORE_PELGAN);
		
		Holder<ConfiguredFeature<?, ?>> holder12 = holdergetter.getOrThrow(OreFeatures.ORE_COAL);
		Holder<ConfiguredFeature<?, ?>> holder13 = holdergetter.getOrThrow(OreFeatures.ORE_COAL_BURIED);
		Holder<ConfiguredFeature<?, ?>> holder14 = holdergetter.getOrThrow(OreFeatures.ORE_IRON);
		Holder<ConfiguredFeature<?, ?>> holder15 = holdergetter.getOrThrow(OreFeatures.ORE_IRON_SMALL);
		Holder<ConfiguredFeature<?, ?>> holder16 = holdergetter.getOrThrow(OreFeatures.ORE_GOLD);
		Holder<ConfiguredFeature<?, ?>> holder17 = holdergetter.getOrThrow(OreFeatures.ORE_GOLD_BURIED);
		Holder<ConfiguredFeature<?, ?>> holder18 = holdergetter.getOrThrow(OreFeatures.ORE_REDSTONE);
		Holder<ConfiguredFeature<?, ?>> holder19 = holdergetter.getOrThrow(OreFeatures.ORE_DIAMOND_SMALL);
		Holder<ConfiguredFeature<?, ?>> holder20 = holdergetter.getOrThrow(OreFeatures.ORE_DIAMOND_LARGE);
		Holder<ConfiguredFeature<?, ?>> holder21 = holdergetter.getOrThrow(OreFeatures.ORE_DIAMOND_BURIED);
		Holder<ConfiguredFeature<?, ?>> holder22 = holdergetter.getOrThrow(OreFeatures.ORE_LAPIS);
		Holder<ConfiguredFeature<?, ?>> holder23 = holdergetter.getOrThrow(OreFeatures.ORE_LAPIS_BURIED);
		Holder<ConfiguredFeature<?, ?>> holder24 = holdergetter.getOrThrow(OreFeatures.ORE_INFESTED);
		Holder<ConfiguredFeature<?, ?>> holder25 = holdergetter.getOrThrow(OreFeatures.ORE_EMERALD);
		Holder<ConfiguredFeature<?, ?>> holder26 = holdergetter.getOrThrow(OreFeatures.ORE_ANCIENT_DEBRIS_LARGE);
		Holder<ConfiguredFeature<?, ?>> holder27 = holdergetter.getOrThrow(OreFeatures.ORE_ANCIENT_DEBRIS_SMALL);
		Holder<ConfiguredFeature<?, ?>> holder28 = holdergetter.getOrThrow(OreFeatures.ORE_COPPPER_SMALL);
		Holder<ConfiguredFeature<?, ?>> holder29 = holdergetter.getOrThrow(OreFeatures.ORE_COPPER_LARGE);
		Holder<ConfiguredFeature<?, ?>> holder30 = holdergetter.getOrThrow(OreFeatures.ORE_CLAY);
		PlacementUtils.register(p_256238_, ORE_YELLOW_CLUSTER_MAGIC, holder, OrePlacements.commonOrePlacement(190, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256))));
		PlacementUtils.register(p_256238_, ORE_YELLOW_CLUSTER, holder1, OrePlacements.commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256))));
		PlacementUtils.register(p_256238_, ORE_TRIDOX_MAGIC, holder2, OrePlacements.commonOrePlacement(40, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(27))));
		PlacementUtils.register(p_256238_, ORE_CASSITERITE, holder3, OrePlacements.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(63))));
		PlacementUtils.register(p_256238_, ORE_LIMESTONE, holder4, OrePlacements.commonOrePlacement(40, HeightRangePlacement.triangle(VerticalAnchor.absolute(31), VerticalAnchor.absolute(62))));
		PlacementUtils.register(p_256238_, ORE_PASQUEM, holder5, OrePlacements.commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));
		PlacementUtils.register(p_256238_, ORE_TRIDOX, holder6, OrePlacements.commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(27))));
		PlacementUtils.register(p_256238_, ORE_RUBY, holder7, OrePlacements.commonOrePlacement(100, HeightRangePlacement.triangle(VerticalAnchor.absolute(4), VerticalAnchor.absolute(31))));
		PlacementUtils.register(p_256238_, ORE_PYRANITE, holder8, OrePlacements.commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))));
		PlacementUtils.register(p_256238_, ORE_SERABLE, holder9, OrePlacements.commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(32))));
		PlacementUtils.register(p_256238_, ORE_LOZYNE, holder10, OrePlacements.commonOrePlacement(1, HeightRangePlacement.triangle(VerticalAnchor.absolute(-120), VerticalAnchor.absolute(60))));
		PlacementUtils.register(p_256238_, ORE_PELGAN, holder8, OrePlacements.commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(60), VerticalAnchor.absolute(200))));
//		PlacementUtils.register(p_256238_, ORE_DIORITE_UPPER, holder9, rareOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(128))));
//		PlacementUtils.register(p_256238_, ORE_DIORITE_LOWER, holder9, commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(60))));
//		PlacementUtils.register(p_256238_, ORE_ANDESITE_UPPER, holder10, rareOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(128))));
//		PlacementUtils.register(p_256238_, ORE_ANDESITE_LOWER, holder10, commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(60))));
//		PlacementUtils.register(p_256238_, ORE_TUFF, holder11, commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));
//		PlacementUtils.register(p_256238_, ORE_COAL_UPPER, holder12, commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.absolute(136), VerticalAnchor.top())));
//		PlacementUtils.register(p_256238_, ORE_COAL_LOWER, holder13, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(192))));
//		PlacementUtils.register(p_256238_, ORE_IRON_UPPER, holder14, commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));
//		PlacementUtils.register(p_256238_, ORE_IRON_MIDDLE, holder14, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
//		PlacementUtils.register(p_256238_, ORE_IRON_SMALL, holder15, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72))));
//		PlacementUtils.register(p_256238_, ORE_GOLD_EXTRA, holder16, commonOrePlacement(50, HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(256))));
//		PlacementUtils.register(p_256238_, ORE_GOLD, holder17, commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
//		PlacementUtils.register(p_256238_, ORE_GOLD_LOWER, holder17, orePlacement(CountPlacement.of(UniformInt.of(0, 1)), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-48))));
//		PlacementUtils.register(p_256238_, ORE_REDSTONE, holder18, commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15))));
//		PlacementUtils.register(p_256238_, ORE_REDSTONE_LOWER, holder18, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))));
//		PlacementUtils.register(p_256238_, ORE_DIAMOND, holder19, commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
//		PlacementUtils.register(p_256238_, ORE_DIAMOND_LARGE, holder20, rareOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
//		PlacementUtils.register(p_256238_, ORE_DIAMOND_BURIED, holder21, commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
//		PlacementUtils.register(p_256238_, ORE_LAPIS, holder22, commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
//		PlacementUtils.register(p_256238_, ORE_LAPIS_BURIED, holder23, commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(64))));
//		PlacementUtils.register(p_256238_, ORE_INFESTED, holder24, commonOrePlacement(14, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63))));
//		PlacementUtils.register(p_256238_, ORE_EMERALD, holder25, commonOrePlacement(100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480))));
//		PlacementUtils.register(p_256238_, ORE_ANCIENT_DEBRIS_LARGE, holder26, InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(8), VerticalAnchor.absolute(24)), BiomeFilter.biome());
//		PlacementUtils.register(p_256238_, ORE_ANCIENT_DEBRIS_SMALL, holder27, InSquarePlacement.spread(), PlacementUtils.RANGE_8_8, BiomeFilter.biome());
//		PlacementUtils.register(p_256238_, ORE_COPPER, holder28, commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
//		PlacementUtils.register(p_256238_, ORE_COPPER_LARGE, holder29, commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
//		PlacementUtils.register(p_256238_, ORE_CLAY, holder30, commonOrePlacement(46, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
	}
}
