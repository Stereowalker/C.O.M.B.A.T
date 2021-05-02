package com.stereowalker.combat.loot.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.Combat;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.registry.Registry;

public class CLootFunctionManager {
	private static List<Pair<String,LootFunctionType>> LOOT_FUNCTION_TYPES = new ArrayList<Pair<String,LootFunctionType>>();
	
	public static final LootFunctionType APPEND_RANDOM_SPELL_WITH_RANK = register("append_random_spell_with_rank", new AppendRandomSpell.Serializer());

	private static LootFunctionType register(String key, ILootSerializer<? extends ILootFunction> function) {
		LootFunctionType loot = new LootFunctionType(function);
		LOOT_FUNCTION_TYPES.add(Pair.of(key, loot));
		return loot;
		
	}
	
	public static void registerAll() {
		for (Pair<String, LootFunctionType> type : LOOT_FUNCTION_TYPES) {
			Registry.register(Registry.LOOT_FUNCTION_TYPE, Combat.getInstance().location(type.getKey()), type.getValue());
		}
	}
}
