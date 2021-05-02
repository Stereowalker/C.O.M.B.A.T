//package com.stereowalker.combat.world.dimension;
//
//import java.util.function.BiFunction;
//
//import net.minecraft.world.World;
//import net.minecraft.world.dimension.Dimension;
//import net.minecraft.world.dimension.DimensionType;
//import net.minecraftforge.common.ModDimension;
//
//public class AcrotlestModDimension extends ModDimension {
//	
//	public AcrotlestModDimension() {
//	}
//	
//	public static DimensionType getDimensionType() {
//		return CDimensionType.THE_ACROTLEST;
//	}
//	
//	@Override
//	public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
//		return AcrotlestDimension::new;
//	}
//
//}
