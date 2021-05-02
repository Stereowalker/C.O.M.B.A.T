package com.stereowalker.combat.client.renderer.entity.model;
//Made with Blockbench
//Paste this code into your mod.

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.entity.monster.LichuEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LichuModel<T extends LichuEntity> extends EntityModel<T> {
	private final ModelRenderer Entity;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer left_leg;
	private final ModelRenderer right_leg;
	private final ModelRenderer left_arm;
	private final ModelRenderer right_arm;

	public LichuModel() {
		textureWidth = 64;
		textureHeight = 64;

		Entity = new ModelRenderer(this);
		Entity.setRotationPoint(0.0F, 24.0F, 0.0F);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -12.0F, 0.0F);
		Entity.addChild(body);
		body.setTextureOffset(0, 20).addBox(-4.0F, -22.0F, -2.0F, 8, 18, 4, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -15.5F, -1.0F);
		body.addChild(head);
		head.setTextureOffset(0, 0).addBox(-3.0F, -3.5F, -4.5F, 6, 6, 6, 0.0F, false);

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(1.0F, -4.0F, 0.0F);
		body.addChild(left_leg);
		left_leg.setTextureOffset(26, 14).addBox(-1.0F, 0.0F, -2.0F, 4, 16, 4, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(-1.0F, -4.0F, 0.0F);
		body.addChild(right_leg);
		right_leg.setTextureOffset(44, 14).addBox(-3.0F, 0.0F, -2.0F, 4, 16, 4, 0.0F, false);

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(5.0F, -21.0F, 0.0F);
		body.addChild(left_arm);
		left_arm.setTextureOffset(18, 44).addBox(-1.0F, -1.0F, -2.0F, 4, 14, 4, 0.0F, false);

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(-5.0F, -21.0F, 0.0F);
		body.addChild(right_arm);
		right_arm.setTextureOffset(0, 22).addBox(-3.0F, -1.0F, -2.0F, 4, 14, 4, 0.0F, false);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.Entity.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
	
	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.right_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.left_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}
}