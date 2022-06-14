package com.stereowalker.combat.world.item;

import com.stereowalker.unionlib.entity.AccessorySlot.Group;
import com.stereowalker.unionlib.world.item.AccessoryItem;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PoisonClensingAmulet extends AccessoryItem {

	public PoisonClensingAmulet(Properties properties) {
		super(properties, Group.NECK);
	}

	@Override
	public void accessoryTick(Level world, LivingEntity entity, ItemStack stack, int slot) {
		if (entity.hasEffect(MobEffects.POISON)) {
			entity.removeEffect(MobEffects.POISON);
		}
	}

	@Override
	public Component accessoryInformation() {
		return new TranslatableComponent("Cleanses the wearer of the poison effect");
	}

}
