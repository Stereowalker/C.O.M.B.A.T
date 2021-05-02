package com.stereowalker.combat.item;

import com.stereowalker.combat.item.ArchSourceItem.ArchType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArchItem extends Item implements ILegendaryGear{
	@SuppressWarnings("unused")
	private float arrow_color;
	public ArchItem(Item.Properties builder) {
		super(builder);
	}

	private ItemStack findAmmo(PlayerEntity player) {
		if (this.isEnergySource(player.getHeldItem(Hand.OFF_HAND))) {
			return player.getHeldItem(Hand.OFF_HAND);
		} else if (this.isEnergySource(player.getHeldItem(Hand.MAIN_HAND))) {
			return player.getHeldItem(Hand.MAIN_HAND);
		} else {
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);
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
		if(item.getARType() == ArchType.EXPLOSIVE) {
			return 0.4F;
		}
		else if(item.getARType() == ArchType.FLAME) {
			return 0.5F;
		}
		else {
			return 0.6F;
		}
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse button).
	 */
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity entityplayer = (PlayerEntity)entityLiving;
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
					boolean flag1 = entityplayer.abilities.isCreativeMode || (itemstack.getItem() instanceof ArchSourceItem);
					if (!worldIn.isRemote) {
						ArchSourceItem itemarrow = (ArchSourceItem)(itemstack.getItem() instanceof ArchSourceItem ? itemstack.getItem() : CItems.ARCH_FLAME_GEM);
						this.arrow_color = getArrowColor(itemarrow);
						AbstractArrowEntity entityarrow = itemarrow.createArrow(worldIn, entityplayer, itemarrow.getARType());
						entityarrow.setDirectionAndMovement(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);
						if (f == 1.0F) {
							entityarrow.setIsCritical(true);
						}

						if (flag1 || entityplayer.abilities.isCreativeMode && (itemstack.getItem() == CItems.ARCH_EXPLOSION_GEM || itemstack.getItem() == CItems.ARCH_FREEZE_GEM || itemstack.getItem() == CItems.ARCH_TELEPORT_GEM)) {
							entityarrow.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
						}

						worldIn.addEntity(entityarrow);
					}

					worldIn.playSound((PlayerEntity)null, entityplayer.getPosX(), entityplayer.getPosY(), entityplayer.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

					entityplayer.addStat(Stats.ITEM_USED.get(this));
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
		boolean flag = !this.findAmmo(playerIn).isEmpty();

		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
		if (ret != null) return ret;

		if (!playerIn.abilities.isCreativeMode && !flag) {
			return flag ? new ActionResult<>(ActionResultType.PASS, itemstack) : new ActionResult<>(ActionResultType.FAIL, itemstack);
		} else {
			playerIn.setActiveHand(handIn);
			return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return getRarity();
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		legendaryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public void legendaryAbilityTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot) {
		
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}