package com.stereowalker.combat.world.level.levelgen;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSamplingSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.NoiseSlideSettings;
import net.minecraft.world.level.levelgen.StructureSettings;

public class CNoiseGeneratorSettings {
	public static final ResourceKey<NoiseGeneratorSettings> ACROTLEST = ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, Combat.getInstance().location("acrotlest"));
	public static final NoiseGeneratorSettings AC =  NoiseGeneratorSettings.register(ACROTLEST, acrotlestSettings(new StructureSettings(true), false));

	public static NoiseGeneratorSettings acrotlestSettings(StructureSettings pStructureSettings, boolean pIsAmplified) {
		return new NoiseGeneratorSettings(pStructureSettings, NoiseSettings.create(-192, 496, new NoiseSamplingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D), new NoiseSlideSettings(-10, 3, 0), new NoiseSlideSettings(15, 3, 0), 1, 2, 1.0D, -0.46875D, true, true, false, pIsAmplified), CBlocks.MEZEPINE.defaultBlockState(), CBlocks.BIABLE.defaultBlockState(), Integer.MIN_VALUE, 0, 126, 75/*minSurfaceLevel*/, false, true/*Aquafers Enabled*/, true/*Noise Caves*/, true/*Generates "Deepslate"*/, false, true/*Noodle Caves*/);
	}
}
