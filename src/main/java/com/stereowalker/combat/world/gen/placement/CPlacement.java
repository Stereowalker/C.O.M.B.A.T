package com.stereowalker.combat.world.gen.placement;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Height4To32;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class CPlacement {
	private static final List<Placement<?>> PLACEMENT = new ArrayList<Placement<?>>();

	public static final Placement<ChanceConfig> TSUNE_LAKE = register("tsune_lake", new LakeTsune(ChanceConfig.CODEC));
	public static final Placement<ChanceConfig> BIABLE_LAKE = register("biable_lake", new LakeTsune(ChanceConfig.CODEC));
	public static final Placement<NoPlacementConfig> RUBY_ORE = register("ruby_ore", new Height4To32(NoPlacementConfig.CODEC));

	private static <T extends IPlacementConfig, G extends Placement<T>> G register(String name, G placement) {
		placement.setRegistryName(Combat.getInstance().location(name));
		PLACEMENT.add(placement);
		return placement;
	}

	public static void registerAll(IForgeRegistry<Placement<?>> registry) {
		for(Placement<?> feature: PLACEMENT) {
			registry.register(feature);
			Combat.debug("Decorator: \""+feature.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All DecoratorsS Registered");
	}
}
