package com.stereowalker.combat.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.stereowalker.combat.block.CBlocks;

import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends AgeableEntity{

	protected AnimalEntityMixin(EntityType<? extends AgeableEntity> type, World worldIn) {
		super(type, worldIn);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 16.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0F);
	}

	/**
	 * Static predicate for determining whether or not an animal can spawn at the provided location.
	 *  
	 * @param animal The animal entity to be spawned
	 * @author Stereowalker
	 * @reason To make elycen spawnable
	 */
	@Overwrite
	public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
		return (worldIn.getBlockState(pos.down()).matchesBlock(Blocks.GRASS_BLOCK) || worldIn.getBlockState(pos.down()).matchesBlock(CBlocks.ELYCEN_BLOCK)) && worldIn.getLightSubtracted(pos, 0) > 8;
	}
}
