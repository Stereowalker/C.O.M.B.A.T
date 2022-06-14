package com.stereowalker.combat.data.worldgen.placement;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.features.CVegetationFeatures;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CVegetationPlacements {
	static String n(String n) {return Combat.MODID+":"+n;}
	public static final Holder<PlacedFeature> TREES_MAGICAL = PlacementUtils.register(n("trees_magical"), CVegetationFeatures.TREES_MAGICAL, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	public static final Holder<PlacedFeature> TREES_AUSLDINE = PlacementUtils.register(n("trees_ausldine"), CVegetationFeatures.TREES_AUSLDINE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	public static final Holder<PlacedFeature> TREES_DEAD_OAK = PlacementUtils.register(n("trees_dead_oak"), CVegetationFeatures.TREES_DEAD_OAK, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	public static final Holder<PlacedFeature> TREES_REZAL = PlacementUtils.register(n("trees_rezal"), CVegetationFeatures.TREES_REZAL, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	public static final Holder<PlacedFeature> TREES_MONORIS = PlacementUtils.register(n("trees_monoris"), CVegetationFeatures.TREES_MONORIS, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
}
