package com.stereowalker.combat.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.stereowalker.combat.world.level.block.entity.ConnectorBlockEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConnectorRenderer implements BlockEntityRenderer<ConnectorBlockEntity> {
	public ConnectorRenderer(BlockEntityRendererProvider.Context pContext) {
	}

	@Override
	public void render(ConnectorBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (tileEntityIn.isConnected()) {
			if (tileEntityIn.getLevel().getBlockEntity(tileEntityIn.getConnection()) instanceof ConnectorBlockEntity) {
				ConnectorBlockEntity connector = (ConnectorBlockEntity)tileEntityIn.getLevel().getBlockEntity(tileEntityIn.getConnection());
				renderLeash(tileEntityIn, partialTicks, matrixStackIn, bufferIn, connector);
			}
		}
	}

	private  void renderLeash(ConnectorBlockEntity pEntityLiving, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, ConnectorBlockEntity pLeashHolder) {
		pMatrixStack.pushPose();
		Vec3 vec3 = pLeashHolder.getRopeHoldPosition();
		double d0 = (double) (/* Mth.lerp(pPartialTicks, pEntityLiving.yBodyRot, pEntityLiving.yBodyRotO) * */((float)Math.PI / 180F)) + (Math.PI / 2D);
		Vec3 vec31 = pEntityLiving.getLeashOffset();
		double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
		double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
		double d3 = ((double)pEntityLiving.getBlockPos().getX()) + d1;
		double d4 = ((double)pEntityLiving.getBlockPos().getY()) + vec31.y;
		double d5 = ((double)pEntityLiving.getBlockPos().getZ()) + d2;
		pMatrixStack.translate(d1, vec31.y, d2);
		float f = (float)(vec3.x - d3);
		float f1 = (float)(vec3.y - d4);
		float f2 = (float)(vec3.z - d5);
		float f3 = 0.025F;
		VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.leash());
		Matrix4f matrix4f = pMatrixStack.last().pose();
		float f4 = Mth.fastInvSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
		float f5 = f2 * f4;
		float f6 = f * f4;
		BlockPos blockpos = new BlockPos(pEntityLiving.getEyePosition(pPartialTicks));
		BlockPos blockpos1 = new BlockPos(pLeashHolder.getEyePosition(pPartialTicks));
		int i = this.getBlockLightLevel(pEntityLiving, blockpos);
		int j = this.getBlockLightLevel(pLeashHolder, blockpos1);
		int k = pEntityLiving.getLevel().getBrightness(LightLayer.SKY, blockpos);
		int l = pEntityLiving.getLevel().getBrightness(LightLayer.SKY, blockpos1);

		for(int i1 = 0; i1 <= 24; ++i1) {
			MobRenderer.addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
		}

		for(int j1 = 24; j1 >= 0; --j1) {
			MobRenderer.addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
		}

		pMatrixStack.popPose();
	}

	   protected int getBlockLightLevel(ConnectorBlockEntity pEntity, BlockPos pPos) {
	      return pEntity.getLevel().getBrightness(LightLayer.BLOCK, pPos);
	   }
}