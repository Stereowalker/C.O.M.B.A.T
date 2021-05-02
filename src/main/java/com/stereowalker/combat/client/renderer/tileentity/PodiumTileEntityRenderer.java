package com.stereowalker.combat.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stereowalker.combat.tileentity.PodiumTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PodiumTileEntityRenderer extends TileEntityRenderer<PodiumTileEntity> {
	/** The texture for the book above the enchantment table. */
	public PodiumTileEntityRenderer(TileEntityRendererDispatcher p_i226010_1_) {
		super(p_i226010_1_);
	}

	public void render(PodiumTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		ItemStack itemstack = tileEntityIn.getItem();
		if (itemstack != ItemStack.EMPTY) {
			matrixStackIn.push();
			matrixStackIn.translate(0.5D, 1.0D, 0.5D);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(tileEntityIn.rotation));
			Minecraft.getInstance().getItemRenderer().renderItem(itemstack, ItemCameraTransforms.TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
			matrixStackIn.pop();
		}
	}
}