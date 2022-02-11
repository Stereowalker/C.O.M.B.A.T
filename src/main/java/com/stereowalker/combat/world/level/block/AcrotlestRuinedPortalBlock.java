package com.stereowalker.combat.world.level.block;

import java.util.Random;

import com.stereowalker.combat.core.particles.CParticleTypes;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.level.dimension.DImensionalTeleportManager;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AcrotlestRuinedPortalBlock extends Block {

	public AcrotlestRuinedPortalBlock(Block.Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		if (worldIn.dimensionType().natural() && worldIn.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && random.nextInt(2000) < worldIn.getDifficulty().getId()) {
			while(worldIn.getBlockState(pos).getBlock() == this) {
				pos = pos.below();
			}
			//TODO: Change what spawns from portals
			if (worldIn.getBlockState(pos).isValidSpawn(worldIn, pos, CEntityType.RED_BIOG)) {
				Entity entity =  CEntityType.RED_BIOG.spawn(worldIn, (CompoundTag)null, (Component)null, (Player)null, pos.above(), MobSpawnType.STRUCTURE, false, false);
				if (entity != null) {
					entity.setPortalCooldown();
				}
			}
		}
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof LivingEntity) {
			if (!entityIn.isPassenger() && !entityIn.isVehicle() && entityIn.canChangeDimensions() && entityIn instanceof ServerPlayer) {
				DImensionalTeleportManager.teleportToAcrotlestViaRuins((ServerPlayer) entityIn);
			}
		}

	}

	//TODO: Use custom portal sounds
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(100) == 0) {
			worldIn.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for(int i = 0; i < 4; ++i) {
			double d0 = (double)((float)pos.getX() + rand.nextFloat());
			double d1 = (double)((float)pos.getY() + rand.nextFloat());
			double d2 = (double)((float)pos.getZ() + rand.nextFloat());
			double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			int j = rand.nextInt(2) * 2 - 1;
			if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this) {
				d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
				d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
			} else {
				d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
				d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
			}

			worldIn.addParticle(CParticleTypes.ACROTLEST_PORTAL, d0, d1, d2, d3, d4, d5);
		}

	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
}