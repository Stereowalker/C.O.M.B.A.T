package com.stereowalker.combat.world.level.levelgen;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.CSurfaceRuleData;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSamplingSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.NoiseSlider;

public class CNoiseGeneratorSettings {
	public static final ResourceKey<NoiseGeneratorSettings> ACROTLEST = ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, Combat.getInstance().location("acrotlest"));

	static NoiseSettings SET(boolean pIsAmplified) {
		return NoiseSettings.create(-192, 496, new NoiseSamplingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D), new NoiseSlider(-10, 3, 0), new NoiseSlider(15, 3, 0), 1, 2, TerrainProvider.overworld(pIsAmplified));
	}
	public static NoiseGeneratorSettings acrotlestSettings(boolean pIsAmplified) {
		NoiseSettings noisesettings = SET(pIsAmplified);
		return new NoiseGeneratorSettings(noisesettings, CBlocks.MEZEPINE.defaultBlockState(), CBlocks.BIABLE.defaultBlockState(), CNoiseRouterData.acrotlest(noisesettings, pIsAmplified), CSurfaceRuleData.acrotlest(), 126, false, true/*Aquafers Enabled*/, true/*Generates "Deepslate"*/, false);
	}
}
