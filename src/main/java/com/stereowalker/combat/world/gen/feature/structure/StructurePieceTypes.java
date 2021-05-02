package com.stereowalker.combat.world.gen.feature.structure;

import com.stereowalker.combat.Combat;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class StructurePieceTypes {
	static IStructurePieceType AMSCORRIDOR = IStructurePieceType.register(AcrotlestMineshaftPieces.Corridor::new, Combat.getInstance().getModid()+"AMSCorridor");
	static IStructurePieceType AMSCROSSING = IStructurePieceType.register(AcrotlestMineshaftPieces.Cross::new, Combat.getInstance().getModid()+"AMSCrossing");
	static IStructurePieceType AMSROOM = IStructurePieceType.register(AcrotlestMineshaftPieces.Room::new, Combat.getInstance().getModid()+"AMSRoom");
	static IStructurePieceType AMSSTAIRS = IStructurePieceType.register(AcrotlestMineshaftPieces.Stairs::new, Combat.getInstance().getModid()+"AMSStairs");
	static IStructurePieceType ACPORTAL = IStructurePieceType.register(AcrotlestPortalPiece.Piece::new, Combat.getInstance().getModid()+"ACPortal");
	static IStructurePieceType ETTOWER = IStructurePieceType.register(EtherionTowerPiece.Piece::new, Combat.getInstance().getModid()+"ETTower");
	static IStructurePieceType MSDEPOT = IStructurePieceType.register(MagicStoneDepositPiece.Piece::new, Combat.getInstance().getModid()+"MSDepot");
}
