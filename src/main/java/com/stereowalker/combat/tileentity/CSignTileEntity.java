package com.stereowalker.combat.tileentity;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class CSignTileEntity extends SignTileEntity {

	public CSignTileEntity() {
		super();
	}

	@Override
	public TileEntityType<?> getType() {
		return CTileEntityType.SIGN;
	}
}
