package com.stereowalker.combat.client.particle;

import com.stereowalker.combat.core.particles.CParticleTypes;
import com.stereowalker.combat.world.level.material.CFluids;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CDripParticle extends DripParticle {

	CDripParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType) {
		super(pLevel, pX, pY, pZ, pType);
	}

	@OnlyIn(Dist.CLIENT)
	public static class DrippingOilProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public DrippingOilProvider(SpriteSet p_i50502_1_) {
			this.spriteSet = p_i50502_1_;
		}

		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			DripParticle dripparticle = new CDripParticle.DripHangParticle(worldIn, x, y, z, CFluids.OIL, CParticleTypes.FALLING_OIL);
			dripparticle.setColor(0.604F, 0.447F, 0.09F);
			dripparticle.pickSprite(this.spriteSet);
			return dripparticle;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class DrippingBiableProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public DrippingBiableProvider(SpriteSet p_i50502_1_) {
			this.spriteSet = p_i50502_1_;
		}

		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			DripParticle dripparticle = new CDripParticle.DripHangParticle(worldIn, x, y, z, CFluids.BIABLE, CParticleTypes.FALLING_BIABLE);
			dripparticle.setColor(0.0F, 0.49F, 0.671F);
			dripparticle.pickSprite(this.spriteSet);
			return dripparticle;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class FallingOilProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public FallingOilProvider(SpriteSet p_i50503_1_) {
			this.spriteSet = p_i50503_1_;
		}

		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			DripParticle dripparticle = new CDripParticle.FallAndLandParticle(worldIn, x, y, z, CFluids.OIL, CParticleTypes.OIL_SPLASH);
			dripparticle.setColor(0.604F, 0.447F, 0.09F);
			dripparticle.pickSprite(this.spriteSet);
			return dripparticle;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class FallingBiableProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public FallingBiableProvider(SpriteSet p_i50503_1_) {
			this.spriteSet = p_i50503_1_;
		}

		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			DripParticle dripparticle = new CDripParticle.FallAndLandParticle(worldIn, x, y, z, CFluids.BIABLE, CParticleTypes.BIABLE_SPLASH);
			dripparticle.setColor(0.0F, 0.49F, 0.671F);
			dripparticle.pickSprite(this.spriteSet);
			return dripparticle;
		}
	}
}