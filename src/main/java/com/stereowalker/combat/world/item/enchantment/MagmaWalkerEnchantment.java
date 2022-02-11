package com.stereowalker.combat.world.item.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;

public class MagmaWalkerEnchantment extends Enchantment{

	public MagmaWalkerEnchantment(Rarity rarityIn, EquipmentSlot[] slots) {
		super(rarityIn, EnchantmentCategory.ARMOR_FEET, slots);
	}

	@Override
	public int getMinCost(int enchantmentLevel) 
	{
		return 20 * enchantmentLevel;
	}

	@Override
	public int getMaxCost(int enchantmentLevel)
	{
		return this.getMinCost(enchantmentLevel) + 10;
	}

	@Override
	public int getMaxLevel()
	{
		return 5;
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	public static void onEntityMoved(LivingEntity pLiving, Level pLevel, BlockPos pPos, int pLevelConflicting) 
	{
		if (pLiving.isOnGround()) {
			BlockState blockstate = Blocks.MAGMA_BLOCK.defaultBlockState();
			float f = (float)Math.min(16, 2 + pLevelConflicting);
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for(BlockPos blockpos : BlockPos.betweenClosed(pPos.offset((double)(-f), -1.0D, (double)(-f)), pPos.offset((double)f, -1.0D, (double)f))) {
				if (blockpos.closerThan(pLiving.position(), (double)f)) {
					blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
					BlockState blockstate1 = pLevel.getBlockState(blockpos$mutableblockpos);
					if (blockstate1.isAir()) {
						BlockState blockstate2 = pLevel.getBlockState(blockpos);
						boolean isFull = blockstate2.getBlock() == Blocks.LAVA && blockstate2.getValue(LiquidBlock.LEVEL) == 0; //TODO: Forge, modded lava?
						if (blockstate2.getMaterial() == Material.LAVA && isFull && blockstate.canSurvive(pLevel, blockpos) && pLevel.isUnobstructed(blockstate, blockpos, CollisionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(pLiving, net.minecraftforge.common.util.BlockSnapshot.create(pLevel.dimension(), pLevel, blockpos), Direction.UP)) {
							pLevel.setBlockAndUpdate(blockpos, blockstate);
							pLevel.getBlockTicks().scheduleTick(blockpos, Blocks.MAGMA_BLOCK, Mth.nextInt(pLiving.getRandom(), 60, 120));
						}
					}
				}
			}
		}
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) 
	{
		return super.checkCompatibility(ench) && ench != Enchantments.FROST_WALKER && ench != Enchantments.DEPTH_STRIDER;
	}

}
