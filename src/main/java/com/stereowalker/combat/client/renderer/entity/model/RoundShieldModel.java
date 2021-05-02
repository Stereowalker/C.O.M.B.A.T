package com.stereowalker.combat.client.renderer.entity.model;
//Made with Blockbench
//Paste this code into your mod.

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RoundShieldModel extends Model {
	private final ModelRenderer plate;
	private final ModelRenderer handle;

	public RoundShieldModel() {
		super(RenderType::getEntityCutout);
		textureWidth = 64;
		textureHeight = 64;

		plate = new ModelRenderer(this, 0, 35);
		plate.setRotationPoint(0.0F, 24.0F, 0.0F);
		plate.addBox(-6.0F, -6.0F, -2.0F, 12, 12, 1, 0.0F, false);
		plate.mirror = false;
		
		plate.addBox("1", -5.0F, -7.0F, -2.0F, 10, 14, 1, 0.0F, 42, 17);
		plate.addBox("2", -7.0F, -5.0F, -2.0F, 14, 10, 1, 0.0F, 0, 24);
		plate.addBox("3", -3.0F, -8.0F, -2.0F, 6, 16, 1, 0.0F, 50, 0);
		plate.addBox("4", -8.0F, -3.0F, -2.0F, 16, 6, 1, 0.0F, 0, 17);
		plate.addBox("5", -8.0F, -8.0F, -2.0F, 16, 16, 1, 0.0F, 0, 0);
		
		handle = new ModelRenderer(this, 34, 0);
		handle.setRotationPoint(0.0F, 24.0F, 0.0F);
		handle.addBox(-1.0F, -3.0F, -1.0F, 2, 6, 6, 0.0F, false);
	}

	public ModelRenderer func_228293_a_() {
		return this.plate;
	}

	public ModelRenderer func_228294_b_() {
		return this.handle;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.plate.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		this.handle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}