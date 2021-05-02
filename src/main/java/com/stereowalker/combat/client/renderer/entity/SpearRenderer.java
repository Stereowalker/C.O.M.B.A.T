package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.entity.model.SpearModel;
import com.stereowalker.combat.entity.projectile.SpearEntity;

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
public class SpearRenderer extends EntityRenderer<SpearEntity> {
	public static final ResourceLocation SPEAR = Combat.getInstance().location("textures/entity/projectiles/spear.png");
	   private final SpearModel spearModel = new SpearModel();

	   public SpearRenderer(EntityRendererManager renderManagerIn) {
	      super(renderManagerIn);
	   }

	   @Override
	   public void render(SpearEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer typeBuffer, int packedLight) {
	      matrixStack.push();
	      matrixStack.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entity.prevRotationYaw, entity.rotationYaw) - 90.0F));
	      matrixStack.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch) + 90.0F));
	      IVertexBuilder ivertexbuilder = net.minecraft.client.renderer.ItemRenderer.getBuffer(typeBuffer, this.spearModel.getRenderType(this.getEntityTexture(entity)), false, entity.func_226572_w_());
	      this.spearModel.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	      matrixStack.pop();
	      super.render(entity, entityYaw, partialTicks, matrixStack, typeBuffer, packedLight);
	   }

	   public ResourceLocation getEntityTexture(SpearEntity entity) {
	      return SPEAR;
	   }
}