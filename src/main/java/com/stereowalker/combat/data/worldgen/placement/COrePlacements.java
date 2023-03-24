package com.stereowalker.combat.data.worldgen.placement;

import java.util.List;

import com.stereowalker.combat.data.worldgen.features.COreFeatures;
import com.stereowalker.old.combat.config.Config;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class COrePlacements {
	public static final Holder<PlacedFeature> ORE_PELGAN = register("ore_plegan", COreFeatures.ORE_PELGAN, OrePlacements.commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(60), VerticalAnchor.absolute(200))));
	public static final Holder<PlacedFeature> ORE_LOZYNE = register("ore_lozyne", COreFeatures.ORE_LOZYNE, OrePlacements.commonOrePlacement(1, HeightRangePlacement.triangle(VerticalAnchor.absolute(-120), VerticalAnchor.absolute(60))));
	public static final Holder<PlacedFeature> ORE_SERABLE = register("ore_serable", COreFeatures.ORE_SERABLE, OrePlacements.commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(32))));
	public static final Holder<PlacedFeature> ORE_PYRANITE = register("ore_pyranite", COreFeatures.ORE_PYRANITE, OrePlacements.commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))));
	public static final Holder<PlacedFeature> ORE_LIMESTONE = register("ore_limestone", COreFeatures.ORE_LIMESTONE, OrePlacements.commonOrePlacement(40, HeightRangePlacement.triangle(VerticalAnchor.absolute(31), VerticalAnchor.absolute(62))));
	public static final Holder<PlacedFeature> ORE_CASSITERITE = register("ore_cassiterite", COreFeatures.ORE_CASSITERITE, OrePlacements.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(63))));
	public static final Holder<PlacedFeature> ORE_YELLOW_CLUSTER = register("ore_yellow_cluster", COreFeatures.ORE_YELLOW_CLUSTER, OrePlacements.commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256))));
	public static final Holder<PlacedFeature> ORE_RUBY = register("ore_ruby", COreFeatures.ORE_RUBY, OrePlacements.commonOrePlacement(100, HeightRangePlacement.triangle(VerticalAnchor.absolute(4), VerticalAnchor.absolute(31))));
	public static final Holder<PlacedFeature> ORE_TRIDOX = register("ore_tridox", COreFeatures.ORE_TRIDOX, OrePlacements.commonOrePlacement(Config.SERVER.tridoxChance.get(), HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(27))));
	public static final Holder<PlacedFeature> ORE_PASQUEM = register("ore_pasquem", COreFeatures.ORE_PASQUEM, OrePlacements.commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));
	public static final Holder<PlacedFeature> ORE_YELLOW_CLUSTER_MAGIC = register("ore_yellow_cluster_magic", COreFeatures.ORE_YELLOW_CLUSTER_MAGIC, OrePlacements.commonOrePlacement(190, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256))));
	public static final Holder<PlacedFeature> ORE_TRIDOX_MAGIC = register("ore_tridox_magic", COreFeatures.ORE_TRIDOX_MAGIC, OrePlacements.commonOrePlacement(Config.SERVER.tridoxChance.get()*2, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(27))));
	public static final Holder<PlacedFeature> DEAD_ORE_COPPER = register("dead_ore_copper", COreFeatures.DEAD_ORE_COPPER, OrePlacements.commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(96))));
	public static final Holder<PlacedFeature> DEAD_ORE_COAL = register("dead_ore_coal", COreFeatures.DEAD_ORE_COAL, OrePlacements.commonOrePlacement(20, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(127))));
	public static final Holder<PlacedFeature> DEAD_ORE_IRON = register("dead_ore_iron", COreFeatures.DEAD_ORE_IRON, OrePlacements.commonOrePlacement(20, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63))));
	public static final Holder<PlacedFeature> DEAD_ORE_GOLD = register("dead_ore_gold", COreFeatures.DEAD_ORE_GOLD, OrePlacements.commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(31))));
	public static final Holder<PlacedFeature> DEAD_ORE_REDSTONE = register("dead_ore_redstone", COreFeatures.DEAD_ORE_REDSTONE, OrePlacements.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15))));
	public static final Holder<PlacedFeature> DEAD_ORE_DIAMOND = register("dead_ore_diamond", COreFeatures.DEAD_ORE_DIAMOND, OrePlacements.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15))));
	public static final Holder<PlacedFeature> DEAD_ORE_LAPIS = register("dead_ore_lapis", COreFeatures.DEAD_ORE_LAPIS, OrePlacements.commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(30))));
	public static final Holder<PlacedFeature> DEAD_ORE_PASQUEM = register("dead_ore_pasquem", COreFeatures.DEAD_ORE_PASQUEM, OrePlacements.commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));


	public static Holder<PlacedFeature> register(String p_206510_, Holder<? extends ConfiguredFeature<?, ?>> p_206511_, List<PlacementModifier> p_206512_) {
		return PlacementUtils.register("combat"+p_206510_, p_206511_, p_206512_);
	}

	public static Holder<PlacedFeature> register(String p_206514_, Holder<? extends ConfiguredFeature<?, ?>> p_206515_, PlacementModifier... p_206516_) {
		return PlacementUtils.register("combat"+p_206514_, p_206515_, p_206516_);
	}
}
