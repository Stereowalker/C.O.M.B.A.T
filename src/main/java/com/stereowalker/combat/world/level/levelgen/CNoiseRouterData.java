package com.stereowalker.combat.world.level.levelgen;

import java.util.stream.Stream;

import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
//import net.minecraft.world.level.levelgen.NoiseRouterWithOnlyNoises;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.OreVeinifier;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class CNoiseRouterData extends NoiseRouterData {
//
//	   static NoiseRouter acrotlest(NoiseSettings p_212283_, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters, boolean p_212284_) {
//	      DensityFunction densityfunction = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5D);
//	      DensityFunction densityfunction1 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67D);
//	      DensityFunction densityfunction2 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143D);
//	      DensityFunction densityfunction3 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_LAVA));
//	      DensityFunction densityfunction4 = NoiseRouterData.getFunction(SHIFT_X);
//	      DensityFunction densityfunction5 = NoiseRouterData.getFunction(SHIFT_Z);
//	      DensityFunction densityfunction6 = DensityFunctions.shiftedNoise2d(densityfunction4, densityfunction5, 0.25D, pNoiseParameters.getOrThrow(p_212284_ ? Noises.TEMPERATURE_LARGE : Noises.TEMPERATURE));
//	      DensityFunction densityfunction7 = DensityFunctions.shiftedNoise2d(densityfunction4, densityfunction5, 0.25D, pNoiseParameters.getOrThrow(p_212284_ ? Noises.VEGETATION_LARGE : Noises.VEGETATION));
//	      DensityFunction densityfunction8 = NoiseRouterData.getFunction(p_212284_ ? NoiseRouterData.FACTOR_LARGE : NoiseRouterData.FACTOR);
//	      DensityFunction densityfunction9 = NoiseRouterData.getFunction(p_212284_ ? NoiseRouterData.DEPTH_LARGE : NoiseRouterData.DEPTH);
//	      DensityFunction densityfunction10 = NoiseRouterData.noiseGradientDensity(DensityFunctions.cache2d(densityfunction8), densityfunction9);
//	      DensityFunction densityfunction11 = NoiseRouterData.getFunction(p_212284_ ? NoiseRouterData.SLOPED_CHEESE_LARGE : NoiseRouterData.SLOPED_CHEESE);
//	      DensityFunction densityfunction12 = DensityFunctions.min(densityfunction11, DensityFunctions.mul(DensityFunctions.constant(5.0D), getFunction(ENTRANCES)));
//	      DensityFunction densityfunction13 = DensityFunctions.rangeChoice(densityfunction11, -1000000.0D, 1.5625D, densityfunction12, NoiseRouterData.underground(densityfunction11));
//	      DensityFunction densityfunction14 = DensityFunctions.min(NoiseRouterData.postProcess(p_212283_, densityfunction13), NoiseRouterData.getFunction(NoiseRouterData.NOODLE));
//	      DensityFunction densityfunction15 = NoiseRouterData.getFunction(Y);
//	      int i = p_212283_.minY();
//	      int j = Stream.of(OreVeinifier.VeinType.values()).mapToInt((p_212286_) -> {
//	         return p_212286_.minY;
//	      }).min().orElse(i);
//	      int k = Stream.of(OreVeinifier.VeinType.values()).mapToInt((p_212281_) -> {
//	         return p_212281_.maxY;
//	      }).max().orElse(i);
//	      DensityFunction densityfunction16 = yLimitedInterpolatable(densityfunction15, DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.ORE_VEININESS), 1.5D, 1.5D), j, k, 0);
//	      float f = 4.0F;
//	      DensityFunction densityfunction17 = yLimitedInterpolatable(densityfunction15, DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.ORE_VEIN_A), 4.0D, 4.0D), j, k, 0).abs();
//	      DensityFunction densityfunction18 = yLimitedInterpolatable(densityfunction15, DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.ORE_VEIN_B), 4.0D, 4.0D), j, k, 0).abs();
//	      DensityFunction densityfunction19 = DensityFunctions.add(DensityFunctions.constant((double)-0.08F), DensityFunctions.max(densityfunction17, densityfunction18));
//	      DensityFunction densityfunction20 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.ORE_GAP));
//	      return new NoiseRouter(densityfunction, densityfunction1, densityfunction2, densityfunction3, densityfunction6, densityfunction7, getFunction(p_212284_ ? CONTINENTS_LARGE : CONTINENTS), getFunction(p_212284_ ? EROSION_LARGE : EROSION), getFunction(p_212284_ ? DEPTH_LARGE : DEPTH), getFunction(RIDGES), densityfunction10, densityfunction14, densityfunction16, densityfunction19, densityfunction20);
//	   }
}
