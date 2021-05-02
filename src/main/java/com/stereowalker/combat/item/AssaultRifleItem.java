package com.stereowalker.combat.item;

import net.minecraft.util.SoundEvent;

public class AssaultRifleItem extends GunItem {

	public AssaultRifleItem(int magazineCapacity, int fireRate, float muzzleSpeed, SoundEvent fireSound, SoundEvent reloadSound) {
		super(magazineCapacity, fireRate, muzzleSpeed, CItems.PISTOL_MAGAZINE, CItems.PISTOL_EMPTY_MAGAZINE, GunType.AUTOMATIC, fireSound, reloadSound);
	}

}
