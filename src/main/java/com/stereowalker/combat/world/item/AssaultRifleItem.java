package com.stereowalker.combat.world.item;

import net.minecraft.sounds.SoundEvent;

public class AssaultRifleItem extends GunItem {

	public AssaultRifleItem(int magazineCapacity, int fireRate, float muzzleSpeed, SoundEvent fireSound, SoundEvent reloadSound) {
		super(magazineCapacity, fireRate, muzzleSpeed, CItems.PISTOL_MAGAZINE, CItems.PISTOL_EMPTY_MAGAZINE, GunType.AUTOMATIC, fireSound, reloadSound);
	}

}
