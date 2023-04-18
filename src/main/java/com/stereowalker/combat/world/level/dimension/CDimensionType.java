package com.stereowalker.combat.world.level.dimension;

import com.stereowalker.combat.Combat;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class CDimensionType {
	public static class CLevel{
		public static final ResourceKey<Level> ACROTLEST = ResourceKey.create(Registries.DIMENSION, Combat.getInstance().location("acrotlest"));
	}
	public static final ResourceLocation ACROTLEST_ID = Combat.getInstance().location("acrotlest");
	public static final ResourceKey<DimensionType> ACROTLEST = ResourceKey.create(Registries.DIMENSION_TYPE, Combat.getInstance().location("acrotlest"));
}
