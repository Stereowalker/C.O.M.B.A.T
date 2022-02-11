package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.entity.projectile.SoulArrow;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class SoulBowItem extends BowItem implements SoulConstruct {
	private int tick = 0;
	public SoulBowItem(Properties builder) {
		super(builder);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player) {
			Player entityplayer = (Player)entityLiving;

			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, true);
			if (i < 0) return;

			float f = getPowerForTime(i);
			if (!((double)f < 0.1D)) {
				if (!worldIn.isClientSide) {
					AbstractArrow entityarrow = new SoulArrow(worldIn, entityLiving);
					entityarrow = this.customArrow(entityarrow);
					entityarrow.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(), 0.0F, f * 3.0F, 1.0F);
					if (f == 1.0F) {
						entityarrow.setCritArrow(true);
					}

					entityarrow.setBaseDamage(entityplayer.getAttribute(CAttributes.MAGIC_STRENGTH).getValue() * 2.0D);

					int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
					if (k > 0) {
						entityarrow.setKnockback(k);
					}

					if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
						entityarrow.setSecondsOnFire(100);
					}

					stack.hurtAndBreak(1, entityplayer, (p_220009_1_) -> {
						p_220009_1_.broadcastBreakEvent(entityplayer.getUsedItemHand());
					});

					worldIn.addFreshEntity(entityarrow);
				}

				worldIn.playSound((Player)null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (worldIn.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

				entityplayer.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, pLevel, pPlayer, pHand, true);
		if (ret != null) return ret;
		pPlayer.startUsingItem(pHand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public int getEnchantmentValue() {
		return 1;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof Player) {
			decompose(stack, worldIn, (Player)entityIn);
		}
	}

	@Override
	public boolean pauseOnUse() {
		return true;
	}

	@Override
	public int getDecomposeTick() {
		return tick;
	}

	@Override
	public void tickUp() {
		tick++;
	}

	@Override
	public void resetDecomposeTick() {
		tick = 0;
	}
}