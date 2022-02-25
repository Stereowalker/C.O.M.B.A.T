package com.stereowalker.combat.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Matrix4f;

import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ICloudRenderHandler;

public class CDimensionSpecialEffects {

	@OnlyIn(Dist.CLIENT)
	public static class Acrotlest extends DimensionSpecialEffects {
		public Acrotlest() {
			//Cloud Height // Colors the sky, Requires closer inspection
			super(200.0F, false, DimensionSpecialEffects.SkyType.NORMAL, false, true);
		}

		@Override
		public Vec3 getBrightnessDependentFogColor(Vec3 p_230494_1_, float p_230494_2_) {
			return p_230494_1_.multiply((double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.91F + 0.09F));
		}

		@Override
		public boolean isFoggyAt(int p_230493_1_, int p_230493_2_) {
			return false;
		}

		@Override
		public ICloudRenderHandler getCloudRenderHandler() {
			return new ICloudRenderHandler(){

				LevelRenderer rend = Minecraft.getInstance().levelRenderer;

				public Vec3 getCloudColor(float partialTicks, ClientLevel world) {
					return new Vec3(world.getCloudColor(partialTicks).x, world.getCloudColor(partialTicks).y - 0.5F, world.getCloudColor(partialTicks).z - 0.5F);
				}

				@SuppressWarnings("unused")
				@Override
				public void render(int ticks, float pPartialTick, PoseStack pPoseStack, ClientLevel level, 
						Minecraft mc, double pCamX, double pCamY, double pCamZ)
				{
					float f = level.effects().getCloudHeight();
					if (!Float.isNaN(f)) {
						RenderSystem.disableCull();
						RenderSystem.enableBlend();
						RenderSystem.enableDepthTest();
						RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
						RenderSystem.depthMask(true);
						float f1 = 12.0F;
						float f2 = 4.0F;
						double d0 = 2.0E-4D;
						double d1 = (double)(((float)rend.ticks + pPartialTick) * 0.03F);
						double d2 = (pCamX + d1) / 12.0D;
						double d3 = (double)(f - (float)pCamY + 0.33F);
						double d4 = pCamZ / 12.0D + (double)0.33F;
						d2 = d2 - (double)(Mth.floor(d2 / 2048.0D) * 2048);
						d4 = d4 - (double)(Mth.floor(d4 / 2048.0D) * 2048);
						float f3 = (float)(d2 - (double)Mth.floor(d2));
						float f4 = (float)(d3 / 4.0D - (double)Mth.floor(d3 / 4.0D)) * 4.0F;
						float f5 = (float)(d4 - (double)Mth.floor(d4));
						Vec3 vec3 = getCloudColor(pPartialTick, level);
						int i = (int)Math.floor(d2);
						int j = (int)Math.floor(d3 / 4.0D);
						int k = (int)Math.floor(d4);
						if (i != rend.prevCloudX || j != rend.prevCloudY || k != rend.prevCloudZ || mc.options.getCloudsType() != rend.prevCloudsType || rend.prevCloudColor.distanceToSqr(vec3) > 2.0E-4D) {
							rend.prevCloudX = i;
							rend.prevCloudY = j;
							rend.prevCloudZ = k;
							rend.prevCloudColor = vec3;
							rend.prevCloudsType = mc.options.getCloudsType();
							rend.generateClouds = true;
						}

						if (rend.generateClouds) {
							rend.generateClouds = false;
							BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
							if (rend.cloudBuffer != null) {
								rend.cloudBuffer.close();
							}

							rend.cloudBuffer = new VertexBuffer();
							rend.buildClouds(bufferbuilder, d2, d3, d4, vec3);
							bufferbuilder.end();
							rend.cloudBuffer.upload(bufferbuilder);
						}

						RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
						RenderSystem.setShaderTexture(0, LevelRenderer.CLOUDS_LOCATION);
						FogRenderer.levelFogColor();
						pPoseStack.pushPose();
						pPoseStack.scale(12.0F, 1.0F, 12.0F);
						pPoseStack.translate((double)(-f3), (double)f4, (double)(-f5));
						if (rend.cloudBuffer != null) {
							int i1 = rend.prevCloudsType == CloudStatus.FANCY ? 0 : 1;

							for(int l = i1; l < 2; ++l) {
								if (l == 0) {
									RenderSystem.colorMask(false, false, false, false);
								} else {
									RenderSystem.colorMask(true, true, true, true);
								}

								ShaderInstance shaderinstance = RenderSystem.getShader();
								//TODO: Figure out how to get the projection Matrix
								Matrix4f matrix4f = pPoseStack.last().pose();
								mc.gameRenderer.resetProjectionMatrix(matrix4f);
								//
								rend.cloudBuffer.drawWithShader(pPoseStack.last().pose(), matrix4f, shaderinstance);
							}
						}

						pPoseStack.popPose();
						RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
						RenderSystem.enableCull();
						RenderSystem.disableBlend();
					}
				}

			};
		}
	}
}
