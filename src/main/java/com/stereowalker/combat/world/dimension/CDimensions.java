//package com.stereowalker.combat.world.dimension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.stereowalker.combat.Combat;
//
//import net.minecraftforge.common.ModDimension;
//import net.minecraftforge.registries.IForgeRegistry;
//
//public class CDimensions {
//	public static final List<ModDimension> DIMENSIONS = new ArrayList<ModDimension>();
//	public static final ModDimension ACROTLEST = register("acrotlest", new AcrotlestModDimension());
//	
//	public static ModDimension register(String name, ModDimension dimension) {
//		dimension.setRegistryName(Combat.location(name));
//		DIMENSIONS.add(dimension);
//		return dimension;
//	}
//
//	public static void registerAll(IForgeRegistry<ModDimension> registry) {
//		for(ModDimension dimension: DIMENSIONS) {
//			registry.register(dimension);
//			Combat.debug("Dimension: \""+dimension.getRegistryName().toString()+"\" registered");
//		}
//		Combat.debug("All Dimensions Registered");
//	}
//}
