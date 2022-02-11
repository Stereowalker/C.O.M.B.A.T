package com.stereowalker.combat.world.food;

import net.minecraft.world.food.FoodProperties;

public class CFoodProperties {
	public static final FoodProperties CORN = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.9F).build();
	public static final FoodProperties ROAST_CORN = (new FoodProperties.Builder()).nutrition(6).saturationMod(1.35F).build();

}
