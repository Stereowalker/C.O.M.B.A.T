package com.stereowalker.combat.world.level.block;

import com.stereowalker.combat.tags.BlockCTags;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PyraniteFireBlock extends FireBlock {

	public PyraniteFireBlock(Properties p_53425_) {
		super(p_53425_);
		this.fireDamage = 2.0f;
	}

	@Override
	public BlockState getStateWithAge(LevelAccessor pLevel, BlockPos pPos, int pAge) {
		BlockState blockstate = getState(pLevel, pPos);
		return blockstate.is(CBlocks.PYRANITE_FIRE) ? blockstate.setValue(AGE, Integer.valueOf(pAge)) : blockstate;
	}

	public static void bootStrap() {
		PyraniteFireBlock fireblock = (PyraniteFireBlock)CBlocks.PYRANITE_FIRE;
		fireblock.setFlammable(CBlocks.MEZEPINE, 60, 100);
		fireblock.setFlammable(CBlocks.PYRANITE_ORE, 60, 100);

		fireblock.setFlammable(Blocks.OAK_PLANKS, 5, 20);
		fireblock.setFlammable(Blocks.SPRUCE_PLANKS, 5, 20);
		fireblock.setFlammable(Blocks.BIRCH_PLANKS, 5, 20);
		fireblock.setFlammable(Blocks.JUNGLE_PLANKS, 5, 20);
		fireblock.setFlammable(Blocks.ACACIA_PLANKS, 5, 20);
		fireblock.setFlammable(Blocks.DARK_OAK_PLANKS, 5, 20);
		fireblock.setFlammable(Blocks.OAK_SLAB, 5, 20);
		fireblock.setFlammable(Blocks.SPRUCE_SLAB, 5, 20);
		fireblock.setFlammable(Blocks.BIRCH_SLAB, 5, 20);
		fireblock.setFlammable(Blocks.JUNGLE_SLAB, 5, 20);
		fireblock.setFlammable(Blocks.ACACIA_SLAB, 5, 20);
		fireblock.setFlammable(Blocks.DARK_OAK_SLAB, 5, 20);
		fireblock.setFlammable(Blocks.OAK_FENCE_GATE, 5, 20);
		fireblock.setFlammable(Blocks.SPRUCE_FENCE_GATE, 5, 20);
		fireblock.setFlammable(Blocks.BIRCH_FENCE_GATE, 5, 20);
		fireblock.setFlammable(Blocks.JUNGLE_FENCE_GATE, 5, 20);
		fireblock.setFlammable(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
		fireblock.setFlammable(Blocks.ACACIA_FENCE_GATE, 5, 20);
		fireblock.setFlammable(Blocks.OAK_FENCE, 5, 20);
		fireblock.setFlammable(Blocks.SPRUCE_FENCE, 5, 20);
		fireblock.setFlammable(Blocks.BIRCH_FENCE, 5, 20);
		fireblock.setFlammable(Blocks.JUNGLE_FENCE, 5, 20);
		fireblock.setFlammable(Blocks.DARK_OAK_FENCE, 5, 20);
		fireblock.setFlammable(Blocks.ACACIA_FENCE, 5, 20);
		fireblock.setFlammable(Blocks.OAK_STAIRS, 5, 20);
		fireblock.setFlammable(Blocks.BIRCH_STAIRS, 5, 20);
		fireblock.setFlammable(Blocks.SPRUCE_STAIRS, 5, 20);
		fireblock.setFlammable(Blocks.JUNGLE_STAIRS, 5, 20);
		fireblock.setFlammable(Blocks.ACACIA_STAIRS, 5, 20);
		fireblock.setFlammable(Blocks.DARK_OAK_STAIRS, 5, 20);
		fireblock.setFlammable(Blocks.OAK_LOG, 5, 5);
		fireblock.setFlammable(Blocks.SPRUCE_LOG, 5, 5);
		fireblock.setFlammable(Blocks.BIRCH_LOG, 5, 5);
		fireblock.setFlammable(Blocks.JUNGLE_LOG, 5, 5);
		fireblock.setFlammable(Blocks.ACACIA_LOG, 5, 5);
		fireblock.setFlammable(Blocks.DARK_OAK_LOG, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_OAK_LOG, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_SPRUCE_LOG, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_BIRCH_LOG, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_JUNGLE_LOG, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_ACACIA_LOG, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_DARK_OAK_LOG, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_OAK_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_SPRUCE_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_BIRCH_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_JUNGLE_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_ACACIA_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.STRIPPED_DARK_OAK_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.OAK_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.SPRUCE_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.BIRCH_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.JUNGLE_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.ACACIA_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.DARK_OAK_WOOD, 5, 5);
		fireblock.setFlammable(Blocks.OAK_LEAVES, 30, 60);
		fireblock.setFlammable(Blocks.SPRUCE_LEAVES, 30, 60);
		fireblock.setFlammable(Blocks.BIRCH_LEAVES, 30, 60);
		fireblock.setFlammable(Blocks.JUNGLE_LEAVES, 30, 60);
		fireblock.setFlammable(Blocks.ACACIA_LEAVES, 30, 60);
		fireblock.setFlammable(Blocks.DARK_OAK_LEAVES, 30, 60);
		fireblock.setFlammable(Blocks.BOOKSHELF, 30, 20);
		fireblock.setFlammable(Blocks.TNT, 15, 100);
		fireblock.setFlammable(Blocks.GRASS, 60, 100);
		fireblock.setFlammable(Blocks.FERN, 60, 100);
		fireblock.setFlammable(Blocks.DEAD_BUSH, 60, 100);
		fireblock.setFlammable(Blocks.SUNFLOWER, 60, 100);
		fireblock.setFlammable(Blocks.LILAC, 60, 100);
		fireblock.setFlammable(Blocks.ROSE_BUSH, 60, 100);
		fireblock.setFlammable(Blocks.PEONY, 60, 100);
		fireblock.setFlammable(Blocks.TALL_GRASS, 60, 100);
		fireblock.setFlammable(Blocks.LARGE_FERN, 60, 100);
		fireblock.setFlammable(Blocks.DANDELION, 60, 100);
		fireblock.setFlammable(Blocks.POPPY, 60, 100);
		fireblock.setFlammable(Blocks.BLUE_ORCHID, 60, 100);
		fireblock.setFlammable(Blocks.ALLIUM, 60, 100);
		fireblock.setFlammable(Blocks.AZURE_BLUET, 60, 100);
		fireblock.setFlammable(Blocks.RED_TULIP, 60, 100);
		fireblock.setFlammable(Blocks.ORANGE_TULIP, 60, 100);
		fireblock.setFlammable(Blocks.WHITE_TULIP, 60, 100);
		fireblock.setFlammable(Blocks.PINK_TULIP, 60, 100);
		fireblock.setFlammable(Blocks.OXEYE_DAISY, 60, 100);
		fireblock.setFlammable(Blocks.CORNFLOWER, 60, 100);
		fireblock.setFlammable(Blocks.LILY_OF_THE_VALLEY, 60, 100);
		fireblock.setFlammable(Blocks.WITHER_ROSE, 60, 100);
		fireblock.setFlammable(Blocks.WHITE_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.ORANGE_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.MAGENTA_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.LIGHT_BLUE_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.YELLOW_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.LIME_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.PINK_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.GRAY_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.LIGHT_GRAY_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.CYAN_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.PURPLE_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.BLUE_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.BROWN_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.GREEN_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.RED_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.BLACK_WOOL, 30, 60);
		fireblock.setFlammable(Blocks.VINE, 15, 100);
		fireblock.setFlammable(Blocks.COAL_BLOCK, 5, 5);
		fireblock.setFlammable(Blocks.HAY_BLOCK, 60, 20);
		fireblock.setFlammable(Blocks.TARGET, 15, 20);
		fireblock.setFlammable(Blocks.WHITE_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.ORANGE_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.MAGENTA_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.LIGHT_BLUE_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.YELLOW_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.LIME_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.PINK_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.GRAY_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.LIGHT_GRAY_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.CYAN_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.PURPLE_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.BLUE_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.BROWN_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.GREEN_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.RED_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.BLACK_CARPET, 60, 20);
		fireblock.setFlammable(Blocks.DRIED_KELP_BLOCK, 30, 60);
		fireblock.setFlammable(Blocks.BAMBOO, 60, 60);
		fireblock.setFlammable(Blocks.SCAFFOLDING, 60, 60);
		fireblock.setFlammable(Blocks.LECTERN, 30, 20);
		fireblock.setFlammable(Blocks.COMPOSTER, 5, 20);
		fireblock.setFlammable(Blocks.SWEET_BERRY_BUSH, 60, 100);
		fireblock.setFlammable(Blocks.BEEHIVE, 5, 20);
		fireblock.setFlammable(Blocks.BEE_NEST, 30, 20);
	}

	public static boolean canSurviveOnBlock(BlockState pState) {
		return pState.is(BlockCTags.PYRANITE_FIRE_BASE_BLOCKS);
	}
}