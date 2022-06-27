package com.stereowalker.combat.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

//import com.stereowalker.combat.world.level.levelgen.AcrotlestAquifer;
//import com.stereowalker.combat.world.level.levelgen.AcrotlestLayeredBaseStoneSource;
import com.stereowalker.combat.world.level.levelgen.CNoiseGeneratorSettings;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
//import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
//import net.minecraft.world.level.levelgen.NoiseSampler;
//import net.minecraft.world.level.levelgen.NoodleCavifier;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
//import net.minecraft.world.level.levelgen.synth.SurfaceNoise;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseBasedChunkGeneratorMixin {
//	@Shadow @Final protected int cellHeight;
//	@Shadow @Final protected int cellWidth;
//	@Shadow @Final protected int cellCountX;
//	@Shadow @Final protected int cellCountY;
//	@Shadow @Final protected int cellCountZ;
//	@Shadow @Final protected SurfaceNoise surfaceNoise;
//	@Shadow @Final protected NormalNoise barrierNoise;
//	@Shadow @Final protected NormalNoise waterLevelNoise;
//	@Shadow @Final protected NormalNoise lavaNoise;
//	@Shadow @Final protected BlockState defaultBlock;
//	@Shadow @Final protected BlockState defaultFluid;
//	@Shadow @Final protected long seed;
//	@Shadow @Final protected Supplier<NoiseGeneratorSettings> settings;
//	@Shadow @Final protected int height;
//	@Shadow @Final protected NoiseSampler sampler;
//	@Shadow @Final protected OreVeinifier oreVeinifier;
//	@Shadow @Final protected NoodleCavifier noodleCavifier;
	@Shadow public Aquifer.FluidPicker globalFluidPicker;
	@Inject(method = "<init>", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
	public void init_inject(Registry<StructureSet> p_209106_, Registry<NormalNoise.NoiseParameters> p_209107_, BiomeSource p_209108_, long p_209109_, Holder<NoiseGeneratorSettings> p_209110_, CallbackInfo ci) {
		NoiseGeneratorSettings noisegeneratorsettings = p_209110_.value();
		NoiseSettings noisesettings = noisegeneratorsettings.noiseSettings();
		if (p_209110_.is(CNoiseGeneratorSettings.ACROTLEST.location())) {
			Aquifer.FluidStatus aquifer$fluidstatus = new Aquifer.FluidStatus(-108, Blocks.LAVA.defaultBlockState());
			int i = noisegeneratorsettings.seaLevel();
			Aquifer.FluidStatus aquifer$fluidstatus1 = new Aquifer.FluidStatus(i, noisegeneratorsettings.defaultFluid());
			Aquifer.FluidStatus aquifer$fluidstatus2 = new Aquifer.FluidStatus(noisesettings.minY() - 1, Blocks.AIR.defaultBlockState());
			this.globalFluidPicker = (p_198228_, p_198229_, p_198230_) -> {
				return p_198229_ < Math.min(-108, i) ? aquifer$fluidstatus : aquifer$fluidstatus1;
			};
		}
	}
//	@Inject(method = "getAquifer", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
//	public void init_inject(int pMinCellY, int pCellCountY, ChunkPos pChunkPos, CallbackInfoReturnable<Aquifer> cir) {
//		if (BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getKey(this.settings.get()).equals(CNoiseGeneratorSettings.ACROTLEST.location())) {
//			cir.setReturnValue(new AcrotlestAquifer(pChunkPos, this.barrierNoise, this.waterLevelNoise, this.lavaNoise, this.settings.get(), this.sampler, pMinCellY * this.cellHeight, pCellCountY * this.cellHeight));
//		}
//	}
}
