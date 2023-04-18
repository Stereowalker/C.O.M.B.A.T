package com.stereowalker.combat.world.level.storage.loot.functions;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;

import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class AppendRandomSpellFunction extends LootItemConditionalFunction {
	private final List<Rank> rank;
	private final List<SpellCategory> categories;

	private AppendRandomSpellFunction(LootItemCondition[] condition, List<Rank> rankIn, List<SpellCategory> spellCategoryIn) {
		super(condition);
		this.rank = rankIn;
		this.categories = spellCategoryIn;
	}

	@Override
	public LootItemFunctionType getType() {
		return CLootItemFunctions.APPEND_RANDOM_SPELL;
	}

	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		RandomSource random = context.getRandom();
		return SpellUtil.addSpellToStack(stack, SpellUtil.getWeightedRandomSpell(random, null, categories, rank));
	}

	public static AppendRandomSpellFunction.Builder appendRandomSpell(List<Rank> rankIn, List<SpellCategory> spellCategoryIn) {
		return new AppendRandomSpellFunction.Builder(rankIn, spellCategoryIn);
	}

	public static class Builder extends LootItemConditionalFunction.Builder<AppendRandomSpellFunction.Builder> {
		private final List<Rank> rankBu;
		private final List<SpellCategory> categories;

		public Builder(List<Rank> rankIn, List<SpellCategory> spellCategoryIn) {
			this.rankBu = rankIn;
			this.categories = spellCategoryIn;
		}

		@Override
		protected AppendRandomSpellFunction.Builder getThis() {
			return this;
		}

		@Override
		public LootItemFunction build() {
			return new AppendRandomSpellFunction(this.getConditions(), this.rankBu, this.categories);
		}
	}

	public static class Serializer extends LootItemConditionalFunction.Serializer<AppendRandomSpellFunction> {
		@Override
		public void serialize(JsonObject json, AppendRandomSpellFunction function, JsonSerializationContext serializationContext) {
			super.serialize(json, function, serializationContext);
			if (!function.rank.isEmpty()) {
				JsonArray jsonarray = new JsonArray();
				for(Rank rank : function.rank) {
					jsonarray.add(new JsonPrimitive(rank.getName()));
				}
				json.add("ranks", jsonarray);
			}

			if (!function.categories.isEmpty()) {
				JsonArray jsonarray = new JsonArray();
				for(SpellCategory category : function.categories) {
					jsonarray.add(new JsonPrimitive(category.getName()));
				}
				json.add("categories", jsonarray);
			}
		}

		@Override
		public AppendRandomSpellFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
			List<Rank> list1 = Lists.newArrayList();
			if (object.has("ranks")) {
				for(JsonElement jsonelement : GsonHelper.getAsJsonArray(object, "ranks")) {
					String s = GsonHelper.convertToString(jsonelement, "rank");
					Rank rank = Rank.getRankFromString(s);
					list1.add(rank);
				}
			}
			
			List<SpellCategory> list2 = Lists.newArrayList();
			if (object.has("categories")) {
				for(JsonElement jsonelement : GsonHelper.getAsJsonArray(object, "categories")) {
					String s = GsonHelper.convertToString(jsonelement, "category");
					SpellCategory category = SpellCategory.getCategoryFromString(s);
					list2.add(category);
				}
			}


			return new AppendRandomSpellFunction(conditionsIn, list1, list2);
		}
	}
}
