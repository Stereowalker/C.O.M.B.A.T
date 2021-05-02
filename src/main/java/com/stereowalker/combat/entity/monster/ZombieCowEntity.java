package com.stereowalker.combat.entity.monster;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.stereowalker.combat.entity.CEntityType;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ZombieCowEntity extends MonsterEntity {
	private static final UUID BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier BABY_SPEED_BOOST = new AttributeModifier(BABY_SPEED_BOOST_ID, "Baby speed boost", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
	private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.createKey(ZombieCowEntity.class, DataSerializers.BOOLEAN);
	public ZombieCowEntity(EntityType<? extends ZombieCowEntity> p_i48567_1_, World p_i48567_2_) {
		super(p_i48567_1_, p_i48567_2_);
	}

	public ZombieCowEntity(World worldIn) {
		this(CEntityType.ZOMBIE_COW, worldIn);
	}

	/**
	 * Static predicate for determining whether or not an animal can spawn at the provided location.
	 *  
	 * @param animal The animal entity to be spawned
	 */
	public static boolean canZombieCowSpawn(EntityType<? extends ZombieCowEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
		return worldIn.getBlockState(pos.down()).matchesBlock(Blocks.GRASS_BLOCK);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.WHEAT), false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtGoal(this, CowEntity.class, 8.0F));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		//		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(ZombiePigmanEntity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, CowEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.23F).createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D).createMutableAttribute(Attributes.ARMOR, 2.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	protected void registerData() {
		super.registerData();
		this.getDataManager().register(IS_CHILD, false);
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	protected int getExperiencePoints(PlayerEntity player) {
		if (this.isChild()) {
			this.experienceValue = (int)((float)this.experienceValue * 2.5F);
		}

		return super.getExperiencePoints(player);
	}

	/**
	 * Set whether this zombie is a child.
	 */
	public void setChild(boolean childZombie) {
		this.getDataManager().set(IS_CHILD, childZombie);
		if (this.world != null && !this.world.isRemote) {
			ModifiableAttributeInstance iattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
			iattributeinstance.removeModifier(BABY_SPEED_BOOST);
			if (childZombie) {
				iattributeinstance.applyPersistentModifier(BABY_SPEED_BOOST);
			}
		}
	}

	public void notifyDataManagerChange(DataParameter<?> key) {
		super.notifyDataManagerChange(key);
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_COW_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_COW_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_COW_DEATH;
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return EntityType.ZOMBIE.getLootTable();
	}

	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.UNDEAD;
	}

	public ZombieCowEntity createChild(AgeableEntity ageable) {
		return new ZombieCowEntity(this.world);
	}

	@Override
	public float getEyeHeight(Pose p_213307_1_) {
		return this.isChild() ? this.getHeight() : 1.3F;
	}
	/**
	 * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
	 * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
	 */
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		if (this.isChild()) {
			compound.putBoolean("IsBaby", true);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.getBoolean("IsBaby")) {
			this.setChild(true);
		}

	}

	/**
	 * This method gets called when the entity kills another one.
	 */
	@Override
	public void onKillEntity(ServerWorld serverWorld, LivingEntity entityLivingIn) {
		super.onKillEntity(serverWorld, entityLivingIn);
		if ((serverWorld.getDifficulty() == Difficulty.NORMAL || serverWorld.getDifficulty() == Difficulty.HARD) && entityLivingIn instanceof CowEntity) {
			if (serverWorld.getDifficulty() != Difficulty.HARD && this.rand.nextBoolean()) {
				return;
			}

			CowEntity entityvillager = (CowEntity)entityLivingIn;
			ZombieCowEntity entityzombievillager = new ZombieCowEntity(serverWorld);
			entityzombievillager.copyLocationAndAnglesFrom(entityvillager);
			entityvillager.remove();
			entityzombievillager.setChild(entityvillager.isChild());
			entityzombievillager.setNoAI(entityvillager.isAIDisabled());
			if (entityvillager.hasCustomName()) {
				entityzombievillager.setCustomName(entityvillager.getCustomName());
				entityzombievillager.setCustomNameVisible(entityvillager.isCustomNameVisible());
			}

			serverWorld.addEntity(entityzombievillager);
			serverWorld.playEvent((PlayerEntity)null, 1026, this.getPosition(), 0);
		}

	}


	protected void func_207301_a(boolean p_207301_1_, boolean p_207301_2_, boolean p_207301_3_, boolean p_207301_4_) {
		this.setCanPickUpLoot(p_207301_1_);
		this.setChild(p_207301_3_);
		this.setNoAI(p_207301_4_);
	}

	public class GroupData implements ILivingEntityData {
		public boolean isChild;

		private GroupData(boolean p_i47328_2_) {
			this.isChild = p_i47328_2_;
		}
	}
}