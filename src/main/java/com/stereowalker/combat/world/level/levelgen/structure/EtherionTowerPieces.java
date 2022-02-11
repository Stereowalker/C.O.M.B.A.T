package com.stereowalker.combat.world.level.levelgen.structure;

import java.util.Random;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.levelgen.feature.StructurePieceTypes;
import com.stereowalker.combat.world.level.storage.loot.CLootTables;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class EtherionTowerPieces {
	private static final ResourceLocation BOTTOM = Combat.getInstance().location("etherion_tower/tower_bottom");
	private static final ResourceLocation TOP = Combat.getInstance().location("etherion_tower/tower_top");

	public static void addPieces(StructureManager templateManager, BlockPos blockPos, Rotation rotation, StructurePieceAccessor pPieces, Random random, boolean overworld) {
		pPieces.addPiece(new EtherionTowerPieces.Piece(templateManager, BOTTOM, blockPos, rotation, 0, overworld));
		pPieces.addPiece(new EtherionTowerPieces.Piece(templateManager, TOP, blockPos, rotation, 32, overworld));

	}

	public static class Piece extends CustomTemplateStructurePiece {
		private final boolean overworld;

		public Piece(StructureManager pStructureManager, ResourceLocation pLocation, BlockPos pPos, Rotation pRotation, int pDown, boolean overworld) {
			super(StructurePieceTypes.ETHERION_TOWER, 0, pStructureManager, pLocation, pLocation.toString(), makeSettings(pRotation, BlockPos.ZERO), makePosition(BlockPos.ZERO, pPos, pDown));
			this.overworld = overworld;
		}

		public Piece(ServerLevel pLevel, CompoundTag pTag) {
			super(StructurePieceTypes.ETHERION_TOWER, pTag, pLevel, (p_162451_) -> {
				return makeSettings(Rotation.valueOf(pTag.getString("Rot")), BlockPos.ZERO);
			});
			this.overworld = true;
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void addAdditionalSaveData(ServerLevel pLevel, CompoundTag pTag) {
			super.addAdditionalSaveData(pLevel, pTag);
			pTag.putString("Rot", this.placeSettings.getRotation().name());
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, Random rand, BoundingBox sbb) {
			Biome biome = worldIn.getBiome(pos);
			boolean flag = !this.overworld;
			EntityType<?>[] monster = (!flag) ? 
					new EntityType<?>[] {EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.CAVE_SPIDER, EntityType.HUSK} : 
						new EntityType<?>[] {CEntityType.BIOG, CEntityType.LICHU};
			if ("chest0".equals(function)) {
				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				BlockEntity tileentity = worldIn.getBlockEntity(pos.below(3));
				if (tileentity instanceof TrappedChestBlockEntity) {
					if (!flag) {
						((TrappedChestBlockEntity)tileentity).setLootTable(CLootTables.CHESTS_ETHERION_TOWER_0, rand.nextLong());
					} else /* if (dimension == CDimensionType.ACROTLEST) */ {
						((TrappedChestBlockEntity)tileentity).setLootTable(CLootTables.CHESTS_ETHERION_TOWER_BLUE_0, rand.nextLong());
					}
				}
				worldIn.setBlock(pos.below(2), Blocks.SPAWNER.defaultBlockState(), 3);
				BlockEntity tileentity2 = worldIn.getBlockEntity(pos.below(2));
				if (tileentity2 instanceof SpawnerBlockEntity) {
					((SpawnerBlockEntity)tileentity2).getSpawner().setEntityId(monster[rand.nextInt(monster.length)]);
				}

				worldIn.setBlock(pos.below(5), Blocks.TNT.defaultBlockState(), 3);
			}
			String[] chestMarkers = {
					"chest9", "chest8", "chest7", "chest6", "chest5", 
					"chest4", "chest3", "chest2", "chest1"};
			ResourceLocation[] chestLoot = {
					CLootTables.CHESTS_ETHERION_TOWER_9, CLootTables.CHESTS_ETHERION_TOWER_8, 
					CLootTables.CHESTS_ETHERION_TOWER_7, CLootTables.CHESTS_ETHERION_TOWER_6, CLootTables.CHESTS_ETHERION_TOWER_5, BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.SIMPLE_DUNGEON};

			for (int i = 0; i < chestMarkers.length; i++) {
				if (chestMarkers[i].equals(function)) {
					worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					BlockEntity tileentity = worldIn.getBlockEntity(pos.below(3));
					if (tileentity instanceof ChestBlockEntity) {
						((ChestBlockEntity)tileentity).setLootTable(chestLoot[i], rand.nextLong());
					}

				}
			}
			if ("spawner1".equals(function)) {
				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				worldIn.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 3);
				BlockEntity tileentity = worldIn.getBlockEntity(pos);
				if (tileentity instanceof SpawnerBlockEntity) {
					((SpawnerBlockEntity)tileentity).getSpawner().setEntityId(monster[rand.nextInt(monster.length)]);
				}
			}
			if ("spawner2".equals(function)) {
				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				worldIn.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 3);
				BlockEntity tileentity = worldIn.getBlockEntity(pos);
				if (tileentity instanceof SpawnerBlockEntity) {
					((SpawnerBlockEntity)tileentity).getSpawner().setEntityId(monster[rand.nextInt(monster.length)]);
				}
			}
			if ("replace".equals(function)) {
				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				if (!flag) {
					if (checkType(biome, Biome.BiomeCategory.DESERT)) {
						replaceBlocks(worldIn, pos, Blocks.SANDSTONE, Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE_SLAB, Blocks.ACACIA_LOG, Blocks.ACACIA_STAIRS, Blocks.ACACIA_SLAB);
					}
					else if (checkType(biome, Biome.BiomeCategory.MESA)) {
						replaceBlocks(worldIn, pos, Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE_SLAB, Blocks.ACACIA_LOG, Blocks.ACACIA_STAIRS, Blocks.ACACIA_SLAB);
					}
				} else /* if (dimension == CDimensionType.ACROTLEST) */ {
					replaceBlocks(worldIn, pos, CBlocks.MEZEPINE_BRICKS, CBlocks.MEZEPINE_BRICK_STAIRS, CBlocks.MEZEPINE_BRICK_SLAB, CBlocks.MONORIS_LOG, CBlocks.MONORIS_STAIRS, CBlocks.MONORIS_SLAB);
					replaceLights(worldIn, pos, CBlocks.PYRANITE_TORCH, CBlocks.PYRANITE_WALL_TORCH, CBlocks.PYRANITE_LANTERN);
				}
			}
		}

		public static boolean checkType(Biome biome, Biome.BiomeCategory type) {
			return type.equals(biome.getBiomeCategory());
		}

		public static void replaceBlocks(LevelAccessor worldIn, BlockPos pos, Block newBrickBlock, Block newBrickStairs, Block newBrickSlab, Block newLog, Block newWoodStairs, Block newWoodSlab) {
			for (int x = 0; x < 64; x++) {
				for (int y = 0; y < 64; y++) {
					for (int z = 0; z < 64; z++) {
						BlockPos newPos = new BlockPos(pos.getX()+(x-32), pos.getY()-y, pos.getZ()+(z-32));
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.STONE_BRICK_STAIRS) {
							BlockState stairs = worldIn.getBlockState(newPos);
							worldIn.setBlock(newPos, newBrickStairs.defaultBlockState().setValue(StairBlock.HALF, stairs.getValue(StairBlock.HALF)).setValue(StairBlock.SHAPE, stairs.getValue(StairBlock.SHAPE)).setValue(StairBlock.FACING, stairs.getValue(StairBlock.FACING)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.OAK_STAIRS) {
							BlockState stairs = worldIn.getBlockState(newPos);
							worldIn.setBlock(newPos, newWoodStairs.defaultBlockState().setValue(StairBlock.HALF, stairs.getValue(StairBlock.HALF)).setValue(StairBlock.SHAPE, stairs.getValue(StairBlock.SHAPE)).setValue(StairBlock.FACING, stairs.getValue(StairBlock.FACING)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.OAK_LOG) {
							BlockState log = worldIn.getBlockState(newPos);
							worldIn.setBlock(newPos, newLog.defaultBlockState().setValue(RotatedPillarBlock.AXIS, log.getValue(RotatedPillarBlock.AXIS)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.STONE_BRICK_SLAB) {
							BlockState slab = worldIn.getBlockState(newPos);
							worldIn.setBlock(newPos, newBrickSlab.defaultBlockState().setValue(SlabBlock.TYPE, slab.getValue(SlabBlock.TYPE)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.OAK_SLAB) {
							BlockState slab = worldIn.getBlockState(newPos);
							worldIn.setBlock(newPos, newWoodSlab.defaultBlockState().setValue(SlabBlock.TYPE, slab.getValue(SlabBlock.TYPE)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.STONE_BRICKS) worldIn.setBlock(newPos, newBrickBlock.defaultBlockState(), 3);

					}
				}
			}
		}

		public static void replaceLights(LevelAccessor worldIn, BlockPos pos, Block newTorch, Block newWallTorch, Block newLantern) {
			for (int x = 0; x < 64; x++) {
				for (int y = 0; y < 64; y++) {
					for (int z = 0; z < 64; z++) {
						BlockPos newPos = new BlockPos(pos.getX()+(x-32), pos.getY()-y, pos.getZ()+(z-32));
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.WALL_TORCH) {
							BlockState torch = worldIn.getBlockState(newPos);
							worldIn.setBlock(newPos, newWallTorch.defaultBlockState().setValue(WallTorchBlock.FACING, torch.getValue(WallTorchBlock.FACING)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.LANTERN) {
							BlockState lantern = worldIn.getBlockState(newPos);
							worldIn.setBlock(newPos, newLantern.defaultBlockState().setValue(LanternBlock.HANGING, lantern.getValue(LanternBlock.HANGING)), 3);
						}
						if (worldIn.getBlockState(newPos).getBlock() == Blocks.TORCH) worldIn.setBlock(newPos, newTorch.defaultBlockState(), 3);

					}
				}
			}
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
		 * the end, it adds Fences...
		 */
		@Override
		public boolean postProcess(WorldGenLevel seedReader, StructureFeatureManager mamager, ChunkGenerator chunkGenerator, Random randomIn, BoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn, BlockPos pos) {
			StructurePlaceSettings placementsettings = makeSettings(this.placeSettings.getRotation(), BlockPos.ZERO);
			BlockPos blockpos = BlockPos.ZERO;
			BlockPos blockpos1 = this.templatePosition.offset(StructureTemplate.calculateRelativePosition(placementsettings, new BlockPos(3 - blockpos.getX(), 0, 0 - blockpos.getZ())));
			int i = seedReader.getHeight(Heightmap.Types.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
			BlockPos blockpos2 = this.templatePosition;
			this.templatePosition = this.templatePosition.offset(0, i - 90 - 1, 0);
			boolean flag = super.postProcess(seedReader, mamager, chunkGenerator, randomIn, structureBoundingBoxIn, chunkPosIn, pos);
			if (new ResourceLocation(this.templateName).equals(EtherionTowerPieces.TOP)) {
				BlockPos blockpos3 = this.templatePosition.offset(StructureTemplate.calculateRelativePosition(placementsettings, new BlockPos(3, 0, 5)));
				BlockState blockstate = seedReader.getBlockState(blockpos3.below());
				if (!blockstate.isAir() && blockstate.getBlock() != Blocks.LADDER) {
					seedReader.setBlock(blockpos3, Blocks.SNOW_BLOCK.defaultBlockState(), 3);
				}
			}

			this.templatePosition = blockpos2;
			return flag;
		}
	}
}