package com.stereowalker.combat.world.level.block.state.properties;

import net.minecraft.world.level.block.state.properties.WoodType;

public class CWoodType extends WoodType {
	
	protected CWoodType(String p_i225775_1_) {
		super(p_i225775_1_);
	}

	public static final WoodType AUSLDINE = WoodType.register(new CWoodType("ausldine"));
	public static final WoodType DEAD_OAK = WoodType.register(new CWoodType("dead_oak"));
	public static final WoodType MONORIS = WoodType.register(new CWoodType("monoris"));
}
