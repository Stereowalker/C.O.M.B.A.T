package com.stereowalker.combat.data.worldgen.placement;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.features.MiscAcrotlestFeatures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;

public class MiscAcrotlestPlacements {
	static String n(String n) {return Combat.MODID+":"+n;}
	public static final ResourceKey<PlacedFeature> SPRING_BIABLE = PlacementUtils.createKey("combat:spring_biable");
	public static final ResourceKey<PlacedFeature> TSUNE_SPIKE = PlacementUtils.createKey("combat:tsune_spike");
	public static final ResourceKey<PlacedFeature> MAGENTA_TSUNE_COLUMN = PlacementUtils.createKey("combat:magenta_tsune_column");
	public static final ResourceKey<PlacedFeature> LAKE_WHITE_TSUNE_UNDERGROUND = PlacementUtils.createKey("combat:lake_white_tsune_underground");
	public static final ResourceKey<PlacedFeature> LAKE_YELLOW_TSUNE_UNDERGROUND = PlacementUtils.createKey("combat:lake_yellow_tsune_underground");
	public static final ResourceKey<PlacedFeature> LAKE_RED_TSUNE_SURFACE = PlacementUtils.createKey("combat:lake_red_tsune_surface");
	public static final ResourceKey<PlacedFeature> LAKE_PURPLE_TSUNE_SURFACE = PlacementUtils.createKey("combat:lake_purple_tsune_surface");
	
	public static void init() {}

	public static void bootstrap(BootstapContext<PlacedFeature> p_255762_) {
		HolderGetter<ConfiguredFeature<?, ?>> holdergetter = p_255762_.lookup(Registries.CONFIGURED_FEATURE);
		Holder<ConfiguredFeature<?, ?>> holder = holdergetter.getOrThrow(MiscAcrotlestFeatures.SPRING_BIABLE);
		Holder<ConfiguredFeature<?, ?>> holder1 = holdergetter.getOrThrow(MiscAcrotlestFeatures.LAKE_WHITE_TSUNE);
		Holder<ConfiguredFeature<?, ?>> holder2 = holdergetter.getOrThrow(MiscAcrotlestFeatures.LAKE_YELLOW_TSUNE);
		Holder<ConfiguredFeature<?, ?>> holder3 = holdergetter.getOrThrow(MiscAcrotlestFeatures.LAKE_RED_TSUNE);
		Holder<ConfiguredFeature<?, ?>> holder4 = holdergetter.getOrThrow(MiscAcrotlestFeatures.LAKE_PURPLE_TSUNE);
		Holder<ConfiguredFeature<?, ?>> holder5 = holdergetter.getOrThrow(MiscAcrotlestFeatures.TSUNE_SPIKE);
		Holder<ConfiguredFeature<?, ?>> holder6 = holdergetter.getOrThrow(MiscAcrotlestFeatures.MAGENTA_TSUNE_COLUMN);
		PlacementUtils.register(p_255762_, SPRING_BIABLE, holder, CountPlacement.of(25), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(192)), BiomeFilter.biome());
		PlacementUtils.register(p_255762_, LAKE_WHITE_TSUNE_UNDERGROUND, holder1, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.top())), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 32), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5), BiomeFilter.biome());
		PlacementUtils.register(p_255762_, LAKE_YELLOW_TSUNE_UNDERGROUND, holder2, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.top())), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 32), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5), BiomeFilter.biome());
		PlacementUtils.register(p_255762_, LAKE_RED_TSUNE_SURFACE, holder3, RarityFilter.onAverageOnceEvery(130), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(p_255762_, LAKE_PURPLE_TSUNE_SURFACE, holder4, RarityFilter.onAverageOnceEvery(130), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		PlacementUtils.register(p_255762_, TSUNE_SPIKE, holder5, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		PlacementUtils.register(p_255762_, MAGENTA_TSUNE_COLUMN, holder6, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
	}
}
