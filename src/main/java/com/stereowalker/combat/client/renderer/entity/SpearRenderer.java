package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.SpearModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.projectile.ThrownSpear;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpearRenderer extends EntityRenderer<ThrownSpear> {
	public static final ResourceLocation SPEAR = Combat.getInstance().location("textures/entity/projectiles/spear.png");
	private final SpearModel spearModel;

	public SpearRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_);
		this.spearModel = new SpearModel(p_173964_.bakeLayer(CModelLayers.SPEAR));
	}

	@Override
	public void render(ThrownSpear entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource typeBuffer, int packedLight) {
		matrixStack.pushPose();
		matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
		matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 90.0F));
		VertexConsumer ivertexbuilder = net.minecraft.client.renderer.entity.ItemRenderer.getFoilBuffer(typeBuffer, this.spearModel.renderType(this.getTextureLocation(entity)), false, entity.isFoil());
		this.spearModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.popPose();
		super.render(entity, entityYaw, partialTicks, matrixStack, typeBuffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(ThrownSpear entity) {
		return SPEAR;
	}
}