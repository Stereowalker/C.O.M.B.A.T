package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.ShurikenModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.projectile.Shuriken;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShurikenRenderer extends EntityRenderer<Shuriken> {
	private static final ResourceLocation SHURIKEN_TEXTURE = Combat.getInstance().location("textures/item/shuriken.png");
	private final ShurikenModel shurikenModel;

	public ShurikenRenderer(EntityRendererProvider.Context p_174420_) {
		super(p_174420_);
		this.shurikenModel = new ShurikenModel(p_174420_.bakeLayer(CModelLayers.SHURIKEN));
	}

	@Override
	public void render(Shuriken entity, float entityYaw, float p_225623_3_, PoseStack matrixStack, MultiBufferSource typeBuffer, int packedLight) {
		matrixStack.pushPose();
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(p_225623_3_, entity.yRotO, entity.getYRot()) - 90.0F));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(p_225623_3_, entity.xRotO, entity.getXRot()) + 180.0F));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		VertexConsumer ivertexbuilder = net.minecraft.client.renderer.entity.ItemRenderer.getFoilBuffer(typeBuffer, this.shurikenModel.renderType(this.getTextureLocation(entity)), false, false);
		this.shurikenModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.popPose();
		super.render(entity, entityYaw, p_225623_3_, matrixStack, typeBuffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(Shuriken entity) {
		return SHURIKEN_TEXTURE;
	}
}