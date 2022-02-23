package com.stereowalker.combat.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class BlockCTags {
	public static final Tag.Named<Block> NEEDS_PELGAN_TOOL = BlockTags.bind("combat:needs_pelgan_tool");
	public static final Tag.Named<Block> BEAMS = BlockTags.bind("combat:beams");
	public static final Tag.Named<Block> WOODEN_BEAMS = BlockTags.bind("combat:wooden_beams");
	public static final Tag.Named<Block> PYRANITE_FIRE_BASE_BLOCKS = BlockTags.bind("combat:pyranite_fire_base");

	public BlockCTags() {
	}
}
