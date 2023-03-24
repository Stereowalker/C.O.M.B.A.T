package com.stereowalker.combat.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.stereowalker.combat.world.level.biome.CBiomes;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class CSurfaceRuleData {
	private static final SurfaceRules.RuleSource SLYAPHY = makeStateRule(CBlocks.SLYAPHY);
	private static final SurfaceRules.RuleSource GREEN_TERRACOTTA = makeStateRule(Blocks.GREEN_TERRACOTTA);
	private static final SurfaceRules.RuleSource PURIFIED_DIRT = makeStateRule(CBlocks.PURIFIED_DIRT);
	private static final SurfaceRules.RuleSource PURIFIED_GRASS_BLOCK = makeStateRule(CBlocks.PURIFIED_GRASS_BLOCK);
	private static final SurfaceRules.RuleSource GROPAPY = makeStateRule(CBlocks.GROPAPY);
	private static final SurfaceRules.RuleSource BIABLE = makeStateRule(CBlocks.BIABLE);
	private static final SurfaceRules.RuleSource HOMSE = makeStateRule(CBlocks.HOMSE);
	private static final SurfaceRules.RuleSource HOMSE_STONE = makeStateRule(CBlocks.HOMSE);
	
	
	private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
	private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
	private static final SurfaceRules.RuleSource WHITE_TERRACOTTA = makeStateRule(Blocks.WHITE_TERRACOTTA);
	private static final SurfaceRules.RuleSource ORANGE_TERRACOTTA = makeStateRule(Blocks.ORANGE_TERRACOTTA);
	private static final SurfaceRules.RuleSource TERRACOTTA = makeStateRule(Blocks.TERRACOTTA);
	private static final SurfaceRules.RuleSource RED_SAND = makeStateRule(Blocks.RED_SAND);
	private static final SurfaceRules.RuleSource RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
	private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
	private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
	private static final SurfaceRules.RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
	private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
	private static final SurfaceRules.RuleSource MYCELIUM = makeStateRule(Blocks.MYCELIUM);
	private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
	private static final SurfaceRules.RuleSource CALCITE = makeStateRule(Blocks.CALCITE);
	private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
	private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
	private static final SurfaceRules.RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
	private static final SurfaceRules.RuleSource PACKED_ICE = makeStateRule(Blocks.PACKED_ICE);
	private static final SurfaceRules.RuleSource SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
	private static final SurfaceRules.RuleSource POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);
	private static final SurfaceRules.RuleSource ICE = makeStateRule(Blocks.ICE);
	private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);

	private static SurfaceRules.RuleSource makeStateRule(Block pBlock) {
		return SurfaceRules.state(pBlock.defaultBlockState());
	}

	public static SurfaceRules.RuleSource acrotlest() {
		return overworldLike(true, false, true);
	}
	
	public static SurfaceRules.RuleSource overworldLike(boolean p_198381_, boolean p_198382_, boolean p_198383_) {
		SurfaceRules.ConditionSource surfacerules$conditionsource = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(97), 2);
		SurfaceRules.ConditionSource surfacerules$conditionsource1 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(256), 0);
		SurfaceRules.ConditionSource surfacerules$conditionsource2 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1);
		SurfaceRules.ConditionSource surfacerules$conditionsource3 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1);
		SurfaceRules.ConditionSource surfacerules$conditionsource4 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
		SurfaceRules.ConditionSource surfacerules$conditionsource5 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
		SurfaceRules.ConditionSource surfacerules$conditionsource6 = SurfaceRules.waterBlockCheck(-1, 0);
		SurfaceRules.ConditionSource surfacerules$conditionsource7 = SurfaceRules.waterBlockCheck(0, 0);
		SurfaceRules.ConditionSource surfacerules$conditionsource8 = SurfaceRules.waterStartCheck(-6, -1);
		SurfaceRules.ConditionSource surfacerules$conditionsource9 = SurfaceRules.hole();
		SurfaceRules.ConditionSource surfacerules$conditionsource10 = SurfaceRules.isBiome(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);
		SurfaceRules.ConditionSource surfacerules$conditionsource11 = SurfaceRules.steep();
		SurfaceRules.RuleSource dirt_rule = SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource7, GRASS_BLOCK), DIRT);
		SurfaceRules.RuleSource sand$stone_rule = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SAND);
		SurfaceRules.RuleSource surfacerules$rulesource2 = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE), GRAVEL);
		SurfaceRules.ConditionSource surfacerules$conditionsource12 = SurfaceRules.isBiome(Biomes.WARM_OCEAN, Biomes.BEACH, Biomes.SNOWY_BEACH);
		SurfaceRules.ConditionSource surfacerules$conditionsource13 = SurfaceRules.isBiome(Biomes.DESERT);
		SurfaceRules.RuleSource surfacerules$rulesource3 = 
				//Misc biome generation
				SurfaceRules.sequence(
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.STONY_PEAKS), 
								SurfaceRules.sequence(SurfaceRules.ifTrue(
										SurfaceRules.noiseCondition(Noises.CALCITE, -0.0125D, 0.0125D), CALCITE), STONE)), 
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.STONY_SHORE), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition(Noises.GRAVEL, -0.05D, 0.05D), surfacerules$rulesource2), STONE)), 
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.WINDSWEPT_HILLS), 
								SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D), STONE)), 
						SurfaceRules.ifTrue(surfacerules$conditionsource12, sand$stone_rule), 
						SurfaceRules.ifTrue(surfacerules$conditionsource13, sand$stone_rule), 
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.DRIPSTONE_CAVES), STONE));
		SurfaceRules.RuleSource surfacerules$rulesource4 = SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.45D, 0.58D), SurfaceRules.ifTrue(surfacerules$conditionsource7, POWDER_SNOW));
		SurfaceRules.RuleSource surfacerules$rulesource5 = SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35D, 0.6D), SurfaceRules.ifTrue(surfacerules$conditionsource7, POWDER_SNOW));
		SurfaceRules.RuleSource surfacerules$rulesource6 = 
				SurfaceRules.sequence(
						//This generates the ice and snow underneath the frozen peaks
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.FROZEN_PEAKS), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(surfacerules$conditionsource11, PACKED_ICE), 
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition(Noises.PACKED_ICE, -0.5D, 0.2D), PACKED_ICE), 
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition(Noises.ICE, -0.0625D, 0.025D), ICE), 
										SurfaceRules.ifTrue(surfacerules$conditionsource7, SNOW_BLOCK))), 
						//Snowy Slopes
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.SNOWY_SLOPES), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(surfacerules$conditionsource11, STONE), surfacerules$rulesource4, 
										SurfaceRules.ifTrue(surfacerules$conditionsource7, SNOW_BLOCK))), 
						//Jagged peaks
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.JAGGED_PEAKS), STONE), 
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.GROVE), 
								SurfaceRules.sequence(surfacerules$rulesource4, DIRT)), 
						surfacerules$rulesource3, 
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.WINDSWEPT_SAVANNA), 
								SurfaceRules.ifTrue(surfaceNoiseAbove(1.75D), STONE)), 
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(surfaceNoiseAbove(2.0D), surfacerules$rulesource2), 
										SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D), STONE), 
										SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0D), DIRT), surfacerules$rulesource2)), 
						DIRT);
		
		SurfaceRules.RuleSource surfacerules$rulesource7 = 
				SurfaceRules.sequence(
						//This generates the ice and snow on top of the frozen peaks
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.FROZEN_PEAKS), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(surfacerules$conditionsource11, PACKED_ICE), 
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition(Noises.PACKED_ICE, 0.0D, 0.2D), PACKED_ICE), 
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition(Noises.ICE, 0.0D, 0.025D), ICE), 
										SurfaceRules.ifTrue(surfacerules$conditionsource7, SNOW_BLOCK))), 
						//
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.SNOWY_SLOPES), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(surfacerules$conditionsource11, STONE), surfacerules$rulesource5, 
										SurfaceRules.ifTrue(surfacerules$conditionsource7, SNOW_BLOCK))), 
						//
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.JAGGED_PEAKS), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(surfacerules$conditionsource11, STONE), 
										SurfaceRules.ifTrue(surfacerules$conditionsource7, SNOW_BLOCK))), 
						//
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.GROVE), 
								SurfaceRules.sequence(surfacerules$rulesource5, 
										SurfaceRules.ifTrue(surfacerules$conditionsource7, SNOW_BLOCK))), 
						//
						surfacerules$rulesource3, 
						//
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.WINDSWEPT_SAVANNA), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(surfaceNoiseAbove(1.75D), STONE), 
										SurfaceRules.ifTrue(surfaceNoiseAbove(-0.5D), COARSE_DIRT))), 
						//
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(surfaceNoiseAbove(2.0D), surfacerules$rulesource2), 
										SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D), STONE), 
										SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0D), dirt_rule), surfacerules$rulesource2)), 
						//
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(surfaceNoiseAbove(1.75D), COARSE_DIRT), 
										SurfaceRules.ifTrue(surfaceNoiseAbove(-0.95D), PODZOL))), 
						//
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.ICE_SPIKES), 
								SurfaceRules.ifTrue(surfacerules$conditionsource7, SNOW_BLOCK)), 
						//
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.MUSHROOM_FIELDS), MYCELIUM),
						//
						dirt_rule);
		
		SurfaceRules.ConditionSource surfacerules$conditionsource14 = SurfaceRules.noiseCondition(Noises.SURFACE, -0.909D, -0.5454D);
		SurfaceRules.ConditionSource surfacerules$conditionsource15 = SurfaceRules.noiseCondition(Noises.SURFACE, -0.1818D, 0.1818D);
		SurfaceRules.ConditionSource surfacerules$conditionsource16 = SurfaceRules.noiseCondition(Noises.SURFACE, 0.5454D, 0.909D);
		SurfaceRules.RuleSource surfacerules$rulesource8 = 
				SurfaceRules.sequence(
						SurfaceRules.ifTrue(
								SurfaceRules.ON_FLOOR, 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(
												SurfaceRules.isBiome(Biomes.WOODED_BADLANDS), 
												SurfaceRules.ifTrue(surfacerules$conditionsource, 
														SurfaceRules.sequence(
																SurfaceRules.ifTrue(surfacerules$conditionsource14, COARSE_DIRT), 
																SurfaceRules.ifTrue(surfacerules$conditionsource15, COARSE_DIRT), 
																SurfaceRules.ifTrue(surfacerules$conditionsource16, COARSE_DIRT), 
																dirt_rule))), 
										SurfaceRules.ifTrue(
												SurfaceRules.isBiome(Biomes.SWAMP), 
												SurfaceRules.ifTrue(surfacerules$conditionsource4, 
														SurfaceRules.ifTrue(
																SurfaceRules.not(surfacerules$conditionsource5), 
																SurfaceRules.ifTrue(
																		SurfaceRules.noiseCondition(Noises.SWAMP, 0.0D), WATER)))))), 
						//
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS), 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(
												SurfaceRules.ON_FLOOR, 
												SurfaceRules.sequence(
														SurfaceRules.ifTrue(surfacerules$conditionsource1, ORANGE_TERRACOTTA), 
														SurfaceRules.ifTrue(surfacerules$conditionsource3, 
																SurfaceRules.sequence(
																		SurfaceRules.ifTrue(surfacerules$conditionsource14, TERRACOTTA), 
																		SurfaceRules.ifTrue(surfacerules$conditionsource15, TERRACOTTA), 
																		SurfaceRules.ifTrue(surfacerules$conditionsource16, TERRACOTTA), 
																		SurfaceRules.bandlands())), 
														SurfaceRules.ifTrue(surfacerules$conditionsource6, 
																SurfaceRules.sequence(
																		SurfaceRules.ifTrue(
																				SurfaceRules.ON_CEILING, RED_SANDSTONE), RED_SAND)), 
														SurfaceRules.ifTrue(
																SurfaceRules.not(surfacerules$conditionsource9), ORANGE_TERRACOTTA), 
														SurfaceRules.ifTrue(surfacerules$conditionsource8, WHITE_TERRACOTTA), surfacerules$rulesource2)), 
										SurfaceRules.ifTrue(surfacerules$conditionsource2, 
												SurfaceRules.sequence(
														SurfaceRules.ifTrue(surfacerules$conditionsource5, 
																SurfaceRules.ifTrue(
																		SurfaceRules.not(surfacerules$conditionsource3), ORANGE_TERRACOTTA)), 
														SurfaceRules.bandlands())), 
										SurfaceRules.ifTrue(
												SurfaceRules.UNDER_FLOOR, 
												SurfaceRules.ifTrue(surfacerules$conditionsource8, WHITE_TERRACOTTA)))), 
						// Seems to generate stuff on the biome floor
						SurfaceRules.ifTrue(
								SurfaceRules.ON_FLOOR, 
								SurfaceRules.ifTrue(surfacerules$conditionsource6, 
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(surfacerules$conditionsource10, 
														SurfaceRules.ifTrue(surfacerules$conditionsource9, 
																SurfaceRules.sequence(
																		SurfaceRules.ifTrue(surfacerules$conditionsource7, AIR), 
																		SurfaceRules.ifTrue(
																				SurfaceRules.temperature(), ICE), WATER))), surfacerules$rulesource7))), 
						// Seems to generate stuff underneath the biome floor
						SurfaceRules.ifTrue(surfacerules$conditionsource8, 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(
												SurfaceRules.ON_FLOOR, 
												SurfaceRules.ifTrue(surfacerules$conditionsource10, 
														SurfaceRules.ifTrue(surfacerules$conditionsource9, WATER))), 
										SurfaceRules.ifTrue(
												SurfaceRules.UNDER_FLOOR, surfacerules$rulesource6), 
										SurfaceRules.ifTrue(surfacerules$conditionsource12, 
												SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SANDSTONE)), 
										SurfaceRules.ifTrue(surfacerules$conditionsource13, 
												SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SANDSTONE)))), 
						//
						SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(
												SurfaceRules.isBiome(Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS), STONE), 
										SurfaceRules.ifTrue(
												SurfaceRules.isBiome(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), 
												sand$stone_rule), 
										surfacerules$rulesource2)));
		
		
		Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
		if (p_198382_) {
			builder.add(SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK));
		}

		if (p_198383_) {
			builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK));
		}

		SurfaceRules.RuleSource surfacerules$rulesource9 = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), surfacerules$rulesource8);
		//
		//This modifies the surface of the dimension
		//
		SurfaceRules.RuleSource ac = acLike(p_198381_, p_198382_, p_198383_, 
				surfacerules$conditionsource6, 
				surfacerules$conditionsource7, 
				surfacerules$conditionsource8, 
				surfacerules$conditionsource9, 
				surfacerules$conditionsource10, 
				surfacerules$conditionsource12);
		SurfaceRules.RuleSource acs = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), ac);
		builder.add(p_198381_ ? acs : ac);
		builder.add(p_198381_ ? surfacerules$rulesource9 : surfacerules$rulesource8);
		//
		//This handles the default stone
		//
		builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lower_level", VerticalAnchor.absolute(-68), VerticalAnchor.absolute(-60)), GREEN_TERRACOTTA));
		builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("middle_level", VerticalAnchor.absolute(60), VerticalAnchor.absolute(68)), SLYAPHY));
		return SurfaceRules.sequence(builder.build().toArray((p_198379_) -> {
			return new SurfaceRules.RuleSource[p_198379_];
		}));
	}
	
	public static SurfaceRules.RuleSource acLike(boolean p_198381_, boolean p_198382_, boolean p_198383_, 
			SurfaceRules.ConditionSource surfacerules$conditionsource6, 
			SurfaceRules.ConditionSource surfacerules$conditionsource7, 
			SurfaceRules.ConditionSource surfacerules$conditionsource8, 
			SurfaceRules.ConditionSource surfacerules$conditionsource9, 
			SurfaceRules.ConditionSource surfacerules$conditionsource10, 
			SurfaceRules.ConditionSource surfacerules$conditionsource12) {
		SurfaceRules.RuleSource homse = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, HOMSE_STONE), HOMSE);
		SurfaceRules.RuleSource pure_dirt = SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource7, PURIFIED_GRASS_BLOCK), PURIFIED_DIRT);
		SurfaceRules.RuleSource arules$rulesource3 = 
				SurfaceRules.sequence(
						SurfaceRules.ifTrue(
								SurfaceRules.isBiome(CBiomes.HISOV_SANDS_BI), homse));
		
		SurfaceRules.RuleSource arules$rulesource6 = 
				SurfaceRules.sequence(
						arules$rulesource3,
						PURIFIED_DIRT);
		
		SurfaceRules.RuleSource arules$rulesource7 = 
				SurfaceRules.sequence(
						arules$rulesource3,
						pure_dirt);
		
		return 
				SurfaceRules.sequence(
						//Seems to generate stuff on the biome floor
						SurfaceRules.ifTrue(
								SurfaceRules.ON_FLOOR, 
								SurfaceRules.ifTrue(surfacerules$conditionsource6, 
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(surfacerules$conditionsource10, 
														SurfaceRules.ifTrue(surfacerules$conditionsource9, 
																SurfaceRules.sequence(
																		SurfaceRules.ifTrue(surfacerules$conditionsource7, AIR), 
																		SurfaceRules.ifTrue(
																				SurfaceRules.temperature(), GROPAPY), BIABLE))), arules$rulesource7))),
						//Seems to generate stuff underneath the biome floor
						SurfaceRules.ifTrue(surfacerules$conditionsource8, 
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(
												SurfaceRules.ON_FLOOR, 
												SurfaceRules.ifTrue(surfacerules$conditionsource10, 
														SurfaceRules.ifTrue(surfacerules$conditionsource9, BIABLE))), 
										SurfaceRules.ifTrue(
												SurfaceRules.UNDER_FLOOR, arules$rulesource6), 
										SurfaceRules.ifTrue(surfacerules$conditionsource12, 
												SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, HOMSE_STONE)), 
										SurfaceRules.ifTrue(SurfaceRules.isBiome(CBiomes.HISOV_SANDS_BI), 
												SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, HOMSE_STONE))))
						);
	}

	private static SurfaceRules.ConditionSource surfaceNoiseAbove(double p_194809_) {
		return SurfaceRules.noiseCondition(Noises.SURFACE, p_194809_ / 8.25D, Double.MAX_VALUE);
	}
}
