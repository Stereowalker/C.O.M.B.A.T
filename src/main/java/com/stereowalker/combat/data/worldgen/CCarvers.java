package com.stereowalker.combat.data.worldgen;

import com.stereowalker.combat.Combat;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;

public class CCarvers {
	public static final ResourceKey<ConfiguredWorldCarver<?>> ACROTLEST_CAVE = createKey("acrotlest_cave");
	public static final ResourceKey<ConfiguredWorldCarver<?>> ACROTLEST_CANYON = createKey("acrotlest_canyon");

	private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String pName) {
		return ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(Combat.MODID,pName));
	}

//	public static final Holder<ConfiguredWorldCarver<CaveCarverConfiguration>> ACROTLEST_CAVE_CA = register("acrotlest_cave", CWorldCarver.ACROTLEST_CAVE.configured(new CaveCarverConfiguration(0.33333334F, UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(126)), UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), UniformFloat.of(0.7F, 1.4F), UniformFloat.of(0.8F, 1.3F), UniformFloat.of(-1.0F, -0.4F))));
//	public static final Holder<ConfiguredWorldCarver<CanyonCarverConfiguration>> ACROTLEST_CANYON_CA = register("acrotlest_canyon", CWorldCarver.ACROTLEST_CANYON.configured(new CanyonCarverConfiguration(0.02F, UniformHeight.of(VerticalAnchor.absolute(10), VerticalAnchor.absolute(67)), ConstantFloat.of(3.0F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.75F, 1.0F), TrapezoidFloat.of(0.0F, 6.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));
}
