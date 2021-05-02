package com.stereowalker.combat.item;

import net.minecraft.util.SoundEvent;

public class PistolItem extends GunItem {

	public PistolItem(int magazineCapacity, float muzzleSpeed, SoundEvent fireSound, SoundEvent reloadSound) {
		super(magazineCapacity, 0, muzzleSpeed, CItems.PISTOL_MAGAZINE, CItems.PISTOL_EMPTY_MAGAZINE, GunType.SEMI_AUTOMATIC, fireSound, reloadSound);
	}

}
