package com.stereowalker.combat.world.entity.boss.robin;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.entity.ai.goal.RangedArchAttackGoal;
import com.stereowalker.combat.world.entity.projectile.ArchArrow;
import com.stereowalker.combat.world.entity.projectile.ThrownSpear;
import com.stereowalker.combat.world.item.ArchSourceItem;
import com.stereowalker.combat.world.item.ArchSourceItem.ArchType;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RobinBoss extends Monster implements RangedAttackMob {
	private static final EntityDataAccessor<Boolean> SWINGING_ARMS = SynchedEntityData.defineId(RobinBoss.class, EntityDataSerializers.BOOLEAN);
	private final ServerBossEvent bossInfo = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_20)).setDarkenScreen(true);
	private final RangedArchAttackGoal<RobinBoss> aiArrowAttack = new RangedArchAttackGoal<>(this, 1.0D, 20, 15.0F);
	private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
		@Override
		public void stop() {
			super.stop();
			RobinBoss.this.setAggressive(false);
		}

		@Override
		public void start() {
			super.start();
			RobinBoss.this.setAggressive(true);
		}
	};
	

	public RobinBoss(EntityType<? extends RobinBoss> p_i50148_1_, Level worldIn) {
		super(p_i50148_1_, worldIn);
	}

	public RobinBoss(Level p_i48555_2_) {
		super(CEntityType.ROBIN, p_i48555_2_);
		this.reassessWeaponGoal();
		this.xpReward = 600;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 200.0D).add(Attributes.MOVEMENT_SPEED, (double)0.25F).add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.ARMOR, 8.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SWINGING_ARMS, false);
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SKELETON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.SKELETON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SKELETON_DEATH;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.SKELETON_STEP;
	}

	public void reassessWeaponGoal() {
		if (this.level != null && !this.level.isClientSide) {
			this.goalSelector.removeGoal(this.aiAttackOnCollide);
			this.goalSelector.removeGoal(this.aiArrowAttack);
			ItemStack itemstack = this.getMainHandItem();
			if (itemstack.getItem() == CItems.ARCH) {
				int i = 20;
				if (this.level.getDifficulty() != Difficulty.HARD) {
					i = 40;
				}
				this.aiArrowAttack.setMinAttackInterval(i);
				this.goalSelector.addGoal(4, this.aiArrowAttack);
			} else {
				this.goalSelector.addGoal(4, this.aiAttackOnCollide);
			}

		}
	}

	public AbstractArrow getArrow(float p_190726_1_) {
		ArchSourceItem.ArchType type;
		int i = this.random.nextInt(4);
		if(i == 1) {
			if (this.level.hasNearbyAlivePlayer(this.getX(), this.getY(), this.getZ(), 5)) {
				type = ArchType.FREEZE;
			} else {
				type = ArchType.EXPLOSIVE;
			}
		} else if(i == 2) {
			if (this.getTarget().isInWaterOrRain()) {
				type = ArchType.TELEPORT;
			} else {
				type = ArchType.FLAME;
			}
		} else if(i == 3){
			type = ArchType.FREEZE;
		} else {
			if (!this.level.hasNearbyAlivePlayer(this.getX(), this.getY(), this.getZ(), 15)) {
				type = ArchType.FREEZE;
			} else {
				type = ArchType.TELEPORT;
			}
		}
		ArchArrow entitytippedarrow = new ArchArrow(this.level, this, type);
		return entitytippedarrow;
	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 */
	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(CItems.ARCH));
	}

	@Override
	public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {
		super.setItemSlot(slotIn, stack);
		if (!this.level.isClientSide && slotIn == EquipmentSlot.MAINHAND) {
			this.reassessWeaponGoal();
		}

	}

	@Override
	public float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.74F;
	}

	@Override
	public double getMyRidingOffset() {
		return -0.6D;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isSwingingArms() {
		return this.entityData.get(SWINGING_ARMS);
	}

	public void setSwingingArms(boolean swingingArms) {
		this.entityData.set(SWINGING_ARMS, swingingArms);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.populateDefaultEquipmentSlots(difficultyIn);
		this.populateDefaultEquipmentEnchantments(difficultyIn);
		this.reassessWeaponGoal();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * difficultyIn.getSpecialMultiplier());
		if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
			LocalDate localdate = LocalDate.now();
			int i = localdate.get(ChronoField.DAY_OF_MONTH);
			int j = localdate.get(ChronoField.MONTH_OF_YEAR);
			if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
				this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
				this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		return spawnDataIn;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.reassessWeaponGoal();
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}

	@Override
	public void setCustomName(@Nullable Component name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}



	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
		if (this.tickCount % 10 == 0) {
			this.heal(0.2F);
		}
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}
	
	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	//	   @Override
	//	public boolean canDespawn() {
	//		return false;
	//	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		AbstractArrow arrowEntity = this.getArrow(distanceFactor);
		double d0 = target.getX() - this.getX();
		double d1 = target.getBoundingBox().minY + (double)(target.getBbHeight() / 3.0F) - arrowEntity.getY();
		double d2 = target.getZ() - this.getZ();
		double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
		arrowEntity.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level.getDifficulty().getId() * 4));
		this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.level.addFreshEntity(arrowEntity);
	}

}
