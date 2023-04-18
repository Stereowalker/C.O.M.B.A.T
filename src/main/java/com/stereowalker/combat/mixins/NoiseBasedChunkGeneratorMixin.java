package com.stereowalker.combat.mixins;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.base.Suppliers;
//import com.stereowalker.combat.world.level.levelgen.AcrotlestAquifer;
//import com.stereowalker.combat.world.level.levelgen.AcrotlestLayeredBaseStoneSource;
import com.stereowalker.combat.world.level.levelgen.CNoiseGeneratorSettings;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseBasedChunkGeneratorMixin {
	@Shadow public Supplier<Aquifer.FluidPicker> globalFluidPicker;
	/**
	 * The intent of this is to fix MC-237017
	 * @param p_256415_
	 * @param p_256182_
	 * @param ci
	 */
	@Inject(method = "<init>", at = @At("TAIL"))
	public void init_inject(BiomeSource p_256415_, Holder<NoiseGeneratorSettings> p_256182_, CallbackInfo ci) {
		if (p_256182_.is(CNoiseGeneratorSettings.ACROTLEST.location())) {
			this.globalFluidPicker = Suppliers.memoize(() -> {
		         return createFluidPicker(p_256182_.value());
		      });
		}
	}

	private static Aquifer.FluidPicker createFluidPicker(NoiseGeneratorSettings pSettings) {
		Aquifer.FluidStatus aquifer$fluidstatus = new Aquifer.FluidStatus(-208, Blocks.LAVA.defaultBlockState());
		int i = pSettings.seaLevel();
		Aquifer.FluidStatus aquifer$fluidstatus1 = new Aquifer.FluidStatus(i, pSettings.defaultFluid());
		return (p_224274_, p_224275_, p_224276_) -> {
			return p_224275_ < Math.min(-208, i) ? aquifer$fluidstatus : aquifer$fluidstatus1;
		};
	}
}
