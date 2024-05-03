package com.stereowalker.combat.client.renderer.blockentity;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stereowalker.combat.world.item.WireItem;
import com.stereowalker.combat.world.level.block.entity.ConnectorBlockEntity;
import com.stereowalker.unionlib.util.VersionHelper;
import com.stereowalker.unionlib.util.math.Color;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConnectorRenderer implements BlockEntityRenderer<ConnectorBlockEntity> {
	public ConnectorRenderer(BlockEntityRendererProvider.Context pContext) {
	}

	@Override
	public boolean shouldRenderOffScreen(ConnectorBlockEntity pBlockEntity) {
		return true;
	}

	@Override
	public int getViewDistance() {
		return 128;
	}

	@Override
	public boolean shouldRender(ConnectorBlockEntity pBlockEntity, Vec3 pCameraPos) {
		return Vec3.atCenterOf(pBlockEntity.getBlockPos()).multiply(1.0D, 0.0D, 1.0D).closerThan(pCameraPos.multiply(1.0D, 0.0D, 1.0D), (double)this.getViewDistance());
	}

	@Override
	public void render(ConnectorBlockEntity pBlockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (pBlockEntity.isConnected() && pBlockEntity.isFirst()) {
			if (pBlockEntity.getLevel().getBlockEntity(pBlockEntity.getConnection()) instanceof ConnectorBlockEntity) {
				ConnectorBlockEntity connector = (ConnectorBlockEntity)pBlockEntity.getLevel().getBlockEntity(pBlockEntity.getConnection());
				if (pBlockEntity.cable() == null)
					System.err.println("Did not find cable to render");
				else if (connector.cable() == null)
					System.err.println("Did not find cable to render");
				else
					renderWire(connector, partialTicks, matrixStackIn, bufferIn, pBlockEntity);
			}
		}
	}

	private  void renderWire(ConnectorBlockEntity start, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, ConnectorBlockEntity end) {
		pPoseStack.pushPose();
		Vec3 vec3 = start.getWireStartPos();
		double d0 = (double) (((float)Math.PI / 180F)) + (Math.PI / 2D);
		Vec3 vec31 = end.getWireEndPos();
		double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
		double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
		double d3 = ((double)end.getBlockPos().getX()) + d1;
		double d4 = ((double)end.getBlockPos().getY()) + vec31.y;
		double d5 = ((double)end.getBlockPos().getZ()) + d2;
		pPoseStack.translate(d1, vec31.y, d2);
		float f = (float)(vec3.x - d3);
		float f1 = (float)(vec3.y - d4);
		float f2 = (float)(vec3.z - d5);
		VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.leash());
		Matrix4f matrix4f = pPoseStack.last().pose();
		float f4 = Mth.fastInvSqrt(f * f + f2 * f2) * start.cable().detail.size() / 2.0F;
		float f5 = f2 * f4;
		float f6 = f * f4;
		BlockPos blockpos = new BlockPos(end.getEyePosition(pPartialTicks));
		BlockPos blockpos1 = new BlockPos(start.getEyePosition(pPartialTicks));
		int i = this.getBlockLightLevel(end, blockpos);
		int j = this.getBlockLightLevel(start, blockpos1);
		int k = end.getLevel().getBrightness(LightLayer.SKY, blockpos);
		int l = end.getLevel().getBrightness(LightLayer.SKY, blockpos1);

		int dist = Mth.ceil(vec3.distanceTo(new Vec3(d3, d4, d5))) * 50;
		for(int i1 = 0; i1 <= dist; ++i1) {
			addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, start.cable().detail.size(), start.cable().detail.size(), f5, f6, i1, false, (float)dist, start.cable());
		}

		for(int j1 = dist; j1 >= 0; --j1) {
			addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, start.cable().detail.size(), 0.0F, f5, f6, j1, true, (float)dist, start.cable());
		}

		pPoseStack.popPose();
	}

	protected int getBlockLightLevel(ConnectorBlockEntity pEntity, BlockPos pPos) {
		return pEntity.getLevel().getBrightness(LightLayer.BLOCK, pPos);
	}

	public static void addVertexPair(VertexConsumer pConsumer, Matrix4f pMatrix, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_, float dist, WireItem.Type type) {
		float f = (float)p_174321_ / dist;
		int i = (int)Mth.lerp(f, (float)p_174313_, (float)p_174314_);
		int j = (int)Mth.lerp(f, (float)p_174315_, (float)p_174316_);
		int k = LightTexture.pack(i, j);
		float f1 = p_174321_ % 2 == (p_174322_ ? 1 : 0) ? 0.9F : 1.0F;
		float f2 = type.detail.r() * f1;
		float f3 = type.detail.g() * f1;
		float f4 = type.detail.b() * f1;
		Vec3 pos = WireItem.addVertexPairs(p_174310_, p_174311_, p_174312_, p_174321_, dist);
		pConsumer.vertex(pMatrix, (float)pos.x - p_174319_, (float)pos.y + p_174318_, (float)pos.z + p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
		pConsumer.vertex(pMatrix, (float)pos.x + p_174319_, (float)pos.y + p_174317_ - p_174318_, (float)pos.z - p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
	}
}