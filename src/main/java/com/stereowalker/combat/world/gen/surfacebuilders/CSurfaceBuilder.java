package com.stereowalker.combat.world.gen.surfacebuilders;

import com.stereowalker.combat.block.CBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class CSurfaceBuilder {
	//TODO: Create Surface Builders
	public static final BlockState PURIFIED_DIRT = CBlocks.PURIFIED_DIRT.getDefaultState();
	public static final BlockState PURIFIED_GRASS = CBlocks.PURIFIED_GRASS_BLOCK.getDefaultState();
	public static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
	public static final BlockState HOMSE = CBlocks.HOMSE.getDefaultState();
	public static final SurfaceBuilderConfig PURIFIED_GRASS_PURIFIED_DIRT_GRAVEL_CONFIG = new SurfaceBuilderConfig(PURIFIED_GRASS, PURIFIED_DIRT, GRAVEL);
	public static final SurfaceBuilderConfig HOMSE_HOMSE_GRAVEL_CONFIG = new SurfaceBuilderConfig(HOMSE, HOMSE, GRAVEL);
}
