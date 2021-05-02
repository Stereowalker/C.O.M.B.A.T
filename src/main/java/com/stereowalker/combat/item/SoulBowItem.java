package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.entity.projectile.SoulArrowEntity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class SoulBowItem extends Item {
	private int tick = 0;
	public SoulBowItem(Item.Properties builder) {
		super(builder);
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse button).
	 */
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity entityplayer = (PlayerEntity)entityLiving;

			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, true);
			if (i < 0) return;

			float f = getArrowVelocity(i);
			if (!((double)f < 0.1D)) {
				if (!worldIn.isRemote) {
					AbstractArrowEntity entityarrow = new SoulArrowEntity(worldIn, entityLiving);
					entityarrow = this.customizeArrow(entityarrow);
					entityarrow.setDirectionAndMovement(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);
					if (f == 1.0F) {
						entityarrow.setIsCritical(true);
					}

					entityarrow.setDamage(entityplayer.getAttribute(CAttributes.MAGIC_STRENGTH).getValue() * 2.0D);

					int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
					if (k > 0) {
						entityarrow.setKnockbackStrength(k);
					}

					if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
						entityarrow.setFire(100);
					}

					stack.damageItem(1, entityplayer, (p_220009_1_) -> {
						p_220009_1_.sendBreakAnimation(entityplayer.getActiveHand());
					});

					worldIn.addEntity(entityarrow);
				}

				worldIn.playSound((PlayerEntity)null, entityplayer.getPosX(), entityplayer.getPosY(), entityplayer.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

				entityplayer.addStat(Stats.ITEM_USED.get(this));
			}
		}
	}

	/**
	 * Gets the velocity of the arrow entity from the bow's charge
	 */
	public static float getArrowVelocity(int charge) {
		float f = (float)charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, true);
		if (ret != null) return ret;

		playerIn.setActiveHand(handIn);
		return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getItemEnchantability() {
		return 1;
	}

	public AbstractArrowEntity customizeArrow(AbstractArrowEntity arrow) {
		return arrow;
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)entityIn;
			if(!worldIn.isRemote && !player.abilities.isCreativeMode) {
				if((float)(stack.getUseDuration() - player.getItemInUseCount()) / 20.0F == 3600.0F) {
					tick++;
					if (tick > 20) {
						tick = 0;
						if (!CombatEntityStats.addMana(player, -0.02F)) {
							stack.damageItem((stack.getMaxDamage()-stack.getDamage()), player, (p_220009_1_) -> {
								p_220009_1_.sendBreakAnimation(player.getActiveHand());
							});
						}
					}
				}
			}
		}
	}
}