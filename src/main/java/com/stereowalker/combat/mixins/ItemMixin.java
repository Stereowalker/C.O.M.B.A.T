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

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.core.EnergyUtils;
import com.stereowalker.combat.world.item.ItemFilters;
import com.stereowalker.combat.world.item.Magisteel;
import com.stereowalker.combat.world.item.Mythril;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolActions;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemLike, net.minecraftforge.common.extensions.IForgeItem {

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Overwrite
	public UseAnim getUseAnimation(ItemStack stack) {
		if (Combat.BATTLE_CONFIG.swordBlocking && ItemFilters.BLOCKABLE_WEAPONS.test(stack)) {
			return UseAnim.BLOCK;
		} else {
			return stack.getItem().isEdible() ? UseAnim.EAT : UseAnim.NONE;
		}
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Overwrite
	public int getUseDuration(ItemStack stack) {
		if (stack.getItem().isEdible()) {
			return this.getFoodProperties().isFastFood() ? 16 : 32;
		} else if (Combat.BATTLE_CONFIG.swordBlocking && ItemFilters.BLOCKABLE_WEAPONS.test(stack)) {
			return 40;
		} else {
			return 0;
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "appendHoverText")
	public void displayEnergy(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn, CallbackInfo info) {
		if (this instanceof Mythril) {
			tooltip.add(EnergyUtils.getEnergyComponent(stack, EnergyUtils.EnergyType.DIVINE_ENERGY));
		}
		if (this instanceof Magisteel) {
			tooltip.add(EnergyUtils.getEnergyComponent(stack, EnergyUtils.EnergyType.MAGIC_ENERGY));
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "inventoryTick")
	public void inventoryTicking(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected, CallbackInfo info) {
		if (this instanceof Mythril) {
			((Mythril)this).handleInInventory(stack, entityIn);
		}
		if (this instanceof Magisteel) {
			((Magisteel)this).handleInInventory(stack, entityIn);
		}
	}
	
	@Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/InteractionResultHolder;pass(Ljava/lang/Object;)Lnet/minecraft/world/InteractionResultHolder;"))
	public InteractionResultHolder<ItemStack> onItemRightClickMixin(Object type, Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		ItemStack otherstack = playerIn.getItemInHand(handIn == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
		if (playerIn.isShiftKeyDown()) {
			if (this instanceof Mythril) {
				if (EnergyUtils.getEnergy(itemstack, EnergyUtils.EnergyType.DIVINE_ENERGY) > 0) {
					((Mythril)this).switchActivity(itemstack, playerIn);
					return InteractionResultHolder.success(itemstack);
				} else {
					return InteractionResultHolder.fail(itemstack);
				}
			} else {
				return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
			}
		} else {
			if (Combat.BATTLE_CONFIG.swordBlocking && !otherstack.canPerformAction(ToolActions.SHIELD_BLOCK) && ItemFilters.BLOCKABLE_WEAPONS.test(itemstack)) {
				playerIn.startUsingItem(handIn);
				return InteractionResultHolder.consume(itemstack);
			} else {
				return InteractionResultHolder.pass(itemstack);
			}
		}
	}

	@Nullable
	@Shadow
	public FoodProperties getFoodProperties() {return null;}

	@Shadow
	public boolean isEdible() {return true;}
}
