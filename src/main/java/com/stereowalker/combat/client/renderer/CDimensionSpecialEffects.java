package com.stereowalker.combat.client.renderer;

import org.joml.Matrix4f;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.stereowalker.combat.world.level.biome.CBiomes;

import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CDimensionSpecialEffects {

	@OnlyIn(Dist.CLIENT)
	public static class Acrotlest extends DimensionSpecialEffects {
		public Acrotlest() {
			//Cloud Height // Colors the sky, Requires closer inspection
			super(100.0F, false, DimensionSpecialEffects.SkyType.NORMAL, false, true);
		}

		@Override
		public Vec3 getBrightnessDependentFogColor(Vec3 p_230494_1_, float p_230494_2_) {
			return p_230494_1_.multiply((double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.91F + 0.09F));
		}

		@Override
		public boolean isFoggyAt(int p_230493_1_, int p_230493_2_) {
			return false;
		}

		public Vec3 getCloudColor(float partialTicks, ClientLevel world) {
			return new Vec3(world.getCloudColor(partialTicks).x, world.getCloudColor(partialTicks).y - 0.5F, world.getCloudColor(partialTicks).z - 0.5F);
		}
		
		@Override
		public boolean renderSnowAndRain(ClientLevel level, int ticks, float pPartialTick, LightTexture pLightTexture, double pCamX, double pCamY, double pCamZ) {
			float f = /*this.minecraft.level.getRainLevel(pPartialTick)*/1.0f;
		      if (!(f <= 0.0F)) {
		         pLightTexture.turnOnLightLayer();
		         int i = Mth.floor(pCamX);
		         int j = Mth.floor(pCamY);
		         int k = Mth.floor(pCamZ);
		         Tesselator tesselator = Tesselator.getInstance();
		         BufferBuilder bufferbuilder = tesselator.getBuilder();
		         RenderSystem.disableCull();
		         RenderSystem.enableBlend();
		         RenderSystem.defaultBlendFunc();
		         RenderSystem.enableDepthTest();
		         int l = 5;
		         if (Minecraft.useFancyGraphics()) {
		            l = 10;
		         }

		         RenderSystem.depthMask(Minecraft.useShaderTransparency());
		         int i1 = -1;
		         float f1 = (float)ticks + pPartialTick;
		         RenderSystem.setShader(GameRenderer::getParticleShader);
		         RenderSystem.setShaderColor(0.1F, 0.1F, 0.1F, 1.0F);
		         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		         for(int j1 = k - l; j1 <= k + l; ++j1) {
		            for(int k1 = i - l; k1 <= i + l; ++k1) {
		               int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
		               double d0 = (double)Minecraft.getInstance().levelRenderer.rainSizeX[l1] * 0.5D;
		               double d1 = (double)Minecraft.getInstance().levelRenderer.rainSizeZ[l1] * 0.5D;
		               blockpos$mutableblockpos.set((double)k1, pCamY, (double)j1);
		               Biome biome = level.getBiome(blockpos$mutableblockpos).value();
		               if (biome.getPrecipitation() != Biome.Precipitation.NONE) {
		                  int i2 = level.getHeight(Heightmap.Types.MOTION_BLOCKING, k1, j1);
		                  int j2 = j - l;
		                  int k2 = j + l;
		                  if (j2 < i2) {
		                     j2 = i2;
		                  }

		                  if (k2 < i2) {
		                     k2 = i2;
		                  }

		                  int l2 = i2;
		                  if (i2 < j) {
		                     l2 = j;
		                  }

		                  if (j2 != k2) {
		                     RandomSource randomsource = RandomSource.create((long)(k1 * k1 * 3121 + k1 * 45238971 ^ j1 * j1 * 418711 + j1 * 13761));
		                     blockpos$mutableblockpos.set(k1, j2, j1);
		                     boolean hasBlackSnow = level.getBiome(blockpos$mutableblockpos).is(CBiomes.ACROTLEST_FOREST);
		                     if (hasBlackSnow) {
		                    	 if (i1 != 1) {
			                           if (i1 >= 0) {
			                              tesselator.end();
			                           }

			                           i1 = 1;
			                           RenderSystem.setShaderTexture(0, /*SNOW_LOCATION*/ new ResourceLocation("textures/environment/snow.png"));
			                           bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
			                        }

			                        float f5 = -((float)(ticks & 511) + pPartialTick) / 512.0F;
			                        float f6 = (float)(randomsource.nextDouble() + (double)f1 * 0.01D * (double)((float)randomsource.nextGaussian()));
			                        float f7 = (float)(randomsource.nextDouble() + (double)(f1 * (float)randomsource.nextGaussian()) * 0.001D);
			                        double d3 = (double)k1 + 0.5D - pCamX;
			                        double d5 = (double)j1 + 0.5D - pCamZ;
			                        float f8 = (float)Math.sqrt(d3 * d3 + d5 * d5) / (float)l;
			                        float f9 = ((1.0F - f8 * f8) * 0.3F + 0.5F) * f;
			                        blockpos$mutableblockpos.set(k1, l2, j1);
			                        int k3 = LevelRenderer.getLightColor(level, blockpos$mutableblockpos);
			                        int l3 = k3 >> 16 & '\uffff';
			                        int i4 = k3 & '\uffff';
			                        int j4 = (l3 * 3 + 240) / 4;
			                        int k4 = (i4 * 3 + 240) / 4;
			                        bufferbuilder.vertex((double)k1 - pCamX - d0 + 0.5D, (double)k2 - pCamY, (double)j1 - pCamZ - d1 + 0.5D).uv(0.0F + f6, (float)j2 * 0.25F + f5 + f7).color(1.0F, 1.0F, 1.0F, f9).uv2(k4, j4).endVertex();
			                        bufferbuilder.vertex((double)k1 - pCamX + d0 + 0.5D, (double)k2 - pCamY, (double)j1 - pCamZ + d1 + 0.5D).uv(1.0F + f6, (float)j2 * 0.25F + f5 + f7).color(1.0F, 1.0F, 1.0F, f9).uv2(k4, j4).endVertex();
			                        bufferbuilder.vertex((double)k1 - pCamX + d0 + 0.5D, (double)j2 - pCamY, (double)j1 - pCamZ + d1 + 0.5D).uv(1.0F + f6, (float)k2 * 0.25F + f5 + f7).color(1.0F, 1.0F, 1.0F, f9).uv2(k4, j4).endVertex();
			                        bufferbuilder.vertex((double)k1 - pCamX - d0 + 0.5D, (double)j2 - pCamY, (double)j1 - pCamZ - d1 + 0.5D).uv(0.0F + f6, (float)k2 * 0.25F + f5 + f7).color(1.0F, 1.0F, 1.0F, f9).uv2(k4, j4).endVertex();
		                     }
		                  }
		               }
		            }
		         }

		         if (i1 >= 0) {
		            tesselator.end();
		         }

		         RenderSystem.enableCull();
		         RenderSystem.disableBlend();
		         pLightTexture.turnOffLightLayer();
		      }
		      return false;
		}
		
		@Override
		public boolean renderClouds(ClientLevel level, int ticks, float pPartialTick, PoseStack pPoseStack, double pCamX, double pCamY, double pCamZ, Matrix4f pProjectionMatrix) {
			float f = level.effects().getCloudHeight();
			LevelRenderer rend = Minecraft.getInstance().levelRenderer;
		      if (!Float.isNaN(f)) {
		         RenderSystem.disableCull();
		         RenderSystem.enableBlend();
		         RenderSystem.enableDepthTest();
		         RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		         RenderSystem.depthMask(true);
		         float f1 = 12.0F;
		         float f2 = 4.0F;
		         double d0 = 2.0E-4D;
		         double d1 = (double)(((float)ticks + pPartialTick) * 0.03F);
		         double d2 = (pCamX + d1) / 12.0D;
		         double d3 = (double)(f - (float)pCamY + 0.33F);
		         double d4 = pCamZ / 12.0D + (double)0.33F;
		         d2 -= (double)(Mth.floor(d2 / 2048.0D) * 2048);
		         d4 -= (double)(Mth.floor(d4 / 2048.0D) * 2048);
		         float f3 = (float)(d2 - (double)Mth.floor(d2));
		         float f4 = (float)(d3 / 4.0D - (double)Mth.floor(d3 / 4.0D)) * 4.0F;
		         float f5 = (float)(d4 - (double)Mth.floor(d4));
		         Vec3 vec3 = getCloudColor(pPartialTick, level);
		         int i = (int)Math.floor(d2);
		         int j = (int)Math.floor(d3 / 4.0D);
		         int k = (int)Math.floor(d4);
		         if (i != rend.prevCloudX || j != rend.prevCloudY || k != rend.prevCloudZ || Minecraft.getInstance().options.getCloudsType() != rend.prevCloudsType || rend.prevCloudColor.distanceToSqr(vec3) > 2.0E-4D) {
		        	 rend.prevCloudX = i;
		        	 rend.prevCloudY = j;
		        	 rend.prevCloudZ = k;
		        	 rend.prevCloudColor = vec3;
		        	 rend.prevCloudsType = Minecraft.getInstance().options.getCloudsType();
		        	 rend.generateClouds = true;
		         }

		         if (rend.generateClouds) {
		        	 rend.generateClouds = false;
		            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		            if (rend.cloudBuffer != null) {
		            	rend.cloudBuffer.close();
		            }

		            rend.cloudBuffer = new VertexBuffer();
		            BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = rend.buildClouds(bufferbuilder, d2, d3, d4, vec3);
		            rend.cloudBuffer.bind();
		            rend.cloudBuffer.upload(bufferbuilder$renderedbuffer);
		            VertexBuffer.unbind();
		         }

		         RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
		         RenderSystem.setShaderTexture(0, LevelRenderer.CLOUDS_LOCATION);
		         FogRenderer.levelFogColor();
		         pPoseStack.pushPose();
		         pPoseStack.scale(12.0F, 1.0F, 12.0F);
		         pPoseStack.translate(-f3, f4, -f5);
		         if (rend.cloudBuffer != null) {
		        	 rend.cloudBuffer.bind();
		            int l = rend.prevCloudsType == CloudStatus.FANCY ? 0 : 1;

		            for(int i1 = l; i1 < 2; ++i1) {
		               if (i1 == 0) {
		                  RenderSystem.colorMask(false, false, false, false);
		               } else {
		                  RenderSystem.colorMask(true, true, true, true);
		               }

		               ShaderInstance shaderinstance = RenderSystem.getShader();
		               rend.cloudBuffer.drawWithShader(pPoseStack.last().pose(), pProjectionMatrix, shaderinstance);
		            }

		            VertexBuffer.unbind();
		         }

		         pPoseStack.popPose();
		         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		         RenderSystem.enableCull();
		         RenderSystem.disableBlend();
		      }
		      return true;
		}
	}
}
