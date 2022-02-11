package com.stereowalker.combat.world.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NecronomiconItem extends AbstractSpellBookItem {

	public NecronomiconItem(Properties properties) {
		super(properties, 20);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if(entityIn instanceof Player) {
			Player player = (Player)entityIn;
			if (!stack.getTag().contains("Owner")) {
				stack.getTag().putString("Owner", Player.createPlayerUUID(player.getGameProfile()).toString());
			}
			if (stack.getTag().contains("Owner")) {
				if (!stack.getTag().getString("Owner").contentEquals(Player.createPlayerUUID(player.getGameProfile()).toString())) {
					player.addEffect(new MobEffectInstance(MobEffects.WITHER, 20, 20));
					player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 40, 1));
					player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 1));
				}
			}
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		if (entity.level.isClientSide) {
			for (Player player : entity.level.players()) {
				if (isOwner(stack, player)) {
					if (player.getInventory().add(stack)) {
						entity.discard();
					}
				}
			}
		}
		return super.onEntityItemUpdate(stack, entity);
	}
	
	public static boolean isOwner(ItemStack stack, Player player) {
		if (!(stack.getTag() == null)) {
			if (stack.getTag().contains("Owner")) {
				if (stack.getTag().getString("Owner").contentEquals(Player.createPlayerUUID(player.getGameProfile()).toString())) {
					return true;
				}
			}
		}
		return false;
	}

}
