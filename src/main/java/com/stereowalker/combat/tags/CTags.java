package com.stereowalker.combat.tags;

import com.stereowalker.combat.Combat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class CTags {
	public static class FluidCTags {
		public static final TagKey<Fluid> OIL = FluidTags.create(new ResourceLocation(Combat.MODID, "oil"));
		public static final TagKey<Fluid> BIABLE = FluidTags.create(new ResourceLocation(Combat.MODID, "biable"));
		
	}
	
	public static void init() {
	}
	
}
