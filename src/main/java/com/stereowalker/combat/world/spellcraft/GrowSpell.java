package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.AbstractBlockSpell;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GrowSpell extends AbstractBlockSpell {

	protected GrowSpell(SpellCategory category, Rank tier, float cost, int cooldown, int castTime) {
		super(category, tier, cost, cooldown, castTime);
	}

	@Override
	public InteractionResult blockProgram(LivingEntity caster, UseOnContext context, double strength) {
		Level world = caster.level;
		BlockPos blockpos = context.getClickedPos();
		BlockPos blockpos1 = blockpos.relative(context.getClickedFace() );
		if (BoneMealItem.applyBonemeal(ItemStack.EMPTY, world, blockpos, (Player)caster)) {
			if (!world.isClientSide) {
				world.levelEvent(1505, blockpos, 0);
			}
			return InteractionResult.sidedSuccess(world.isClientSide);

		} else {
			BlockState blockstate = world.getBlockState(blockpos);
			boolean flag = blockstate.isFaceSturdy(world, blockpos, context.getClickedFace());
			if (flag && BoneMealItem.growWaterPlant(ItemStack.EMPTY, world, blockpos1, context.getClickedFace())) {
				if (!world.isClientSide) {
					world.levelEvent(1505, blockpos1, 0);
				}
				return InteractionResult.sidedSuccess(world.isClientSide);
			}
		}
		return InteractionResult.PASS;
	}
}
