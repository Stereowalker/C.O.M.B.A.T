package com.stereowalker.combat.client.renderer.entity.model;
//Made with Blockbench
//Paste this code into your mod.

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.entity.monster.BiogEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BiogModel<T extends BiogEntity> extends EntityModel<T> {
	private final ModelRenderer Body;
	private final ModelRenderer Neck;
	private final ModelRenderer Head;
	private final ModelRenderer Pincer1;
	private final ModelRenderer Pincer2;
	private final ModelRenderer FrontLeftLeg;
	private final ModelRenderer FrontRightLeg;
	private final ModelRenderer BackLeftLeg;
	private final ModelRenderer BackRightLeg;

	public BiogModel() {
		super(RenderType::getEntityTranslucent);
		textureWidth = 64;
		textureHeight = 64;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 18.0F, 0.0F);
		Body.setTextureOffset(0, 8).addBox(-3.0F, -3.0F, -5.0F, 6, 4, 9, 0.0F, false);

		Neck = new ModelRenderer(this);
		Neck.setRotationPoint(0.0F, -2.0F, -2.0F);
		setRotationAngle(Neck, 0.4363F, 0.0F, 0.0F);
		Body.addChild(Neck);
		Neck.setTextureOffset(31, 9).addBox(-1.0F, -9.9043F, -2.5683F, 2, 9, 3, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, -7.4043F, -0.735F);
		setRotationAngle(Head, -0.3491F, 0.0F, 0.0F);
		Neck.addChild(Head);
		Head.setTextureOffset(9, 0).addBox(-2.0F, -3.5F, -2.8333F, 4, 4, 3, 0.0F, false);

		Pincer1 = new ModelRenderer(this);
		Pincer1.setRotationPoint(-1.5F, -1.0F, -2.3333F);
		setRotationAngle(Pincer1, 0.0F, -0.4363F, 0.0F);
		Head.addChild(Pincer1);
		Pincer1.setTextureOffset(24, 5).addBox(-7.5F, -0.5F, -0.5F, 8, 1, 1, 0.0F, false);
		Pincer1.setTextureOffset(42, 12).addBox(-8.5F, -0.5F, -7.5F, 1, 1, 8, 0.0F, false);
		Pincer1.setTextureOffset(7, 23).addBox(-8.0F, 0.0F, -7.0F, 8, 0, 7, 0.0F, false);

		Pincer2 = new ModelRenderer(this);
		Pincer2.setRotationPoint(1.5F, -1.0F, -2.3333F);
		setRotationAngle(Pincer2, 0.0F, -2.7053F, 0.0F);
		Head.addChild(Pincer2);
		Pincer2.setTextureOffset(24, 5).addBox(-7.5F, -0.5F, -0.5F, 8, 1, 1, 0.0F, false);
		Pincer2.setTextureOffset(42, 12).addBox(-8.5F, -0.5F, -0.5F, 1, 1, 8, 0.0F, false);
		Pincer2.setTextureOffset(7, 31).addBox(-8.0F, 0.0F, 0.0F, 8, 0, 7, 0.0F, false);

		FrontLeftLeg = new ModelRenderer(this);
		FrontLeftLeg.setRotationPoint(2.0F, 1.0F, -4.0F);
		Body.addChild(FrontLeftLeg);
		FrontLeftLeg.setTextureOffset(0, 0).addBox(-1.0F, 0.0017F, -0.9983F, 2, 5, 2, 0.0F, false);

		FrontRightLeg = new ModelRenderer(this);
		FrontRightLeg.setRotationPoint(-2.0F, 1.0F, -4.0F);
		Body.addChild(FrontRightLeg);
		FrontRightLeg.setTextureOffset(0, 0).addBox(-1.0F, -0.0017F, -1.0017F, 2, 5, 2, 0.0F, false);

		BackLeftLeg = new ModelRenderer(this);
		BackLeftLeg.setRotationPoint(2.0F, 1.0F, 3.0F);
		Body.addChild(BackLeftLeg);
		BackLeftLeg.setTextureOffset(0, 0).addBox(-1.0017F, 0.0035F, -0.9983F, 2, 5, 2, 0.0F, false);

		BackRightLeg = new ModelRenderer(this);
		BackRightLeg.setRotationPoint(-2.0F, 1.0F, 3.0F);
		Body.addChild(BackRightLeg);
		BackRightLeg.setTextureOffset(0, 0).addBox(-1.0017F, 0.0F, -1.0017F, 2, 5, 2, 0.0F, false);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
	
	
	
	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.Head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.BackRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.BackLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.FrontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.FrontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		if (entityIn.isAggressive()) {
			this.Pincer1.rotateAngleY = /*-0.9599F*/-0.4363F + ((MathHelper.cos(limbSwing * 0.6662F)/2)+0.5F)*-0.5236F;
			this.Pincer2.rotateAngleY = /*-2.1817F*/-2.7053F + ((MathHelper.cos(limbSwing * 0.6662F)/2)+0.5F)*0.5236F;
		}
		else {
			this.Pincer1.rotateAngleY = -0.4363F;
			this.Pincer2.rotateAngleY = -2.7053F;
		}
	}
}