package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.MeteorModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.misc.Meteor;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MeteorRenderer extends EntityRenderer<Meteor>{
	public static final ResourceLocation textures = Combat.getInstance().location("textures/entity/meteor.png");
	private final MeteorModel meteorModel;

	protected MeteorRenderer(EntityRendererProvider.Context p_174420_) {
		super(p_174420_);
		this.meteorModel = new MeteorModel(p_174420_.bakeLayer(CModelLayers.METEOR));
	}

	@Override
	public void render(Meteor entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource typeBuffer, int p_225623_6_) {
		matrixStack.pushPose();
		matrixStack.translate(0.0D, 0.015D, 0.0D);
		matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
		matrixStack.scale(entity.getMeteorSize().getSize(), entity.getMeteorSize().getSize(), entity.getMeteorSize().getSize());
		matrixStack.mulPose(Axis.YP.rotationDegrees(90.0F));
		VertexConsumer ivertexbuilder = typeBuffer.getBuffer(this.meteorModel.renderType(this.getTextureLocation(entity)));
		this.meteorModel.renderToBuffer(matrixStack, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.popPose();
		super.render(entity, entityYaw, partialTicks, matrixStack, typeBuffer, p_225623_6_);
	}

	@Override
	public ResourceLocation getTextureLocation(Meteor entity) {
		return textures;
	}

}
