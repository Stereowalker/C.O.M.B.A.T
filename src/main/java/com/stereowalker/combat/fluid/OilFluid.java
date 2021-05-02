package com.stereowalker.combat.fluid;

import java.util.Random;

import javax.annotation.Nullable;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.item.CItems;
import com.stereowalker.combat.particles.CParticleTypes;
import com.stereowalker.combat.tags.CTags.FluidCTags;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;

public abstract class OilFluid extends FlowingFluid {
	public Fluid getFlowingFluid() {
		return CFluids.FLOWING_OIL;
	}

	public Fluid getStillFluid() {
		return CFluids.OIL;
	}

	public Item getFilledBucket() {
		return CItems.OIL_BUCKET;
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(World worldIn, BlockPos pos, FluidState state, Random random) {
		if (!state.isSource() && !state.get(FALLING)) {
			if (random.nextInt(64) == 0) {
				worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
			}
		} else if (random.nextInt(10) == 0) {
			worldIn.addParticle(ParticleTypes.UNDERWATER, (double)((float)pos.getX() + random.nextFloat()), (double)((float)pos.getY() + random.nextFloat()), (double)((float)pos.getZ() + random.nextFloat()), 0.0D, 0.0D, 0.0D);
		}

	}

	@Override
	protected FluidAttributes createAttributes() {
		return net.minecraftforge.fluids.FluidAttributes.builder(
				Combat.getInstance().location("block/oil_still"),
				Combat.getInstance().location("block/oil_flow"))
                .overlay(Combat.getInstance().location("block/oil_overlay"))
                .translationKey("block.combat.oil")
                .build(this);
	}

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public IParticleData getDripParticleData() {
		return CParticleTypes.DRIPPING_OIL;
	}

	protected boolean canSourcesMultiply() {
		return false;
	}

	protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
		TileEntity tileentity = state.hasTileEntity() ? worldIn.getTileEntity(pos) : null;
		Block.spawnDrops(state, worldIn, pos, tileentity);
	}

	public int getSlopeFindDistance(IWorldReader worldIn) {
		return 4;
	}

	public BlockState getBlockState(FluidState state) {
		return CBlocks.OIL.getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
	}

	public boolean isEquivalentTo(Fluid fluidIn) {
		return fluidIn == CFluids.OIL || fluidIn == CFluids.FLOWING_OIL;
	}

	public int getLevelDecreasePerBlock(IWorldReader worldIn) {
		return 1;
	}

	public int getTickRate(IWorldReader p_205569_1_) {
		return 5;
	}

	public boolean func_215665_a(FluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
		return p_215665_5_ == Direction.DOWN && !p_215665_4_.isIn(FluidCTags.OIL);
	}

	protected float getExplosionResistance() {
		return 50.0F;
	}

	public static class Flowing extends OilFluid {
		protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
			super.fillStateContainer(builder);
			builder.add(LEVEL_1_8);
		}

		public int getLevel(FluidState p_207192_1_) {
			return p_207192_1_.get(LEVEL_1_8);
		}

		public boolean isSource(FluidState state) {
			return false;
		}

		@Override
		protected boolean canDisplace(FluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_,
				Fluid p_215665_4_, Direction p_215665_5_) {
			return false;
		}
	}

	public static class Source extends OilFluid {
		public int getLevel(FluidState p_207192_1_) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}

		@Override
		protected boolean canDisplace(FluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_,
				Fluid p_215665_4_, Direction p_215665_5_) {
			return false;
		}
	}
}