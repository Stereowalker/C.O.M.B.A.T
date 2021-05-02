package com.stereowalker.combat.fluid;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.IForgeRegistry;

public class CFluids {
	public static final List<Fluid> FLUIDS = new ArrayList<Fluid>();
	
	public static final FlowingFluid FLOWING_OIL = register("flowing_oil", new OilFluid.Flowing());
	public static final FlowingFluid OIL = register("oil", new OilFluid.Source());
	public static final FlowingFluid FLOWING_BIABLE = register("flowing_biable", new BiableFluid.Flowing());
	public static final FlowingFluid BIABLE = register("biable", new BiableFluid.Source());

	
	public static <T extends Fluid> T register(String name, T fluid) {
		fluid.setRegistryName(Combat.getInstance().location(name));
		FLUIDS.add(fluid);
		return fluid;
	}

	public static void registerAll(IForgeRegistry<Fluid> registry) {
		for(Fluid fluid: FLUIDS) {
			registry.register(fluid);
			Combat.debug("Fluid: \""+fluid.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Fluids Registered");
	}
}
