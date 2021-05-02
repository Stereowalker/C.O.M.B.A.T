package com.stereowalker.combat.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ThrowableItemModel extends Model {
	private final ModelRenderer model;

	public ThrowableItemModel() {
		super(RenderType::getEntityTranslucent);
		textureWidth = 64;
		textureHeight = 64;

		model = new ModelRenderer(this, 0, 0);
		model.mirror = false;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				model.addBox("modeled", -8.0F + i, -0.5F, -8.0F + j, 1, 1, 1, 0, i*4, j*4);
			}
		}
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

	}

}
