package com.stereowalker.combat.block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.material.CMaterial;
import com.stereowalker.combat.block.trees.AusldineTree;
import com.stereowalker.combat.block.trees.MonorisTree;
import com.stereowalker.combat.fluid.CFluids;
import com.stereowalker.combat.item.CItemGroup;
import com.stereowalker.combat.particles.CParticleTypes;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SandBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.IForgeRegistry;

public class CBlocks {
	private static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final List<ItemGroup> ITEMGROUPS = new ArrayList<ItemGroup>();
	public static final List<Block> BLOCKITEMS = new ArrayList<Block>();
	//-Fluids-\\
	public static final Block OIL = register("oil", new FlowingFluidCBlock((FlowingFluid) CFluids.OIL, Block.Properties.create(CMaterial.OIL).doesNotBlockMovement().hardnessAndResistance(100.0F).lootFrom(() -> Blocks.AIR)));
	public static final Block BIABLE = register("biable", new FlowingFluidCBlock((FlowingFluid) CFluids.BIABLE, Block.Properties.create(CMaterial.BIABLE).doesNotBlockMovement().hardnessAndResistance(100.0F).lootFrom(() -> Blocks.AIR)));
	//-Metals-\\
	public static final Block COPPER_ORE = register("copper_ore", new OreCBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(1).hardnessAndResistance(3.0F, 3.0F)));
	public static final Block PASQUEM_ORE = register("pasquem_ore", new OreCBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(3).hardnessAndResistance(3.0F, 3.0F)));
	public static final Block TRIDOX_ORE = register("tridox_ore", new OreCBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)));
	public static final Block RUBY_ORE = register("ruby_ore", new OreCBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)));
	public static final Block PELGAN_ORE = register("pelgan_ore", new OreCBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(1).hardnessAndResistance(3.0F, 3.0F)));
	public static final Block LOZYNE_ORE = register("lozyne_ore", new OreCBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)));
	public static final Block PYRANITE_ORE = register("pyranite_ore", new PyraniteOreBlock(Block.Properties.create(Material.ROCK).tickRandomly().setLightLevel((p_235418_0_) -> {
		return 2;
	}).hardnessAndResistance(3.0F, 3.0F)));
	public static final Block SERABLE_ORE = register("serable_ore", new OreCBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)));
	public static final Block CASSITERITE = register("cassiterite", new OreCBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(1).hardnessAndResistance(3.0F, 3.0F)));
	public static final Block COPPER_BLOCK = register("copper_block", new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(1).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block BRONZE_BLOCK = register("bronze_block", new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block PASQUEM_BLOCK = register("pasquem_block", new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(3).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block TRIDOX_BLOCK = register("tridox_block", new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block RUBY_BLOCK = register("ruby_block", new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block PELGAN_BLOCK = register("pelgan_block", new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(1).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block LOZYNE_BLOCK = register("lozyne_block", new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block STEEL_BLOCK = register("steel_block", new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block SERABLE_BLOCK = register("serable_block", new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block ETHERION_BLOCK = register("etherion_block", new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(3).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)));
	public static final Block COPPER_BARS = register("copper_bars", new PaneBlock(Block.Properties.create(Material.IRON, MaterialColor.AIR).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).notSolid()));
	public static final Block COPPER_DOOR = register("copper_door", new DoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()));
	public static final Block COPPER_TRAPDOOR = register("copper_trapdoor", new TrapDoorBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()));
	//-Wood-\\
	//Ausldine -> Acacia
	public static final Block AUSLDINE_BEAM = register("ausldine_beam", new BeamBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block AUSLDINE_BUTTON = register("ausldine_button", new WoodButtonCBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_DOOR = register("ausldine_door", ItemGroup.REDSTONE, new DoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()));
	public static final Block AUSLDINE_FENCE_GATE = register("ausldine_fence_gate", ItemGroup.REDSTONE, new FenceGateBlock(Block.Properties.create(Material.WOOD, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_FENCE = register("ausldine_fence", CItemGroup.MISC, new FenceBlock(Block.Properties.create(Material.WOOD, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_LEAVES = register("ausldine_leaves", CItemGroup.MISC, new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()));
	public static final Block AUSLDINE_LOG = register("ausldine_log", CItemGroup.BUILDING_BLOCKS, createLogBlock(MaterialColor.CYAN_TERRACOTTA, MaterialColor.CYAN_TERRACOTTA));
	public static final Block AUSLDINE_PLANKS = register("ausldine_planks", CItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_PODIUM = register("ausldine_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	public static final Block AUSLDINE_PRESSURE_PLATE = register("ausldine_pressure_plate", ItemGroup.REDSTONE, new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.CYAN_TERRACOTTA).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_SAPLING = register("ausldine_sapling", CItemGroup.MISC, new MagicSaplingBlock(new AusldineTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
	public static final Block AUSLDINE_SIGN = register("ausldine_sign", new CStandingSignBlock(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), CWoodType.AUSLDINE));
	public static final Block AUSLDINE_SLAB = register("ausldine_slab", CItemGroup.BUILDING_BLOCKS, new SlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_STAIRS = register("ausldine_stairs", CItemGroup.BUILDING_BLOCKS, new StairsBlock(() -> CBlocks.AUSLDINE_PLANKS.getDefaultState(), Block.Properties.from(CBlocks.AUSLDINE_PLANKS)));
	public static final Block AUSLDINE_TRAPDOOR = register("ausldine_trapdoor", CItemGroup.MISC, new TrapDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()));
	public static final Block AUSLDINE_VERTICAL_SLAB = register("ausldine_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block AUSLDINE_WALL_SIGN = register("ausldine_wall_sign", new CWallSignBlock(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD).lootFrom(() -> AUSLDINE_SIGN), CWoodType.AUSLDINE));
	public static final Block AUSLDINE_WOOD = register("ausldine_wood", CItemGroup.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
	public static final Block STRIPPED_AUSLDINE_LOG = register("stripped_ausldine_log", CItemGroup.BUILDING_BLOCKS, createLogBlock(MaterialColor.CYAN_TERRACOTTA, MaterialColor.CYAN_TERRACOTTA));
	public static final Block STRIPPED_AUSLDINE_WOOD = register("stripped_ausldine_wood", CItemGroup.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
	public static final Block POTTED_AUSLDINE_SAPLING = register("potted_ausldine_sapling", new FlowerPotBlock(Blocks.FLOWER_POT == null ? null : () -> (FlowerPotBlock) Blocks.FLOWER_POT.delegate.get(), () -> CBlocks.AUSLDINE_SAPLING.delegate.get(), Block.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	//Dead Oak -> Oak
	public static final Block DEAD_OAK_BEAM = register("dead_oak_beam", new BeamBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block DEAD_OAK_BUTTON = register("dead_oak_button", ItemGroup.REDSTONE, new WoodButtonCBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_DOOR = register("dead_oak_door", ItemGroup.REDSTONE, new DoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.BLACK).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()));
	public static final Block DEAD_OAK_FENCE_GATE = register("dead_oak_fence_gate", ItemGroup.REDSTONE, new FenceGateBlock(Block.Properties.create(Material.WOOD, MaterialColor.BLACK).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_FENCE = register("dead_oak_fence", CItemGroup.MISC, new FenceBlock(Block.Properties.create(Material.WOOD, MaterialColor.BLACK).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_LOG = register("dead_oak_log", CItemGroup.BUILDING_BLOCKS, createLogBlock(MaterialColor.BLACK, MaterialColor.BLACK));
	public static final Block DEAD_OAK_PLANKS = register("dead_oak_planks", CItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_PODIUM = register("dead_oak_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BLACK).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	public static final Block DEAD_OAK_PRESSURE_PLATE = register("dead_oak_pressure_plate", ItemGroup.REDSTONE, new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.BLACK).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_SIGN = register("dead_oak_sign", new CStandingSignBlock(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), CWoodType.DEAD_OAK));
	public static final Block DEAD_OAK_SLAB = register("dead_oak_slab", CItemGroup.BUILDING_BLOCKS, new SlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_STAIRS = register("dead_oak_stairs", CItemGroup.BUILDING_BLOCKS, new StairsBlock(() -> CBlocks.DEAD_OAK_PLANKS.getDefaultState(), Block.Properties.from(CBlocks.DEAD_OAK_PLANKS)));
	public static final Block DEAD_OAK_TRAPDOOR = register("dead_oak_trapdoor", CItemGroup.MISC, new TrapDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.BLACK).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()));
	public static final Block DEAD_OAK_VERTICAL_SLAB = register("dead_oak_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block DEAD_OAK_WALL_SIGN = register("dead_oak_wall_sign", new CWallSignBlock(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD).lootFrom(() -> DEAD_OAK_SIGN), CWoodType.DEAD_OAK));
	public static final Block DEAD_OAK_WOOD = register("dead_oak_wood", CItemGroup.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.BLACK).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
	public static final Block STRIPPED_DEAD_OAK_LOG = register("stripped_dead_oak_log", CItemGroup.BUILDING_BLOCKS, createLogBlock(MaterialColor.BLACK, MaterialColor.BLACK));
	public static final Block STRIPPED_DEAD_OAK_WOOD = register("stripped_dead_oak_wood", CItemGroup.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.BLACK).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
	//Monoris -> Oak
	public static final Block MONORIS_BEAM = register("monoris_beam", new BeamBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block MONORIS_BUTTON = register("monoris_button", ItemGroup.REDSTONE, new WoodButtonCBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
	public static final Block MONORIS_DOOR = register("monoris_door", ItemGroup.REDSTONE, new DoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.SNOW).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()));
	public static final Block MONORIS_FENCE_GATE = register("monoris_fence_gate", ItemGroup.REDSTONE, new FenceGateBlock(Block.Properties.create(Material.WOOD, MaterialColor.SNOW).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block MONORIS_FENCE = register("monoris_fence", CItemGroup.MISC, new FenceBlock(Block.Properties.create(Material.WOOD, MaterialColor.SNOW).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block MONORIS_LEAVES = register("monoris_leaves", CItemGroup.MISC, new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()));
	public static final Block MONORIS_LOG = register("monoris_log", CItemGroup.BUILDING_BLOCKS, createLogBlock(MaterialColor.SNOW, MaterialColor.SNOW));
	public static final Block MONORIS_PLANKS = register("monoris_planks", CItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.WOOD, MaterialColor.SNOW).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block MONORIS_PODIUM = register("monoris_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.SNOW).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	public static final Block MONORIS_SAPLING = register("monoris_sapling", CItemGroup.MISC, new AcrotlestSaplingBlock(new MonorisTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
	public static final Block MONORIS_SLAB = register("monoris_slab", CItemGroup.BUILDING_BLOCKS, new SlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.SNOW).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block MONORIS_STAIRS = register("monoris_stairs", CItemGroup.BUILDING_BLOCKS, new StairsBlock(() -> CBlocks.MONORIS_PLANKS.getDefaultState(), Block.Properties.from(CBlocks.MONORIS_PLANKS)));
	public static final Block MONORIS_VERTICAL_SLAB = register("monoris_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block MONORIS_WOOD = register("monoris_wood", CItemGroup.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.SNOW).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
	public static final Block STRIPPED_MONORIS_LOG = register("stripped_monoris_log", CItemGroup.BUILDING_BLOCKS, createLogBlock(MaterialColor.SNOW, MaterialColor.SNOW));
	public static final Block STRIPPED_MONORIS_WOOD = register("stripped_monoris_wood", CItemGroup.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.SNOW).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
	public static final Block POTTED_MONORIS_SAPLING = register("potted_monoris_sapling", new FlowerPotBlock(Blocks.FLOWER_POT == null ? null : () -> (FlowerPotBlock) Blocks.FLOWER_POT.delegate.get(), () -> CBlocks.MONORIS_SAPLING.delegate.get(), Block.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	//Rezal -> Jungle
	public static final Block REZAL_LEAVES = register("rezal_leaves", CItemGroup.MISC, new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()));
	public static final Block REZAL_LOG = register("rezal_log", CItemGroup.BUILDING_BLOCKS, createLogBlock(MaterialColor.RED, MaterialColor.GREEN));
	public static final Block REZAL_PLANKS = register("rezal_planks", CItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final Block REZAL_WOOD = register("rezal_wood", CItemGroup.BUILDING_BLOCKS, new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.GREEN).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));

	//Minecraft Wood
	//Oak
	public static final Block OAK_BEAM = register("oak_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block OAK_VERTICAL_SLAB = register("oak_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block OAK_PODIUM = register("oak_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Spruce
	public static final Block SPRUCE_BEAM = register("spruce_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block SPRUCE_VERTICAL_SLAB = register("spruce_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block SPRUCE_PODIUM = register("spruce_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Birch
	public static final Block BIRCH_BEAM = register("birch_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.SAND).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block BIRCH_VERTICAL_SLAB = register("birch_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block BIRCH_PODIUM = register("birch_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Jungle
	public static final Block JUNGLE_BEAM = register("jungle_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.DIRT).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block JUNGLE_VERTICAL_SLAB = register("jungle_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block JUNGLE_PODIUM = register("jungle_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Acacia
	public static final Block ACACIA_BEAM = register("acacia_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block ACACIA_VERTICAL_SLAB = register("acacia_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block ACACIA_PODIUM = register("acacia_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Dark Oak
	public static final Block DARK_OAK_BEAM = register("dark_oak_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block DARK_OAK_VERTICAL_SLAB = register("dark_oak_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block DARK_OAK_PODIUM = register("dark_oak_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Crimson
	public static final Block CRIMSON_BEAM = register("crimson_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block CRIMSON_VERTICAL_SLAB = register("crimson_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block CRIMSON_PODIUM = register("crimson_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Warped
	public static final Block WARPED_BEAM = register("warped_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block WARPED_VERTICAL_SLAB = register("warped_vertical_slab", CItemGroup.BUILDING_BLOCKS, new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block WARPED_PODIUM = register("warped_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//BOP Wood
	//Fir
	public static final Block FIR_BEAM = registerModded(Combat.isBOPLoaded(true), "fir_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block FIR_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "fir_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block FIR_PODIUM = registerModded(Combat.isBOPLoaded(true), "fir_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Redwood
	public static final Block REDWOOD_BEAM = registerModded(Combat.isBOPLoaded(true), "redwood_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block REDWOOD_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "redwood_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block REDWOOD_PODIUM = registerModded(Combat.isBOPLoaded(true), "redwood_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Cherry
	public static final Block CHERRY_BEAM = registerModded(Combat.isBOPLoaded(true), "cherry_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block CHERRY_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "cherry_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block CHERRY_PODIUM = registerModded(Combat.isBOPLoaded(true), "cherry_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Mahogany
	public static final Block MAHOGANY_BEAM = registerModded(Combat.isBOPLoaded(true), "mahogany_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block MAHOGANY_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "mahogany_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block MAHOGANY_PODIUM = registerModded(Combat.isBOPLoaded(true), "mahogany_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Jacaranda
	public static final Block JACARANDA_BEAM = registerModded(Combat.isBOPLoaded(true), "jacaranda_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block JACARANDA_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "jacaranda_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block JACARANDA_PODIUM = registerModded(Combat.isBOPLoaded(true), "jacaranda_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Palm
	public static final Block PALM_BEAM = registerModded(Combat.isBOPLoaded(true), "palm_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block PALM_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "palm_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block PALM_PODIUM = registerModded(Combat.isBOPLoaded(true), "palm_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Willow
	public static final Block WILLOW_BEAM = registerModded(Combat.isBOPLoaded(true), "willow_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block WILLOW_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "willow_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block WILLOW_PODIUM = registerModded(Combat.isBOPLoaded(true), "willow_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Dead
	public static final Block DEAD_BEAM = registerModded(Combat.isBOPLoaded(true), "dead_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block DEAD_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "dead_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block DEAD_PODIUM = registerModded(Combat.isBOPLoaded(true), "dead_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Magic
	public static final Block MAGIC_BEAM = registerModded(Combat.isBOPLoaded(true), "magic_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block MAGIC_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "magic_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block MAGIC_PODIUM = registerModded(Combat.isBOPLoaded(true), "magic_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Umbran
	public static final Block UMBRAN_BEAM = registerModded(Combat.isBOPLoaded(true), "umbran_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block UMBRAN_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "umbran_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block UMBRAN_PODIUM = registerModded(Combat.isBOPLoaded(true), "umbran_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
	//Hellbark
	public static final Block HELLBARK_BEAM = registerModded(Combat.isBOPLoaded(true), "hellbark_beam", new BeamBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).sound(SoundType.WOOD).hardnessAndResistance(1.0F, 2.0F)));
	public static final Block HELLBARK_VERTICAL_SLAB = registerModded(Combat.isBOPLoaded(true), "hellbark_vertical_slab", new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
	public static final Block HELLBARK_PODIUM = registerModded(Combat.isBOPLoaded(true), "hellbark_podium", new PodiumBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));

	//-Crops-\\
	public static final Block CORN = register("corn", new CornBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0, 0).sound(SoundType.PLANT)));

	//-Redstone-\\
	public static final Block TORCH_LEVER = register("torch_lever", new TorchLeverBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0, 0).setLightLevel((p_235418_0_) -> {
		return 14;
	}).sound(SoundType.WOOD), ParticleTypes.FLAME));
	public static final Block WALL_TORCH_LEVER = register("wall_torch_lever", new WallTorchLeverBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0, 0).setLightLevel((p_235418_0_) -> {
		return 14;
	}).sound(SoundType.WOOD), ParticleTypes.FLAME));

	//-Miscellaneous-\\
	public static final Block WHITE_TSUNE = register("white_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.WHITE, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block ORANGE_TSUNE = register("orange_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.ORANGE, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block MAGENTA_TSUNE = register("magenta_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.MAGENTA, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block LIGHT_BLUE_TSUNE = register("light_blue_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.LIGHT_BLUE, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block YELLOW_TSUNE = register("yellow_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.YELLOW, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block LIME_TSUNE = register("lime_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.LIME, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block PINK_TSUNE = register("pink_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.PINK, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block GRAY_TSUNE = register("gray_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.GRAY, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block LIGHT_GRAY_TSUNE = register("light_gray_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.LIGHT_GRAY, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block CYAN_TSUNE = register("cyan_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.CYAN, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block PURPLE_TSUNE = register("purple_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.PURPLE, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block BLUE_TSUNE = register("blue_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.BLUE, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block BROWN_TSUNE = register("brown_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.BROWN, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block GREEN_TSUNE = register("green_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.GREEN, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block RED_TSUNE = register("red_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.RED, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block BLACK_TSUNE = register("black_tsune", CItemGroup.BUILDING_BLOCKS, new TsuneBlock(DyeColor.BLACK, Block.Properties.create(Material.PACKED_ICE).hardnessAndResistance(3.0F).slipperiness(1.112625F).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.GLASS)));
	public static final Block ROBIN_SUMMONER = register("robin_summoner", CItemGroup.MISC, new RobinSummonerBlock(Block.Properties.create(Material.IRON)));
	public static final Block EMPTY_ROBIN_SUMMONER = register("empty_robin_summoner", CItemGroup.MISC, new EmptySummonerBlock(Block.Properties.create(Material.GLASS).notSolid()));
	public static final Block CARDBOARD_BOX = register("cardboard_box", new CardboardBoxBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.CLOTH)));
	public static final Block ACROTLEST_RUINED_PORTAL = register("acrotlest_ruined_portal", new AcrotlestRuinedPortalBlock(Block.Properties.create(Material.PORTAL).doesNotBlockMovement().tickRandomly().hardnessAndResistance(-1.0F).sound(SoundType.GLASS).setLightLevel((p_235418_0_) -> {
		return 11;
	})));
	public static final Block ACROTLEST_PORTAL = register("acrotlest_portal", new AcrotlestPortalBlock(Block.Properties.create(Material.PORTAL).doesNotBlockMovement().tickRandomly().hardnessAndResistance(-1.0F).sound(SoundType.GLASS).setLightLevel((p_235418_0_) -> {
		return 11;
	})));
	public static final Block ALLOY_FURNACE = register("alloy_furnace", new AlloyFurnaceBlock(Block.Properties.create(Material.MISCELLANEOUS).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(2.0f, 3.0f).setLightLevel(getLightValueLit(13))));
	public static final Block LIMESTONE = register("limestone", CItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(1)));
	public static final Block MEZEPINE = register("mezepine", CItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.75F, 3.0F)));
	public static final Block MEZEPINE_BRICKS = register("mezepine_bricks", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.75F, 3.0F)));
	public static final Block MEZEPINE_FURNACE = register("mezepine_furnace", CItemGroup.MISC, new MezepineFurnaceBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.75F).setLightLevel((p_235418_0_) -> {
		return 13;
	})));
	public static final Block MEZEPINE_SLAB = register("mezepine_slab", new SlabBlock(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.0F, 3.0F)));
	public static final Block MEZEPINE_BRICK_SLAB = register("mezepine_brick_slab", new SlabBlock(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.0F, 3.0F)));
	public static final Block MEZEPINE_BRICK_STAIRS = register("mezepine_brick_stairs", new StairsBlock(() -> CBlocks.MEZEPINE_BRICKS.getDefaultState(), Block.Properties.from(CBlocks.MEZEPINE_BRICKS)));
	public static final Block PURIFIED_DIRT = register("purified_dirt", CItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.4F).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)));
	public static final Block PURIFIED_GRASS_BLOCK = register("purified_grass_block", CItemGroup.BUILDING_BLOCKS, new GrassBlock(Block.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.5F).harvestTool(ToolType.SHOVEL).sound(SoundType.PLANT)));
	public static final Block ELYCEN_BLOCK = register("elycen_block", CItemGroup.BUILDING_BLOCKS, new GrassBlock(Block.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.5F).harvestTool(ToolType.SHOVEL).sound(SoundType.PLANT)));
	public static final Block CALTAS = register("caltas", new Block(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)));
	public static final Block WOODCUTTER = register("woodcutter", new WoodcutterBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F)));
	public static final Block PYRANITE_FIRE = register("pyranite_fire", new PyraniteFireBlock(Block.Properties.create(Material.FIRE, MaterialColor.TNT).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().setLightLevel((p_235418_0_) -> {
		return 13;
	}).sound(SoundType.CLOTH).noDrops()));
	public static final Block ARCANE_WORKBENCH = register("arcane_workbench", new ArcaneWorkbenchBlock(Block.Properties.create(Material.ROCK, MaterialColor.GOLD).hardnessAndResistance(5.0F, 1200.0F).setLightLevel((p_235418_0_) -> {
		return 2;
	})));
	public static final Block MANA_GENERATOR = register("mana_generator", new ManaGeneratorBlock(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(2.0f, 3.0f).setLightLevel((p_235418_0_) -> {
		return 3;
	}).sound(SoundType.METAL)));
	public static final Block ELECTRIC_FURNACE = register("electric_furnace", new ElectricFurnaceBlock(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(2.0f, 3.0f).setLightLevel(getLightValueLit(3)).sound(SoundType.METAL)));
	public static final Block CONNECTOR = register("connector", new ConnectorBlock(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(2.0f, 3.0f).setLightLevel((p_235418_0_) -> {
		return 3;
	}).sound(SoundType.METAL)));
	public static final Block ACROTLEST_FARMLAND = register("acrotlest_farmland", new FarmlandBlock(Block.Properties.create(Material.EARTH).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.GROUND)));
	public static final Block GROPAPY = register("gropapy", new GropapyBlock(Block.Properties.create(Material.ORGANIC, MaterialColor.ADOBE).slipperiness(0.8F).sound(SoundType.SLIME).notSolid()));
	public static final Block PYRANITE_TORCH = register("pyranite_torch", new TorchBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((p_235418_0_) -> {
		return 13;
	}).sound(SoundType.WOOD), CParticleTypes.PYRANITE_FLAME));
	public static final Block PYRANITE_LANTERN = register("pyranite_lantern", new LanternBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3.5F).sound(SoundType.LANTERN).setLightLevel((p_235418_0_) -> {
		return 13;
	}).notSolid()));
	public static final Block PYRANITE_WALL_TORCH = register("pyranite_wall_torch", new WallTorchBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((p_235418_0_) -> {
		return 13;
	}).sound(SoundType.WOOD).lootFrom(() -> PYRANITE_TORCH), CParticleTypes.PYRANITE_FLAME));
	public static final Block MAGIC_STONE = register("magic_stone", new OreCBlock(Block.Properties.create(Material.ROCK, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(3).setLightLevel((p_235418_0_) -> {
		return 15;
	}).sound(SoundType.STONE)));
	public static final Block YELLOW_MAGIC_CLUSTER = register("yellow_magic_cluster", new OreCBlock(Block.Properties.create(Material.ROCK, MaterialColor.YELLOW_TERRACOTTA).hardnessAndResistance(6.0F).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).setLightLevel((p_235418_0_) -> {
		return 7;
	}).sound(SoundType.STONE)));
	public static final Block DISENCHANTING_TABLE = register("disenchanting_table", new DisenchantingTableBlock(Block.Properties.create(Material.ROCK, MaterialColor.BLUE).hardnessAndResistance(5.0F, 1200.0F)));
	public static final Block HOMSE = register("homse", new SandBlock(0Xfff98f, Block.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.7F).sound(SoundType.GROUND)));
	public static final Block BATTERY_CHARGER = register("battery_charger", new BatteryChargerBlock(Block.Properties.create(Material.IRON)));

	private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
		return (p_235421_1_) -> {
			return p_235421_1_.get(BlockStateProperties.LIT) ? lightValue : 0;
		};
	}

	private static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
		return new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, (p_235431_2_) -> {
			return p_235431_2_.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor;
		}).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
	}

	public static Block register(String name, Block block) {
		block.setRegistryName(Combat.getInstance().location(name));
		CBlocks.BLOCKS.add(block);
		return block;
	}

	public static Block registerModded(boolean modIsLoaded, String name, Block block) {
		return modIsLoaded ? register(name, block) : block;
	}

	public static Block register(String name, ItemGroup group, Block block) {
		CBlocks.BLOCKITEMS.add(block);
		CBlocks.ITEMGROUPS.add(group);
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
