package com.stereowalker.combat.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.stereowalker.combat.world.entity.projectile.ThrownDagger;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentHelper;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class DaggerItem extends TieredItem {
	private final float attackDamage;
	/** Modifiers applied when the item is in the mainhand of a user. */
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;

	public DaggerItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
		super(tier, properties);
		this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public float getDamage() {
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

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.SPEAR;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player) {
			Player entityplayer = (Player)entityLiving;
			if (PlayerSkills.hasSkill(entityplayer, Skills.DAGGER_THROW)) {
				int i = this.getUseDuration(stack) - timeLeft;
				if (i >= 10 - (CEnchantmentHelper.getShortThrowModifier(stack) * 3)) {
					if (!worldIn.isClientSide) {
						stack.hurtAndBreak(1, entityplayer, (p_220009_1_) -> {
							p_220009_1_.broadcastBreakEvent(entityplayer.getUsedItemHand());
						});
						ThrownDagger entityDagger = new ThrownDagger(worldIn, entityplayer, stack);
						entityDagger.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(), 0.0F, 2.5F + (float)0.5F, 1.0F);
						if (entityplayer.getAbilities().instabuild) {
							entityDagger.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
						}

						worldIn.addFreshEntity(entityDagger);
						if (!entityplayer.getAbilities().instabuild) {
							entityplayer.getInventory().removeItem(stack);
						}
					}
					SoundEvent soundevent = SoundEvents.SNOWBALL_THROW;
					worldIn.playSound((Player)null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
					entityplayer.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (PlayerSkills.hasSkill(playerIn, Skills.DAGGER_THROW)) {
			if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
				return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
			} else if (EnchantmentHelper.getRiptide(itemstack) > 0 && !playerIn.isInWaterOrRain()) {
				return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
			} else {
				playerIn.startUsingItem(handIn);
				return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
			}
		} else {
			return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (p_220009_1_) -> {
			p_220009_1_.broadcastBreakEvent(attacker.getUsedItemHand());
		});
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
			stack.hurtAndBreak(2, entityLiving, (p_220009_1_) -> {
				p_220009_1_.broadcastBreakEvent(entityLiving.getUsedItemHand());
			});
		}

		return true;
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState blockIn) {
		return blockIn.getBlock() == Blocks.COBWEB;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
	}
}