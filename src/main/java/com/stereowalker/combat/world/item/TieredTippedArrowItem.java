package com.stereowalker.combat.world.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TieredTippedArrowItem extends TieredArrowItem {
	public TieredTippedArrowItem(Item.Properties builder, ArrowType type) {
		super(builder, type);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack getDefaultInstance() {
		return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		PotionUtils.addPotionTooltip(stack, tooltip, 0.125F);
	}

	@Override
	public String getDescriptionId(ItemStack stack) {
		return PotionUtils.getPotion(stack).getName(this.getDescriptionId() + ".effect.");
	}

}
