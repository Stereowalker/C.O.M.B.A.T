package com.stereowalker.combat.data.worldgen.placement;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.features.MiscAcrotlestFeatures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
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
	public static final Holder<PlacedFeature> SPRING_BIABLE = PlacementUtils.register(n("spring_biable"), MiscAcrotlestFeatures.SPRING_BIABLE, CountPlacement.of(25), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(192)), BiomeFilter.biome());
	public static final Holder<PlacedFeature> LAKE_WHITE_TSUNE_UNDERGROUND = PlacementUtils.register(n("lake_white_tsune_underground"), MiscAcrotlestFeatures.LAKE_WHITE_TSUNE, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.top())), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 32), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5), BiomeFilter.biome());
	public static final Holder<PlacedFeature> LAKE_YELLOW_TSUNE_UNDERGROUND = PlacementUtils.register(n("lake_yellow_tsune_underground"), MiscAcrotlestFeatures.LAKE_YELLOW_TSUNE, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.top())), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 32), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5), BiomeFilter.biome());
	public static final Holder<PlacedFeature> LAKE_RED_TSUNE_SURFACE = PlacementUtils.register(n("lake_red_tsune_surface"), MiscAcrotlestFeatures.LAKE_RED_TSUNE, RarityFilter.onAverageOnceEvery(130), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	public static final Holder<PlacedFeature> LAKE_PURPLE_TSUNE_SURFACE = PlacementUtils.register(n("lake_purple_tsune_surface"), MiscAcrotlestFeatures.LAKE_PURPLE_TSUNE, RarityFilter.onAverageOnceEvery(130), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

	public static final Holder<PlacedFeature> TSUNE_SPIKE = PlacementUtils.register(n("tsune_spike"), MiscAcrotlestFeatures.TSUNE_SPIKE, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
	public static final Holder<PlacedFeature> MAGENTA_TSUNE_COLUMN = PlacementUtils.register(n("magenta_tsune_column"), MiscAcrotlestFeatures.MAGENTA_TSUNE_COLUMN, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
}
