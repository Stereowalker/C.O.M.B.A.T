package com.stereowalker.combat.world.level.levelgen.feature;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.level.levelgen.structure.AcrotlestMineShaftPieces;
import com.stereowalker.combat.world.level.levelgen.structure.AcrotlestPortalPieces;
import com.stereowalker.combat.world.level.levelgen.structure.EtherionTowerPieces;
import com.stereowalker.combat.world.level.levelgen.structure.MagicStoneDepositPieces;

import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;


public interface StructurePieceTypes {
	StructurePieceType MINE_SHAFT_CORRIDOR = StructurePieceType.setPieceId(AcrotlestMineShaftPieces.AcrotlestMineShaftCorridor::new, "CMSCorridor");
	StructurePieceType MINE_SHAFT_CROSSING = StructurePieceType.setPieceId(AcrotlestMineShaftPieces.AcrotlestMineShaftCrossing::new, "CMSCrossing");
	StructurePieceType MINE_SHAFT_ROOM = StructurePieceType.setPieceId(AcrotlestMineShaftPieces.AcrotlestMineShaftRoom::new, "CMSRoom");
	StructurePieceType MINE_SHAFT_STAIRS = StructurePieceType.setPieceId(AcrotlestMineShaftPieces.AcrotlestMineShaftStairs::new, "CMSStairs");
	StructurePieceType ACROTLEST_PORTAL = StructurePieceType.setTemplatePieceId(AcrotlestPortalPieces.Piece::new, Combat.getInstance().getModid()+"ACPortal");
	StructurePieceType ETHERION_TOWER = StructurePieceType.setTemplatePieceId(EtherionTowerPieces.Piece::new, Combat.getInstance().getModid()+"ETTower");
	StructurePieceType AGIC_STONE_DEPOT = StructurePieceType.setTemplatePieceId(MagicStoneDepositPieces.Piece::new, Combat.getInstance().getModid()+"MSDepot");
}
