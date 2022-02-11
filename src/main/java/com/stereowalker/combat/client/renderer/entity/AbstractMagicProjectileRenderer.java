package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.stereowalker.combat.world.entity.projectile.AbstractMagicProjectile;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractMagicProjectileRenderer<T extends AbstractMagicProjectile> extends EntityRenderer<T> {
	public AbstractMagicProjectileRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_);
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		float f9 = (float)entityIn.arrowShake - partialTicks;
		if (f9 > 0.0F) {
			float f10 = -Mth.sin(f9 * 3.0F) * f9;
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f10));
		}

		matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(45.0F));
		matrixStackIn.scale(0.05625F, 0.05625F, 0.05625F);
		matrixStackIn.translate(-4.0D, 0.0D, 0.0D);
		VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		PoseStack.Pose matrixstack$entry = matrixStackIn.last();
		Matrix4f matrix4f = matrixstack$entry.pose();
		Matrix3f matrix3f = matrixstack$entry.normal();
		this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLightIn);
		this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLightIn);
		this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLightIn);
		this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLightIn);
		this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLightIn);
		this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLightIn);
		this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLightIn);
		this.vertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLightIn);

		for(int j = 0; j < 4; ++j) {
			matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
			this.vertex(matrix4f, matrix3f, ivertexbuilder, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
			this.vertex(matrix4f, matrix3f, ivertexbuilder, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLightIn);
			this.vertex(matrix4f, matrix3f, ivertexbuilder, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLightIn);
			this.vertex(matrix4f, matrix3f, ivertexbuilder, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLightIn);
		}

		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public void vertex(Matrix4f p_113826_, Matrix3f p_113827_, VertexConsumer p_113828_, int p_113829_, int p_113830_, int p_113831_, float p_113832_, float p_113833_, int p_113834_, int p_113835_, int p_113836_, int p_113837_) {
		p_113828_.vertex(p_113826_, (float)p_113829_, (float)p_113830_, (float)p_113831_).color(255, 255, 255, 255).uv(p_113832_, p_113833_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_113837_).normal(p_113827_, (float)p_113834_, (float)p_113836_, (float)p_113835_).endVertex();
	}
}