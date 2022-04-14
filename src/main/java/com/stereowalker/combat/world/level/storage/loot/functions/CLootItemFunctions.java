package com.stereowalker.combat.world.level.storage.loot.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.Combat;

import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class CLootItemFunctions {
	private static List<Pair<String,LootItemFunctionType>> LOOT_FUNCTION_TYPES = new ArrayList<Pair<String,LootItemFunctionType>>();
	
	public static final LootItemFunctionType APPEND_RANDOM_SPELL = register("append_random_spell", new AppendRandomSpellFunction.Serializer());

	private static LootItemFunctionType register(String key, Serializer<? extends LootItemFunction> function) {
		LootItemFunctionType loot = new LootItemFunctionType(function);
		LOOT_FUNCTION_TYPES.add(Pair.of(key, loot));
		return loot;
		
	}
	
	public static void registerAll() {
		for (Pair<String, LootItemFunctionType> type : LOOT_FUNCTION_TYPES) {
			Registry.register(Registry.LOOT_FUNCTION_TYPE, Combat.getInstance().location(type.getKey()), type.getValue());
		}
	}
}
