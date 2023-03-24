package com.stereowalker.combat.world.level.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

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
