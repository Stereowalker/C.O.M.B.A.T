package com.stereowalker.combat.data.worldgen;

import java.util.List;
import java.util.Optional;

import com.stereowalker.combat.world.level.levelgen.structure.CombatStructureSets;
import com.stereowalker.combat.world.level.levelgen.structure.CombatStructures;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public interface CStructureSets {
	public static void init() {}
	static void bootstrap(BootstapContext<StructureSet> pContext) {
	      HolderGetter<Structure> holdergetter = pContext.lookup(Registries.STRUCTURE);
	      HolderGetter<Biome> holdergetter1 = pContext.lookup(Registries.BIOME);
	      Holder.Reference<StructureSet> reference = pContext.register(BuiltinStructureSets.VILLAGES, new StructureSet(List.of(StructureSet.entry(holdergetter.getOrThrow(BuiltinStructures.VILLAGE_PLAINS)), StructureSet.entry(holdergetter.getOrThrow(BuiltinStructures.VILLAGE_DESERT)), StructureSet.entry(holdergetter.getOrThrow(BuiltinStructures.VILLAGE_SAVANNA)), StructureSet.entry(holdergetter.getOrThrow(BuiltinStructures.VILLAGE_SNOWY)), StructureSet.entry(holdergetter.getOrThrow(BuiltinStructures.VILLAGE_TAIGA))), new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.LINEAR, 10387312)));
	      pContext.register(CombatStructureSets.MAGIC_STONE_DEPOSITS, new StructureSet(List.of(StructureSet.entry(holdergetter.getOrThrow(CombatStructures.MAGIC_STONE_DEPOSIT))), new RandomSpreadStructurePlacement(10, 4, RandomSpreadType.LINEAR, 71509846)));
	      pContext.register(CombatStructureSets.ACROTLEST_PORTALS, new StructureSet(List.of(StructureSet.entry(holdergetter.getOrThrow(CombatStructures.ACROTLEST_PORTAL)), StructureSet.entry(holdergetter.getOrThrow(CombatStructures.OVERWORLD_PORTAL))), new RandomSpreadStructurePlacement(100, 10, RandomSpreadType.LINEAR, 65165156)));
	      pContext.register(CombatStructureSets.ETHERION_TOWERS, new StructureSet(List.of(StructureSet.entry(holdergetter.getOrThrow(CombatStructures.ETHERION_TOWER)), StructureSet.entry(holdergetter.getOrThrow(CombatStructures.ETHERION_TOWER_ACROTLEST)), StructureSet.entry(holdergetter.getOrThrow(CombatStructures.ETHERION_TOWER_BADLANDS)), StructureSet.entry(holdergetter.getOrThrow(CombatStructures.ETHERION_TOWER_DESERT))), new RandomSpreadStructurePlacement(190, 95, RandomSpreadType.LINEAR, 53060063)));
	      pContext.register(CombatStructureSets.ACROTLEST_MINESHAFTS, new StructureSet(List.of(StructureSet.entry(holdergetter.getOrThrow(CombatStructures.ACROTLEST_MINESHAFT_NORMAL))), new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_3, 0.004F, 0, Optional.empty(), 1, 0, RandomSpreadType.LINEAR)));
	   }
}
