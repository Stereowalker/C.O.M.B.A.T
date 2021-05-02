package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.entity.model.MeteorModel;
import com.stereowalker.combat.entity.item.MeteorEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class MeteorRenderer extends EntityRenderer<MeteorEntity>{
	public static final ResourceLocation textures = Combat.getInstance().location("textures/entity/meteor.png");
	private final MeteorModel meteorModel = new MeteorModel();

	protected MeteorRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(MeteorEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer typeBuffer, int p_225623_6_) {
		matrixStack.push();
		matrixStack.translate(0.0D, 0.015D, 0.0D);
		matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		matrixStack.scale(entity.getMeteorSize().getSize(), entity.getMeteorSize().getSize(), entity.getMeteorSize().getSize());
		matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0F));
		IVertexBuilder ivertexbuilder = typeBuffer.getBuffer(this.meteorModel.getRenderType(this.getEntityTexture(entity)));
		this.meteorModel.render(matrixStack, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.pop();
		super.render(entity, entityYaw, partialTicks, matrixStack, typeBuffer, p_225623_6_);
	}

	@Override
	public ResourceLocation getEntityTexture(MeteorEntity entity) {
		return textures;
	}

}
