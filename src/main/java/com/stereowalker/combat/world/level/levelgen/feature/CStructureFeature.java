package com.stereowalker.combat.world.level.levelgen.feature;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.CStructureSets;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.AcrotlestMineshaftConfiguration;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.EtherionTowerConfiguration;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.ProbabilityStructureConfiguration;

import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class CStructureFeature<C extends FeatureConfiguration> extends StructureFeature<C> {

	public static final List<StructureFeature<?>> STRUCTURES = new ArrayList<StructureFeature<?>>();
	public static final StructureFeature<AcrotlestMineshaftConfiguration> ACROTLEST_MINESHAFT = register("acrotlest_mineshaft", new AcrotlestMineshaftFeature(AcrotlestMineshaftConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES);
	public static final StructureFeature<EtherionTowerConfiguration> ETHERION_TOWER = register("etherion_tower", new EtherionTowerFeature(EtherionTowerConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES);
	public static final StructureFeature<ProbabilityStructureConfiguration> ACROTLEST_PORTAL = register("acrotlest_portal", new AcrotlestPortalFeature(), GenerationStep.Decoration.SURFACE_STRUCTURES);
	public static final StructureFeature<ProbabilityStructureConfiguration> MAGIC_STONE_DEPOSIT = register("magic_stone_deposit", new MagicStoneDepositFeature(), GenerationStep.Decoration.UNDERGROUND_STRUCTURES);

	public CStructureFeature(Codec<C> p_197165_, PieceGeneratorSupplier<C> p_197166_) {
		super(p_197165_, p_197166_);
	}

	public static <F extends StructureFeature<?>> F register(String name, F structure, GenerationStep.Decoration genStage){
		STEP.put(structure, genStage);
		structure.setRegistryName(Combat.getInstance().location(name));
		STRUCTURES.add(structure);
		return structure;
	}

	public static void registerAll(IForgeRegistry<StructureFeature<?>> registry) {
		for(StructureFeature<?> structure: STRUCTURES) {
			registry.register(structure);
			Combat.debug("StructureFeature: \""+structure.getRegistryName().toString()+"\" registered");
		}
		CStructureSets.init();
		Combat.debug("All Structures Registered");
	}
}
