package com.stereowalker.combat.block;

import net.minecraft.block.WoodType;

public class CWoodType extends WoodType{
	
	protected CWoodType(String p_i225775_1_) {
		super(p_i225775_1_);
	}

	public static final WoodType AUSLDINE = WoodType.register(new CWoodType("ausldine"));
	public static final WoodType DEAD_OAK = WoodType.register(new CWoodType("dead_oak"));
	public static final WoodType MONORIS = WoodType.register(new CWoodType("monoris"));
}
