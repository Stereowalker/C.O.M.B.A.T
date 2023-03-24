package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.entity.monster.SkeletonMinion;

import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WrathItem extends Item implements LegendaryGear{

	public WrathItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if(!worldIn.isClientSide) {
			if(handIn == InteractionHand.MAIN_HAND) {
				Bat bat = new Bat(EntityType.BAT, worldIn);
				bat.copyPosition(playerIn);
				worldIn.addFreshEntity(bat);
			}
			else {
				SkeletonMinion skull = new SkeletonMinion(CEntityType.SKELETON_MINION, worldIn);
				skull.copyPosition(playerIn);
				skull.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
				skull.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
				skull.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				skull.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
				skull.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				skull.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
				skull.setCustomName(((BaseComponent) playerIn.getDisplayName()).append(new TranslatableComponent("'s Skeleton")));
				skull.setMasterId(playerIn.getUUID());
				worldIn.addFreshEntity(skull);
				skull.setTarget(playerIn.getKillCredit());
				skull.setLastHurtByMob(playerIn.getLastHurtByMob());
			}
		}
		return super.use(worldIn, playerIn, handIn);
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

}
