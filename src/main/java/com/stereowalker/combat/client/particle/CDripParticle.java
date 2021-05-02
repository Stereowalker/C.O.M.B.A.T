package com.stereowalker.combat.client.particle;

import com.stereowalker.combat.fluid.CFluids;
import com.stereowalker.combat.particles.CParticleTypes;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CDripParticle extends SpriteTexturedParticle {
   private final Fluid fluid;

   private CDripParticle(ClientWorld p_i49197_1_, double p_i49197_2_, double p_i49197_4_, double p_i49197_6_, Fluid p_i49197_8_) {
      super(p_i49197_1_, p_i49197_2_, p_i49197_4_, p_i49197_6_);
      this.setSize(0.01F, 0.01F);
      this.particleGravity = 0.06F;
      this.fluid = p_i49197_8_;
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public int getBrightnessForRender(float partialTick) {
      return this.fluid.isIn(FluidTags.LAVA) ? 240 : super.getBrightnessForRender(partialTick);
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.func_217576_g();
      if (!this.isExpired) {
         this.motionY -= (double)this.particleGravity;
         this.move(this.motionX, this.motionY, this.motionZ);
         this.func_217577_h();
         if (!this.isExpired) {
            this.motionX *= (double)0.98F;
            this.motionY *= (double)0.98F;
            this.motionZ *= (double)0.98F;
            BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
            FluidState ifluidstate = this.world.getFluidState(blockpos);
            if (ifluidstate.getFluid() == this.fluid && this.posY < (double)((float)blockpos.getY() + ifluidstate.getActualHeight(this.world, blockpos))) {
               this.setExpired();
            }

         }
      }
   }

   protected void func_217576_g() {
      if (this.maxAge-- <= 0) {
         this.setExpired();
      }

   }

   protected void func_217577_h() {
   }

   @OnlyIn(Dist.CLIENT)
   static class Dripping extends CDripParticle {
      private final IParticleData field_217579_C;

      private Dripping(ClientWorld p_i50509_1_, double p_i50509_2_, double p_i50509_4_, double p_i50509_6_, Fluid p_i50509_8_, IParticleData p_i50509_9_) {
         super(p_i50509_1_, p_i50509_2_, p_i50509_4_, p_i50509_6_, p_i50509_8_);
         this.field_217579_C = p_i50509_9_;
         this.particleGravity *= 0.02F;
         this.maxAge = 40;
      }

      protected void func_217576_g() {
         if (this.maxAge-- <= 0) {
            this.setExpired();
            this.world.addParticle(this.field_217579_C, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
         }

      }

      protected void func_217577_h() {
         this.motionX *= 0.02D;
         this.motionY *= 0.02D;
         this.motionZ *= 0.02D;
      }
   }

//   @OnlyIn(Dist.CLIENT)
//   static class DrippingLava extends CDripParticle.Dripping {
//      private DrippingLava(World p_i50513_1_, double p_i50513_2_, double p_i50513_4_, double p_i50513_6_, Fluid p_i50513_8_, IParticleData p_i50513_9_) {
//         super(p_i50513_1_, p_i50513_2_, p_i50513_4_, p_i50513_6_, p_i50513_8_, p_i50513_9_);
//      }
//
//      protected void func_217576_g() {
//         this.particleRed = 1.0F;
//         this.particleGreen = 16.0F / (float)(40 - this.maxAge + 16);
//         this.particleBlue = 4.0F / (float)(40 - this.maxAge + 8);
//         super.func_217576_g();
//      }
//   }
//
//   @OnlyIn(Dist.CLIENT)
//   public static class DrippingLavaFactory implements IParticleFactory<BasicParticleType> {
//      protected final IAnimatedSprite spriteSet;
//
//      public DrippingLavaFactory(IAnimatedSprite p_i50505_1_) {
//         this.spriteSet = p_i50505_1_;
//      }
//
//      public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
//         CDripParticle.DrippingLava dripparticle$drippinglava = new CDripParticle.DrippingLava(worldIn, x, y, z, Fluids.LAVA, ParticleTypes.FALLING_LAVA);
//         dripparticle$drippinglava.selectSpriteRandomly(this.spriteSet);
//         return dripparticle$drippinglava;
//      }
//   }

   @OnlyIn(Dist.CLIENT)
   public static class DrippingOilFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite spriteSet;

      public DrippingOilFactory(IAnimatedSprite p_i50502_1_) {
         this.spriteSet = p_i50502_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CDripParticle dripparticle = new CDripParticle.Dripping(worldIn, x, y, z, CFluids.OIL, CParticleTypes.FALLING_OIL);
         dripparticle.setColor(0.604F, 0.447F, 0.09F);
         dripparticle.selectSpriteRandomly(this.spriteSet);
         return dripparticle;
      }
   }
   
   @OnlyIn(Dist.CLIENT)
   public static class DrippingBiableFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite spriteSet;

      public DrippingBiableFactory(IAnimatedSprite p_i50502_1_) {
         this.spriteSet = p_i50502_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CDripParticle dripparticle = new CDripParticle.Dripping(worldIn, x, y, z, CFluids.BIABLE, CParticleTypes.FALLING_BIABLE);
         dripparticle.setColor(0.0F, 0.49F, 0.671F);
         dripparticle.selectSpriteRandomly(this.spriteSet);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Falling extends CDripParticle {
      private final IParticleData field_217578_C;

      private Falling(ClientWorld p_i50511_1_, double p_i50511_2_, double p_i50511_4_, double p_i50511_6_, Fluid p_i50511_8_, IParticleData p_i50511_9_) {
         super(p_i50511_1_, p_i50511_2_, p_i50511_4_, p_i50511_6_, p_i50511_8_);
         this.field_217578_C = p_i50511_9_;
         this.maxAge = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
      }

      protected void func_217577_h() {
         if (this.onGround) {
            this.setExpired();
            this.world.addParticle(this.field_217578_C, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
         }

      }
   }

//   @OnlyIn(Dist.CLIENT)
//   public static class FallingLavaFactory implements IParticleFactory<BasicParticleType> {
//      protected final IAnimatedSprite spriteSet;
//
//      public FallingLavaFactory(IAnimatedSprite p_i50506_1_) {
//         this.spriteSet = p_i50506_1_;
//      }
//
//      public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
//         CDripParticle dripparticle = new CDripParticle.Falling(worldIn, x, y, z, Fluids.LAVA, ParticleTypes.LANDING_LAVA);
//         dripparticle.setColor(1.0F, 0.2857143F, 0.083333336F);
//         dripparticle.selectSpriteRandomly(this.spriteSet);
//         return dripparticle;
//      }
//   }

   @OnlyIn(Dist.CLIENT)
   public static class FallingOilFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite spriteSet;

      public FallingOilFactory(IAnimatedSprite p_i50503_1_) {
         this.spriteSet = p_i50503_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CDripParticle dripparticle = new CDripParticle.Falling(worldIn, x, y, z, CFluids.OIL, CParticleTypes.OIL_SPLASH);
         dripparticle.setColor(0.604F, 0.447F, 0.09F);
         dripparticle.selectSpriteRandomly(this.spriteSet);
         return dripparticle;
      }
   }
   
   @OnlyIn(Dist.CLIENT)
   public static class FallingBiableFactory implements IParticleFactory<BasicParticleType> {
      protected final IAnimatedSprite spriteSet;

      public FallingBiableFactory(IAnimatedSprite p_i50503_1_) {
         this.spriteSet = p_i50503_1_;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CDripParticle dripparticle = new CDripParticle.Falling(worldIn, x, y, z, CFluids.BIABLE, CParticleTypes.BIABLE_SPLASH);
         dripparticle.setColor(0.0F, 0.49F, 0.671F);
         dripparticle.selectSpriteRandomly(this.spriteSet);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Landing extends CDripParticle {
      private Landing(ClientWorld p_i50507_1_, double p_i50507_2_, double p_i50507_4_, double p_i50507_6_, Fluid p_i50507_8_) {
         super(p_i50507_1_, p_i50507_2_, p_i50507_4_, p_i50507_6_, p_i50507_8_);
         this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      }
   }

//   @OnlyIn(Dist.CLIENT)
//   public static class LandingLavaFactory implements IParticleFactory<BasicParticleType> {
//      protected final IAnimatedSprite spriteSet;
//
//      public LandingLavaFactory(IAnimatedSprite p_i50504_1_) {
//         this.spriteSet = p_i50504_1_;
//      }
//
//      public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
//         CDripParticle dripparticle = new CDripParticle.Landing(worldIn, x, y, z, Fluids.LAVA);
//         dripparticle.setColor(1.0F, 0.2857143F, 0.083333336F);
//         dripparticle.selectSpriteRandomly(this.spriteSet);
//         return dripparticle;
//      }
//   }
}