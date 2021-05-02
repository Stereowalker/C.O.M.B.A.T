package com.stereowalker.combat.spell;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.spell.AbstractBlockSpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GrowSpell extends AbstractBlockSpell {
	private static Random random = new Random();

	protected GrowSpell(SpellCategory category, Rank tier, float cost, int cooldown, int castTime) {
		super(category, tier, cost, cooldown, castTime);
	}

	@Override
	public ActionResultType blockProgram(LivingEntity caster, ItemUseContext context, double strength) {
		World world = caster.world;
		BlockPos blockpos = context.getPos();
		BlockPos blockpos1 = blockpos.offset(context.getFace() );
		if (applyBonemeal(caster.getHeldItemMainhand(), world, blockpos, (PlayerEntity)caster)) {
			if (!world.isRemote) {
				world.playEvent(2005, blockpos, 0);
			}
			return ActionResultType.func_233537_a_(world.isRemote);

		} else {
			BlockState blockstate = world.getBlockState(blockpos);
			boolean flag = blockstate.isSolidSide(world, blockpos, context.getFace());
			if (flag && growSeagrass(caster.getHeldItemMainhand(), world, blockpos1, context.getFace())) {
				if (!world.isRemote) {
					world.playEvent(2005, blockpos1, 0);
				}
				return ActionResultType.func_233537_a_(world.isRemote);
			}
		}
		return ActionResultType.PASS;
	}

	   @Deprecated //Forge: Use Player/Hand version
	   public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos pos) {
	      if (worldIn instanceof net.minecraft.world.server.ServerWorld)
	         return applyBonemeal(stack, worldIn, pos, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((net.minecraft.world.server.ServerWorld)worldIn));
	      return false;
	   }

	   public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos pos, net.minecraft.entity.player.PlayerEntity player) {
	      BlockState blockstate = worldIn.getBlockState(pos);
	      int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, pos, blockstate, stack);
	      if (hook != 0) return hook > 0;
	      if (blockstate.getBlock() instanceof IGrowable) {
	         IGrowable igrowable = (IGrowable)blockstate.getBlock();
	         if (igrowable.canGrow(worldIn, pos, blockstate, worldIn.isRemote)) {
	            if (worldIn instanceof ServerWorld) {
	               if (igrowable.canUseBonemeal(worldIn, worldIn.rand, pos, blockstate)) {
	                  igrowable.grow((ServerWorld)worldIn, worldIn.rand, pos, blockstate);
	               }

	            }

	            return true;
	         }
	      }

	      return false;
	   }

	   public static boolean growSeagrass(ItemStack stack, World worldIn, BlockPos pos, @Nullable Direction side) {
	      if (worldIn.getBlockState(pos).getBlock() == Blocks.WATER && worldIn.getFluidState(pos).getLevel() == 8) {
	         if (!(worldIn instanceof ServerWorld)) {
	            return true;
	         } else {
	            label80:
	            for(int i = 0; i < 128; ++i) {
	               BlockPos blockpos = pos;
	               Biome biome = worldIn.getBiome(pos);
	               BlockState blockstate = Blocks.SEAGRASS.getDefaultState();

	               for(int j = 0; j < i / 16; ++j) {
	                  blockpos = blockpos.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
	                  biome = worldIn.getBiome(blockpos);
	                  if (worldIn.getBlockState(blockpos).hasOpaqueCollisionShape(worldIn, blockpos)) {
	                     continue label80;
	                  }
	               }

	               // FORGE: Use BiomeDictionary here to allow modded warm ocean biomes to spawn coral from bonemeal
	               //TODO: RE-Add this when biome dictionary becomes available again
	               Optional<RegistryKey<Biome>> optional = worldIn.func_242406_i(blockpos);
	               if (Objects.equals(optional, Optional.of(Biomes.WARM_OCEAN)) || Objects.equals(optional, Optional.of(Biomes.DEEP_WARM_OCEAN))) {
	                  if (i == 0 && side != null && side.getAxis().isHorizontal()) {
	                     blockstate = BlockTags.WALL_CORALS.getRandomElement(worldIn.rand).getDefaultState().with(DeadCoralWallFanBlock.FACING, side);
	                  } else if (random.nextInt(4) == 0) {
	                     blockstate = BlockTags.UNDERWATER_BONEMEALS.getRandomElement(random).getDefaultState();
	                  }
	               }

	               if (blockstate.getBlock().isIn(BlockTags.WALL_CORALS)) {
	                  for(int k = 0; !blockstate.isValidPosition(worldIn, blockpos) && k < 4; ++k) {
	                     blockstate = blockstate.with(DeadCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.random(random));
	                  }
	               }

	               if (blockstate.isValidPosition(worldIn, blockpos)) {
	                  BlockState blockstate1 = worldIn.getBlockState(blockpos);
	                  if (blockstate1.getBlock() == Blocks.WATER && worldIn.getFluidState(blockpos).getLevel() == 8) {
	                     worldIn.setBlockState(blockpos, blockstate, 3);
	                  } else if (blockstate1.getBlock() == Blocks.SEAGRASS && random.nextInt(10) == 0) {
	                     ((IGrowable)Blocks.SEAGRASS).grow((ServerWorld)worldIn, random, blockpos, blockstate1);
	                  }
	               }
	            }

	            return true;
	         }
	      } else {
	         return false;
	      }
	   }

	@OnlyIn(Dist.CLIENT)
	public static void spawnBonemealParticles(IWorld worldIn, BlockPos posIn, int data) {
		if (data == 0) {
			data = 15;
		}

		BlockState blockstate = worldIn.getBlockState(posIn);
		if (!blockstate.isAir(worldIn, posIn)) {
			for(int i = 0; i < data; ++i) {
				double d0 = random.nextGaussian() * 0.02D;
				double d1 = random.nextGaussian() * 0.02D;
				double d2 = random.nextGaussian() * 0.02D;
				worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)posIn.getX() + random.nextFloat()), (double)posIn.getY() + (double)random.nextFloat() * blockstate.getShape(worldIn, posIn).getEnd(Direction.Axis.Y), (double)((float)posIn.getZ() + random.nextFloat()), d0, d1, d2);
			}

		}
	}

}
