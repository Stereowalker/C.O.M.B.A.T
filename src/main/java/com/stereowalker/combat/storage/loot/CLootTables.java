package com.stereowalker.combat.storage.loot;

import com.stereowalker.combat.Combat;

import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;

public class CLootTables {
	public static final ResourceLocation CHESTS_ETHERION_TOWER_5 = register("chests/etherion_tower_5");
	public static final ResourceLocation CHESTS_ETHERION_TOWER_6 = register("chests/etherion_tower_6");
	public static final ResourceLocation CHESTS_ETHERION_TOWER_7 = register("chests/etherion_tower_7");
	public static final ResourceLocation CHESTS_ETHERION_TOWER_8 = register("chests/etherion_tower_8");
	public static final ResourceLocation CHESTS_ETHERION_TOWER_9 = register("chests/etherion_tower_9");
	public static final ResourceLocation CHESTS_ETHERION_TOWER_0 = register("chests/etherion_tower_0");
	public static final ResourceLocation CHESTS_ETHERION_TOWER_BLUE_0 = register("chests/etherion_tower_blue_0");

	private static ResourceLocation register(String id) {
		return LootTables.register(Combat.getInstance().location(id));
	}
}
