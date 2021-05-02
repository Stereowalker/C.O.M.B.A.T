package com.stereowalker.combat.item;

import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.api.spell.Rank;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class StaffItem extends AbstractMagicCastingItem {

	public StaffItem(Properties properties, Rank tier) {
		super(properties, tier, 0.5D, 0);
	}

	@Override
	public boolean canCast(PlayerEntity playerEntity, ItemStack wand) {
		return SpellUtil.canItemCastSpell(wand, AbstractSpellBookItem.getMainSpellBookItem(playerEntity).getCurrentSpell(wand));
	}

}
