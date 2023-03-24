package com.stereowalker.combat.world.level.dimension;

import com.stereowalker.combat.Combat;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class CDimensionType {
	public static class CLevel{
		public static final ResourceKey<Level> ACROTLEST = ResourceKey.create(Registry.DIMENSION_REGISTRY, Combat.getInstance().location("acrotlest"));
	}
	public static final ResourceLocation ACROTLEST_ID = Combat.getInstance().location("acrotlest");
	public static final ResourceKey<DimensionType> ACROTLEST = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, Combat.getInstance().location("acrotlest"));
}
