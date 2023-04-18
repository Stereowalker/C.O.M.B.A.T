package com.stereowalker.combat.tags;

import com.stereowalker.combat.Combat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockCTags {
	public static final TagKey<Block> NEEDS_LOZYNE_TOOL = bind("needs_lozyne_tool");
	public static final TagKey<Block> TREASURE_HUNTABLE = bind("treasure_huntable");
	public static final TagKey<Block> ACROTLEST_CARVER_REPLACEABLES = bind("acrotlest_carver_replaceables");
	public static final TagKey<Block> BEAMS = bind("beams");
	public static final TagKey<Block> WOODEN_BEAMS = bind("wooden_beams");
	public static final TagKey<Block> PYRANITE_FIRE_BASE_BLOCKS = bind("pyranite_fire_base");
	public static final TagKey<Block> MEZEPINE_ORE_REPLACEABLES = bind("mezepine_ore_replaceables");

	public BlockCTags() {
	}

	private static TagKey<Block> bind(String pName) {
		return BlockTags.create(new ResourceLocation(Combat.MODID, pName));
	}
}
