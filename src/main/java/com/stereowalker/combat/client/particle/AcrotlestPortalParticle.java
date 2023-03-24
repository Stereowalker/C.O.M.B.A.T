package com.stereowalker.combat.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AcrotlestPortalParticle extends TextureSheetParticle {
   private final double portalPosX;
   private final double portalPosY;
   private final double portalPosZ;

   private AcrotlestPortalParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn);
      this.xd = xSpeedIn;
      this.yd = ySpeedIn;
      this.zd = zSpeedIn;
      this.x = xCoordIn;
      this.y = yCoordIn;
      this.z = zCoordIn;
      this.portalPosX = this.x;
      this.portalPosY = this.y;
      this.portalPosZ = this.z;
      this.quadSize = 0.1F * (this.random.nextFloat() * 0.2F + 0.5F);
      float f = this.random.nextFloat() * 0.6F + 0.4F;
      this.rCol = 0;
      this.gCol = f * 0.3F;
      this.bCol = f * 0.9F;
      this.lifetime = (int)(Math.random() * 10.0D) + 40;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public void move(double x, double y, double z) {
      this.setBoundingBox(this.getBoundingBox().move(x, y, z));
      this.setLocationFromBoundingbox();
   }

   @Override
   public float getQuadSize(float p_217561_1_) {
      float f = ((float)this.age + p_217561_1_) / (float)this.lifetime;
      f = 1.0F - f;
      f = f * f;
      f = 1.0F - f;
      return this.quadSize * f;
   }

   @Override
   public int getLightColor(float partialTick) {
      int i = super.getLightColor(partialTick);
      float f = (float)this.age / (float)this.lifetime;
      f = f * f;
      f = f * f;
      int j = i & 255;
      int k = i >> 16 & 255;
      k = k + (int)(f * 15.0F * 16.0F);
      if (k > 240) {
         k = 240;
      }

      return j | k << 16;
   }

   @Override
   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         float f = (float)this.age / (float)this.lifetime;
         float f1 = -f + f * f * 2.0F;
         float f2 = 1.0F - f1;
         this.x = this.portalPosX + this.xd * (double)f2;
         this.y = this.portalPosY + this.yd * (double)f2 + (double)(1.0F - f);
         this.z = this.portalPosZ + this.zd * (double)f2;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet p_i50607_1_) {
         this.spriteSet = p_i50607_1_;
      }

      @Override
      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         AcrotlestPortalParticle portalparticle = new AcrotlestPortalParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
         portalparticle.pickSprite(this.spriteSet);
         return portalparticle;
      }
   }
}