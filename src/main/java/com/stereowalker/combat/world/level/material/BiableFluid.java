package com.stereowalker.combat.world.level.material;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.core.particles.CParticleTypes;
import com.stereowalker.combat.tags.CTags.FluidCTags;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

public abstract class BiableFluid extends FlowingFluid {
	@Override
	public Fluid getFlowing() {
		return CFluids.FLOWING_BIABLE;
	}

	@Override
	public Fluid getSource() {
		return CFluids.BIABLE;
	}

	@Override
	public Item getBucket() {
		return CItems.BIABLE_BUCKET;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(Level worldIn, BlockPos pos, FluidState state, RandomSource random) {
		if (!state.isSource() && !state.getValue(FALLING)) {
			if (random.nextInt(64) == 0) {
				worldIn.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
			}
		} else if (random.nextInt(10) == 0) {
			worldIn.addParticle(ParticleTypes.UNDERWATER, (double)((float)pos.getX() + random.nextFloat()), (double)((float)pos.getY() + random.nextFloat()), (double)((float)pos.getZ() + random.nextFloat()), 0.0D, 0.0D, 0.0D);
		}

	}

	public static final FluidType TYPE = new FluidType(FluidType.Properties.create()
            .descriptionId("block.combat.biable")
            .fallDistanceModifier(0F)
            .canExtinguish(true)
            .canConvertToSource(true)
            .supportsBoating(true)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
            .canHydrate(true)
			.density(10)
			//.luminosity(1)
			//.color(0xff000000+Survive.PURIFIED_WATER_COLOR)
			.viscosity(10))
    {
        @Override
        public @Nullable BlockPathTypes getBlockPathType(FluidState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, boolean canFluidLog)
        {
            return canFluidLog ? super.getBlockPathType(state, level, pos, mob, true) : null;
        }

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
        {
            consumer.accept(new IClientFluidTypeExtensions()
            {
                private static final ResourceLocation UNDERWATER_LOCATION = new ResourceLocation("textures/misc/underwater.png");

                @Override
                public ResourceLocation getStillTexture()
                {
                    return Combat.getInstance().location("block/biable_still");
                }

                @Override
                public ResourceLocation getFlowingTexture()
                {
                    return Combat.getInstance().location("block/biable_flow");
                }

                @Nullable
                @Override
                public ResourceLocation getOverlayTexture()
                {
                    return Combat.getInstance().location("block/biable_overlay");
                }

                @Override
                public ResourceLocation getRenderOverlayTexture(Minecraft mc)
                {
                    return UNDERWATER_LOCATION;
                }
            });
        }
    };
    
    @Override
    public FluidType getFluidType() {
    	return TYPE;
    }
    
	@Nullable
	@OnlyIn(Dist.CLIENT)
	public ParticleOptions getDripParticleData() {
		return CParticleTypes.DRIPPING_BIABLE;
	}

	@Override
	protected boolean canConvertToSource(Level level) {
		return false;
	}

	@Override
	protected void beforeDestroyingBlock(LevelAccessor worldIn, BlockPos pos, BlockState state) {
		BlockEntity tileentity = state.hasBlockEntity() ? worldIn.getBlockEntity(pos) : null;
		Block.dropResources(state, worldIn, pos, tileentity);
	}

	@Override
	public int getSlopeFindDistance(LevelReader worldIn) {
		return 4;
	}

	@Override
	public BlockState createLegacyBlock(FluidState state) {
		return CBlocks.BIABLE.defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
	}

	@Override
	public boolean isSame(Fluid fluidIn) {
		return fluidIn == CFluids.BIABLE || fluidIn == CFluids.FLOWING_BIABLE;
	}

	@Override
	public int getDropOff(LevelReader worldIn) {
		return 1;
	}

	@Override
	public int getTickDelay(LevelReader p_205569_1_) {
		return 5;
	}

	@Override
	public boolean canBeReplacedWith(FluidState p_215665_1_, BlockGetter p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
		return p_215665_5_ == Direction.DOWN && !p_215665_4_.is(FluidCTags.BIABLE);
	}

	@Override
	protected float getExplosionResistance() {
		return 100.0F;
	}

	public static class Flowing extends BiableFluid {
		@Override
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		@Override
		public int getAmount(FluidState p_207192_1_) {
			return p_207192_1_.getValue(LEVEL);
		}

		@Override
		public boolean isSource(FluidState state) {
			return false;
		}
	}

	public static class Source extends BiableFluid {
		@Override
		public int getAmount(FluidState p_207192_1_) {
			return 8;
		}

		@Override
		public boolean isSource(FluidState state) {
			return true;
		}
	}
}