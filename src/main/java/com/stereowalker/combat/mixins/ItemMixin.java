package com.stereowalker.combat.mixins;

import java.util.List;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.item.IMagisteelItem;
import com.stereowalker.combat.item.IMythrilItem;
import com.stereowalker.combat.item.ItemFilters;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

@Mixin(Item.class)
public abstract class ItemMixin extends net.minecraftforge.registries.ForgeRegistryEntry<Item> implements IItemProvider, net.minecraftforge.common.extensions.IForgeItem {

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Overwrite
	public UseAction getUseAction(ItemStack stack) {
		if (Config.BATTLE_COMMON.swordBlocking.get() && (ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(stack) || ItemFilters.DOUBLE_EDGE_STRAIGHT_WEAPONS.test(stack))) {
			return UseAction.BLOCK;
		} else {
			return stack.getItem().isFood() ? UseAction.EAT : UseAction.NONE;
		}
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Overwrite
	public int getUseDuration(ItemStack stack) {
		if (stack.getItem().isFood()) {
			return this.getFood().isFastEating() ? 16 : 32;
		} else if (Config.BATTLE_COMMON.swordBlocking.get() && (ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(stack) || ItemFilters.DOUBLE_EDGE_STRAIGHT_WEAPONS.test(stack))) {
			return 40;
		} else {
			return 0;
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "addInformation")
	public void displayEnergy(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn, CallbackInfo info) {
		if (this instanceof IMythrilItem) {
			tooltip.add(EnergyUtils.getEnergyComponent(stack, EnergyType.DIVINE_ENERGY));
		}
		if (this instanceof IMagisteelItem) {
			tooltip.add(EnergyUtils.getEnergyComponent(stack, EnergyType.MAGIC_ENERGY));
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "inventoryTick")
	public void inventoryTicking(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected, CallbackInfo info) {
		if (this instanceof IMythrilItem) {
			((IMythrilItem)this).handleInInventory(stack, entityIn);
		}
		if (this instanceof IMagisteelItem) {
			((IMagisteelItem)this).handleInInventory(stack, entityIn);
		}
	}
	
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ActionResult;resultPass(Ljava/lang/Object;)Lnet/minecraft/util/ActionResult;"), method = "onItemRightClick")
	public ActionResult<ItemStack> onItemRightClickMixin(Object type, World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (playerIn.isSneaking()) {
			if (this instanceof IMythrilItem) {
				if (EnergyUtils.getEnergy(itemstack, EnergyType.DIVINE_ENERGY) > 0) {
					((IMythrilItem)this).switchActivity(itemstack, playerIn);
					return ActionResult.resultSuccess(itemstack);
				} else {
					return ActionResult.resultFail(itemstack);
				}
			} else {
				return ActionResult.resultPass(playerIn.getHeldItem(handIn));
			}
		} else {
			if (Config.BATTLE_COMMON.swordBlocking.get() && (ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(itemstack) || ItemFilters.DOUBLE_EDGE_STRAIGHT_WEAPONS.test(itemstack))) {
				playerIn.setActiveHand(handIn);
				return ActionResult.resultConsume(itemstack);
			} else {
				return ActionResult.resultPass(itemstack);
			}
		}
	}

	@Nullable
	@Shadow
	public Food getFood() {return null;}

	@Shadow
	public boolean isFood() {return true;}
}
