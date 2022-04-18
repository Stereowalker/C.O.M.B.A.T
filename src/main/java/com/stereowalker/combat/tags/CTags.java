package com.stereowalker.combat.tags;

import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class CTags {
	public static class FluidCTags {
//		public static Tag<Fluid> OIL = bind("oil");
//		public static Tag<Fluid> BIABLE = bind("biable");
		public static final Tag.Named<Fluid> OIL = FluidTags.bind("combat:oil");
		public static final Tag.Named<Fluid> BIABLE = FluidTags.bind("combat:biable");
		
//		private static Tag<Fluid> bind(String p_206956_0_) {
//		      return new FluidTags.Wrapper(Combat.location(p_206956_0_));
//		}
	}
	
	public static void init() {
	}
	
}
