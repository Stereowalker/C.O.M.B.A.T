package com.stereowalker.combat.world.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.damagesource.CDamageSource;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.entity.CombatEntityStats;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Vampire extends Monster
{
	private boolean isBreakDoorsTaskSet;

	public Vampire(EntityType<? extends Vampire> type, Level worldIn)
	{
		super(type, worldIn);
	}

	public Vampire(Level worldIn) {
		this(CEntityType.VAMPIRE, worldIn);
	}

	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(3, new FloatGoal(this));
		this.goalSelector.addGoal(4, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::isBreakDoorsTaskSet));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Villager.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, false, true, (p_213619_0_) -> {
			return !CombatEntityStats.isVampire(p_213619_0_);
		}));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 75.0D).add(Attributes.MOVEMENT_SPEED, 0.3708750033639371360625D).add(Attributes.FOLLOW_RANGE, 70.0D).add(Attributes.ARMOR, 4.0D).add(Attributes.ATTACK_DAMAGE, 6.0D);
	}

	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
	} 

	public boolean isBreakDoorsTaskSet()
	{
		return this.isBreakDoorsTaskSet;
	}

	@Override
	public void aiStep()
	{
		if (this.isAlive()) {
			boolean flag = this.isSunSensitive() && this.isSunBurnTick();
			boolean flag2 = this.isSunBurnTick();
			if (flag) {
				ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
				if (!itemstack.isEmpty()) {
					if (itemstack.isDamageableItem()) {
						itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
						if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
							this.broadcastBreakEvent(EquipmentSlot.HEAD);
							this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
						}
					}

					flag = false;
				}

				if (flag) {
					this.setSecondsOnFire(8);
					this.hurt(CDamageSource.SUNBURNED, 20.0F);
				}
			}
			if(!flag2) {
				this.addEffect(new MobEffectInstance(MobEffects.JUMP, 20, 1, false, false));
				if (this.getHealth() < this.getMaxHealth() && this.getHealth() != 0)
				{
					this.heal(0.5f);
				}
			}
		}
		super.aiStep();
	}

	protected boolean isSunSensitive()
	{
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entityIn)
	{
		boolean flag = super.doHurtTarget(entityIn);

		if (flag)
		{
			float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();

			if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F)
			{
				entityIn.setSecondsOnFire(2 * (int)f);
				this.setHealth(this.getHealth() + 10);
			}
		}

		return flag;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.VILLAGER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.VILLAGER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.VILLAGER_DEATH;
	}

	protected SoundEvent getStepSound()
	{
		return SoundEvents.VILLAGER_AMBIENT;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn)
	{
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Nullable
	@Override
	protected ResourceLocation getDefaultLootTable()
	{
		return null;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
	}

	@Override
	public boolean wasKilled(ServerLevel serverWorld, LivingEntity entityLivingIn)
	{
		boolean flag = super.wasKilled(serverWorld, entityLivingIn);

		if ((serverWorld.getDifficulty() == Difficulty.NORMAL || serverWorld.getDifficulty() == Difficulty.HARD) && entityLivingIn instanceof Villager)
		{
			if (serverWorld.getDifficulty() != Difficulty.HARD && this.random.nextBoolean())
			{
				return flag;
			}

			Villager villagerentity = (Villager)entityLivingIn;
			Vampire vampirevillagerentity = new Vampire(CEntityType.VAMPIRE, serverWorld);
			vampirevillagerentity.copyPosition(villagerentity);
			villagerentity.discard();
			vampirevillagerentity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(vampirevillagerentity.blockPosition()), MobSpawnType.CONVERSION, null, (CompoundTag)null);
			vampirevillagerentity.setNoAi(villagerentity.isNoAi());

			if (villagerentity.hasCustomName())
			{
				vampirevillagerentity.setCustomName(villagerentity.getCustomName());
				vampirevillagerentity.setCustomNameVisible(villagerentity.isCustomNameVisible());
			}

			serverWorld.addFreshEntity(vampirevillagerentity);
			serverWorld.levelEvent((Player)null, 1026, this.blockPosition(), 0);
			flag = false;
		}
		return flag;
	}

//	public boolean processInteract(Player player, Hand hand) {
//		ItemStack bottle = player.getHeldItem(Hand.OFF_HAND);
//		ItemStack dagger = player.getHeldItem(Hand.MAIN_HAND);
//		if (bottle.getItem() == Items.GLASS_BOTTLE && dagger.getItem() instanceof DaggerItem) {
//			if (!player.abilities.isCreativeMode) {
//				bottle.shrink(1);
//			}
//			player.addItemStackToInventory(new ItemStack(CItems.VAMPIRE_BLOOD));
//
//			return true;
//		} else {
//			return false;
//		}
//	}

	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		return stack.getItem() == Items.EGG && this.isBaby() && this.isPassenger() ? false : super.canHoldItem(stack);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		float f = difficultyIn.getSpecialMultiplier();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * f);
		RandomSource source = worldIn.getRandom();
		if (spawnDataIn instanceof Zombie.ZombieGroupData) {
			Zombie.ZombieGroupData zombieentity$groupdata = (Zombie.ZombieGroupData)spawnDataIn;
			if (zombieentity$groupdata.isBaby) {
				if ((double)worldIn.getRandom().nextFloat() < 0.05D) {
					List<Chicken> list = worldIn.getEntitiesOfClass(Chicken.class, this.getBoundingBox().inflate(5.0D, 3.0D, 5.0D), EntitySelector.ENTITY_NOT_BEING_RIDDEN);
					if (!list.isEmpty()) {
						Chicken chickenentity = list.get(0);
						chickenentity.setChickenJockey(true);
						this.startRiding(chickenentity);
					}
				} else if ((double)worldIn.getRandom().nextFloat() < 0.05D) {
					Chicken chickenentity1 = EntityType.CHICKEN.create(this.level);
					chickenentity1.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
					chickenentity1.finalizeSpawn(worldIn, difficultyIn, MobSpawnType.JOCKEY, (SpawnGroupData)null, (CompoundTag)null);
					chickenentity1.setChickenJockey(true);
					worldIn.addFreshEntity(chickenentity1);
					this.startRiding(chickenentity1);
				}
			}

			this.populateDefaultEquipmentSlots(source, difficultyIn);
			this.populateDefaultEquipmentEnchantments(source, difficultyIn);
		}

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
}