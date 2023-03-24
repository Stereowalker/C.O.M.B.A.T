package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.effect.CMobEffects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoonBootsItem extends ArmorItem implements LegendaryGear{

	public MoonBootsItem(EquipmentSlot slots, Properties builder) {
		super(CArmorMaterials.MOON_BOOTS, EquipmentSlot.FEET, builder);
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);
		legendaryArmorTick(stack, world, player);
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
	public void legendaryAbilityTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot) {
		Player player = (Player)entityIn;
		player.addEffect(new MobEffectInstance(CMobEffects.GRAVITY_MINUS, 20, 82, false, false));
	}

}
