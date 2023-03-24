package com.stereowalker.combat.world.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DimensionalGateKeyItem extends Item {

	public DimensionalGateKeyItem(Properties pProperties) {
		super(pProperties);
	}
	
	@Override
	public Entity createEntity(Level level, Entity location, ItemStack stack) {
		return new Cow(EntityType.COW, level);
	}

}
