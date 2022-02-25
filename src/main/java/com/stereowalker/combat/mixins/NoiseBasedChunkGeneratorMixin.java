package com.stereowalker.combat.mixins;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.AcrotlestAquifer;
import com.stereowalker.combat.world.level.levelgen.AcrotlestLayeredBaseStoneSource;
import com.stereowalker.combat.world.level.levelgen.CNoiseGeneratorSettings;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSampler;
import net.minecraft.world.level.levelgen.NoodleCavifier;
import net.minecraft.world.level.levelgen.OreVeinifier;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.SurfaceNoise;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseBasedChunkGeneratorMixin {
	@Shadow @Final protected int cellHeight;
	@Shadow @Final protected int cellWidth;
	@Shadow @Final protected int cellCountX;
	@Shadow @Final protected int cellCountY;
	@Shadow @Final protected int cellCountZ;
	@Shadow @Final protected SurfaceNoise surfaceNoise;
	@Shadow @Final protected NormalNoise barrierNoise;
	@Shadow @Final protected NormalNoise waterLevelNoise;
	@Shadow @Final protected NormalNoise lavaNoise;
	@Shadow @Final protected BlockState defaultBlock;
	@Shadow @Final protected BlockState defaultFluid;
	@Shadow @Final protected long seed;
	@Shadow @Final protected Supplier<NoiseGeneratorSettings> settings;
	@Shadow @Final protected int height;
	@Shadow @Final protected NoiseSampler sampler;
	@Shadow protected BaseStoneSource baseStoneSource;
	@Shadow @Final protected OreVeinifier oreVeinifier;
	@Shadow @Final protected NoodleCavifier noodleCavifier;
	@Inject(method = "<init>", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
	public void init_inject(BiomeSource p_64337_, long p_64338_, Supplier<NoiseGeneratorSettings> p_64339_, CallbackInfo ci) {
		NoiseGeneratorSettings noisegeneratorsettings = p_64339_.get();
		if (BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getKey(noisegeneratorsettings).equals(CNoiseGeneratorSettings.ACROTLEST.location())) {
			this.baseStoneSource = new AcrotlestLayeredBaseStoneSource(p_64338_, this.defaultBlock, CBlocks.SLYAPHY.defaultBlockState(), Blocks.GREEN_TERRACOTTA.defaultBlockState());
		}
	}
	@Inject(method = "getAquifer", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	public void init_inject(int pMinCellY, int pCellCountY, ChunkPos pChunkPos, CallbackInfoReturnable<Aquifer> cir) {
		if (BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getKey(this.settings.get()).equals(CNoiseGeneratorSettings.ACROTLEST.location())) {
			cir.setReturnValue(new AcrotlestAquifer(pChunkPos, this.barrierNoise, this.waterLevelNoise, this.lavaNoise, this.settings.get(), this.sampler, pMinCellY * this.cellHeight, pCellCountY * this.cellHeight));
		}
	}
}
