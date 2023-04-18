package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stereowalker.combat.world.entity.projectile.ThrownDagger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThrownDaggerRenderer extends EntityRenderer<ThrownDagger> {

	public ThrownDaggerRenderer(EntityRendererProvider.Context p_174420_) {
		super(p_174420_);
	}

	@Override
	public void render(ThrownDagger entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) + 180.0F));
		matrixStackIn.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot()) - 90.0F));
		matrixStackIn.mulPose(Axis.YN.rotationDegrees(90.0F));
		matrixStackIn.mulPose(Axis.ZN.rotationDegrees(-45.0F));
		matrixStackIn.scale(1.5F, 1.5F, 1.5F);
		Minecraft.getInstance().getItemRenderer().renderStatic(entityIn.getItem(), ItemTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, 0);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(ThrownDagger entity) {
		return null;
	}
}