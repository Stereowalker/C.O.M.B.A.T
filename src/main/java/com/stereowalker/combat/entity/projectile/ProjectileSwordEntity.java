package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.api.spell.SpellInstance;
import com.stereowalker.combat.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.spell.SpellStats;
import com.stereowalker.combat.util.CDamageSource;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TieredItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ProjectileSwordEntity extends AbstractMagicProjectileEntity {
	private static final DataParameter<Boolean> EJECTED_SWORD = EntityDataManager.createKey(AbstractMagicProjectileEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CONNECTED_TO_SHOOTER = EntityDataManager.createKey(AbstractMagicProjectileEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<ItemStack> THROWN_STACK = EntityDataManager.createKey(AbstractMagicProjectileEntity.class, DataSerializers.ITEMSTACK);
	private static final DataParameter<CompoundNBT> SPELL = EntityDataManager.createKey(AbstractMagicProjectileEntity.class, DataSerializers.COMPOUND_NBT);
	private static final DataParameter<Float> POSITION = EntityDataManager.createKey(AbstractMagicProjectileEntity.class, DataSerializers.FLOAT);
	public ProjectileSwordEntity(EntityType<? extends ProjectileSwordEntity> type, World worldIn) {
		super(type, worldIn);
		this.dataManager.set(CONNECTED_TO_SHOOTER, true);
	}

	public ProjectileSwordEntity(World worldIn, LivingEntity throwerIn) {
		super(CEntityType.PROJECTILE_SWORD, throwerIn, worldIn);
		this.dataManager.set(CONNECTED_TO_SHOOTER, true);
	}

	public ProjectileSwordEntity(World worldIn, double x, double y, double z) {
		super(CEntityType.PROJECTILE_SWORD, x, y, z, worldIn);
		this.dataManager.set(CONNECTED_TO_SHOOTER, true);
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(EJECTED_SWORD, false);
		this.dataManager.register(CONNECTED_TO_SHOOTER, true);
		this.dataManager.register(SPELL, new CompoundNBT());
		this.dataManager.register(THROWN_STACK, new ItemStack(Items.AIR));
		this.dataManager.register(POSITION, 1.0F);
	}

	/**
	 * Handler for {@link World#setEntityState}
	 */
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			for(int i = 0; i < 8; ++i) {
				this.world.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}

	public boolean hasEjectedSword() {
		return this.dataManager.get(EJECTED_SWORD);
	}

	public void setEjectedSword(boolean value) {
		this.dataManager.set(EJECTED_SWORD, value);
	}

	public boolean isConnectedToCaster() {
		return this.dataManager.get(CONNECTED_TO_SHOOTER);
	}

	public void setConnectedToShooter(boolean value) {
		this.dataManager.set(CONNECTED_TO_SHOOTER, value);
	}

	public float getSwordPosition() {
		return this.dataManager.get(POSITION);
	}

	public void setSwordPosition(float value) {
		this.dataManager.set(POSITION, value);
	}

	public ItemStack getSword() {
		return this.dataManager.get(THROWN_STACK);
	}

	public void setSword(ItemStack value) {
		this.dataManager.set(THROWN_STACK, value);
	}

	public SpellInstance getSpellInstance() {
		return SpellInstance.read(this.dataManager.get(SPELL));
	}

	public void setSpell(SpellInstance value) {
		this.dataManager.set(SPELL, value.write(new CompoundNBT()));
	}

	@Override
	public void tick() {
		super.tick();
		PlayerEntity caster = (PlayerEntity)this.getShooter();
		if (caster != null) {
			if (SpellStats.getSpellPrimed(caster, this.getSpellInstance().getSpell()) && !this.hasEjectedSword()) {
				double radius = 2.0d;
				double angle = (caster.rotationYaw + this.getSwordPosition()) % 360;
				double x = caster.getPosX() + (radius * Math.cos(Math.toRadians(angle)));
				double z = caster.getPosZ() + (radius * Math.sin(Math.toRadians(angle)));
				this.setPosition(x, caster.getPosY() + 2.5, z);
				this.setRotation(-caster.rotationYaw, this.rotationPitch+10);
			} else if (this.isConnectedToCaster()) {
				if (!this.world.isRemote) {
					if (caster.isSneaking())
						this.shoot(caster, caster.rotationPitch, caster.rotationYaw, 0.0F, -1.0F, 0);
					else
						this.shoot(caster, caster.rotationPitch, caster.rotationYaw, 0.0F, 1.0F, 0);
				}
				if (this.getPositionVec().distanceTo(caster.getPositionVec()) >= 100) {
					this.setConnectedToShooter(false);
				}
			}
		}
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		Entity entity = result.getEntity();
		if (entity != this.getShooter()) {
			float f;
			if (this.getSword().getItem() instanceof TieredItem) {
				f = 4.0F + ((TieredItem)this.getSword().getItem()).getTier().getAttackDamage() + (float)(CEnchantmentHelper.getPenetrationModifier(this.getSword())/2);
			} else {
				f = 4.0F;
			}
			if (this.isConnectedToCaster() && entity instanceof LivingEntity) {
				f*=this.getSpellInstance().getStrength();
			}
			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity)entity;
				f += EnchantmentHelper.getModifierForCreature(this.getSword(), livingentity.getCreatureAttribute());
			}

			Entity entity1 = this.getShooter();
			DamageSource damagesource = CDamageSource.causeMagicProjectileDamage(this, (Entity)(entity1 == null ? this : entity1));
			//		this.dealtDamage = true;
			SoundEvent soundevent = SoundEvents.ENTITY_ARROW_HIT;
			if (entity.attackEntityFrom(damagesource, f) && entity instanceof LivingEntity) {
				LivingEntity livingentity1 = (LivingEntity)entity;
				if (entity1 instanceof LivingEntity) {
					EnchantmentHelper.applyThornEnchantments(livingentity1, entity1);
					EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity1);
				}

				this.magicHit(livingentity1);
			}

			this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
			float f1 = 1.0F;
			this.playSound(soundevent, f1, 1.0F);
		}
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT;
	}

	@Override
	void magicHit(LivingEntity living) {
		// TODO Auto-generated method stub

	}
}