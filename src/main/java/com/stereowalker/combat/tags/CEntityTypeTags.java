package com.stereowalker.combat.tags;

import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;

public class CEntityTypeTags {
	public static final ITag.INamedTag<EntityType<?>> BOSSES = EntityTypeTags.getTagById("forge:bosses");
	
	public static void init() {
		
	}
}
