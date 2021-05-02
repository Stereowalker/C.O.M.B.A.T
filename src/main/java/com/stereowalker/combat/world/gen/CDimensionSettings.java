package com.stereowalker.combat.world.gen;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.CBlocks;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;

public class CDimensionSettings {
	public static final RegistryKey<DimensionSettings> ACROTLEST = RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, Combat.getInstance().location("acrotlest"));
	public static final DimensionSettings AC =  DimensionSettings.registerDimensionSettings(ACROTLEST, acrotlestSettings(new DimensionStructuresSettings(true), false, ACROTLEST.getLocation()));

	public static DimensionSettings acrotlestSettings(DimensionStructuresSettings p_242743_0_, boolean p_242743_1_, ResourceLocation p_242743_2_) {
		return new DimensionSettings(p_242743_0_, new NoiseSettings(256, new ScalingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D), new SlideSettings(-10, 3, 0), new SlideSettings(-30, 0, 0), 1, 2, 1.0D, -0.46875D, true, true, false, p_242743_1_), CBlocks.MEZEPINE.getDefaultState(), CBlocks.BIABLE.getDefaultState(), -10, 0, 63, false);
	}
}
