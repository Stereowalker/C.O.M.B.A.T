package com.stereowalker.combat.world.level.levelgen.placement;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class CFeatureDecorator {
	private static final List<FeatureDecorator<?>> PLACEMENT = new ArrayList<FeatureDecorator<?>>();

//	public static final FeatureDecorator<ChanceDecoratorConfiguration> TSUNE_LAKE = register("tsune_lake", new LakeTsune(ChanceDecoratorConfiguration.CODEC));
//	public static final FeatureDecorator<ChanceDecoratorConfiguration> BIABLE_LAKE = register("biable_lake", new LakeTsune(ChanceDecoratorConfiguration.CODEC));

	private static <T extends DecoratorConfiguration, G extends FeatureDecorator<T>> G register(String name, G placement) {
		placement.setRegistryName(Combat.getInstance().location(name));
		PLACEMENT.add(placement);
		return placement;
	}

	public static void registerAll(IForgeRegistry<FeatureDecorator<?>> registry) {
		for(FeatureDecorator<?> feature: PLACEMENT) {
			registry.register(feature);
			Combat.debug("Decorator: \""+feature.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All DecoratorsS Registered");
	}
}
