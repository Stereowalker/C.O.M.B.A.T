package com.stereowalker.combat.item;

import com.stereowalker.combat.potion.CEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class AffinityRingItem extends AbstractRingItem {

	public AffinityRingItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public void accessoryTick(World world, LivingEntity entity, ItemStack stack, int slot) {
		if (this == CItems.PYROMANCER_RING) {
			entity.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 30, 1, false, false));
		}
		if (this == CItems.HYDROMANCER_RING) {
			entity.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 30, 1, false, false));
			entity.addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 30, 1, false, false));
		}
		if (this == CItems.ELECTROMANCER_RING) {
			entity.addPotionEffect(new EffectInstance(CEffects.INSULATION, 30, 1, false, false));
		}
		if (this == CItems.TERRAMANCER_RING) {
			entity.addPotionEffect(new EffectInstance(Effects.HASTE, 30, 10, false, false));
		}
		if (this == CItems.AEROMANCER_RING) {
			entity.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 30, 10, false, false));
		}
		super.accessoryTick(world, entity, stack, slot);
	}

	@Override
	public ITextComponent accessoryInformation() {
		if (this == CItems.PYROMANCER_RING) {
			return new TranslationTextComponent("Gives you the ability to use fire magic when equipped");
		}
		if (this == CItems.HYDROMANCER_RING) {
			return new TranslationTextComponent("Gives you the ability to use water magic when equipped");
		}
		if (this == CItems.ELECTROMANCER_RING) {
			return new TranslationTextComponent("Gives you the ability to use lightning magic when equipped");
		}
		if (this == CItems.TERRAMANCER_RING) {
			return new TranslationTextComponent("Gives you the ability to use earth magic when equipped");
		}
		if (this == CItems.AEROMANCER_RING) {
			return new TranslationTextComponent("Gives you the ability to use wind magic when equipped");
		}
		return null;
	}

}
