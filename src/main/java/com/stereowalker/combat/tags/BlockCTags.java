package com.stereowalker.combat.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockCTags {
	public static final TagKey<Block> NEEDS_LOZYNE_TOOL = bind("combat:needs_lozyne_tool");
	public static final TagKey<Block> BEAMS = bind("combat:beams");
	public static final TagKey<Block> WOODEN_BEAMS = bind("combat:wooden_beams");
	public static final TagKey<Block> PYRANITE_FIRE_BASE_BLOCKS = bind("combat:pyranite_fire_base");

	public BlockCTags() {
	}

	private static TagKey<Block> bind(String pName) {
		return BlockTags.create(new ResourceLocation(pName));
	}
}
