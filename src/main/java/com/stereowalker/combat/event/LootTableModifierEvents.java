package com.stereowalker.combat.event;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class LootTableModifierEvents {
	private static final String SCROLL_LOOT = "chest/scroll";

	private static final List<Pair<ResourceLocation, List<String>>> LOOT_MODIFIERS = Lists.newArrayList(
			Pair.of(BuiltInLootTables.SPAWN_BONUS_CHEST, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.END_CITY_TREASURE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.SIMPLE_DUNGEON, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.ABANDONED_MINESHAFT, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.NETHER_BRIDGE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.STRONGHOLD_LIBRARY, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.STRONGHOLD_CROSSING, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.STRONGHOLD_CORRIDOR, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.DESERT_PYRAMID, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.JUNGLE_TEMPLE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.IGLOO_CHEST, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.WOODLAND_MANSION, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.UNDERWATER_RUIN_SMALL, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.UNDERWATER_RUIN_BIG, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.BURIED_TREASURE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.SHIPWRECK_MAP, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.SHIPWRECK_SUPPLY, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.SHIPWRECK_TREASURE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.PILLAGER_OUTPOST, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.BASTION_TREASURE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.BASTION_OTHER, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.BASTION_BRIDGE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.BASTION_HOGLIN_STABLE, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.RUINED_PORTAL, Lists.newArrayList(SCROLL_LOOT)),
			Pair.of(BuiltInLootTables.FISHING_TREASURE, Lists.newArrayList(SCROLL_LOOT))
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
		return LootPool.lootPool().add(getInjectEntry(entryName, 1)).setBonusRolls(UniformGenerator.between(0.0F, 1.0F)).name("combat_inject")
				.build();
	}

	private static LootPoolEntryContainer.Builder<?> getInjectEntry(String name, int weight) {
		ResourceLocation table = Combat.getInstance().location("inject/" + name);
		return LootTableReference.lootTableReference(table).setWeight(weight);
	}
}
