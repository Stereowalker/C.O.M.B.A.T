package com.stereowalker.combat.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stereowalker.combat.world.level.block.entity.PodiumBlockEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PodiumRenderer implements BlockEntityRenderer<PodiumBlockEntity> {
	public PodiumRenderer(BlockEntityRendererProvider.Context pContext) {
	}

	@Override
	public void render(PodiumBlockEntity pBlockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		ItemStack itemstack = pBlockEntity.getItem();
		int i = (int)pBlockEntity.getBlockPos().asLong();
		if (itemstack != ItemStack.EMPTY) {
			matrixStackIn.pushPose();
			matrixStackIn.translate(0.5D, 1.0D, 0.5D);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(pBlockEntity.rotation));
			Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemTransforms.TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, i);
			matrixStackIn.popPose();
		}
	}
}