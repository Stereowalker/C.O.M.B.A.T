package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.effect.CMobEffects;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AffinityRingItem extends AbstractRingItem {

	public AffinityRingItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public void accessoryTick(Level world, LivingEntity entity, ItemStack stack, int slot) {
		if (this == CItems.PYROMANCER_RING) {
			entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 30, 1, false, false));
		}
		if (this == CItems.HYDROMANCER_RING) {
			entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 30, 1, false, false));
			entity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 30, 1, false, false));
		}
		if (this == CItems.ELECTROMANCER_RING) {
			entity.addEffect(new MobEffectInstance(CMobEffects.INSULATION, 30, 1, false, false));
		}
		if (this == CItems.TERRAMANCER_RING) {
			entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 30, 10, false, false));
		}
		if (this == CItems.AEROMANCER_RING) {
			entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 30, 10, false, false));
		}
		super.accessoryTick(world, entity, stack, slot);
	}

	@Override
	public Component accessoryInformation() {
		if (this == CItems.PYROMANCER_RING) {
			return new TranslatableComponent("Gives you the ability to use fire magic when equipped");
		}
		if (this == CItems.HYDROMANCER_RING) {
			return new TranslatableComponent("Gives you the ability to use water magic when equipped");
		}
		if (this == CItems.ELECTROMANCER_RING) {
			return new TranslatableComponent("Gives you the ability to use lightning magic when equipped");
		}
		if (this == CItems.TERRAMANCER_RING) {
			return new TranslatableComponent("Gives you the ability to use earth magic when equipped");
		}
		if (this == CItems.AEROMANCER_RING) {
			return new TranslatableComponent("Gives you the ability to use wind magic when equipped");
		}
		return null;
	}

}
