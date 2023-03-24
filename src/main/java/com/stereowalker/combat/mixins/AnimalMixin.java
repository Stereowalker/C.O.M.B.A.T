package com.stereowalker.combat.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

@Mixin(Animal.class)
public abstract class AnimalMixin extends AgeableMob {

	protected AnimalMixin(EntityType<? extends AgeableMob> type, Level worldIn) {
		super(type, worldIn);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
	}

	/**
	 * Static predicate for determining whether or not an animal can spawn at the provided location.
	 *  
	 * @param animal The animal entity to be spawned
	 * @author Stereowalker
	 * @reason To make elycen spawnable
	 */
	@Overwrite
	public static boolean checkAnimalSpawnRules(EntityType<? extends Animal> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random random) {
		return (worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) || worldIn.getBlockState(pos.below()).is(CBlocks.ELYCEN_BLOCK)) && worldIn.getRawBrightness(pos, 0) > 8;
	}
}
