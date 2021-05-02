package com.stereowalker.combat.world;

import java.util.OptionalLong;

import com.stereowalker.combat.Combat;

import net.minecraft.tags.BlockTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.ColumnFuzzedBiomeMagnifier;

public class CDimensionType {
	public static class CWorld{
		public static final RegistryKey<World> ACROTLEST = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, Combat.getInstance().location("acrotlest"));
	}
	public static final ResourceLocation ACROTLEST_ID = Combat.getInstance().location("acrotlest");
	public static final RegistryKey<DimensionType> ACROTLEST = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, Combat.getInstance().location("acrotlest"));
	protected static final DimensionType ACROTLEST_TYPE = new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0D, false, false, true, false, true, 256, ColumnFuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getName(), ACROTLEST_ID, 0.0F);

}
