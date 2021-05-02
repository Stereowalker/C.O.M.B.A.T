package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.entity.model.ShurikenModel;
import com.stereowalker.combat.entity.projectile.ShurikenEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShurikenRenderer extends EntityRenderer<ShurikenEntity> {
	private static final ResourceLocation SHURIKEN_TEXTURE = Combat.getInstance().location("textures/item/shuriken.png");
	private final ShurikenModel shurikenModel = new ShurikenModel();

	public ShurikenRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void render(ShurikenEntity entity, float entityYaw, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer typeBuffer, int packedLight) {
		matrixStack.push();
		matrixStack.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(p_225623_3_, entity.prevRotationYaw, entity.rotationYaw) - 90.0F));
		matrixStack.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(p_225623_3_, entity.prevRotationPitch, entity.rotationPitch) + 180.0F));
		matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		IVertexBuilder ivertexbuilder = net.minecraft.client.renderer.ItemRenderer.getBuffer(typeBuffer, this.shurikenModel.getRenderType(this.getEntityTexture(entity)), false, false);
		this.shurikenModel.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.pop();
		super.render(entity, entityYaw, p_225623_3_, matrixStack, typeBuffer, packedLight);
	}

	public ResourceLocation getEntityTexture(ShurikenEntity entity) {
		return SHURIKEN_TEXTURE;
	}
}