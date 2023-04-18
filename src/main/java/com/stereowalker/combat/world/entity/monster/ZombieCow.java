package com.stereowalker.combat.world.entity.monster;

import java.util.UUID;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ZombieCow extends Monster {
	private static final UUID BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier BABY_SPEED_BOOST = new AttributeModifier(BABY_SPEED_BOOST_ID, "Baby speed boost", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
	private static final EntityDataAccessor<Boolean> IS_CHILD = SynchedEntityData.defineId(ZombieCow.class, EntityDataSerializers.BOOLEAN);
	public ZombieCow(EntityType<? extends ZombieCow> p_i48567_1_, Level p_i48567_2_) {
		super(p_i48567_1_, p_i48567_2_);
	}

	public ZombieCow(Level worldIn) {
		this(CEntityType.ZOMBIE_COW, worldIn);
	}

	/**
	 * Static predicate for determining whether or not an animal can spawn at the provided location.
	 *  
	 * @param animal The animal entity to be spawned
	 */
	public static boolean canZombieCowSpawn(EntityType<? extends ZombieCow> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource random) {
		return worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Cow.class, 8.0F));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		//		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(ZombiePigmanEntity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Cow.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, (double)0.23F).add(Attributes.FOLLOW_RANGE, 35.0D).add(Attributes.ARMOR, 2.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(IS_CHILD, false);
	}

	@Override
	public int getExperienceReward() {
		if (this.isBaby()) {
			this.xpReward = (int)((float)this.xpReward * 2.5F);
		}

		return super.getExperienceReward();
	}

	/**
	 * Set whether this zombie is a child.
	 */
	public void setChild(boolean childZombie) {
		this.getEntityData().set(IS_CHILD, childZombie);
		if (this.level != null && !this.level.isClientSide) {
			AttributeInstance iattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
			iattributeinstance.removeModifier(BABY_SPEED_BOOST);
			if (childZombie) {
				iattributeinstance.addPermanentModifier(BABY_SPEED_BOOST);
			}
		}
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.COW_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.COW_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.COW_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Nullable
	@Override
	protected ResourceLocation getDefaultLootTable() {
		return EntityType.ZOMBIE.getDefaultLootTable();
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	public ZombieCow createChild(AgeableMob ageable) {
		return new ZombieCow(this.level);
	}

	@Override
	public float getEyeHeight(Pose p_213307_1_) {
		return this.isBaby() ? this.getBbHeight() : 1.3F;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (this.isBaby()) {
			compound.putBoolean("IsBaby", true);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.getBoolean("IsBaby")) {
			this.setChild(true);
		}
	}

	/**
	 * This method gets called when the entity kills another one.
	 */
	@Override
	public boolean wasKilled(ServerLevel pLevel, LivingEntity pEntity) {
		boolean flag = super.wasKilled(pLevel, pEntity);
		if ((pLevel.getDifficulty() == Difficulty.NORMAL || pLevel.getDifficulty() == Difficulty.HARD) && pEntity instanceof Cow cow) {
			if (pLevel.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
				return flag;
			}

			ZombieCow entityzombievillager = cow.convertTo(CEntityType.ZOMBIE_COW, false);
			if (entityzombievillager != null) {
				entityzombievillager.setChild(cow.isBaby());
				entityzombievillager.setNoAi(cow.isNoAi());
				if (!this.isSilent()) {
					pLevel.levelEvent((Player)null, 1026, this.blockPosition(), 0);
				}
			}

	            flag = false;
		}

	      return flag;

	}


	protected void func_207301_a(boolean p_207301_1_, boolean p_207301_2_, boolean p_207301_3_, boolean p_207301_4_) {
		this.setCanPickUpLoot(p_207301_1_);
		this.setChild(p_207301_3_);
		this.setNoAi(p_207301_4_);
	}

	public class GroupData implements SpawnGroupData {
		public boolean isBaby;

		private GroupData(boolean p_i47328_2_) {
			this.isBaby = p_i47328_2_;
		}
	}
}