package com.stereowalker.combat.data.worldgen;

import com.stereowalker.combat.world.level.levelgen.carver.CWorldCarver;

import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class CCarvers {
	public static final Holder<ConfiguredWorldCarver<CaveCarverConfiguration>> ACROTLEST_CAVE = register("acrotlest_cave", CWorldCarver.ACROTLEST_CAVE.configured(new CaveCarverConfiguration(0.33333334F, UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(126)), UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), UniformFloat.of(0.7F, 1.4F), UniformFloat.of(0.8F, 1.3F), UniformFloat.of(-1.0F, -0.4F))));
	public static final Holder<ConfiguredWorldCarver<CanyonCarverConfiguration>> ACROTLEST_CANYON = register("acrotlest_canyon", CWorldCarver.ACROTLEST_CANYON.configured(new CanyonCarverConfiguration(0.02F, UniformHeight.of(VerticalAnchor.absolute(10), VerticalAnchor.absolute(67)), ConstantFloat.of(3.0F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.75F, 1.0F), TrapezoidFloat.of(0.0F, 6.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));

	private static <WC extends CarverConfiguration> Holder<ConfiguredWorldCarver<WC>> register(String pId, ConfiguredWorldCarver<WC> pCarver) {
		return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_CARVER, pId, pCarver);
	}
}
