package com.stereowalker.combat.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.tileentity.ConnectorTileEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.LightType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConnectorTileEntityRenderer extends TileEntityRenderer<ConnectorTileEntity> {
	/** The texture for the book above the enchantment table. */
	public ConnectorTileEntityRenderer(TileEntityRendererDispatcher p_i226010_1_) {
		super(p_i226010_1_);
	}

	public void render(ConnectorTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (tileEntityIn.isConnected()) {
			if (tileEntityIn.getWorld().getTileEntity(tileEntityIn.getConnection()) instanceof ConnectorTileEntity) {
				ConnectorTileEntity connector = (ConnectorTileEntity)tileEntityIn.getWorld().getTileEntity(tileEntityIn.getConnection());
				renderLeash(tileEntityIn, partialTicks, matrixStackIn, bufferIn, connector);
			}
		}
	}

	private <E extends Entity> void renderLeash(ConnectorTileEntity entityLivingIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, ConnectorTileEntity leashHolder) {
		matrixStackIn.push();
		//		double d0 = (double)(MathHelper.lerp(partialTicks * 0.5F, leashHolder.rotationYaw, leashHolder.prevRotationYaw) * ((float)Math.PI / 180F));
		//		double d1 = (double)(MathHelper.lerp(partialTicks * 0.5F, leashHolder.rotationPitch, leashHolder.prevRotationPitch) * ((float)Math.PI / 180F));
		//		double d2 = Math.cos(d0);
		//		double d3 = Math.sin(d0);
		//		double d4 = Math.sin(d1);
		//		if (leashHolder instanceof HangingEntity) {
		double d2 = 0.0D;
		double d3 = 0.0D;
		double d4 = -1.0D;
		//		}

		double d5 = Math.cos(/* d1 */0);
		double d6 = /*
		 * MathHelper.lerp((double)partialTicks, leashHolder.prevPosX,
		 * leashHolder.getPosX())
		 */entityLivingIn.getPos().getX() - d2 * 0.7D - d3 * 0.5D * d5;
		double d7 = /*
		 * MathHelper.lerp((double)partialTicks, leashHolder.prevPosY +
		 * (double)leashHolder.getEyeHeight() * 0.7D, leashHolder.getPosY() +
		 * (double)leashHolder.getEyeHeight() * 0.7D)
		 */entityLivingIn.getPos().getY() - d4 * 0.5D - 0.25D;
		double d8 = /*
		 * MathHelper.lerp((double)partialTicks, leashHolder.prevPosZ,
		 * leashHolder.getPosZ())
		 */entityLivingIn.getPos().getZ() - d3 * 0.7D + d2 * 0.5D * d5;
		double d9 = /*
		 * (double)(MathHelper.lerp(partialTicks, entityLivingIn.renderYawOffset,
		 * entityLivingIn.prevRenderYawOffset) * ((float)Math.PI / 180F))
		 */0 + (Math.PI / 2D);
		Vector3d vector3d = /*entityLivingIn.func_241205_ce_()*/Vector3d.ZERO;
		d2 = Math.cos(d9) * vector3d.z + Math.sin(d9) * vector3d.x;
		d3 = Math.sin(d9) * vector3d.z - Math.cos(d9) * vector3d.x;
		double d10 = /*
		 * MathHelper.lerp((double)partialTicks, entityLivingIn.prevPosX,
		 * entityLivingIn.getPosX())
		 */entityLivingIn.getPos().getX() + d2;
		double d11 = /*MathHelper.lerp((double)partialTicks, entityLivingIn.prevPosY, entityLivingIn.getPosY())*/entityLivingIn.getPos().getY() + vector3d.y;
		double d12 = /*
		 * MathHelper.lerp((double)partialTicks, entityLivingIn.prevPosZ,
		 * entityLivingIn.getPosZ())
		 */entityLivingIn.getPos().getZ() + d3;
		matrixStackIn.translate(d2, vector3d.y, d3);
		float f = (float)(d6 - d10);
		float f1 = (float)(d7 - d11);
		float f2 = (float)(d8 - d12);
		float f3 = 0.025F;
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getLeash());
		Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
		float f4 = MathHelper.fastInvSqrt(f * f + f2 * f2) * f3 / 2.0F;
		float f5 = f2 * f4;
		float f6 = f * f4;
		BlockPos blockpos = /*new BlockPos(entityLivingIn.getEyePosition(partialTicks));*/entityLivingIn.getPos();
		BlockPos blockpos1 = /*new BlockPos(leashHolder.getEyePosition(partialTicks));*/leashHolder.getPos();
		int i = entityLivingIn.getWorld().getLightFor(LightType.BLOCK, blockpos);
		int j = leashHolder.getWorld().getLightFor(LightType.BLOCK, blockpos1);
		int k = entityLivingIn.getWorld().getLightFor(LightType.SKY, blockpos);
		int l = entityLivingIn.getWorld().getLightFor(LightType.SKY, blockpos1);
		MobRenderer.renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6);
		MobRenderer.renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6);
		matrixStackIn.pop();
	}
}