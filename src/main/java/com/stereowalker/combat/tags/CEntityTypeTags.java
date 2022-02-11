package com.stereowalker.combat.tags;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;

public class CEntityTypeTags {
	public static final Tag.Named<EntityType<?>> BOSSES = EntityTypeTags.bind("forge:bosses");
	
	public static void init() {
		
	}
}
