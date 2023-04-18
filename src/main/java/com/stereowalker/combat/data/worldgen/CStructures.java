package com.stereowalker.combat.data.worldgen;

import com.stereowalker.combat.tags.BiomeCTags;
import com.stereowalker.combat.world.level.levelgen.structure.AcrotlestMineshaftStructure;
import com.stereowalker.combat.world.level.levelgen.structure.AcrotlestPortalStructure;
import com.stereowalker.combat.world.level.levelgen.structure.CombatStructures;
import com.stereowalker.combat.world.level.levelgen.structure.EtherionTowerStructure;
import com.stereowalker.combat.world.level.levelgen.structure.MagicStoneDepositStructure;
import com.stereowalker.combat.world.level.levelgen.structure.ProbabilityStructure;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class CStructures {
	
	public static void init() {}
	public static void bootstrap(BootstapContext<Structure> pContext) {
		HolderGetter<Biome> holdergetter = pContext.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> holdergetter1 = pContext.lookup(Registries.TEMPLATE_POOL);
		pContext.register(CombatStructures.ACROTLEST_PORTAL, new AcrotlestPortalStructure(Structures.structure(holdergetter.getOrThrow(BiomeCTags.ACROTLEST_PORTAL), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE), ProbabilityStructure.DimensionVariant.OVERWORLD));
		pContext.register(CombatStructures.OVERWORLD_PORTAL, new AcrotlestPortalStructure(Structures.structure(holdergetter.getOrThrow(BiomeCTags.OVERWORLD_PORTAL), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE), ProbabilityStructure.DimensionVariant.ACROLEST));
		pContext.register(CombatStructures.ETHERION_TOWER, new EtherionTowerStructure(Structures.structure(holdergetter.getOrThrow(BiomeCTags.HAS_ETHERION_TOWER), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE), EtherionTowerStructure.Variant.OVERWORLD));
		pContext.register(CombatStructures.ETHERION_TOWER_BADLANDS, new EtherionTowerStructure(Structures.structure(holdergetter.getOrThrow(BiomeCTags.HAS_ETHERION_TOWER_BADLANDS), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE), EtherionTowerStructure.Variant.BADLANDS));
		pContext.register(CombatStructures.ETHERION_TOWER_DESERT, new EtherionTowerStructure(Structures.structure(holdergetter.getOrThrow(BiomeCTags.HAS_ETHERION_TOWER_DESERT), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE), EtherionTowerStructure.Variant.DESERT));
		pContext.register(CombatStructures.ETHERION_TOWER_ACROTLEST, new EtherionTowerStructure(Structures.structure(holdergetter.getOrThrow(BiomeCTags.HAS_ETHERION_TOWER_ACROTLEST), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE), EtherionTowerStructure.Variant.ACROLEST));
		pContext.register(CombatStructures.MAGIC_STONE_DEPOSIT, new MagicStoneDepositStructure(Structures.structure(holdergetter.getOrThrow(BiomeCTags.MAGIC_STONE_DEPOSIT), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE)));
		pContext.register(CombatStructures.ACROTLEST_MINESHAFT_NORMAL, new AcrotlestMineshaftStructure(Structures.structure(holdergetter.getOrThrow(BiomeCTags.ACROTLEST_MINESHAFT_NORMAL), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE), AcrotlestMineshaftStructure.Type.NORMAL));
	}
}
