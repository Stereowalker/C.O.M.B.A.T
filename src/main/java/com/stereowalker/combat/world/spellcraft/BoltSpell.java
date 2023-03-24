package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.AbstractProjectileSpell;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class BoltSpell extends AbstractProjectileSpell {

	protected BoltSpell(SpellCategory category, Rank rank, float cost, int castTime) {
		super(category, rank, cost, false, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vec3 location, InteractionHand hand) {
		float damage = (this.getRank().ordinal() * 0.5f) + 0.5f;
		if(target instanceof LivingEntity) {
			LivingEntity living = (LivingEntity)target;
			if(this.getCategory() == SpellCategory.LIGHTNING) {
				if(!target.level.isClientSide) {
					SpellUtil.lightningAttack(living, caster, (float) (5.0F*strength*damage), true, 5, 5);
					return true;
				}
			}
			else if(this.getCategory() == SpellCategory.FIRE) {
				if(!target.level.isClientSide) {
					SpellUtil.fireAttack(living, caster, (float) (5.0F*strength*damage));
					return true;
				}
			}
			else if(this.getCategory() == SpellCategory.EARTH) {
				if(!target.level.isClientSide) {
					SpellUtil.earthAttack(living, caster, (float) (5.0F*strength*damage));
					return true;
				}
			}
			else if(this.getCategory() == SpellCategory.WIND) {
				if(!target.level.isClientSide) {
					SpellUtil.airAttack(living, caster, (float) (5.0F*strength*damage));
					return true;
				}
			}
			else if(this.getCategory() == SpellCategory.WATER && this == Spells.ICE_SPIKE) {
				if(!target.level.isClientSide) {
					SpellUtil.iceAttack(living, caster, (float) (5.0F*strength*damage));
					return true;
				}
			}
			else if(this.getCategory() == SpellCategory.WATER && this == Spells.WATER_BULLET) {
				if(!target.level.isClientSide) {
					SpellUtil.waterAttack(living, caster, (float) (5.0F*strength*damage));
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ParticleOptions trailParticles() {
		if(this.getCategory() == SpellCategory.LIGHTNING)
			return ParticleTypes.INSTANT_EFFECT;
		else if(this.getCategory() == SpellCategory.FIRE)
			return ParticleTypes.FLAME;
		else if(this.getCategory() == SpellCategory.WIND)
			return ParticleTypes.CLOUD;
		else if(this.equals(Spells.ROCK_SHOT))
			return new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.COBBLESTONE.defaultBlockState());
		else if(this.getCategory() == SpellCategory.WATER && this == Spells.ICE_SPIKE)
			return ParticleTypes.ITEM_SNOWBALL;
		else if(this.getCategory() == SpellCategory.WATER && this == Spells.WATER_BULLET)
			return ParticleTypes.FALLING_WATER;
		else
			return ParticleTypes.SMOKE;
	}

	@Override
	public ResourceLocation projectileTexture() {
		if(this.getCategory() == SpellCategory.WATER && this == Spells.ICE_SPIKE)
			return Combat.getInstance().location("textures/entity/projectiles/spells/ice_spike.png");
		else if(this.getCategory() == SpellCategory.WATER && this == Spells.WATER_BULLET)
			return Combat.getInstance().location("textures/entity/projectiles/spells/water_bullet.png");
		else if(this.getCategory() == SpellCategory.FIRE)
			return Combat.getInstance().location("textures/entity/projectiles/spells/fire_bolt.png");
		else if(this.getCategory() == SpellCategory.WIND)
			return Combat.getInstance().location("textures/entity/projectiles/spells/air_bullet.png");
		else if(this.equals(Spells.ROCK_SHOT))
			return Combat.getInstance().location("textures/entity/projectiles/spells/rock_shot.png");
		else
			return Combat.getInstance().location("textures/entity/projectiles/spells/default_projectile.png");
	}

	@Override
	public SoundEvent projectileHitSound() {
		if(this.getCategory() == SpellCategory.WATER && this == Spells.ICE_SPIKE)
			return SoundEvents.GLASS_BREAK;
		if(this.getCategory() == SpellCategory.WATER && this == Spells.WATER_BULLET)
			return SoundEvents.AMBIENT_UNDERWATER_ENTER;
		else if(this.equals(Spells.ROCK_SHOT))
			return SoundEvents.STONE_BREAK;
		else if(this.getCategory() == SpellCategory.FIRE)
			return SoundEvents.FIRE_EXTINGUISH;
		else if(this.getCategory() == SpellCategory.WIND)
			return SoundEvents.PHANTOM_FLAP;
		else
			return SoundEvents.LIGHTNING_BOLT_IMPACT;
	}
}
