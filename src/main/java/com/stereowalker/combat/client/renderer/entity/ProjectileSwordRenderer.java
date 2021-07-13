package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stereowalker.combat.entity.projectile.ProjectileSwordEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ProjectileSwordRenderer extends EntityRenderer<ProjectileSwordEntity> {
	public ProjectileSwordRenderer(EntityRendererManager manager) {
		super(manager);
	}
	
	@Override
	public void render(ProjectileSwordEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) + 180.0F));
		matrixStackIn.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) - 90.0F));
		matrixStackIn.rotate(Vector3f.YN.rotationDegrees(90.0F));
		matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(-45.0F));
		matrixStackIn.scale(1.5F, 1.5F, 1.5F);
		Minecraft.getInstance().getItemRenderer().renderItem(entityIn.getSword(), ItemCameraTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public ResourceLocation getEntityTexture(ProjectileSwordEntity entity) {
		return null;
	}
}
