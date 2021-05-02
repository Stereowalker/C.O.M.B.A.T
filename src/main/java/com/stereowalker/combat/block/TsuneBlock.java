package com.stereowalker.combat.block;

import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;

public class TsuneBlock extends Block {
	DyeColor color;
	
	public TsuneBlock(DyeColor color, Properties properties) {
		super(properties);
		this.color = color;
	}

	public DyeColor getColor() {
		return color;
	}
}
