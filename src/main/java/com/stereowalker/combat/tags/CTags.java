package com.stereowalker.combat.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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
	public static class BlockCTags {
//		public static Tag<Block> BEAMS = bind("beam");
		public static Tag.Named<Block> BEAMS = BlockTags.bind("combat:beams");
		public static Tag.Named<Block> WOODEN_BEAMS = BlockTags.bind("combat:wooden_beams");
		public static Tag.Named<Block> PYRANITE_FIRE_BASE_BLOCKS = BlockTags.bind("combat:pyranite_fire_base");
		
//		private static Tag<Block> bind(String p_206956_0_) {
//		      return new BlockTags.Wrapper(Combat.location(p_206956_0_));
//		}
	}
	public static class ItemCTags {

		public static Tag.Named<Item> SPELLBOOKS = ItemTags.bind("combat:spellbook");
		public static Tag.Named<Item> BRACELETS = ItemTags.bind("curios:bracelet");
		public static Tag.Named<Item> RINGS = ItemTags.bind("curios:ring");
		public static Tag.Named<Item> CHARMS = ItemTags.bind("curios:charm");
		public static Tag.Named<Item> NECKLACES = ItemTags.bind("curios:necklace");
		public static Tag.Named<Item> INGOTS_STEEL = ItemTags.bind("forge:ingots/steel");
		public static Tag.Named<Item> INGOTS_BRONZE = ItemTags.bind("forge:ingots/steel");
        
//        private static Tag<Item> tag(String name)
//        {
//            return new ItemTags.Wrapper(new ResourceLocation("forge", name));
//        }
//        
//        private static Tag<Block> bind(String p_206956_0_) {
//		      return new BlockTags.Wrapper(Combat.location(p_206956_0_));
//		}
	}
	
	public static void init() {
		ItemCTags.SPELLBOOKS = ItemTags.bind("combat:spellbook");
		ItemCTags.BRACELETS = ItemTags.bind("curios:bracelet");
		ItemCTags.RINGS = ItemTags.bind("curios:ring");
		ItemCTags.CHARMS = ItemTags.bind("curios:charm");
		ItemCTags.NECKLACES = ItemTags.bind("curios:necklace");
		ItemCTags.INGOTS_STEEL = ItemTags.bind("forge:ingots/steel");
		ItemCTags.INGOTS_BRONZE = ItemTags.bind("forge:ingots/bronze");
		BlockCTags.BEAMS = BlockTags.bind("combat:beams");
	}
	
}
