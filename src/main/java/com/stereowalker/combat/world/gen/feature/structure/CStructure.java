package com.stereowalker.combat.world.gen.feature.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.serialization.Codec;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.gen.CDimensionSettings;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class CStructure<C extends IFeatureConfig> extends Structure<C> {

	public static final List<Structure<?>> STRUCTURES = new ArrayList<Structure<?>>();
	public static final Structure<AcrotlestMineshaftConfig> ACROTLEST_MINESHAFT = register("acrotlest_mineshaft", new AcrotlestMineshaftStructure(AcrotlestMineshaftConfig.field_236541_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
	public static final Structure<ProbabilityStructureConfig> ETHERION_TOWER = register("etherion_tower", new EtherionTowerStructure(), GenerationStage.Decoration.SURFACE_STRUCTURES);
	public static final Structure<ProbabilityStructureConfig> ACROTLEST_PORTAL = register("acrotlest_portal", new AcrotlestPortalStructure(), GenerationStage.Decoration.SURFACE_STRUCTURES);
	public static final Structure<ProbabilityStructureConfig> MAGIC_STONE_DEPOSIT = register("magic_stone_deposit", new MagicStoneDepositStructure(), GenerationStage.Decoration.SURFACE_STRUCTURES);

	public CStructure(Codec<C> p_i231997_1_) {
		super(p_i231997_1_);
	}

	public static <F extends Structure<?>> F register(String name, F structure, GenerationStage.Decoration genStage){
		NAME_STRUCTURE_BIMAP.put(name.toLowerCase(Locale.ROOT), structure);
		STRUCTURE_DECORATION_STAGE_MAP.put(structure, genStage);
		structure.setRegistryName(Combat.getInstance().location(name));
		STRUCTURES.add(structure);
		return structure;
	}

	public static void registerAll(IForgeRegistry<Structure<?>> registry) {
		for(Structure<?> structure: STRUCTURES) {
			registry.register(structure);
			Combat.debug("Structure: \""+structure.getRegistryName().toString()+"\" registered");
			if (structure instanceof ProbabilityStructure) {
				ProbabilityStructure probStructure = (ProbabilityStructure)structure;
				addStructureSeperation(CDimensionSettings.ACROTLEST, probStructure, probStructure.getStructureSeperationSettings());
				addStructureSeperation(DimensionSettings.OVERWORLD, probStructure, probStructure.getStructureSeperationSettings());
			}
		}
		Combat.debug("Registered "+CStructureFeatures.CONFIGURED_STRUCTURE_FEATURES.size()+" Structure Features");
		for (Pair<String,StructureFeature<?, ?>> pair : CStructureFeatures.CONFIGURED_STRUCTURE_FEATURES) {
			Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, Combat.getInstance().location(pair.getLeft()), pair.getRight());
		}
		Combat.debug("All Structures Registered");
	}

	public static void addStructureSeperation(RegistryKey<DimensionSettings> preset, Structure<?> structure, StructureSeparationSettings settings) {
		WorldGenRegistries.NOISE_SETTINGS.getValueForKey(preset).getStructures().func_236195_a_().put(structure, settings);
	}
}
