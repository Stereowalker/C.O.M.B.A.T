package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.AbstractRaySpell;
import com.stereowalker.combat.world.entity.magic.Ray;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class RayRenderer extends EntityRenderer<Ray>{
	public static final ResourceLocation RAY_TEXTURES = Combat.getInstance().location("textures/entity/ray.png");

	protected RayRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_);
	}

	private Vec3 getPosition(Entity entityLivingBaseIn, double p_177110_2_, float p_177110_4_) {
		double d0 = Mth.lerp((double)p_177110_4_, entityLivingBaseIn.xOld, entityLivingBaseIn.getX());
		double d1 = Mth.lerp((double)p_177110_4_, entityLivingBaseIn.yOld, entityLivingBaseIn.getY()) + p_177110_2_;
		double d2 = Mth.lerp((double)p_177110_4_, entityLivingBaseIn.zOld, entityLivingBaseIn.getZ());
		return new Vec3(d0, d1, d2);
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	@Override
	public void render(Ray entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int p_225623_6_) {
		super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, p_225623_6_);
		LivingEntity owner = entity.getOwner();
		if (entity != null) {
			float f1 = (float)owner.level.getGameTime() + partialTicks;
			float f2 = f1 * 0.5F % 1.0F;
			float f3 = owner.getEyeHeight() - 0.2F;
			matrixStackIn.pushPose();
			double x = owner.getX() - entity.getX();
			double y = owner.getY() - entity.getY();
			double z = owner.getZ() - entity.getZ();
			matrixStackIn.translate(x, y + f3, z);
			Vec3 vec3d = this.getPosition(entity, 0.0D, partialTicks);
			Vec3 vec3d1 = this.getPosition(owner, f3, partialTicks);
			Vec3 vec3d2 = vec3d.subtract(vec3d1);
	         float f4 = (float)(vec3d2.length() + 1.0D);
	         vec3d2 = vec3d2.normalize();
	         float f5 = (float)Math.acos(vec3d2.y);
	         float f6 = (float)Math.atan2(vec3d2.z, vec3d2.x);
	         matrixStackIn.mulPose(Vector3f.YP.rotationDegrees((((float)Math.PI / 2F) - f6) * (180F / (float)Math.PI)));
	         matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f5 * (180F / (float)Math.PI)));
	         float f7 = f1 * 0.05F * -1.5F;
	         int j = (int) (entity.getSpell().getSpell().getCategory().getrCOlor()*255); 
	         int k = (int) (entity.getSpell().getSpell().getCategory().getgCOlor()*255);
	         int l = (int) (entity.getSpell().getSpell().getCategory().getbCOlor()*255);
	         float f11 = Mth.cos(f7 + 2.3561945F) * 0.282F;
	         float f12 = Mth.sin(f7 + 2.3561945F) * 0.282F;
	         float f13 = Mth.cos(f7 + ((float)Math.PI / 4F)) * 0.282F;
	         float f14 = Mth.sin(f7 + ((float)Math.PI / 4F)) * 0.282F;
	         float f15 = Mth.cos(f7 + 3.926991F) * 0.282F;
	         float f16 = Mth.sin(f7 + 3.926991F) * 0.282F;
	         float f17 = Mth.cos(f7 + 5.4977875F) * 0.282F;
	         float f18 = Mth.sin(f7 + 5.4977875F) * 0.282F;
	         float f19 = Mth.cos(f7 + (float)Math.PI) * 0.2F;
	         float f20 = Mth.sin(f7 + (float)Math.PI) * 0.2F;
	         float f21 = Mth.cos(f7 + 0.0F) * 0.2F;
	         float f22 = Mth.sin(f7 + 0.0F) * 0.2F;
	         float f23 = Mth.cos(f7 + ((float)Math.PI / 2F)) * 0.2F;
	         float f24 = Mth.sin(f7 + ((float)Math.PI / 2F)) * 0.2F;
	         float f25 = Mth.cos(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
	         float f26 = Mth.sin(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
	         float f29 = -1.0F + f2;
	         float f30 = f4 * 2.5F + f29;
	         VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(((AbstractRaySpell)entity.getSpell().getSpell()).rayTexture()));
	         PoseStack.Pose matrixstack$entry = matrixStackIn.last();
	         Matrix4f matrix4f = matrixstack$entry.pose();
	         Matrix3f matrix3f = matrixstack$entry.normal();
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f30);
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f29);
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f30);
	         float f31 = 0.0F;
	         if (owner.tickCount % 2 == 0) {
	            f31 = 0.5F;
	         }

	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f17, f4, f18, j, k, l, 1.0F, f31);
	         func_229108_a_(ivertexbuilder, matrix4f, matrix3f, f15, f4, f16, j, k, l, 0.5F, f31);
	         matrixStackIn.popPose();
		}
	}

	private static void func_229108_a_(VertexConsumer p_229108_0_, Matrix4f p_229108_1_, Matrix3f p_229108_2_, float p_229108_3_, float p_229108_4_, float p_229108_5_, int p_229108_6_, int p_229108_7_, int p_229108_8_, float p_229108_9_, float p_229108_10_) {
		p_229108_0_.vertex(p_229108_1_, p_229108_3_, p_229108_4_, p_229108_5_).color(p_229108_6_, p_229108_7_, p_229108_8_, 255).uv(p_229108_9_, p_229108_10_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_229108_2_, 0.0F, 1.0F, 0.0F).endVertex();
	}


	@Override
	public ResourceLocation getTextureLocation(Ray entity) {
		return RAY_TEXTURES;
	}

}
