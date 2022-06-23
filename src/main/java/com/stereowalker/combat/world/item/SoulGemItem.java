package com.stereowalker.combat.world.item;

import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.world.item.Item;

public class SoulGemItem extends Item {
	SpellCategory cat;

	public SoulGemItem(SpellCategory cat, Properties pProperties) {
		super(pProperties);
		this.cat = cat;
	}
	
	public SpellCategory getCat() {
		return cat;
	}

}
