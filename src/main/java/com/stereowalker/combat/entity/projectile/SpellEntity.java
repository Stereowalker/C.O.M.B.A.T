package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.spell.AbstractProjectileSpell;
import com.stereowalker.combat.api.spell.SpellInstance;
import com.stereowalker.combat.entity.CEntityType;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpellEntity extends AbstractMagicProjectileEntity {
	protected static final DataParameter<CompoundNBT> SPELL = EntityDataManager.createKey(AbstractMagicProjectileEntity.class, DataSerializers.COMPOUND_NBT);
	private int duration = 20;
	public boolean hasSetSpell = false;

	public SpellEntity(EntityType<? extends SpellEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public SpellEntity(World worldIn, LivingEntity shooter) {
		super(CEntityType.SPELL, shooter, worldIn);
	}

	public SpellEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.SPELL, x, y, z, worldIn);
	}

	@Override
	protected void registerData() {
		this.dataManager.register(SPELL, new CompoundNBT());
		super.registerData();
	}

	public void setSpell(SpellInstance spell) {
		this.hasSetSpell = true;
		if (!(spell.getSpell() instanceof AbstractProjectileSpell)) {
			Combat.getInstance().LOGGER.error("This Spell "+spell.getSpell()+" Is Not An Instance Of A Projectile Spell");
		}
		this.dataManager.set(SPELL, spell.write(new CompoundNBT()));
	}

	public SpellInstance getSpell() {
		return SpellInstance.read(this.dataManager.get(SPELL));
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
	
	/**
	 * Called to update the entity's position/logic.
	 */
	int tick;
	public void tick() {
		tick++;
		super.tick();
		if (this.world.isRemote && !this.inGround) {
			this.world.addParticle(((AbstractProjectileSpell)this.getSpell().getSpell()).trailParticles(), this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
		}
		if (tick > 4000) {
			this.remove();
		}

	}

	protected void magicHit(LivingEntity living) {
//		this.getSpell().getSpell().setCaster((LivingEntity) this.getShooter());
		this.getSpell().executeExtensionSpell((LivingEntity)this.getShooter(), living);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("Duration")) {
			this.duration = compound.getInt("Duration");
		}
		if (compound.contains("Spell")) {
			this.setSpell(SpellInstance.read(compound.getCompound("Spell")));
		}

	}
	
	public void notifyDataManagerChange(DataParameter<?> key)
	{
		if (SPELL.equals(key))
		{
			this.setSpell(this.getSpell());
		}
		super.notifyDataManagerChange(key);
	}

	/**
	 * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
	 * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
	 */
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("Duration", this.duration);
		compound.put("Spell", this.getSpell().write(new CompoundNBT()));
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}