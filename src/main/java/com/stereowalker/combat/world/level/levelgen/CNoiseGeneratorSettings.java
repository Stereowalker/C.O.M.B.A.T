package com.stereowalker.combat.world.level.levelgen;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.CSurfaceRuleData;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.world.level.biome.TerrainShaper;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSamplingSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.NoiseSlider;

public class CNoiseGeneratorSettings {
	public static final ResourceKey<NoiseGeneratorSettings> ACROTLEST = ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation(Combat.MODID, "acrotlest"));

	static NoiseSettings SET(boolean pIsAmplified) {
		TerrainShaper overworldShaper = TerrainProvider.overworld(pIsAmplified);
		float f = 0.6f;
		ToFloatFunction<Float> tofloatfunction = pIsAmplified ? TerrainShaper::getAmplifiedOffset : TerrainShaper.NO_TRANSFORM;
		CubicSpline<TerrainShaper.Point> cubicspline = TerrainShaper.buildErosionOffsetSpline(-0.15F+f, 0.0F+f, 0.0F+f, 0.1F+f, 0.0F+f, -0.03F+f, false, false, tofloatfunction);
		CubicSpline<TerrainShaper.Point> cubicspline1 = TerrainShaper.buildErosionOffsetSpline(-0.1F+f, 0.03F+f, 0.1F+f, 0.1F+f, 0.01F+f, -0.03F+f, false, false, tofloatfunction);
		CubicSpline<TerrainShaper.Point> cubicspline2 = TerrainShaper.buildErosionOffsetSpline(-0.1F+f, 0.03F+f, 0.1F+f, 0.7F+f, 0.01F+f, -0.03F+f, true, true, tofloatfunction);
		CubicSpline<TerrainShaper.Point> cubicspline3 = TerrainShaper.buildErosionOffsetSpline(-0.05F+f, 0.03F+f, 0.1F+f, 1.0F+f, 0.01F+f, 0.01F+f, true, true, tofloatfunction);
		
		CubicSpline<TerrainShaper.Point> cubicspline4 = CubicSpline.builder(TerrainShaper.Coordinate.CONTINENTS, tofloatfunction).addPoint(-1.1F, 0.044F+f, 0.0F).addPoint(-1.02F, -0.2222F+f, 0.0F).addPoint(-0.51F, -0.2222F+f, 0.0F).addPoint(-0.44F, -0.12F+f, 0.0F).addPoint(-0.18F, -0.12F+f, 0.0F).addPoint(-0.16F, cubicspline, 0.0F).addPoint(-0.15F, cubicspline, 0.0F).addPoint(-0.1F, cubicspline1, 0.0F).addPoint(0.25F, cubicspline2, 0.0F).addPoint(1.0F, cubicspline3, 0.0F).build();
		TerrainShaper shaper = new TerrainShaper(cubicspline4, overworldShaper.factorSampler(), overworldShaper.jaggednessSampler());
		
		return NoiseSettings.create(-192, 496, new NoiseSamplingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D), new NoiseSlider(-10, 0, 8), new NoiseSlider(15, 3, 0), 1, 2, shaper);
	}
	public static NoiseGeneratorSettings acrotlestSettings(boolean pIsAmplified) {
		NoiseSettings noisesettings = SET(pIsAmplified);
		return new NoiseGeneratorSettings(noisesettings, CBlocks.MEZEPINE.defaultBlockState(), CBlocks.BIABLE.defaultBlockState(), CNoiseRouterData.acrotlest(noisesettings, pIsAmplified), CSurfaceRuleData.acrotlest(), 126, false, true, true, false);
	}
}
