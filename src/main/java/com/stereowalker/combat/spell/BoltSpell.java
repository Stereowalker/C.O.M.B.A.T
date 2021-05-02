package com.stereowalker.combat.spell;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.spell.AbstractProjectileSpell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.SpellUtil;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;

public class BoltSpell extends AbstractProjectileSpell {

	protected BoltSpell(SpellCategory category, Rank rank, float cost, int castTime) {
		super(category, rank, cost, false, castTime);
	}

	@Override
	public boolean extensionProgram(LivingEntity caster, Entity target, double strength, Vector3d location, Hand hand) {
		float damage = (this.getRank().ordinal() * 0.5f) + 0.5f;
		if(target instanceof LivingEntity) {
			LivingEntity living = (LivingEntity)target;
			if(this.getCategory() == SpellCategory.LIGHTNING) {
				if(!target.world.isRemote) {
					SpellUtil.lightningAttack(living, caster, (float) (5.0F*strength*damage), true, 5, 5);
					return true;
				}
			}
			else if(this.getCategory() == SpellCategory.FIRE) {
				if(!target.world.isRemote) {
					SpellUtil.fireAttack(living, caster, (float) (5.0F*strength*damage));
					return true;
				}
			}
			else if(this.getCategory() == SpellCategory.EARTH) {
				if(!target.world.isRemote) {
					SpellUtil.earthAttack(living, caster, (float) (5.0F*strength*damage));
					return true;
				}
			}
			else if(this.getCategory() == SpellCategory.WIND) {
				if(!target.world.isRemote) {
					SpellUtil.airAttack(living, caster, (float) (5.0F*strength*damage));
					return true;
				}
			}
			else if(this.getCategory() == SpellCategory.WATER && this == Spells.ICE_SPIKE) {
				if(!target.world.isRemote) {
					SpellUtil.iceAttack(living, caster, (float) (5.0F*strength*damage));
					return true;
				}
			}
			else if(this.getCategory() == SpellCategory.WATER && this == Spells.WATER_BULLET) {
				if(!target.world.isRemote) {
					SpellUtil.waterAttack(living, caster, (float) (5.0F*strength*damage));
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public IParticleData trailParticles() {
		if(this.getCategory() == SpellCategory.LIGHTNING)
			return ParticleTypes.INSTANT_EFFECT;
		else if(this.getCategory() == SpellCategory.FIRE)
			return ParticleTypes.FLAME;
		else if(this.getCategory() == SpellCategory.WIND)
			return ParticleTypes.CLOUD;
		else if(this.equals(Spells.ROCK_SHOT))
			return new BlockParticleData(ParticleTypes.FALLING_DUST, Blocks.COBBLESTONE.getDefaultState());
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
			return SoundEvents.BLOCK_GLASS_BREAK;
		if(this.getCategory() == SpellCategory.WATER && this == Spells.WATER_BULLET)
			return SoundEvents.AMBIENT_UNDERWATER_ENTER;
		else if(this.equals(Spells.ROCK_SHOT))
			return SoundEvents.BLOCK_STONE_BREAK;
		else if(this.getCategory() == SpellCategory.FIRE)
			return SoundEvents.BLOCK_FIRE_EXTINGUISH;
		else if(this.getCategory() == SpellCategory.WIND)
			return SoundEvents.ENTITY_PHANTOM_FLAP;
		else
			return SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT;
	}
}
