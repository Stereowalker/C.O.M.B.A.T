package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.AbstractProjectileSpell;
import com.stereowalker.combat.api.world.spellcraft.SpellInstance;
import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class ProjectileSpell extends AbstractMagicProjectile {
	protected static final EntityDataAccessor<CompoundTag> SPELL = SynchedEntityData.defineId(AbstractMagicProjectile.class, EntityDataSerializers.COMPOUND_TAG);
	private int duration = 20;
	public boolean hasSetSpell = false;

	public ProjectileSpell(EntityType<? extends ProjectileSpell> type, Level worldIn) {
		super(type, worldIn);
	}

	public ProjectileSpell(Level worldIn, LivingEntity shooter) {
		super(CEntityType.SPELL, shooter, worldIn);
	}

	public ProjectileSpell(Level worldIn, double x, double y, double z) {
		super(CEntityType.SPELL, x, y, z, worldIn);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SPELL, new CompoundTag());
	}

	public void setSpell(SpellInstance spell) {
		this.hasSetSpell = true;
		if (!(spell.getSpell() instanceof AbstractProjectileSpell)) {
			Combat.getInstance().getLogger().error("This Spell "+spell.getSpell()+" Is Not An Instance Of A Projectile Spell");
		}
		this.entityData.set(SPELL, spell.write(new CompoundTag()));
	}

	public SpellInstance getSpell() {
		return SpellInstance.read(this.entityData.get(SPELL));
	}

	@Override
	protected SoundEvent getHitSound() {
		if (!hasSetSpell) {
			return null;
		}
		if (this.getSpell().getSpell() instanceof AbstractProjectileSpell) {
			return ((AbstractProjectileSpell)this.getSpell().getSpell()).projectileHitSound();
		} else return null;
	}
	
	@Override
	public void tick() {
		super.tick();
		if (this.level.isClientSide && !this.inGround && this.hasSetSpell) {
			this.level.addParticle(((AbstractProjectileSpell)this.getSpell().getSpell()).trailParticles(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
		}
		if (this.tickCount > 4000) {
			this.discard();
		}

	}

	@Override
	protected void magicHit(LivingEntity living) {
//		this.getSpell().getSpell().setCaster((LivingEntity) this.getShooter());
		this.getSpell().executeExtensionSpell((LivingEntity)this.getShooter(), living);
	}

	@Override
	public void readAdditional(CompoundTag compound) {
		super.readAdditional(compound);
		if (compound.contains("Duration")) {
			this.duration = compound.getInt("Duration");
		}
		if (compound.contains("Spell")) {
			this.setSpell(SpellInstance.read(compound.getCompound("Spell")));
		}

	}
	
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key)
	{
		if (SPELL.equals(key))
		{
			this.setSpell(this.getSpell());
		}
		super.onSyncedDataUpdated(key);
	}

	@Override
	public void writeAdditional(CompoundTag compound) {
		super.writeAdditional(compound);
		compound.putInt("Duration", this.duration);
		compound.put("Spell", this.getSpell().write(new CompoundTag()));
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}