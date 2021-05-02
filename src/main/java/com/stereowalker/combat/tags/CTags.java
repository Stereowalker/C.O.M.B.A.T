package com.stereowalker.combat.tags;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class CTags {
	public static class FluidCTags {
//		public static Tag<Fluid> OIL = makeWrapperTag("oil");
//		public static Tag<Fluid> BIABLE = makeWrapperTag("biable");
		public static final ITag.INamedTag<Fluid> OIL = FluidTags.makeWrapperTag("combat:oil");
		public static final ITag.INamedTag<Fluid> BIABLE = FluidTags.makeWrapperTag("combat:biable");
		
//		private static Tag<Fluid> makeWrapperTag(String p_206956_0_) {
//		      return new FluidTags.Wrapper(Combat.location(p_206956_0_));
//		}
	}
	public static class BlockCTags {
//		public static Tag<Block> BEAMS = makeWrapperTag("beam");
		public static ITag.INamedTag<Block> BEAMS = BlockTags.makeWrapperTag("combat:beams");
		
//		private static Tag<Block> makeWrapperTag(String p_206956_0_) {
//		      return new BlockTags.Wrapper(Combat.location(p_206956_0_));
//		}
	}
	public static class ItemCTags {

		public static ITag.INamedTag<Item> SPELLBOOKS = ItemTags.makeWrapperTag("combat:spellbook");
		public static ITag.INamedTag<Item> BRACELETS = ItemTags.makeWrapperTag("curios:bracelet");
		public static ITag.INamedTag<Item> RINGS = ItemTags.makeWrapperTag("curios:ring");
		public static ITag.INamedTag<Item> CHARMS = ItemTags.makeWrapperTag("curios:charm");
		public static ITag.INamedTag<Item> NECKLACES = ItemTags.makeWrapperTag("curios:necklace");
		public static ITag.INamedTag<Item> INGOTS_STEEL = ItemTags.makeWrapperTag("forge:ingots/steel");
		public static ITag.INamedTag<Item> INGOTS_BRONZE = ItemTags.makeWrapperTag("forge:ingots/steel");
        
//        private static Tag<Item> tag(String name)
//        {
//            return new ItemTags.Wrapper(new ResourceLocation("forge", name));
//        }
//        
//        private static Tag<Block> makeWrapperTag(String p_206956_0_) {
//		      return new BlockTags.Wrapper(Combat.location(p_206956_0_));
//		}
	}
	
	public static void init() {
		ItemCTags.SPELLBOOKS = ItemTags.makeWrapperTag("combat:spellbook");
		ItemCTags.BRACELETS = ItemTags.makeWrapperTag("curios:bracelet");
		ItemCTags.RINGS = ItemTags.makeWrapperTag("curios:ring");
		ItemCTags.CHARMS = ItemTags.makeWrapperTag("curios:charm");
		ItemCTags.NECKLACES = ItemTags.makeWrapperTag("curios:necklace");
		ItemCTags.INGOTS_STEEL = ItemTags.makeWrapperTag("forge:ingots/steel");
		ItemCTags.INGOTS_BRONZE = ItemTags.makeWrapperTag("forge:ingots/bronze");
		BlockCTags.BEAMS = BlockTags.makeWrapperTag("combat:beams");
	}
	
}
