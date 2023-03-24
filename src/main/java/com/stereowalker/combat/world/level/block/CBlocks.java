package com.stereowalker.combat.world.level.block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.core.particles.CParticleTypes;
import com.stereowalker.combat.world.item.CCreativeModeTab;
import com.stereowalker.combat.world.level.block.grower.AusldineTreeGrower;
import com.stereowalker.combat.world.level.block.grower.MonorisTreeGrower;
import com.stereowalker.combat.world.level.block.state.properties.CWoodType;
import com.stereowalker.combat.world.level.material.CFluids;
import com.stereowalker.combat.world.level.material.CMaterial;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.IForgeRegistry;

public class CBlocks {
	private static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final List<CreativeModeTab> CREATIVE_MODE_TABS = new ArrayList<CreativeModeTab>();
	public static final List<Block> BLOCKITEMS = new ArrayList<Block>();
	//-Fluids-\\
	public static final Block OIL = register("oil", new LiquidCBlock(() ->(FlowingFluid) CFluids.OIL, Block.Properties.of(CMaterial.OIL).noCollission().strength(100.0F).lootFrom(() -> Blocks.AIR)));
	public static final Block BIABLE = register("biable", new LiquidCBlock(() ->(FlowingFluid) CFluids.BIABLE, Block.Properties.of(CMaterial.BIABLE).noCollission().strength(100.0F).lootFrom(() -> Blocks.AIR)));
	//-Acrotlest Blocks-\\
	public static final Block MEZEPINE = register("mezepine", new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
	public static final Block SLYAPHY = register("slyaphy", new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
	public static final Block COBBLED_SLYAPHY = register("cobbled_slyaphy", new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
	//-Metals-\\
	public static final Block PASQUEM_ORE = register("pasquem_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
	public static final Block TRIDOX_ORE = register("tridox_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(5, 10)));
	public static final Block RUBY_ORE = register("ruby_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(3, 7)));
	public static final Block PELGAN_ORE = register("pelgan_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
	public static final Block SLYAPHY_PELGAN_ORE = register("slyaphy_pelgan_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
	public static final Block LOZYNE_ORE = register("lozyne_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
	public static final Block SLYAPHY_LOZYNE_ORE = register("slyaphy_lozyne_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
	public static final Block PYRANITE_ORE = register("pyranite_ore", new PyraniteOreBlock(Block.Properties.of(Material.STONE).randomTicks().lightLevel((p_235418_0_) -> {
		return 2;
	}).strength(3.0F, 3.0F)));
	public static final Block SERABLE_ORE = register("serable_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
	public static final Block CASSITERITE = register("cassiterite", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
	public static final Block BRONZE_BLOCK = register("bronze_block", new Block(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block PASQUEM_BLOCK = register("pasquem_block", new Block(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block TRIDOX_BLOCK = register("tridox_block", new Block(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block RUBY_BLOCK = register("ruby_block", new Block(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block PELGAN_BLOCK = register("pelgan_block", new Block(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block LOZYNE_BLOCK = register("lozyne_block", new Block(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block STEEL_BLOCK = register("steel_block", new Block(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block SERABLE_BLOCK = register("serable_block", new Block(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block ETHERION_BLOCK = register("etherion_block", new Block(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block COPPER_BARS = register("copper_bars", new IronBarsBlock(Block.Properties.of(Material.METAL, MaterialColor.NONE).strength(5.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));
	public static final Block COPPER_DOOR = register("copper_door", new DoorBlock(Block.Properties.of(Material.METAL, MaterialColor.METAL).strength(5.0F).sound(SoundType.METAL).noOcclusion()));
	public static final Block COPPER_TRAPDOOR = register("copper_trapdoor", new TrapDoorBlock(Block.Properties.of(Material.METAL).strength(5.0F).sound(SoundType.METAL).noOcclusion()));
	//-Wood-\\
	//Ausldine -> Acacia
	public static final Block AUSLDINE_BEAM = register("ausldine_beam", new BeamBlock(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block AUSLDINE_BUTTON = register("ausldine_button", new WoodButtonBlock(Block.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_DOOR = register("ausldine_door", CreativeModeTab.TAB_REDSTONE, new DoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_CYAN).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final Block AUSLDINE_FENCE_GATE = register("ausldine_fence_gate", CreativeModeTab.TAB_REDSTONE, new FenceGateBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_CYAN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_FENCE = register("ausldine_fence", CCreativeModeTab.COMBAT_TAB_MISC, new FenceBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_CYAN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_LEAVES = register("ausldine_leaves", CCreativeModeTab.COMBAT_TAB_MISC,  Blocks.leaves(SoundType.GRASS));
	public static final Block AUSLDINE_LOG = register("ausldine_log", CCreativeModeTab.BUILDING_BLOCKS, createLogBlock(MaterialColor.TERRACOTTA_CYAN, MaterialColor.TERRACOTTA_CYAN));
	public static final Block AUSLDINE_PLANKS = register("ausldine_planks", CCreativeModeTab.BUILDING_BLOCKS, new Block(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_PODIUM = register("ausldine_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_CYAN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final Block AUSLDINE_PRESSURE_PLATE = register("ausldine_pressure_plate", CreativeModeTab.TAB_REDSTONE, new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_CYAN).noCollission().strength(0.5F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_SAPLING = register("ausldine_sapling", CCreativeModeTab.COMBAT_TAB_MISC, new MagicSaplingBlock(new AusldineTreeGrower(), Block.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
	public static final Block AUSLDINE_SIGN = register("ausldine_sign", new CStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), CWoodType.AUSLDINE));
	public static final Block AUSLDINE_SLAB = register("ausldine_slab", CCreativeModeTab.BUILDING_BLOCKS, new SlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_CYAN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_STAIRS = register("ausldine_stairs", CCreativeModeTab.BUILDING_BLOCKS, new StairBlock(() -> CBlocks.AUSLDINE_PLANKS.defaultBlockState(), Block.Properties.copy(CBlocks.AUSLDINE_PLANKS)));
	public static final Block AUSLDINE_TRAPDOOR = register("ausldine_trapdoor", CCreativeModeTab.COMBAT_TAB_MISC, new TrapDoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_CYAN).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final Block AUSLDINE_WALL_SIGN = register("ausldine_wall_sign", new CWallSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(() -> AUSLDINE_SIGN), CWoodType.AUSLDINE));
	public static final Block AUSLDINE_WOOD = register("ausldine_wood", CCreativeModeTab.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_CYAN).strength(2.0F).sound(SoundType.WOOD)));
	public static final Block STRIPPED_AUSLDINE_LOG = register("stripped_ausldine_log", CCreativeModeTab.BUILDING_BLOCKS, createLogBlock(MaterialColor.TERRACOTTA_CYAN, MaterialColor.TERRACOTTA_CYAN));
	public static final Block STRIPPED_AUSLDINE_WOOD = register("stripped_ausldine_wood", CCreativeModeTab.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_CYAN).strength(2.0F).sound(SoundType.WOOD)));
	public static final Block POTTED_AUSLDINE_SAPLING = register("potted_ausldine_sapling", new FlowerPotBlock(Blocks.FLOWER_POT == null ? null : () -> (FlowerPotBlock) Blocks.FLOWER_POT.delegate.get(), () -> CBlocks.AUSLDINE_SAPLING.delegate.get(), Block.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
	//Dead Oak -> Oak
	public static final Block DEAD_OAK_BEAM = register("dead_oak_beam", new BeamBlock(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block DEAD_OAK_BUTTON = register("dead_oak_button", CreativeModeTab.TAB_REDSTONE, new WoodButtonBlock(Block.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_DOOR = register("dead_oak_door", CreativeModeTab.TAB_REDSTONE, new DoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final Block DEAD_OAK_FENCE_GATE = register("dead_oak_fence_gate", CreativeModeTab.TAB_REDSTONE, new FenceGateBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_FENCE = register("dead_oak_fence", CCreativeModeTab.COMBAT_TAB_MISC, new FenceBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_LOG = register("dead_oak_log", CCreativeModeTab.BUILDING_BLOCKS, createLogBlock(MaterialColor.COLOR_BLACK, MaterialColor.COLOR_BLACK));
	public static final Block DEAD_OAK_PLANKS = register("dead_oak_planks", CCreativeModeTab.BUILDING_BLOCKS, new Block(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_PODIUM = register("dead_oak_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final Block DEAD_OAK_PRESSURE_PLATE = register("dead_oak_pressure_plate", CreativeModeTab.TAB_REDSTONE, new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).noCollission().strength(0.5F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_SIGN = register("dead_oak_sign", new CStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), CWoodType.DEAD_OAK));
	public static final Block DEAD_OAK_SLAB = register("dead_oak_slab", CCreativeModeTab.BUILDING_BLOCKS, new SlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_CYAN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_STAIRS = register("dead_oak_stairs", CCreativeModeTab.BUILDING_BLOCKS, new StairBlock(() -> CBlocks.DEAD_OAK_PLANKS.defaultBlockState(), Block.Properties.copy(CBlocks.DEAD_OAK_PLANKS)));
	public static final Block DEAD_OAK_TRAPDOOR = register("dead_oak_trapdoor", CCreativeModeTab.COMBAT_TAB_MISC, new TrapDoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final Block DEAD_OAK_WALL_SIGN = register("dead_oak_wall_sign", new CWallSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(() -> DEAD_OAK_SIGN), CWoodType.DEAD_OAK));
	public static final Block DEAD_OAK_WOOD = register("dead_oak_wood", CCreativeModeTab.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F).sound(SoundType.WOOD)));
	public static final Block STRIPPED_DEAD_OAK_LOG = register("stripped_dead_oak_log", CCreativeModeTab.BUILDING_BLOCKS, createLogBlock(MaterialColor.COLOR_BLACK, MaterialColor.COLOR_BLACK));
	public static final Block STRIPPED_DEAD_OAK_WOOD = register("stripped_dead_oak_wood", CCreativeModeTab.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F).sound(SoundType.WOOD)));
	//Monoris -> Oak
	public static final Block MONORIS_BEAM = register("monoris_beam", new BeamBlock(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block MONORIS_BUTTON = register("monoris_button", CreativeModeTab.TAB_REDSTONE, new WoodButtonBlock(Block.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)));
	public static final Block MONORIS_DOOR = register("monoris_door", CreativeModeTab.TAB_REDSTONE, new DoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.SNOW).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final Block MONORIS_FENCE_GATE = register("monoris_fence_gate", CreativeModeTab.TAB_REDSTONE, new FenceGateBlock(Block.Properties.of(Material.WOOD, MaterialColor.SNOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block MONORIS_FENCE = register("monoris_fence", CCreativeModeTab.COMBAT_TAB_MISC, new FenceBlock(Block.Properties.of(Material.WOOD, MaterialColor.SNOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block MONORIS_LEAVES = register("monoris_leaves", CCreativeModeTab.COMBAT_TAB_MISC, Blocks.leaves(SoundType.GRASS));
	public static final Block MONORIS_LOG = register("monoris_log", CCreativeModeTab.BUILDING_BLOCKS, createLogBlock(MaterialColor.SNOW, MaterialColor.SNOW));
	public static final Block MONORIS_PLANKS = register("monoris_planks", CCreativeModeTab.BUILDING_BLOCKS, new Block(Block.Properties.of(Material.WOOD, MaterialColor.SNOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block MONORIS_PODIUM = register("monoris_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.SNOW).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final Block MONORIS_SAPLING = register("monoris_sapling", CCreativeModeTab.COMBAT_TAB_MISC, new AcrotlestSaplingBlock(new MonorisTreeGrower(), Block.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
	public static final Block MONORIS_SLAB = register("monoris_slab", CCreativeModeTab.BUILDING_BLOCKS, new SlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.SNOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block MONORIS_STAIRS = register("monoris_stairs", CCreativeModeTab.BUILDING_BLOCKS, new StairBlock(() -> CBlocks.MONORIS_PLANKS.defaultBlockState(), Block.Properties.copy(CBlocks.MONORIS_PLANKS)));
	public static final Block MONORIS_WOOD = register("monoris_wood", CCreativeModeTab.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.of(Material.WOOD, MaterialColor.SNOW).strength(2.0F).sound(SoundType.WOOD)));
	public static final Block STRIPPED_MONORIS_LOG = register("stripped_monoris_log", CCreativeModeTab.BUILDING_BLOCKS, createLogBlock(MaterialColor.SNOW, MaterialColor.SNOW));
	public static final Block STRIPPED_MONORIS_WOOD = register("stripped_monoris_wood", CCreativeModeTab.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.of(Material.WOOD, MaterialColor.SNOW).strength(2.0F).sound(SoundType.WOOD)));
	public static final Block POTTED_MONORIS_SAPLING = register("potted_monoris_sapling", new FlowerPotBlock(Blocks.FLOWER_POT == null ? null : () -> (FlowerPotBlock) Blocks.FLOWER_POT.delegate.get(), () -> CBlocks.MONORIS_SAPLING.delegate.get(), Block.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
	//Rezal -> Jungle
	public static final Block REZAL_LEAVES = register("rezal_leaves", CCreativeModeTab.COMBAT_TAB_MISC, Blocks.leaves(SoundType.GRASS));
	public static final Block REZAL_LOG = register("rezal_log", CCreativeModeTab.BUILDING_BLOCKS, createLogBlock(MaterialColor.COLOR_RED, MaterialColor.COLOR_GREEN));
	public static final Block REZAL_PLANKS = register("rezal_planks", CCreativeModeTab.BUILDING_BLOCKS, new Block(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block REZAL_WOOD = register("rezal_wood", CCreativeModeTab.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_GREEN).strength(2.0F).sound(SoundType.WOOD)));
	public static final Block REZAL_SAPLING = register("rezal_sapling", CCreativeModeTab.COMBAT_TAB_MISC, new MagicSaplingBlock(new MonorisTreeGrower(), Block.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
	
	//Minecraft Wood
	//Oak
	public static final Block OAK_BEAM = register("oak_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block OAK_PODIUM = register("oak_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Spruce
	public static final Block SPRUCE_BEAM = register("spruce_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.PODZOL).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block SPRUCE_PODIUM = register("spruce_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Birch
	public static final Block BIRCH_BEAM = register("birch_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.SAND).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block BIRCH_PODIUM = register("birch_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Jungle
	public static final Block JUNGLE_BEAM = register("jungle_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.DIRT).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block JUNGLE_PODIUM = register("jungle_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Acacia
	public static final Block ACACIA_BEAM = register("acacia_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block ACACIA_PODIUM = register("acacia_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Dark Oak
	public static final Block DARK_OAK_BEAM = register("dark_oak_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block DARK_OAK_PODIUM = register("dark_oak_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Crimson
	public static final Block CRIMSON_BEAM = register("crimson_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block CRIMSON_PODIUM = register("crimson_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Warped
	public static final Block WARPED_BEAM = register("warped_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block WARPED_PODIUM = register("warped_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//BOP Wood
	//Fir
	public static final Block FIR_BEAM = registerModded(Combat.isBOPLoaded(true), "fir_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block FIR_PODIUM = registerModded(Combat.isBOPLoaded(true), "fir_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Redwood
	public static final Block REDWOOD_BEAM = registerModded(Combat.isBOPLoaded(true), "redwood_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block REDWOOD_PODIUM = registerModded(Combat.isBOPLoaded(true), "redwood_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Cherry
	public static final Block CHERRY_BEAM = registerModded(Combat.isBOPLoaded(true), "cherry_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block CHERRY_PODIUM = registerModded(Combat.isBOPLoaded(true), "cherry_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Mahogany
	public static final Block MAHOGANY_BEAM = registerModded(Combat.isBOPLoaded(true), "mahogany_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block MAHOGANY_PODIUM = registerModded(Combat.isBOPLoaded(true), "mahogany_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Jacaranda
	public static final Block JACARANDA_BEAM = registerModded(Combat.isBOPLoaded(true), "jacaranda_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block JACARANDA_PODIUM = registerModded(Combat.isBOPLoaded(true), "jacaranda_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Palm
	public static final Block PALM_BEAM = registerModded(Combat.isBOPLoaded(true), "palm_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block PALM_PODIUM = registerModded(Combat.isBOPLoaded(true), "palm_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Willow
	public static final Block WILLOW_BEAM = registerModded(Combat.isBOPLoaded(true), "willow_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block WILLOW_PODIUM = registerModded(Combat.isBOPLoaded(true), "willow_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Dead
	public static final Block DEAD_BEAM = registerModded(Combat.isBOPLoaded(true), "dead_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block DEAD_PODIUM = registerModded(Combat.isBOPLoaded(true), "dead_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Magic
	public static final Block MAGIC_BEAM = registerModded(Combat.isBOPLoaded(true), "magic_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block MAGIC_PODIUM = registerModded(Combat.isBOPLoaded(true), "magic_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Umbran
	public static final Block UMBRAN_BEAM = registerModded(Combat.isBOPLoaded(true), "umbran_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block UMBRAN_PODIUM = registerModded(Combat.isBOPLoaded(true), "umbran_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
	//Hellbark
	public static final Block HELLBARK_BEAM = registerModded(Combat.isBOPLoaded(true), "hellbark_beam", new BeamBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD).strength(1.0F, 2.0F)));
	public static final Block HELLBARK_PODIUM = registerModded(Combat.isBOPLoaded(true), "hellbark_podium", new PodiumBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));

	//-Crops-\\
	public static final Block CORN = register("corn", new CornBlock(Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0, 0).sound(SoundType.GRASS)));

	//-Redstone-\\
	public static final Block TORCH_LEVER = register("torch_lever", new TorchLeverBlock(Block.Properties.of(Material.DECORATION).noCollission().strength(0, 0).lightLevel((p_235418_0_) -> {
		return 14;
	}).sound(SoundType.WOOD), ParticleTypes.FLAME));
	public static final Block WALL_TORCH_LEVER = register("wall_torch_lever", new WallTorchLeverBlock(Block.Properties.of(Material.DECORATION).noCollission().strength(0, 0).lightLevel((p_235418_0_) -> {
		return 14;
	}).sound(SoundType.WOOD), ParticleTypes.FLAME));

	//-Miscellaneous-\\
	public static final Block WHITE_TSUNE = register("white_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.WHITE, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block ORANGE_TSUNE = register("orange_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.ORANGE, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block MAGENTA_TSUNE = register("magenta_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.MAGENTA, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block LIGHT_BLUE_TSUNE = register("light_blue_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.LIGHT_BLUE, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block YELLOW_TSUNE = register("yellow_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.YELLOW, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block LIME_TSUNE = register("lime_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.LIME, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block PINK_TSUNE = register("pink_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.PINK, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block GRAY_TSUNE = register("gray_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.GRAY, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block LIGHT_GRAY_TSUNE = register("light_gray_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.LIGHT_GRAY, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block CYAN_TSUNE = register("cyan_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.CYAN, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block PURPLE_TSUNE = register("purple_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.PURPLE, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block BLUE_TSUNE = register("blue_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.BLUE, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	//Hisov Sands
	public static final Block BROWN_TSUNE = register("brown_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.BROWN, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	//Aquifers
	public static final Block GREEN_TSUNE = register("green_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.GREEN, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block RED_TSUNE = register("red_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.RED, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block BLACK_TSUNE = register("black_tsune", CCreativeModeTab.BUILDING_BLOCKS, new TsuneBlock(DyeColor.BLACK, Block.Properties.of(Material.ICE_SOLID).strength(3.0F).friction(1.112625F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block ROBIN_SUMMONER = register("robin_summoner", CCreativeModeTab.COMBAT_TAB_MISC, new RobinSummonerBlock(Block.Properties.of(Material.METAL)));
	public static final Block EMPTY_ROBIN_SUMMONER = register("empty_robin_summoner", CCreativeModeTab.COMBAT_TAB_MISC, new EmptySummonerBlock(Block.Properties.of(Material.GLASS).noOcclusion()));
	public static final Block CARDBOARD_BOX = register("cardboard_box", new CardboardBoxBlock(Block.Properties.of(Material.DECORATION).strength(1.0F, 1.0F).sound(SoundType.WOOL)));
	public static final Block ACROTLEST_RUINED_PORTAL = register("acrotlest_ruined_portal", new AcrotlestRuinedPortalBlock(Block.Properties.of(Material.PORTAL).noCollission().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel((p_235418_0_) -> {
		return 11;
	})));
	public static final Block ACROTLEST_PORTAL = register("acrotlest_portal", new AcrotlestPortalBlock(Block.Properties.of(Material.PORTAL).noCollission().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel((p_235418_0_) -> {
		return 11;
	})));
	public static final Block ALLOY_FURNACE = register("alloy_furnace", new AlloyFurnaceBlock(Block.Properties.of(Material.DECORATION).requiresCorrectToolForDrops().strength(2.0f, 3.0f).lightLevel(getLightValueLit(13))));
	public static final Block LIMESTONE = register("limestone", CCreativeModeTab.BUILDING_BLOCKS, new Block(Block.Properties.of(Material.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops()));
	public static final Block MEZEPINE_BRICKS = register("mezepine_bricks", new Block(Block.Properties.of(Material.STONE).strength(0.75F, 3.0F)));
	public static final Block MEZEPINE_FURNACE = register("mezepine_furnace", CCreativeModeTab.COMBAT_TAB_MISC, new MezepineFurnaceBlock(Block.Properties.of(Material.STONE).strength(1.75F).lightLevel((p_235418_0_) -> {
		return 13;
	})));
	public static final Block MEZEPINE_SLAB = register("mezepine_slab", new SlabBlock(Block.Properties.of(Material.STONE, MaterialColor.STONE).strength(1.0F, 3.0F)));
	public static final Block MEZEPINE_BRICK_SLAB = register("mezepine_brick_slab", new SlabBlock(Block.Properties.of(Material.STONE, MaterialColor.STONE).strength(1.0F, 3.0F)));
	public static final Block MEZEPINE_BRICK_STAIRS = register("mezepine_brick_stairs", new StairBlock(() -> CBlocks.MEZEPINE_BRICKS.defaultBlockState(), Block.Properties.copy(CBlocks.MEZEPINE_BRICKS)));
	public static final Block PURIFIED_DIRT = register("purified_dirt", CCreativeModeTab.BUILDING_BLOCKS, new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.4F).sound(SoundType.GRAVEL)));
	public static final Block PURIFIED_GRASS_BLOCK = register("purified_grass_block", CCreativeModeTab.BUILDING_BLOCKS, new GrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.5F).sound(SoundType.GRASS)));
	public static final Block ELYCEN_BLOCK = register("elycen_block", CCreativeModeTab.BUILDING_BLOCKS, new GrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.5F).sound(SoundType.GRASS)));
	public static final Block CALTAS = register("caltas", new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
	public static final Block WOODCUTTER = register("woodcutter", new WoodcutterBlock(Block.Properties.of(Material.STONE).strength(3.5F)));
	public static final Block PYRANITE_FIRE = register("pyranite_fire", new PyraniteFireBlock(Block.Properties.of(Material.FIRE, MaterialColor.FIRE).noCollission().randomTicks().instabreak().lightLevel((p_235418_0_) -> {
		return 13;
	}).sound(SoundType.WOOL).noDrops()));
	public static final Block ARCANE_WORKBENCH = register("arcane_workbench", new ArcaneWorkbenchBlock(Block.Properties.of(Material.STONE, MaterialColor.GOLD).strength(5.0F, 1200.0F).lightLevel((p_235418_0_) -> {
		return 2;
	})));
	public static final Block MANA_GENERATOR = register("mana_generator", new ManaGeneratorBlock(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).lightLevel((p_235418_0_) -> {
		return 3;
	}).sound(SoundType.METAL)));
	public static final Block ELECTRIC_FURNACE = register("electric_furnace", new ElectricFurnaceBlock(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).lightLevel(getLightValueLit(3)).sound(SoundType.METAL)));
	public static final Block CONNECTOR = register("connector", new ConnectorBlock(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).lightLevel((p_235418_0_) -> {
		return 3;
	}).sound(SoundType.METAL)));
	public static final Block MYTHRIL_CHARGER = register("mythril_charger", new MythrilChargerBlock(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block ACROTLEST_FARMLAND = register("acrotlest_farmland", new FarmBlock(Block.Properties.of(Material.DIRT).randomTicks().strength(0.6F).sound(SoundType.GRAVEL)));
	public static final Block GROPAPY = register("gropapy", new GropapyBlock(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_ORANGE).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion()));
	public static final Block PYRANITE_TORCH = register("pyranite_torch", new TorchBlock(Block.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((p_235418_0_) -> {
		return 13;
	}).sound(SoundType.WOOD), CParticleTypes.PYRANITE_FLAME));
	public static final Block PYRANITE_LANTERN = register("pyranite_lantern", new LanternBlock(Block.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.LANTERN).lightLevel((p_235418_0_) -> {
		return 13;
	}).noOcclusion()));
	public static final Block PYRANITE_WALL_TORCH = register("pyranite_wall_torch", new WallTorchBlock(Block.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((p_235418_0_) -> {
		return 13;
	}).sound(SoundType.WOOD).lootFrom(() -> PYRANITE_TORCH), CParticleTypes.PYRANITE_FLAME));
	public static final Block MAGIC_STONE = register("magic_stone", new OreBlock(Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).strength(6.0F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.STONE), UniformInt.of(11, 23)));
	public static final Block YELLOW_MAGIC_CLUSTER = register("yellow_magic_cluster", new OreBlock(Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_YELLOW).strength(6.0F).requiresCorrectToolForDrops().lightLevel((p_235418_0_) -> {
		return 7;
	}).sound(SoundType.STONE), UniformInt.of(5, 9)));
	public static final Block DISENCHANTING_TABLE = register("disenchanting_table", new DisenchantmentTableBlock(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLUE).strength(5.0F, 1200.0F)));
	public static final Block HOMSE = register("homse", new SandBlock(0Xfff98f, Block.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.7F).sound(SoundType.GRAVEL)));
//	public static final Block BATTERY_CHARGER = register("battery_charger", new MythrilChargerBlock(Block.Properties.of(Material.METAL)));

	private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
		return (p_235421_1_) -> {
			return p_235421_1_.getValue(BlockStateProperties.LIT) ? lightValue : 0;
		};
	}

	private static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
		return new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (p_235431_2_) -> {
			return p_235431_2_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor;
		}).strength(2.0F).sound(SoundType.WOOD));
	}

	public static Block register(String name, Block block) {
		block.setRegistryName(Combat.getInstance().location(name));
		CBlocks.BLOCKS.add(block);
		return block;
	}

	public static Block registerModded(boolean modIsLoaded, String name, Block block) {
		return modIsLoaded ? register(name, block) : block;
	}

	public static Block register(String name, CreativeModeTab group, Block block) {
		CBlocks.BLOCKITEMS.add(block);
		CBlocks.CREATIVE_MODE_TABS.add(group);
		return register(name, block);
	}

	public static void registerAll(IForgeRegistry<Block> registry) {
		for(Block block: BLOCKS) {
			registry.register(block);
			Combat.debug("Block: \""+block.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Blocks Registered");
	}

}
