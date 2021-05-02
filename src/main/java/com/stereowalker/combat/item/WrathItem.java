package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.entity.monster.SkeletonMinionEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WrathItem extends Item implements ILegendaryGear{

	public WrathItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(!worldIn.isRemote) {
			if(handIn == Hand.MAIN_HAND) {
				BatEntity bat = new BatEntity(EntityType.BAT, worldIn);
				bat.copyLocationAndAnglesFrom(playerIn);
				worldIn.addEntity(bat);
			}
			else {
				SkeletonMinionEntity skull = new SkeletonMinionEntity(CEntityType.SKELETON_MINION, worldIn);
				skull.copyLocationAndAnglesFrom(playerIn);
				skull.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
				skull.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
				skull.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				skull.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(Items.IRON_BOOTS));
				skull.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.IRON_HELMET));
				skull.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(Items.IRON_LEGGINGS));
				skull.setCustomName(((TextComponent) playerIn.getDisplayName()).appendSibling(new TranslationTextComponent("'s Skeleton")));
				skull.setOwnerId(playerIn.getUniqueID());
				worldIn.addEntity(skull);
				skull.setAttackTarget(playerIn.getAttackingEntity());
				skull.setRevengeTarget(playerIn.getRevengeTarget());
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
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

}
