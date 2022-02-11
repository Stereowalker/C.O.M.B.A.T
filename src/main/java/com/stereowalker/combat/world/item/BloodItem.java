package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.entity.CombatEntityStats;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class BloodItem extends Item{

	public BloodItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		Player entityplayer = entityLiving instanceof Player ? (Player)entityLiving : null;
		if (entityplayer == null || !entityplayer.getAbilities().instabuild) {
			stack.shrink(1);
		}

		if (entityplayer instanceof ServerPlayer) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)entityplayer, stack);
		}

		if (CombatEntityStats.isVampire(entityplayer)) {
			entityplayer.getFoodData().eat(20, 6.0F);
		} else {
			entityplayer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1200));
			entityplayer.addEffect(new MobEffectInstance(MobEffects.POISON, 1200, 10));
		}

		if (entityplayer != null) {
			entityplayer.awardStat(Stats.ITEM_USED.get(this));
		}

		if (entityplayer == null || !entityplayer.getAbilities().instabuild) {
			if (stack.isEmpty()) {
				return new ItemStack(Items.GLASS_BOTTLE);
			}

			if (entityplayer != null) {
				entityplayer.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
			}
		}

		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		playerIn.startUsingItem(handIn);
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
	}

}
