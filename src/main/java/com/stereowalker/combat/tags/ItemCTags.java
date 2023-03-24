package com.stereowalker.combat.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemCTags {

	public static final TagKey<Item> SPELLBOOKS = bind("combat:spellbook");
	public static final TagKey<Item> BRACELETS = bind("curios:bracelet");
	public static final TagKey<Item> RINGS = bind("curios:ring");
	public static final TagKey<Item> CHARMS = bind("curios:charm");
	public static final TagKey<Item> NECKLACES = bind("curios:necklace");
	public static final TagKey<Item> INGOTS_STEEL = bind("forge:ingots/steel");
	public static final TagKey<Item> INGOTS_BRONZE = bind("forge:ingots/steel");
	public static final TagKey<Item> DOUBLE_EDGE_STRAIGHT_WEAPON = bind("combat:double_edge_straight_weapon");
	public static final TagKey<Item> SINGLE_EDGE_CURVED_WEAPON = bind("combat:single_edge_curved_weapon");
	public static final TagKey<Item> EDGELESS_THRUSTING_WEAPON = bind("combat:edgeless_thrusting_weapon");

	public ItemCTags() {
	}

	private static TagKey<Item> bind(String pName) {
		return ItemTags.create(new ResourceLocation(pName));
	}
}
