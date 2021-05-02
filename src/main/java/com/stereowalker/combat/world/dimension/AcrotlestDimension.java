//package com.stereowalker.combat.world.dimension;
//
//import javax.annotation.Nullable;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.stereowalker.combat.block.CBlocks;
//import com.stereowalker.combat.world.biome.provider.AcrotlestBiomeProvider;
//import com.stereowalker.combat.world.biome.provider.AcrotlestBiomeProviderSettings;
//import com.stereowalker.combat.world.biome.provider.CBiomeProviderType;
//import com.stereowalker.combat.world.gen.AcrotlestChunkGenerator;
//import com.stereowalker.combat.world.gen.AcrotlestGenSettings;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.audio.MusicTicker.MusicType;
//import net.minecraft.client.renderer.BufferBuilder;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.WorldRenderer;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//import net.minecraft.client.renderer.vertex.VertexBuffer;
//import net.minecraft.client.settings.CloudOption;
//import net.minecraft.client.world.ClientWorld;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.ChunkPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.Vector3d;
//import net.minecraft.world.GameRules;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.provider.BiomeProviderType;
//import net.minecraft.world.chunk.Chunk;
//import net.minecraft.world.dimension.Dimension;
//import net.minecraft.world.dimension.DimensionType;
//import net.minecraft.world.gen.ChunkGenerator;
//import net.minecraft.world.gen.ChunkGeneratorType;
//import net.minecraftforge.client.CloudRenderHandler;
//import net.minecraftforge.client.IRenderHandler;
//
//public class AcrotlestDimension extends Dimension {
//
//	public AcrotlestDimension(World worldIn, DimensionType typeIn) {
//		super(worldIn, typeIn, 0.0F);
//	}
//
//	public AcrotlestDimension(World world) {
//		this(world, CDimensionType.THE_ACROTLEST);
//	}
//
//	@Override
//	public double getMovementFactor() {
//		return 10;
//	}
//
//	@Override
//	public float getCloudHeight() {
//		return 128;
//	}
//
//	@Override
//	public boolean shouldMapSpin(String entity, double x, double z, double rotation) {
//		return true;
//	}
//
//	@Override
//	public void updateWeather(Runnable defaultLogic) {
//		super.updateWeather(() -> {
//			if (this.getWorld().getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
//				int i = this.getWorld().getWorldInfo().getClearWeatherTime();
//				int j = this.getWorld().getWorldInfo().getThunderTime();
//				int k = this.getWorld().getWorldInfo().getRainTime();
//				boolean flag1 = this.getWorld().getWorldInfo().isThundering();
//				boolean flag2 = this.getWorld().getWorldInfo().isRaining();
//				if (i > 0) {
//					--i;
//					j = flag1 ? 0 : 1;
//					k = flag2 ? 0 : 1;
//					flag1 = false;
//					flag2 = false;
//				} else {
//					if (j > 0) {
//						--j;
//						if (j == 0) {
//							flag1 = !flag1;
//						}
//					} else if (flag1) {
//						j = this.getWorld().rand.nextInt(12000) + 3600;
//					} else {
//						j = this.getWorld().rand.nextInt(168000) + 12000;
//					}
//
//					if (k > 0) {
//						--k;
//						if (k == 0) {
//							flag2 = !flag2;
//						}
//					} else if (flag2) {
//						k = this.getWorld().rand.nextInt(12000) + 12000;
//					} else {
//						k = this.getWorld().rand.nextInt(168000) + 12000;
//					}
//				}
//
//				this.getWorld().getWorldInfo().setThunderTime(j);
//				this.getWorld().getWorldInfo().setRainTime(k);
//				this.getWorld().getWorldInfo().setClearWeatherTime(i);
//				this.getWorld().getWorldInfo().setThundering(flag1);
//				this.getWorld().getWorldInfo().setRaining(flag2);
//			}
//
//			this.getWorld().prevThunderingStrength = this.getWorld().thunderingStrength;
//			if (this.getWorld().getWorldInfo().isThundering()) {
//				this.getWorld().thunderingStrength = (float)((double)this.getWorld().thunderingStrength + 0.01D);
//			} else {
//				this.getWorld().thunderingStrength = (float)((double)this.getWorld().thunderingStrength - 0.01D);
//			}
//
//			this.getWorld().thunderingStrength = MathHelper.clamp(this.getWorld().thunderingStrength, 0.0F, 1.0F);
//			this.getWorld().prevRainingStrength = this.getWorld().rainingStrength;
//			if (this.getWorld().getWorldInfo().isRaining()) {
//				this.getWorld().rainingStrength = (float)((double)this.getWorld().rainingStrength + 0.01D);
//			} else {
//				this.getWorld().rainingStrength = (float)((double)this.getWorld().rainingStrength - 0.01D);
//			}
//
//			this.getWorld().rainingStrength = MathHelper.clamp(this.getWorld().rainingStrength, 0.0F, 1.0F);
//		});
//	}
//
//	@Override
//	public boolean isNether() {
//		return false;
//	}
//
//	@Override
//	public boolean canDoRainSnowIce(Chunk chunk) {
//		return true;
//	}
//
//	@Override
//	public boolean canDoLightning(Chunk chunk) {
//		return true;
//	}
//	@Override
//	public ChunkGenerator<?> createChunkGenerator() {
//		BiomeProviderType<AcrotlestBiomeProviderSettings, AcrotlestBiomeProvider> biomeprovidertype = CBiomeProviderType.ACROTLEST;
//		ChunkGeneratorType<AcrotlestGenSettings, AcrotlestChunkGenerator> chunkGeneratorType = new ChunkGeneratorType<>(AcrotlestChunkGenerator::new, true, AcrotlestGenSettings::new);
//		AcrotlestGenSettings gensettings = chunkGeneratorType.createSettings();
//		AcrotlestBiomeProviderSettings biomeprovidersettings = biomeprovidertype.createSettings(this.world.getWorldInfo()).setGeneratorSettings(gensettings);
//		AcrotlestBiomeProvider biomeprovider = biomeprovidertype.create(biomeprovidersettings);
//
//		gensettings.setDefaultBlock(CBlocks.MEZEPINE.getDefaultState());
//		gensettings.setDefaultFluid(CBlocks.BIABLE.getDefaultState());
//		return chunkGeneratorType.create(this.world, biomeprovider, gensettings);
//	}
//
//	@Override
//	public float calculateCelestialAngle(long worldTime, float partialTicks) {
//		double d0 = MathHelper.frac((double)worldTime / 48000.0D - 0.25D);
//		double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
//		return (float)(d0 * 2.0D + d1) / 3.0F;
//	}
//
//	@Override
//	public long getWorldTime() {
//		return super.getWorldTime();
//	}
//
//	@Override
//	public boolean hasSkyLight() {
//		return true;
//	}
//
//	@Override
//	public int getHeight() {
//		return 512;
//	}
//
//	@Override
//	public int getActualHeight() {
//		return 512;
//	}
//
//	@Override
//	public IRenderHandler getCloudRenderer() {
//		return new CloudRenderHandler(){
//
//			private final ResourceLocation CLOUDS_TEXTURES = new ResourceLocation("textures/environment/clouds.png");
//			@Nullable
//			private VertexBuffer cloudsVBO;
//			WorldRenderer rend = Minecraft.getInstance().worldRenderer;
//			
//			public Vector3d getCloudColor(float partialTicks, ClientWorld world) {
//				return new Vector3d(world.getCloudColor(partialTicks).x, world.getCloudColor(partialTicks).y - 0.5F, world.getCloudColor(partialTicks).z - 0.5F);
//			}
//			
//			@Override
//			public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world,
//					Minecraft mc) {
//				Vector3d vec = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
//				RenderSystem.disableCull();
//				RenderSystem.enableBlend();
//				RenderSystem.enableAlphaTest();
//				RenderSystem.enableDepthTest();
//				RenderSystem.defaultAlphaFunc();
//				RenderSystem.defaultBlendFunc();
//				RenderSystem.enableFog();
//				float f = 12.0F;
//				float f1 = 4.0F;
//				double d0 = 2.0E-4D;
//				double d1 = (double)(((float)ticks + partialTicks) * 0.03F);
//				double d2 = (vec.getX() + d1) / 12.0D;
//				double d3 = (double)(world.dimension.getCloudHeight() - (float)vec.getY() + 0.33F);
//				double d4 = vec.getZ() / 12.0D + (double)0.33F;
//				d2 = d2 - (double)(MathHelper.floor(d2 / 2048.0D) * 2048);
//				d4 = d4 - (double)(MathHelper.floor(d4 / 2048.0D) * 2048);
//				float f2 = (float)(d2 - (double)MathHelper.floor(d2));
//				float f3 = (float)(d3 / 4.0D - (double)MathHelper.floor(d3 / 4.0D)) * 4.0F;
//				float f4 = (float)(d4 - (double)MathHelper.floor(d4));
//				Vector3d Vector3d = getCloudColor(partialTicks, world);
//				int i = (int)Math.floor(d2);
//				int j = (int)Math.floor(d3 / 4.0D);
//				int k = (int)Math.floor(d4);
////				if (i != rend.cloudsCheckX || j != rend.cloudsCheckY || k != rend.cloudsCheckZ || mc.gameSettings.getCloudOption() != rend.cloudOption || rend.cloudsCheckColor.squareDistanceTo(Vector3d) > 2.0E-4D) {
////					rend.cloudsCheckX = i;
////					rend.cloudsCheckY = j;
////					rend.cloudsCheckZ = k;
////					rend.cloudsCheckColor = Vector3d;
////					rend.cloudOption = mc.gameSettings.getCloudOption();
////					rend.cloudsNeedUpdate = true;
////				}
//
////				if (rend.cloudsNeedUpdate) {
////					rend.cloudsNeedUpdate = false;
//					BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
//					if (cloudsVBO != null) {
//						cloudsVBO.close();
//					}
//
//					cloudsVBO = new VertexBuffer(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
//					drawClouds(bufferbuilder, d2, d3, d4, Vector3d);
//					bufferbuilder.finishDrawing();
//					cloudsVBO.upload(bufferbuilder);
////				}
//
//				mc.textureManager.bindTexture(CLOUDS_TEXTURES);
//				matrixStack.push();
//				matrixStack.scale(12.0F, 1.0F, 12.0F);
//				matrixStack.translate((double)(-f2), (double)f3, (double)(-f4));
//				if (cloudsVBO != null) {
//					cloudsVBO.bindBuffer();
//					DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.setupBufferState(0L);
//					int i1 = Minecraft.getInstance().gameSettings.getCloudOption() == CloudOption.FANCY ? 0 : 1;
//
//					for(int l = i1; l < 2; ++l) {
//						if (l == 0) {
//							RenderSystem.colorMask(false, false, false, false);
//						} else {
//							RenderSystem.colorMask(true, true, true, true);
//						}
//
//						cloudsVBO.draw(matrixStack.getLast().getMatrix(), 7);
//					}
//
//					VertexBuffer.unbindBuffer();
//					DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.clearBufferState();
//				}
//
//				matrixStack.pop();
//				RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//				RenderSystem.disableAlphaTest();
//				RenderSystem.enableCull();
//				RenderSystem.disableBlend();
//				RenderSystem.disableFog();
//			}
//
//			private void drawClouds(BufferBuilder bufferIn, double cloudsX, double cloudsY, double cloudsZ, Vector3d cloudsColor) {
//				float f = 4.0F;
//				float f1 = 0.00390625F;
//				int i = 8;
//				int j = 4;
//				float f2 = 9.765625E-4F;
//				float f3 = (float)MathHelper.floor(cloudsX) * 0.00390625F;
//				float f4 = (float)MathHelper.floor(cloudsZ) * 0.00390625F;
//				float f5 = (float)cloudsColor.x;
//				float f6 = (float)cloudsColor.y;
//				float f7 = (float)cloudsColor.z;
//				float f8 = f5 * 0.9F;
//				float f9 = f6 * 0.9F;
//				float f10 = f7 * 0.9F;
//				float f11 = f5 * 0.7F;
//				float f12 = f6 * 0.7F;
//				float f13 = f7 * 0.7F;
//				float f14 = f5 * 0.8F;
//				float f15 = f6 * 0.8F;
//				float f16 = f7 * 0.8F;
//				bufferIn.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
//				float f17 = (float)Math.floor(cloudsY / 4.0D) * 4.0F;
//				if (Minecraft.getInstance().gameSettings.getCloudOption() == CloudOption.FANCY) {
//					for(int k = -3; k <= 4; ++k) {
//						for(int l = -3; l <= 4; ++l) {
//							float f18 = (float)(k * 8);
//							float f19 = (float)(l * 8);
//							if (f17 > -5.0F) {
//								bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
//								bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
//								bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
//								bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
//							}
//
//							if (f17 <= 5.0F) {
//								bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
//								bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
//								bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
//								bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
//							}
//
//							if (k > -1) {
//								for(int i1 = 0; i1 < 8; ++i1) {
//									bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
//									bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
//									bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
//									bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
//								}
//							}
//
//							if (k <= 1) {
//								for(int j2 = 0; j2 < 8; ++j2) {
//									bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
//									bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
//									bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
//									bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
//								}
//							}
//
//							if (l > -1) {
//								for(int k2 = 0; k2 < 8; ++k2) {
//									bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
//									bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
//									bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
//									bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
//								}
//							}
//
//							if (l <= 1) {
//								for(int l2 = 0; l2 < 8; ++l2) {
//									bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
//									bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
//									bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
//									bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
//								}
//							}
//						}
//					}
//				} else {
//					int j1 = 1;
//					int k1 = 32;
//
//					for(int l1 = -32; l1 < 32; l1 += 32) {
//						for(int i2 = -32; i2 < 32; i2 += 32) {
//							bufferIn.pos((double)(l1 + 0), (double)f17, (double)(i2 + 32)).tex((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
//							bufferIn.pos((double)(l1 + 32), (double)f17, (double)(i2 + 32)).tex((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
//							bufferIn.pos((double)(l1 + 32), (double)f17, (double)(i2 + 0)).tex((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
//							bufferIn.pos((double)(l1 + 0), (double)f17, (double)(i2 + 0)).tex((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
//						}
//					}
//				}
//
//			}
//
//		};
//	}
//
//	@Override
//	public boolean isDaytime() {
//		return getDimension().getType() == CDimensionType.THE_ACROTLEST && getWorld().getSkylightSubtracted() < 4;
//	}
//
//	@Override
//	public boolean isSurfaceWorld() {
//		return true;
//	}
//
//	@Override
//	public Vector3d getFogColor(float celestialAngle, float partialTicks) {
//		float f = MathHelper.cos(celestialAngle * ((float)Math.PI * 2.0F)) * 2.0F + 0.5F;
//		f = MathHelper.clamp(f, 0.0F, 0.1F);
//		float f1 = 1.75F * f;
//		float f2 = 0.74F * f;
//		float f3 = 0.25F * f;
//		return new Vector3d(f1, f2, f3);
//	}
//
//	@Override
//	public boolean canRespawnHere() {
//		return true;
//	}
//
//	@Override
//	public boolean doesXZShowFog(int x, int z) {
//		return false;
//	}
//
//	@Override
//	public MusicType getMusicType() {
//		return MusicType.UNDER_WATER;
//	}
//
//	@Override
//	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
//		return null;
//	}
//
//	@Override
//	public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
//		return null;
//	}
//
//}
