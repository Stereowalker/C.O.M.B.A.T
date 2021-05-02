package com.stereowalker.combat.event;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.fluid.CFluids;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class AcrotlestEvents {
	//
	//	@SubscribeEvent
	//	public static void acrotlestTick(WorldTickEvent event) {
	//		if (event.world instanceof ServerWorld) {
	//			ServerWorld world = (ServerWorld) event.world;
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
	public static void freezeAcrotlestChunks(ServerWorld world, ChunkPos chunkpos) {
		int i = chunkpos.getXStart();
		int j = chunkpos.getZStart();
		BlockPos blockpos2 = world.getHeight(Heightmap.Type.MOTION_BLOCKING, world.getBlockRandomPos(i, 0, j, 15));
		BlockPos blockpos3 = blockpos2.down();
		Biome biome = world.getBiome(blockpos2);
		if (world.isAreaLoaded(blockpos2, 1)) // Forge: check area to avoid loading neighbors in unloaded chunks
			if (doesWaterFreeze(biome, world, blockpos3)) {
				world.setBlockState(blockpos3, CBlocks.GROPAPY.getDefaultState());
			}
	}

	public static boolean doesWaterFreeze(Biome biome, IWorldReader worldIn, BlockPos pos) {
		return doesWaterFreeze(biome, worldIn, pos, true);
	}

	public static boolean doesWaterFreeze(Biome biome, IWorldReader worldIn, BlockPos water, boolean mustBeAtEdge) {
		if (biome.getTemperature(water) >= 0.15F) {
			return false;
		} else {
			if (water.getY() >= 0 && water.getY() < 256 && worldIn.getLightFor(LightType.BLOCK, water) < 10) {
				BlockState blockstate = worldIn.getBlockState(water);
				FluidState fluidstate = worldIn.getFluidState(water);
				if (fluidstate.getFluid() == CFluids.BIABLE && blockstate.getBlock() instanceof FlowingFluidBlock) {
					if (!mustBeAtEdge) {
						return true;
					}

					boolean flag = worldIn.hasWater(water.west()) && worldIn.hasWater(water.east()) && worldIn.hasWater(water.north()) && worldIn.hasWater(water.south());
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
