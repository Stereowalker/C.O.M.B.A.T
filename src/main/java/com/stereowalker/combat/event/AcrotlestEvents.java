package com.stereowalker.combat.event;

import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.material.CFluids;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class AcrotlestEvents {
	//
	//	@SubscribeEvent
	//	public static void acrotlestTick(WorldTickEvent event) {
	//		if (event.world instanceof ServerLevel) {
	//			ServerLevel world = (ServerLevel) event.world;
	//			if (world.dimension.getType() == CDimensionType.THE_ACROTLEST) {
	//				for (PlayerEntity player : world.getPlayers()) {
	//					ChunkPos chunkpos = new ChunkPos(player.getPosition());
	//					for (int i = -10; i < 10; i++) {
	//						for (int j = -10; j < 10; j++) {
	//							freezeAcrotlestChunks(world, new ChunkPos(i + chunkpos.x, j + chunkpos.z));
	//						}
	//					}
	//				}
	//			}
	//		}
	//	}
	//
	public static void freezeAcrotlestChunks(ServerLevel world, ChunkPos chunkpos) {
		int i = chunkpos.getMinBlockX();
		int j = chunkpos.getMinBlockZ();
		BlockPos blockpos2 = world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, world.getBlockRandomPos(i, 0, j, 15));
		BlockPos blockpos3 = blockpos2.below();
		Biome biome = world.getBiome(blockpos2).value();
		if (world.isAreaLoaded(blockpos2, 1)) // Forge: check area to avoid loading neighbors in unloaded chunks
			if (doesWaterFreeze(biome, world, blockpos3)) {
				world.setBlockAndUpdate(blockpos3, CBlocks.GROPAPY.defaultBlockState());
			}
	}

	public static boolean doesWaterFreeze(Biome biome, LevelReader worldIn, BlockPos pos) {
		return doesWaterFreeze(biome, worldIn, pos, true);
	}

	public static boolean doesWaterFreeze(Biome biome, LevelReader worldIn, BlockPos water, boolean mustBeAtEdge) {
		if (biome.warmEnoughToRain(water)) {
			return false;
		} else {
			if (water.getY() >= 0 && water.getY() < 256 && worldIn.getBrightness(LightLayer.BLOCK, water) < 10) {
				BlockState blockstate = worldIn.getBlockState(water);
				FluidState fluidstate = worldIn.getFluidState(water);
				if (fluidstate.getType() == CFluids.BIABLE && blockstate.getBlock() instanceof LiquidBlock) {
					if (!mustBeAtEdge) {
						return true;
					}

					boolean flag = worldIn.isWaterAt(water.west()) && worldIn.isWaterAt(water.east()) && worldIn.isWaterAt(water.north()) && worldIn.isWaterAt(water.south());
					if (!flag) {
						return true;
					}
				}
			}

			return false;
		}
	}


	//	@SubscribeEvent
	//	public static void noTeleport(EntityTravelToDimensionEvent event) {
	//		DimensionType dimension = event.getEntity().dimension;
	//		DimensionType destination = event.getDimension();
	//		if(dimension == DimensionType.THE_NETHER && destination == CDimensionType.THE_ACROTLEST) {
	//			event.setCanceled(true);
	//			if (event.getEntity() instanceof LivingEntity) {
	//				((LivingEntity)event.getEntity()).addPotionEffect(new EffectInstance(Effects.BLINDNESS));
	//			}
	//		}
	//	}
}
