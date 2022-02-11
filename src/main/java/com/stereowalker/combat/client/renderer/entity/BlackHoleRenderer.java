package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.BlackHoleModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.misc.BlackHole;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BlackHoleRenderer extends EntityRenderer<BlackHole> {
	public static final ResourceLocation textures = Combat.getInstance().location("textures/entity/black_hole.png");
	private final BlackHoleModel blackHoleModel;

	protected BlackHoleRenderer(EntityRendererProvider.Context p_174420_) {
		super(p_174420_);
		this.blackHoleModel = new BlackHoleModel(p_174420_.bakeLayer(CModelLayers.BLACK_HOLE));
	}

	@Override
	public void render(BlackHole entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource typeBuffer, int p_225623_6_) {
		matrixStack.pushPose();
		matrixStack.translate(0.0D, 0.015D, 0.0D);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		VertexConsumer ivertexbuilder = typeBuffer.getBuffer(this.blackHoleModel.renderType(this.getTextureLocation(entity)));
		this.blackHoleModel.renderToBuffer(matrixStack, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.popPose();
		super.render(entity, entityYaw, partialTicks, matrixStack, typeBuffer, p_225623_6_);
	}

	@Override
	public ResourceLocation getTextureLocation(BlackHole entity) {
		return textures;
	}
}
