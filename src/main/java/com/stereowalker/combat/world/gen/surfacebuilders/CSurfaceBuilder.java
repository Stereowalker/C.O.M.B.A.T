package com.stereowalker.combat.world.gen.surfacebuilders;

import com.stereowalker.combat.block.CBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class CSurfaceBuilder {
	//TODO: Create Surface Builders
	private static final BlockState CALTAS = CBlocks.CALTAS.getDefaultState();
	private static final BlockState ELYCEN_BLOCK = CBlocks.ELYCEN_BLOCK.getDefaultState();
	private static final BlockState PURIFIED_DIRT = CBlocks.PURIFIED_DIRT.getDefaultState();
	private static final BlockState PURIFIED_GRASS = CBlocks.PURIFIED_GRASS_BLOCK.getDefaultState();
	private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
	private static final BlockState HOMSE = CBlocks.HOMSE.getDefaultState();
	public static final SurfaceBuilderConfig PURIFIED_GRASS_PURIFIED_DIRT_GRAVEL_CONFIG = new SurfaceBuilderConfig(PURIFIED_GRASS, PURIFIED_DIRT, GRAVEL);
	public static final SurfaceBuilderConfig HOMSE_HOMSE_GRAVEL_CONFIG = new SurfaceBuilderConfig(HOMSE, HOMSE, GRAVEL);
	public static final SurfaceBuilderConfig ELYCEN_CALTAS_GRAVEL_CONFIG = new SurfaceBuilderConfig(ELYCEN_BLOCK, CALTAS, GRAVEL);
}
