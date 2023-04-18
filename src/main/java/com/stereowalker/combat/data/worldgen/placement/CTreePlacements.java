package com.stereowalker.combat.data.worldgen.placement;

import com.stereowalker.combat.data.worldgen.features.CTreeFeatures;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CTreePlacements {
	   public static final ResourceKey<PlacedFeature> AUSLDINE_CHECKED = PlacementUtils.createKey("combat:ausldine_checked");
	   public static final ResourceKey<PlacedFeature> DEAD_OAK_CHECKED = PlacementUtils.createKey("combat:dead_oak_checked");
	   public static final ResourceKey<PlacedFeature> REZAL_CHECKED = PlacementUtils.createKey("combat:rezal_checked");
	   public static final ResourceKey<PlacedFeature> MONORIS_CHECKED = PlacementUtils.createKey("combat:monoris_checked");
	   
	   public static void init() {}

	   public static void bootstrap(BootstapContext<PlacedFeature> p_255688_) {
	      HolderGetter<ConfiguredFeature<?, ?>> holdergetter = p_255688_.lookup(Registries.CONFIGURED_FEATURE);
	      Holder<ConfiguredFeature<?, ?>> holder = holdergetter.getOrThrow(CTreeFeatures.AUSLDINE);
	      Holder<ConfiguredFeature<?, ?>> holder1 = holdergetter.getOrThrow(CTreeFeatures.DEAD_OAK);
	      Holder<ConfiguredFeature<?, ?>> holder2 = holdergetter.getOrThrow(CTreeFeatures.REZAL);
	      Holder<ConfiguredFeature<?, ?>> holder3 = holdergetter.getOrThrow(CTreeFeatures.MONORIS);
	      PlacementUtils.register(p_255688_, AUSLDINE_CHECKED, holder, PlacementUtils.filteredByBlockSurvival(CBlocks.AUSLDINE_SAPLING));
	      PlacementUtils.register(p_255688_, DEAD_OAK_CHECKED, holder1, PlacementUtils.filteredByBlockSurvival(Blocks.AIR));
	      PlacementUtils.register(p_255688_, REZAL_CHECKED, holder2, PlacementUtils.filteredByBlockSurvival(CBlocks.REZAL_LEAVES));
	      PlacementUtils.register(p_255688_, MONORIS_CHECKED, holder3, PlacementUtils.filteredByBlockSurvival(CBlocks.MONORIS_SAPLING));
	   }
}
