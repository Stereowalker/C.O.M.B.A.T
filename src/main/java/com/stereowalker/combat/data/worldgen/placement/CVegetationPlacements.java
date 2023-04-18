package com.stereowalker.combat.data.worldgen.placement;

import com.stereowalker.combat.data.worldgen.features.CVegetationFeatures;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CVegetationPlacements {
	public static final ResourceKey<PlacedFeature> TREES_MAGICAL = PlacementUtils.createKey("combat:trees_magical");
	public static final ResourceKey<PlacedFeature> TREES_AUSLDINE = PlacementUtils.createKey("combat:trees_ausldine");
	public static final ResourceKey<PlacedFeature> TREES_DEAD_OAK = PlacementUtils.createKey("combat:trees_dead_oak");
	public static final ResourceKey<PlacedFeature> TREES_REZAL = PlacementUtils.createKey("combat:trees_rezal");
	public static final ResourceKey<PlacedFeature> TREES_MONORIS = PlacementUtils.createKey("combat:trees_monoris");

	public static void init() {}
	
	public static void bootstrap(BootstapContext<PlacedFeature> p_255657_) {
		HolderGetter<ConfiguredFeature<?, ?>> holdergetter = p_255657_.lookup(Registries.CONFIGURED_FEATURE);
		Holder<ConfiguredFeature<?, ?>> holder = holdergetter.getOrThrow(CVegetationFeatures.TREES_MAGICAL);
		Holder<ConfiguredFeature<?, ?>> holder1 = holdergetter.getOrThrow(CVegetationFeatures.TREES_AUSLDINE);
		Holder<ConfiguredFeature<?, ?>> holder2 = holdergetter.getOrThrow(CVegetationFeatures.TREES_DEAD_OAK);
		Holder<ConfiguredFeature<?, ?>> holder3 = holdergetter.getOrThrow(CVegetationFeatures.TREES_REZAL);
		Holder<ConfiguredFeature<?, ?>> holder4 = holdergetter.getOrThrow(CVegetationFeatures.TREES_MONORIS);
		PlacementUtils.register(p_255657_, TREES_MAGICAL, holder, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
		PlacementUtils.register(p_255657_, TREES_AUSLDINE, holder1, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
		PlacementUtils.register(p_255657_, TREES_DEAD_OAK, holder2, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
		PlacementUtils.register(p_255657_, TREES_REZAL, holder3, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
		PlacementUtils.register(p_255657_, TREES_MONORIS, holder4, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	}
}
