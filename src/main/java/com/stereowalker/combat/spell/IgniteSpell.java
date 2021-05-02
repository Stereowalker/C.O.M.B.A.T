package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.AbstractBlockSpell;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.SpellCategory;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class IgniteSpell extends AbstractBlockSpell {

	public IgniteSpell(SpellCategory category, Rank tier, float cost, int cooldown, int castTime) {
		super(category, tier, cost, cooldown, castTime);
	}

	@Override
	public ITextComponent getFailedMessage(LivingEntity caster) {
		return new TranslationTextComponent("Unable to Set fire to block");
	}

	@Override
	public ActionResultType blockProgram(LivingEntity caster, ItemUseContext context, double strength) {
		World iworld = context.getWorld();
		BlockPos blockpos = context.getPos();
		BlockState blockstate = iworld.getBlockState(blockpos);
		if (CampfireBlock.canBeLit(blockstate)) {
			iworld.setBlockState(blockpos, blockstate.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
			return ActionResultType.func_233537_a_(iworld.isRemote());
		} else {
			BlockPos blockpos1 = blockpos.offset(context.getFace());
			if (AbstractFireBlock.canLightBlock(iworld, blockpos1, context.getPlacementHorizontalFacing())) {
				BlockState blockstate1 = AbstractFireBlock.getFireForPlacement(iworld, blockpos1);
				iworld.setBlockState(blockpos1, blockstate1, 11);
				return ActionResultType.func_233537_a_(iworld.isRemote());
			} else {
				return ActionResultType.FAIL;
			}
		}
	}

}
