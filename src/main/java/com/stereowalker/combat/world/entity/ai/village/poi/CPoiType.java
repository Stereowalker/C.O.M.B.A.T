package com.stereowalker.combat.world.entity.ai.village.poi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.IForgeRegistry;

public class CPoiType {
	private static final List<PoiType> POINT_OF_INTEREST = new ArrayList<PoiType>();
	//-Metals-\\
	public static final PoiType ACROTLEST_PORTAL = register("acrotlest_portal", PoiType.getBlockStates(CBlocks.ACROTLEST_PORTAL), 0, 1);

	public static PoiType register(String name, Set<BlockState> blockStatesIn, int maxFreeTicketsIn, int validRangeIn) {
		PoiType poi = PoiType.registerBlockStates(new PoiType(name, blockStatesIn, maxFreeTicketsIn, validRangeIn));
		poi.setRegistryName(Combat.getInstance().location(name));
		CPoiType.POINT_OF_INTEREST.add(poi);
		return poi;
	}

	public static void registerAll(IForgeRegistry<PoiType> registry) {
		for(PoiType block: POINT_OF_INTEREST) {
			registry.register(block);
			Combat.debug("Point Of Interest: \""+block.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Point Of Interests Registered");
	}

}
