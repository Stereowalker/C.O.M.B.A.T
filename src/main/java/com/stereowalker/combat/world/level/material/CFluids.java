package com.stereowalker.combat.world.level.material;

import com.stereowalker.unionlib.core.registries.RegistryHolder;
import com.stereowalker.unionlib.core.registries.RegistryObject;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

@RegistryHolder(namespace = "combat", registry = Fluid.class)
public class CFluids {
	@RegistryObject("flowing_oil")
	public static final FlowingFluid FLOWING_OIL = new OilFluid.Flowing();
	@RegistryObject("oil")
	public static final FlowingFluid OIL = new OilFluid.Source();
	@RegistryObject("flowing_biable")
	public static final FlowingFluid FLOWING_BIABLE = new BiableFluid.Flowing();
	@RegistryObject("biable")
	public static final FlowingFluid BIABLE = new BiableFluid.Source();
}
