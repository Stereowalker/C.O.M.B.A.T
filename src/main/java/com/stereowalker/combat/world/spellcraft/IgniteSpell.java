package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.AbstractBlockSpell;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class IgniteSpell extends AbstractBlockSpell {

	public IgniteSpell(SpellCategory category, Rank tier, float cost, int cooldown, int castTime) {
		super(category, tier, cost, cooldown, castTime);
	}

	@Override
	public Component getFailedMessage(LivingEntity caster) {
		return new TranslatableComponent("Unable to Set fire to block");
	}

	@Override
	public InteractionResult blockProgram(LivingEntity caster, UseOnContext context, double strength) {
		Level iworld = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = iworld.getBlockState(blockpos);
		if (CampfireBlock.canLight(blockstate)) {
			iworld.setBlock(blockpos, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
			return InteractionResult.sidedSuccess(iworld.isClientSide());
		} else {
			BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
			if (BaseFireBlock.canBePlacedAt(iworld, blockpos1, context.getHorizontalDirection())) {
				BlockState blockstate1 = BaseFireBlock.getState(iworld, blockpos1);
				iworld.setBlock(blockpos1, blockstate1, 11);
				return InteractionResult.sidedSuccess(iworld.isClientSide());
			} else {
				return InteractionResult.FAIL;
			}
		}
	}

}
