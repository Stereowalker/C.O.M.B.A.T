package com.stereowalker.combat.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

import javax.annotation.Nullable;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.util.CDamageSource;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class VampireEntity extends MonsterEntity
{
	private boolean isBreakDoorsTaskSet;

	public VampireEntity(EntityType<? extends VampireEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	public VampireEntity(World worldIn) {
		this(CEntityType.VAMPIRE, worldIn);
	}

	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(3, new SwimGoal(this));
		this.goalSelector.addGoal(4, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::isBreakDoorsTaskSet));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, false, true, (p_213619_0_) -> {
			return !CombatEntityStats.isVampire(p_213619_0_);
		}));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 75.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3708750033639371360625D).createMutableAttribute(Attributes.FOLLOW_RANGE, 70.0D).createMutableAttribute(Attributes.ARMOR, 4.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0D);
	}

	protected void registerData()
	{
		super.registerData();
	} 

	public boolean isBreakDoorsTaskSet()
	{
		return this.isBreakDoorsTaskSet;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, vampires
	 * use this to react to sunlight and start to burn.
	 */
	public void livingTick()
	{
		if (this.isAlive()) {
			boolean flag = this.shouldBurnInDay() && this.isInDaylight();
			boolean flag2 = this.isInDaylight();
			if (flag) {
				ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.HEAD);
				if (!itemstack.isEmpty()) {
					if (itemstack.isDamageable()) {
						itemstack.setDamage(itemstack.getDamage() + this.rand.nextInt(2));
						if (itemstack.getDamage() >= itemstack.getMaxDamage()) {
							this.sendBreakAnimation(EquipmentSlotType.HEAD);
							this.setItemStackToSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
						}
					}

					flag = false;
				}

				if (flag) {
					this.setFire(8);
					this.attackEntityFrom(CDamageSource.SUNBURNED, 20.0F);
				}
			}
			if(!flag2) {
				this.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 20, 1, false, false));
				if (this.getHealth() < this.getMaxHealth() && this.getHealth() != 0)
				{
					this.heal(0.5f);
				}
			}
		}
		super.livingTick();
	}

	protected boolean shouldBurnInDay()
	{
		return true;
	}

	public boolean attackEntityAsMob(Entity entityIn)
	{
		boolean flag = super.attackEntityAsMob(entityIn);

		if (flag)
		{
			float f = this.world.getDifficultyForLocation(this.getPosition()).getAdditionalDifficulty();

			if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
			{
				entityIn.setFire(2 * (int)f);
				this.setHealth(this.getHealth() + 10);
			}
		}

		return flag;
	}

	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_VILLAGER_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_VILLAGER_HURT;
	}

	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_VILLAGER_DEATH;
	}

	protected SoundEvent getStepSound()
	{
		return SoundEvents.ENTITY_VILLAGER_AMBIENT;
	}

	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.UNDEAD;
	}

	@Nullable
	protected ResourceLocation getLootTable()
	{
		return null;
	}

	/**
	 * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
	 * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
	 */
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
	}

	/**
	 * This method gets called when the entity kills another one.
	 */
	@Override
	public void onKillEntity(ServerWorld serverWorld, LivingEntity entityLivingIn)
	{
		super.onKillEntity(serverWorld, entityLivingIn);

		if ((serverWorld.getDifficulty() == Difficulty.NORMAL || serverWorld.getDifficulty() == Difficulty.HARD) && entityLivingIn instanceof VillagerEntity)
		{
			if (serverWorld.getDifficulty() != Difficulty.HARD && this.rand.nextBoolean())
			{
				return;
			}

			VillagerEntity villagerentity = (VillagerEntity)entityLivingIn;
			VampireEntity vampirevillagerentity = new VampireEntity(CEntityType.VAMPIRE, serverWorld);
			vampirevillagerentity.copyLocationAndAnglesFrom(villagerentity);
			villagerentity.remove();
			vampirevillagerentity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(vampirevillagerentity.getPosition()), SpawnReason.CONVERSION, null, (CompoundNBT)null);
			vampirevillagerentity.setNoAI(villagerentity.isAIDisabled());

			if (villagerentity.hasCustomName())
			{
				vampirevillagerentity.setCustomName(villagerentity.getCustomName());
				vampirevillagerentity.setCustomNameVisible(villagerentity.isCustomNameVisible());
			}

			serverWorld.addEntity(vampirevillagerentity);
			serverWorld.playEvent((PlayerEntity)null, 1026, this.getPosition(), 0);
		}
	}

//	public boolean processInteract(PlayerEntity player, Hand hand) {
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

	public boolean canEquipItem(ItemStack stack)
	{
		return stack.getItem() == Items.EGG && this.isChild() && this.isPassenger() ? false : super.canEquipItem(stack);
	}

	/**
	 * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
	 * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
	 */
	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		float f = difficultyIn.getClampedAdditionalDifficulty();
		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);

		if (spawnDataIn instanceof ZombieEntity.GroupData) {
			ZombieEntity.GroupData zombieentity$groupdata = (ZombieEntity.GroupData)spawnDataIn;
			if (zombieentity$groupdata.isChild) {
				if ((double)worldIn.getRandom().nextFloat() < 0.05D) {
					List<ChickenEntity> list = worldIn.getEntitiesWithinAABB(ChickenEntity.class, this.getBoundingBox().grow(5.0D, 3.0D, 5.0D), EntityPredicates.IS_STANDALONE);
					if (!list.isEmpty()) {
						ChickenEntity chickenentity = list.get(0);
						chickenentity.setChickenJockey(true);
						this.startRiding(chickenentity);
					}
				} else if ((double)worldIn.getRandom().nextFloat() < 0.05D) {
					ChickenEntity chickenentity1 = EntityType.CHICKEN.create(this.world);
					chickenentity1.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
					chickenentity1.onInitialSpawn(worldIn, difficultyIn, SpawnReason.JOCKEY, (ILivingEntityData)null, (CompoundNBT)null);
					chickenentity1.setChickenJockey(true);
					worldIn.addEntity(chickenentity1);
					this.startRiding(chickenentity1);
				}
			}

			this.setEquipmentBasedOnDifficulty(difficultyIn);
			this.setEnchantmentBasedOnDifficulty(difficultyIn);
		}

		if (this.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty()) {
			LocalDate localdate = LocalDate.now();
			int i = localdate.get(ChronoField.DAY_OF_MONTH);
			int j = localdate.get(ChronoField.MONTH_OF_YEAR);
			if (j == 10 && i == 31 && this.rand.nextFloat() < 0.25F) {
				this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
				this.inventoryArmorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.0F;
			}
		}

		return spawnDataIn;
	}
}