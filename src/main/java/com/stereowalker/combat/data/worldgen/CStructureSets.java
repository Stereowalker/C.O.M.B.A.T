package com.stereowalker.combat.data.worldgen;

import java.util.List;

import com.stereowalker.combat.world.level.levelgen.structure.CombatStructureSets;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public interface CStructureSets {
	Holder<StructureSet> ETHERION_TOWERS = StructureSets.register(CombatStructureSets.ETHERION_TOWERS, new StructureSet(List.of(StructureSet.entry(CStructureFeatures.ETHERION_TOWER), StructureSet.entry(CStructureFeatures.ETHERION_TOWER_DESERT), StructureSet.entry(CStructureFeatures.ETHERION_TOWER_ACROTLEST), StructureSet.entry(CStructureFeatures.ETHERION_TOWER_BADLANDS)), new RandomSpreadStructurePlacement(190, 95, RandomSpreadType.LINEAR, 53060063)));
	Holder<StructureSet> ACROTLEST_MINESHAFTS = StructureSets.register(CombatStructureSets.ACROTLEST_MINESHAFTS, new StructureSet(List.of(StructureSet.entry(CStructureFeatures.ACROTLEST_MINESHAFT_NORMAL)), new RandomSpreadStructurePlacement(1, 0, RandomSpreadType.LINEAR, 0)));
	Holder<StructureSet> ACROTLEST_PORTALS = StructureSets.register(CombatStructureSets.ACROTLEST_PORTALS, new StructureSet(List.of(StructureSet.entry(CStructureFeatures.ACROTLEST_PORTAL), StructureSet.entry(CStructureFeatures.OVERWORLD_PORTAL)), new RandomSpreadStructurePlacement(100, 10, RandomSpreadType.LINEAR, 65165156)));
	Holder<StructureSet> MAGIC_STONE_DEPOSITS = StructureSets.register(CombatStructureSets.MAGIC_STONE_DEPOSITS, new StructureSet(List.of(StructureSet.entry(CStructureFeatures.MAGIC_STONE_DEPOSIT)), new RandomSpreadStructurePlacement(10, 4, RandomSpreadType.LINEAR, 71509846)));
	 
	public static void init() {
		
	}
}
