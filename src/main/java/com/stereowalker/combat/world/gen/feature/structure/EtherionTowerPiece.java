package com.stereowalker.combat.world.gen.feature.structure;

import java.util.List;
import java.util.Random;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.storage.loot.CLootTables;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

@SuppressWarnings("deprecation")
public class EtherionTowerPiece {
	private static final ResourceLocation BOTTOM = Combat.getInstance().location("etherion_tower/tower_bottom");
	private static final ResourceLocation TOP = Combat.getInstance().location("etherion_tower/tower_top");

	public static void createStructure(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, List<StructurePiece> structurePieceList, Random random, boolean overworld) {
		structurePieceList.add(new EtherionTowerPiece.Piece(templateManager, BOTTOM, blockPos, rotation, 0, overworld));
		structurePieceList.add(new EtherionTowerPiece.Piece(templateManager, TOP, blockPos, rotation, 32, overworld));

	}

	public static class Piece extends TemplateStructurePiece {
		private final ResourceLocation field_207615_d;
		private final Rotation field_207616_e;
		private final boolean overworld;

		public Piece(TemplateManager p_i49313_1_, ResourceLocation p_i49313_2_, BlockPos p_i49313_3_, Rotation p_i49313_4_, int p_i49313_5_, boolean overworld) {
			super(StructurePieceTypes.ETTOWER, 0);
			this.field_207615_d = p_i49313_2_;
			BlockPos blockpos = BlockPos.ZERO;
			this.templatePosition = p_i49313_3_.add(blockpos.getX(), blockpos.getY() + p_i49313_5_, blockpos.getZ());
			this.field_207616_e = p_i49313_4_;
			this.func_207614_a(p_i49313_1_);
			this.overworld = overworld;
		}

		public Piece(TemplateManager p_i50566_1_, CompoundNBT p_i50566_2_) {
			super(StructurePieceTypes.ETTOWER, p_i50566_2_);
			this.field_207615_d = new ResourceLocation(p_i50566_2_.getString("Template"));
			this.field_207616_e = Rotation.valueOf(p_i50566_2_.getString("Rot"));
			this.func_207614_a(p_i50566_1_);
			this.overworld = true;
		}

		private void func_207614_a(TemplateManager p_207614_1_) {
			Template template = p_207614_1_.getTemplateDefaulted(this.field_207615_d);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.field_207616_e).setMirror(Mirror.NONE).setCenterOffset(BlockPos.ZERO).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
			this.setup(template, this.templatePosition, placementsettings);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		protected void readAdditional(CompoundNBT tagCompound) {
			super.readAdditional(tagCompound);
			tagCompound.putString("Template", this.field_207615_d.toString());
			tagCompound.putString("Rot", this.field_207616_e.name());
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
			Biome biome = worldIn.getBiome(pos);
			boolean flag = !this.overworld;
			EntityType<?>[] monster = (!flag) ? new EntityType<?>[] {EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.CAVE_SPIDER, EntityType.HUSK} : new EntityType<?>[] {CEntityType.BIOG, CEntityType.LICHU};
			if ("chest0".equals(function)) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				TileEntity tileentity = worldIn.getTileEntity(pos.down(3));
				if (tileentity instanceof TrappedChestTileEntity) {
					if (!flag) {
						((TrappedChestTileEntity)tileentity).setLootTable(CLootTables.CHESTS_ETHERION_TOWER_0, rand.nextLong());
					} else /* if (dimension == CDimensionType.ACROTLEST) */ {
						((TrappedChestTileEntity)tileentity).setLootTable(CLootTables.CHESTS_ETHERION_TOWER_BLUE_0, rand.nextLong());
					}
				}
				worldIn.setBlockState(pos.down(2), Blocks.SPAWNER.getDefaultState(), 3);
				TileEntity tileentity2 = worldIn.getTileEntity(pos.down(2));
				if (tileentity2 instanceof MobSpawnerTileEntity) {
					((MobSpawnerTileEntity)tileentity2).getSpawnerBaseLogic().setEntityType(monster[rand.nextInt(monster.length)]);
				}

				worldIn.setBlockState(pos.down(5), Blocks.TNT.getDefaultState(), 3);
			}
			String[] chestMarkers = {"chest9", "chest8", "chest7", "chest6", "chest5", "chest4", "chest3", "chest2", "chest1"};
			ResourceLocation[] chestLoot = {CLootTables.CHESTS_ETHERION_TOWER_9, CLootTables.CHESTS_ETHERION_TOWER_8, CLootTables.CHESTS_ETHERION_TOWER_7, CLootTables.CHESTS_ETHERION_TOWER_6, CLootTables.CHESTS_ETHERION_TOWER_5, LootTables.CHESTS_SIMPLE_DUNGEON, LootTables.CHESTS_SIMPLE_DUNGEON, LootTables.CHESTS_SIMPLE_DUNGEON, LootTables.CHESTS_SIMPLE_DUNGEON};

			for (int i = 0; i < chestMarkers.length; i++) {
				if (chestMarkers[i].equals(function)) {
					worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
					TileEntity tileentity = worldIn.getTileEntity(pos.down(3));
					if (tileentity instanceof ChestTileEntity) {
						((ChestTileEntity)tileentity).setLootTable(chestLoot[i], rand.nextLong());
					}

				}
			}
			if ("spawner1".equals(function)) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				worldIn.setBlockState(pos, Blocks.SPAWNER.getDefaultState(), 3);
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof MobSpawnerTileEntity) {
					((MobSpawnerTileEntity)tileentity).getSpawnerBaseLogic().setEntityType(monster[rand.nextInt(monster.length)]);
				}
			}
			if ("spawner2".equals(function)) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				worldIn.setBlockState(pos, Blocks.SPAWNER.getDefaultState(), 3);
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof MobSpawnerTileEntity) {
					((MobSpawnerTileEntity)tileentity).getSpawnerBaseLogic().setEntityType(monster[rand.nextInt(monster.length)]);
				}
			}
			if ("replace".equals(function)) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				if (!flag) {
					if (checkType(biome, Category.DESERT)) {
						replaceBlocks(worldIn, pos, Blocks.SANDSTONE, Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE_SLAB, Blocks.ACACIA_LOG, Blocks.ACACIA_STAIRS, Blocks.ACACIA_SLAB);
					}
					else if (checkType(biome, Category.MESA)) {
						replaceBlocks(worldIn, pos, Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE_SLAB, Blocks.ACACIA_LOG, Blocks.ACACIA_STAIRS, Blocks.ACACIA_SLAB);
					}
				} else /* if (dimension == CDimensionType.ACROTLEST) */ {
					replaceBlocks(worldIn, pos, CBlocks.MEZEPINE_BRICKS, CBlocks.MEZEPINE_BRICK_STAIRS, CBlocks.MEZEPINE_BRICK_SLAB, CBlocks.MONORIS_LOG, CBlocks.MONORIS_STAIRS, CBlocks.MONORIS_SLAB);
					replaceLights(worldIn, pos, CBlocks.PYRANITE_TORCH, CBlocks.PYRANITE_WALL_TORCH, CBlocks.PYRANITE_LANTERN);
				}
			}
		}

		public static boolean checkType(Biome biome, Category type) {
			return type.equals(biome.getCategory());
		}

		public static void replaceBlocks(IWorld worldIn, BlockPos pos, Block newBrickBlock, Block newBrickStairs, Block newBrickSlab, Block newLog, Block newWoodStairs, Block newWoodSlab) {
			for (int x = 0; x < 64; x++) {
				for (int y = 0; y < 64; y++) {
					for (int z = 0; z < 64; z++) {
						BlockPos newPos = new BlockPos(pos.getX()+(x-32), pos.getY()-y, pos.getZ()+(z-32));
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.STONE_BRICK_STAIRS) {
							BlockState stairs = worldIn.getBlockState(newPos);
							worldIn.setBlockState(newPos, newBrickStairs.getDefaultState().with(StairsBlock.HALF, stairs.get(StairsBlock.HALF)).with(StairsBlock.SHAPE, stairs.get(StairsBlock.SHAPE)).with(StairsBlock.FACING, stairs.get(StairsBlock.FACING)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.OAK_STAIRS) {
							BlockState stairs = worldIn.getBlockState(newPos);
							worldIn.setBlockState(newPos, newWoodStairs.getDefaultState().with(StairsBlock.HALF, stairs.get(StairsBlock.HALF)).with(StairsBlock.SHAPE, stairs.get(StairsBlock.SHAPE)).with(StairsBlock.FACING, stairs.get(StairsBlock.FACING)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.OAK_LOG) {
							BlockState log = worldIn.getBlockState(newPos);
							worldIn.setBlockState(newPos, newLog.getDefaultState().with(RotatedPillarBlock.AXIS, log.get(RotatedPillarBlock.AXIS)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.STONE_BRICK_SLAB) {
							BlockState slab = worldIn.getBlockState(newPos);
						worldIn.setBlockState(newPos, newBrickSlab.getDefaultState().with(SlabBlock.TYPE, slab.get(SlabBlock.TYPE)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.OAK_SLAB) {
							BlockState slab = worldIn.getBlockState(newPos);
						worldIn.setBlockState(newPos, newWoodSlab.getDefaultState().with(SlabBlock.TYPE, slab.get(SlabBlock.TYPE)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.STONE_BRICKS) worldIn.setBlockState(newPos, newBrickBlock.getDefaultState(), 3);

					}
				}
			}
		}

		public static void replaceLights(IWorld worldIn, BlockPos pos, Block newTorch, Block newWallTorch, Block newLantern) {
			for (int x = 0; x < 64; x++) {
				for (int y = 0; y < 64; y++) {
					for (int z = 0; z < 64; z++) {
						BlockPos newPos = new BlockPos(pos.getX()+(x-32), pos.getY()-y, pos.getZ()+(z-32));
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.WALL_TORCH) {
							BlockState torch = worldIn.getBlockState(newPos);
							worldIn.setBlockState(newPos, newWallTorch.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, torch.get(WallTorchBlock.HORIZONTAL_FACING)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.LANTERN) {
							BlockState lantern = worldIn.getBlockState(newPos);
							worldIn.setBlockState(newPos, newLantern.getDefaultState().with(LanternBlock.HANGING, lantern.get(LanternBlock.HANGING)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.TORCH) worldIn.setBlockState(newPos, newTorch.getDefaultState(), 3);

					}
				}
			}
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
		 * the end, it adds Fences...
		 */
		@Override
		public boolean /* create */func_230383_a_(ISeedReader seedReader, StructureManager mamager, ChunkGenerator chunkGenerator, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn, BlockPos pos) {
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.field_207616_e).setMirror(Mirror.NONE).setCenterOffset(BlockPos.ZERO).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
			BlockPos blockpos = BlockPos.ZERO;
			BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(3 - blockpos.getX(), 0, 0 - blockpos.getZ())));
			int i = seedReader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
			BlockPos blockpos2 = this.templatePosition;
			this.templatePosition = this.templatePosition.add(0, i - 90 - 1, 0);
			boolean flag = super./*create*/func_230383_a_(seedReader, mamager, chunkGenerator, randomIn, structureBoundingBoxIn, chunkPosIn, pos);
			if (this.field_207615_d.equals(EtherionTowerPiece.TOP)) {
				BlockPos blockpos3 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(3, 0, 5)));
				BlockState blockstate = seedReader.getBlockState(blockpos3.down());
				if (!blockstate.isAir() && blockstate.getBlock() != Blocks.LADDER) {
					seedReader.setBlockState(blockpos3, Blocks.SNOW_BLOCK.getDefaultState(), 3);
				}
			}

			this.templatePosition = blockpos2;
			return flag;
		}
	}
}