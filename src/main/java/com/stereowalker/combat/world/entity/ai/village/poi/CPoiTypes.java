package com.stereowalker.combat.world.entity.ai.village.poi;

import java.util.Set;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class CPoiTypes {
	public static final ResourceKey<PoiType> ACROTLEST_PORTAL = createKey("acrotlest_portal");

	public static PoiType register(ResourceKey<PoiType> pValue, Set<BlockState> pMatchingStates, int pMaxTickets, int pValidRange) {
		PoiType poi = new PoiType(pMatchingStates, pMaxTickets, pValidRange);
//		PoiTypes.registerBlockStates(BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolderOrThrow(pValue), pMatchingStates);
		return poi;
	}

	private static ResourceKey<PoiType> createKey(String pName) {
		return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, new ResourceLocation("combat",pName));
	}

	public static void registerAll(RegisterHelper<PoiType> registry) {
		registry.register(ACROTLEST_PORTAL, register(ACROTLEST_PORTAL, PoiTypes.getBlockStates(CBlocks.ACROTLEST_PORTAL), 0, 1));
		Combat.debug("All Point Of Interests Registered");
	}

}
