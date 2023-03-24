package com.stereowalker.combat.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.block.PyraniteFireBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin extends Block {

	public BaseFireBlockMixin(Properties p_49795_) {
		super(p_49795_);
	}

	@Inject(method = "getState", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/BlockGetter;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void getState_inject(BlockGetter pReader, BlockPos pPos, CallbackInfoReturnable<BlockState> cir, BlockPos blockpos, BlockState blockstate) {
		if (PyraniteFireBlock.canSurviveOnBlock(blockstate)) {
			cir.setReturnValue(((PyraniteFireBlock)CBlocks.PYRANITE_FIRE).getStateForPlacement(pReader, pPos));
		}
	}

}
