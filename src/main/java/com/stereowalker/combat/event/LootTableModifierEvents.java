package com.stereowalker.combat.event;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;

import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class LootTableModifierEvents {
	private static final String SCROLL_LOOT = "chest/scroll";

	private static final List<Pair<ResourceLocation, List<String>>> LOOT_MODIFIERS = Lists.newArrayList(
			Pair.of(LootTables.CHESTS_SPAWN_BONUS_CHEST, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_END_CITY_TREASURE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_SIMPLE_DUNGEON, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_ABANDONED_MINESHAFT, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_NETHER_BRIDGE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_STRONGHOLD_LIBRARY, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_STRONGHOLD_CROSSING, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_STRONGHOLD_CORRIDOR, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_DESERT_PYRAMID, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_JUNGLE_TEMPLE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_JUNGLE_TEMPLE_DISPENSER, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_IGLOO_CHEST, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_WOODLAND_MANSION, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_UNDERWATER_RUIN_SMALL, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_UNDERWATER_RUIN_BIG, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_BURIED_TREASURE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_SHIPWRECK_MAP, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_SHIPWRECK_SUPPLY, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_SHIPWRECK_TREASURE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.CHESTS_PILLAGER_OUTPOST, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.BASTION_TREASURE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.BASTION_OTHER, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.BASTION_BRIDGE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.BASTION_HOGLIN_STABLE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.RUINED_PORTAL, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(LootTables.GAMEPLAY_FISHING_TREASURE, Lists.newArrayList(SCROLL_LOOT))
			);

	@SubscribeEvent
	public static void lootAppend(LootTableLoadEvent evt) {
		LOOT_MODIFIERS.forEach((pair) -> {
			if(evt.getName().equals(pair.getKey())) {
				pair.getValue().forEach((file) -> {
					Combat.debug("Injecting "+file+" in "+pair.getKey());
					evt.getTable().addPool(getInjectPool(file));
				});
			}
		});
	}

	private static LootPool getInjectPool(String entryName) {
		return LootPool.builder().addEntry(getInjectEntry(entryName, 1)).bonusRolls(0.0F, 1.0F).name("combat_inject")
				.build();
	}

	private static LootEntry.Builder<?> getInjectEntry(String name, int weight) {
		ResourceLocation table = Combat.getInstance().location("inject/" + name);
		return TableLootEntry.builder(table).weight(weight);
	}
}
