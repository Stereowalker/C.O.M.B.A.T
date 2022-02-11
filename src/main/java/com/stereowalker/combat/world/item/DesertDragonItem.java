package com.stereowalker.combat.world.item;

import java.util.Random;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.stereowalker.combat.world.entity.projectile.Bullet;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DesertDragonItem extends Item implements LegendaryGear {
	private final float attackDamage;
	private final float attackSpeed;
	/** Modifiers applied when the item is in the mainhand of a user. */
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;

	public DesertDragonItem(Item.Properties properties) {
		super(properties);
		this.attackSpeed = 10;
		this.attackDamage = (float)10;
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		Block block = state.getBlock();
		if (block == Blocks.COBWEB) {
			return 15.0F;
		} else {
			Material material = state.getMaterial();
			return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
		}
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	@Override
	public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		return true;
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	@Override
	public boolean isCorrectToolForDrops(BlockState blockIn) {
		return blockIn.getBlock() == Blocks.COBWEB;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
	}

	public AbstractArrow createArrow(Level p_200887_1_, LivingEntity p_200887_3_) {
		Bullet entitytippedarrow = new Bullet(p_200887_1_, p_200887_3_);
		return entitytippedarrow;
	}

	/**
	 * Gets the velocity of the arrow entity from the bow's charge
	 */
	public static float getArrowVelocity(int charge) {
		float f = (float)charge / 20.0F;
		f = 2.0F*((f * f + f * 2.0F) / 3.0F);
		if (f > 2.5F) {
			f = 2.5F;
		}

		return f;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.NONE;
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);

		int i = this.getUseDuration(stack) - 100;
		i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerIn, i, true);


		float f = getArrowVelocity(i);
		if (!((double)f < 0.1D)) {
			boolean flag1 = playerIn.getAbilities().instabuild || stack.getDamageValue() > 1;
			if (!worldIn.isClientSide) {
				AbstractArrow entityarrow = createArrow(worldIn, playerIn);
				entityarrow.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, f * 3.0F, 1.0F);
				if (f == 1.0F) {
					entityarrow.setCritArrow(true);
				}

				if (flag1 || playerIn.getAbilities().instabuild ) {
					entityarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
				}

				worldIn.addFreshEntity(entityarrow);
			}


			worldIn.playSound((Player)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.GUARDIAN_ATTACK, SoundSource.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			playerIn.awardStat(Stats.ITEM_USED.get(this));
		}


		boolean flag2 = stack.getDamageValue() > 0;

		InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(stack, worldIn, playerIn, handIn, flag2);
		if (ret != null) return ret;

		if (!flag2) {
			return flag2 ? new InteractionResultHolder<>(InteractionResult.PASS, stack) : new InteractionResultHolder<>(InteractionResult.FAIL, stack);
		} else {
			playerIn.startUsingItem(handIn);
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return getRarity();
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		legendaryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public void legendaryAbilityTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot) {

	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}