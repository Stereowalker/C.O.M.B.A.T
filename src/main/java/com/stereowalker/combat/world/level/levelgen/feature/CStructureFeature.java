package com.stereowalker.combat.world.level.levelgen.feature;

import net.minecraft.world.level.levelgen.structure.Structure;

public abstract class CStructureFeature extends Structure {

//	public static final List<StructureFeature<?>> STRUCTURES = new ArrayList<StructureFeature<?>>();
//	public static final StructureFeature<AcrotlestMineshaftConfiguration> ACROTLEST_MINESHAFT = register("acrotlest_mineshaft", new AcrotlestMineshaftFeature(AcrotlestMineshaftConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES);
//	public static final StructureFeature<EtherionTowerConfiguration> ETHERION_TOWER = register("etherion_tower", new EtherionTowerFeature(EtherionTowerConfiguration.CODEC), GenerationStep.Decoration.SURFACE_STRUCTURES);
//	public static final StructureFeature<ProbabilityStructureConfiguration> ACROTLEST_PORTAL = register("acrotlest_portal", new AcrotlestPortalFeature(), GenerationStep.Decoration.SURFACE_STRUCTURES);
//	public static final StructureFeature<ProbabilityStructureConfiguration> MAGIC_STONE_DEPOSIT = register("magic_stone_deposit", new MagicStoneDepositFeature(), GenerationStep.Decoration.UNDERGROUND_STRUCTURES);

	public CStructureFeature(Structure.StructureSettings pSettings) {
		super(pSettings);
	}

//	public static <F extends StructureFeature<?>> F register(String name, F structure, GenerationStep.Decoration genStage){
//		STEP.put(structure, genStage);
//		structure.setRegistryName(Combat.getInstance().location(name));
//		STRUCTURES.add(structure);
//		return structure;
//	}
//
//	public static void registerAll(IForgeRegistry<StructureFeature<?>> registry) {
//		for(StructureFeature<?> structure: STRUCTURES) {
//			registry.register(structure);
//			Combat.debug("StructureFeature: \""+structure.getRegistryName().toString()+"\" registered");
//		}
//		CStructureSets.init();
//		Combat.debug("All Structures Registered");
//	}
}
