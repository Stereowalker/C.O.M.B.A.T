package com.stereowalker.combat.world.level.levelgen.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.data.worldgen.CStructureFeatures;
import com.stereowalker.combat.world.level.levelgen.CNoiseGeneratorSettings;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.AcrotlestMineshaftConfiguration;
import com.stereowalker.combat.world.level.levelgen.feature.configurations.ProbabilityStructureConfiguration;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class CStructureFeature<C extends FeatureConfiguration> extends StructureFeature<C> {

	public static final List<StructureFeature<?>> STRUCTURES = new ArrayList<StructureFeature<?>>();
	public static final StructureFeature<AcrotlestMineshaftConfiguration> ACROTLEST_MINESHAFT = register("acrotlest_mineshaft", new AcrotlestMineshaftFeature(AcrotlestMineshaftConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES);
	public static final StructureFeature<ProbabilityStructureConfiguration> ETHERION_TOWER = register("etherion_tower", new EtherionTowerFeature(), GenerationStep.Decoration.SURFACE_STRUCTURES);
	public static final StructureFeature<ProbabilityStructureConfiguration> ACROTLEST_PORTAL = register("acrotlest_portal", new AcrotlestPortalFeature(), GenerationStep.Decoration.SURFACE_STRUCTURES);
	public static final StructureFeature<ProbabilityStructureConfiguration> MAGIC_STONE_DEPOSIT = register("magic_stone_deposit", new MagicStoneDepositFeature(), GenerationStep.Decoration.UNDERGROUND_STRUCTURES);

	public CStructureFeature(Codec<C> p_i231997_1_) {
		super(p_i231997_1_);
	}

	public static <F extends StructureFeature<?>> F register(String name, F structure, GenerationStep.Decoration genStage){
		STRUCTURES_REGISTRY.put(name.toLowerCase(Locale.ROOT), structure);
		STEP.put(structure, genStage);
		structure.setRegistryName(Combat.getInstance().location(name));
		STRUCTURES.add(structure);
		return structure;
	}

	public static void registerAll(IForgeRegistry<StructureFeature<?>> registry) {
		for(StructureFeature<?> structure: STRUCTURES) {
			registry.register(structure);
			Combat.debug("StructureFeature: \""+structure.getRegistryName().toString()+"\" registered");
			if (structure instanceof ProbabilityFeature) {
				ProbabilityFeature probStructure = (ProbabilityFeature)structure;
				addStructureSeperation(CNoiseGeneratorSettings.ACROTLEST, probStructure, probStructure.getStructureSeperationSettings());
				addStructureSeperation(NoiseGeneratorSettings.OVERWORLD, probStructure, probStructure.getStructureSeperationSettings());
			}
		}
		Combat.debug("Registered "+CStructureFeatures.CONFIGURED_STRUCTURE_FEATURES.size()+" StructureFeature Features");
		for (Pair<String,ConfiguredStructureFeature<?, ?>> pair : CStructureFeatures.CONFIGURED_STRUCTURE_FEATURES) {
			Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, Combat.getInstance().location(pair.getLeft()), pair.getRight());
		}
		Combat.debug("All Structures Registered");
	}

	public static void addStructureSeperation(ResourceKey<NoiseGeneratorSettings> preset, StructureFeature<?> structure, StructureFeatureConfiguration settings) {
		BuiltinRegistries.NOISE_GENERATOR_SETTINGS.get(preset).structureSettings().structureConfig().put(structure, settings);
	}
}
