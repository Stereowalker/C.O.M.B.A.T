package com.stereowalker.combat.world.item;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StaffItem extends AbstractMagicCastingItem {

	public StaffItem(Properties properties, Rank tier) {
		super(properties, tier, 0.5D, 0);
	}

	@Override
	public boolean canCast(Player playerEntity, ItemStack wand) {
		return SpellUtil.canItemCastSpell(wand, AbstractSpellBookItem.getMainSpellBookItem(playerEntity).getCurrentSpell(wand));
	}

}
