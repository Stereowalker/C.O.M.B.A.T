package com.stereowalker.combat.village;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.CBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.registries.IForgeRegistry;

public class CPointOfInterestType {
	private static final List<PointOfInterestType> POINT_OF_INTEREST = new ArrayList<PointOfInterestType>();
	//-Metals-\\
	public static final PointOfInterestType ACROTLEST_PORTAL = register("acrotlest_portal", PointOfInterestType.getAllStates(CBlocks.ACROTLEST_PORTAL), 0, 1);

	public static PointOfInterestType register(String name, Set<BlockState> blockStatesIn, int maxFreeTicketsIn, int validRangeIn) {
		PointOfInterestType poi = PointOfInterestType.registerBlockStates(new PointOfInterestType(name, blockStatesIn, maxFreeTicketsIn, validRangeIn));
		poi.setRegistryName(Combat.getInstance().location(name));
		CPointOfInterestType.POINT_OF_INTEREST.add(poi);
		return poi;
	}

	public static void registerAll(IForgeRegistry<PointOfInterestType> registry) {
		for(PointOfInterestType block: POINT_OF_INTEREST) {
			registry.register(block);
			Combat.debug("Point Of Interest: \""+block.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Point Of Interests Registered");
	}

}
