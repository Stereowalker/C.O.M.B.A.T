package com.stereowalker.combat.world.level.levelgen.structure;

import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public interface CStructureType<S extends Structure> extends StructureType<S> {
	   StructureType<AcrotlestMineshaftStructure> ACROSTLEST_MINESHAFT = StructureType.register("combat:acrotlest_mineshaft", AcrotlestMineshaftStructure.CODEC);
	   StructureType<AcrotlestPortalStructure> ACROSTLEST_PORTAL = StructureType.register("combat:acrotlest_portal", AcrotlestPortalStructure.CODEC);
	   StructureType<EtherionTowerStructure> ETHERION_TOWER = StructureType.register("combat:etherion_tower", EtherionTowerStructure.CODEC);
	   StructureType<MagicStoneDepositStructure> MAGIC_STONE_DEPOSIT = StructureType.register("combat:magic_stone_deposit", MagicStoneDepositStructure.CODEC);
	   
	   public static void init() {
		   
	   }
}
