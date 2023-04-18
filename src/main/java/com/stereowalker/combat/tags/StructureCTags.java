package com.stereowalker.combat.tags;

import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureCTags {
	public static final TagKey<Structure> ETHERION_COMPASS_POINTS = bind("combat:etherion_compass_points");

	public StructureCTags() {
	}

	private static TagKey<Structure> bind(String pName) {
		return StructureTags.create(pName);
	}
}
