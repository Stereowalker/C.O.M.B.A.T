package com.stereowalker.combat.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class PoisonClensingAmulet extends AbstractNecklaceItem {

	public PoisonClensingAmulet(Properties properties) {
		super(properties);
	}

	@Override
	public void accessoryTick(World world, LivingEntity entity, ItemStack stack, int slot) {
		if (entity.isPotionActive(Effects.POISON)) {
			entity.removePotionEffect(Effects.POISON);
		}
	}

	@Override
	public ITextComponent accessoryInformation() {
		return new TranslationTextComponent("Cleanses the wearer of the poison effect");
	}

}
