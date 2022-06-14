package com.stereowalker.combat.data.worldgen.placement;

import java.util.List;

import com.stereowalker.combat.data.worldgen.features.CTreeFeatures;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class CTreePlacements {
	public static final Holder<PlacedFeature> AUSLDINE_CHECKED = register("ausldine_checked", CTreeFeatures.AUSLDINE, PlacementUtils.filteredByBlockSurvival(CBlocks.AUSLDINE_SAPLING));
	public static final Holder<PlacedFeature> DEAD_OAK_CHECKED = register("dead_oak_checked", CTreeFeatures.DEAD_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.AIR));
	public static final Holder<PlacedFeature> REZAL_CHECKED = register("rezal_checked", CTreeFeatures.REZAL, PlacementUtils.filteredByBlockSurvival(CBlocks.REZAL_LEAVES));
	public static final Holder<PlacedFeature> MONORIS_CHECKED = register("monoris_checked", CTreeFeatures.MONORIS, PlacementUtils.filteredByBlockSurvival(CBlocks.MONORIS_SAPLING));

	public static Holder<PlacedFeature> register(String p_206510_, Holder<? extends ConfiguredFeature<?, ?>> p_206511_, List<PlacementModifier> p_206512_) {
		return PlacementUtils.register("combat"+p_206510_, p_206511_, p_206512_);
	}

	public static Holder<PlacedFeature> register(String p_206514_, Holder<? extends ConfiguredFeature<?, ?>> p_206515_, PlacementModifier... p_206516_) {
		return PlacementUtils.register("combat"+p_206514_, p_206515_, p_206516_);
	}
}
