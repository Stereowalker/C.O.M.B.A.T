package com.stereowalker.combat.item;

import com.stereowalker.combat.potion.CEffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoonBootsItem extends ArmorItem implements ILegendaryGear{

	public MoonBootsItem(EquipmentSlotType slots, Properties builder) {
		super(CArmorMaterial.MOON_BOOTS, EquipmentSlotType.FEET, builder);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		legendaryArmorTick(stack, world, player);
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
	public void legendaryAbilityTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot) {
		PlayerEntity player = (PlayerEntity)entityIn;
		player.addPotionEffect(new EffectInstance(CEffects.GRAVITY_MINUS, 20, 82, false, false));
	}

}
