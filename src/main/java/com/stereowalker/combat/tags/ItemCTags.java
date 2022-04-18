package com.stereowalker.combat.tags;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class ItemCTags {

	public static final Tag.Named<Item> SPELLBOOKS = ItemTags.bind("combat:spellbook");
	public static final Tag.Named<Item> BRACELETS = ItemTags.bind("curios:bracelet");
	public static final Tag.Named<Item> RINGS = ItemTags.bind("curios:ring");
	public static final Tag.Named<Item> CHARMS = ItemTags.bind("curios:charm");
	public static final Tag.Named<Item> NECKLACES = ItemTags.bind("curios:necklace");
	public static final Tag.Named<Item> INGOTS_STEEL = ItemTags.bind("forge:ingots/steel");
	public static final Tag.Named<Item> INGOTS_BRONZE = ItemTags.bind("forge:ingots/steel");
	public static final Tag.Named<Item> DOUBLE_EDGE_STRAIGHT_WEAPON = ItemTags.bind("combat:double_edge_straight_weapon");
	public static final Tag.Named<Item> SINGLE_EDGE_CURVED_WEAPON = ItemTags.bind("combat:single_edge_curved_weapon");
	public static final Tag.Named<Item> EDGELESS_THRUSTING_WEAPON = ItemTags.bind("combat:edgeless_thrusting_weapon");
	
	public ItemCTags() {
	}
}
