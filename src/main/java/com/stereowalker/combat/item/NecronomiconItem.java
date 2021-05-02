package com.stereowalker.combat.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class NecronomiconItem extends AbstractSpellBookItem {

	public NecronomiconItem(Properties properties) {
		super(properties, 20);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if(entityIn instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)entityIn;
			if (!stack.getTag().contains("Owner")) {
				stack.getTag().putString("Owner", PlayerEntity.getUUID(player.getGameProfile()).toString());
			}
			if (stack.getTag().contains("Owner")) {
				if (!stack.getTag().getString("Owner").contentEquals(PlayerEntity.getUUID(player.getGameProfile()).toString())) {
					player.addPotionEffect(new EffectInstance(Effects.WITHER, 20, 20));
					player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 40, 1));
					player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 20, 1));
				}
			}
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		if (entity.world.isRemote) {
			for (PlayerEntity player : entity.world.getPlayers()) {
				if (isOwner(stack, player)) {
					if (player.inventory.addItemStackToInventory(stack)) {
						entity.remove();
					}
				}
			}
		}
		return super.onEntityItemUpdate(stack, entity);
	}
	
	public static boolean isOwner(ItemStack stack, PlayerEntity player) {
		if (!(stack.getTag() == null)) {
			if (stack.getTag().contains("Owner")) {
				if (stack.getTag().getString("Owner").contentEquals(PlayerEntity.getUUID(player.getGameProfile()).toString())) {
					return true;
				}
			}
		}
		return false;
	}

}
