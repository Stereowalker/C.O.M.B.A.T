package com.stereowalker.combat.mixins;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.stereowalker.combat.world.level.levelgen.AcrotlestLayeredBaseStoneSource;
import com.stereowalker.combat.world.level.levelgen.CNoiseGeneratorSettings;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseBasedChunkGeneratorMixin {
	@Shadow protected BaseStoneSource baseStoneSource;
	@Shadow @Final protected BlockState defaultBlock;
	@Inject(method = "<init>", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
	public void init_inject(BiomeSource p_64337_, long p_64338_, Supplier<NoiseGeneratorSettings> p_64339_, CallbackInfo ci) {
		NoiseGeneratorSettings noisegeneratorsettings = p_64339_.get();
		if (BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getKey(noisegeneratorsettings).equals(CNoiseGeneratorSettings.ACROTLEST.location())) {
			this.baseStoneSource = new AcrotlestLayeredBaseStoneSource(p_64338_, this.defaultBlock, Blocks.SANDSTONE.defaultBlockState(), Blocks.GREEN_TERRACOTTA.defaultBlockState());
		}
	}
}
