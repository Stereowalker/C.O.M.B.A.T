package com.stereowalker.combat.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArchItem extends Item implements LegendaryGear {
	@SuppressWarnings("unused")
	private float arrow_color;
	public ArchItem(Item.Properties builder) {
		super(builder);
	}

	private ItemStack findAmmo(Player player) {
		if (this.isEnergySource(player.getItemInHand(InteractionHand.OFF_HAND))) {
			return player.getItemInHand(InteractionHand.OFF_HAND);
		} else if (this.isEnergySource(player.getItemInHand(InteractionHand.MAIN_HAND))) {
			return player.getItemInHand(InteractionHand.MAIN_HAND);
		} else {
			for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
				ItemStack itemstack = player.getInventory().getItem(i);
				if (this.isEnergySource(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	protected boolean isEnergySource(ItemStack stack) {
		return stack.getItem() instanceof ArchSourceItem;
	}

	protected float getArrowColor(ArchSourceItem item) {
		if(item.getARType() == ArchSourceItem.ArchType.EXPLOSIVE) {
			return 0.4F;
		}
		else if(item.getARType() == ArchSourceItem.ArchType.FLAME) {
			return 0.5F;
		}
		else {
			return 0.6F;
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player) {
			Player entityplayer = (Player)entityLiving;
			ItemStack itemstack = this.findAmmo(entityplayer);

			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty());
			if (i < 0) return;

			if (!itemstack.isEmpty()) {
				if (itemstack.isEmpty()) {
					itemstack = new ItemStack(CItems.ARCH_FLAME_GEM);
				}

				float f = getArrowVelocity(i);
				if (!((double)f < 0.1D)) {
					boolean flag1 = entityplayer.getAbilities().instabuild || (itemstack.getItem() instanceof ArchSourceItem);
					if (!worldIn.isClientSide) {
						ArchSourceItem itemarrow = (ArchSourceItem)(itemstack.getItem() instanceof ArchSourceItem ? itemstack.getItem() : CItems.ARCH_FLAME_GEM);
						this.arrow_color = getArrowColor(itemarrow);
						AbstractArrow entityarrow = itemarrow.createArrow(worldIn, entityplayer, itemarrow.getARType());
						entityarrow.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(), 0.0F, f * 3.0F, 1.0F);
						if (f == 1.0F) {
							entityarrow.setCritArrow(true);
						}

						if (flag1 || entityplayer.getAbilities().instabuild && (itemstack.getItem() == CItems.ARCH_EXPLOSION_GEM || itemstack.getItem() == CItems.ARCH_FREEZE_GEM || itemstack.getItem() == CItems.ARCH_TELEPORT_GEM)) {
							entityarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
						}

						worldIn.addFreshEntity(entityarrow);
					}

					worldIn.playSound((Player)null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (worldIn.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

					entityplayer.awardStat(Stats.ITEM_USED.get(this));
				}
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

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		boolean flag = !this.findAmmo(playerIn).isEmpty();

		InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
		if (ret != null) return ret;

		if (!playerIn.getAbilities().instabuild && !flag) {
			return flag ? new InteractionResultHolder<>(InteractionResult.PASS, itemstack) : new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
		} else {
			playerIn.startUsingItem(handIn);
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
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