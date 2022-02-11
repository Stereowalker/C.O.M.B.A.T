package com.stereowalker.combat.world.level.storage.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;

import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class AppendRandomSpellFunction extends LootItemConditionalFunction {
	private final Rank rank;

	private AppendRandomSpellFunction(LootItemCondition[] condition, Rank rankIn) {
		super(condition);
		this.rank = rankIn;
	}

	@Override
	public LootItemFunctionType getType() {
		return CLootItemFunctions.APPEND_RANDOM_SPELL_WITH_RANK;
	}

	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		Random random = context.getRandom();
		return SpellUtil.addSpellToStack(stack, SpellUtil.getWeightedRandomSpell(random, rank));
		//      return EnchantmentHelper.addRandomEnchantment(random, stack, this.randomLevel.generateInt(random), this.isTreasure);
	}

	public static AppendRandomSpellFunction.Builder appendRandomSpell(Rank rankIn) {
		return new AppendRandomSpellFunction.Builder(rankIn);
	}

	public static class Builder extends LootItemConditionalFunction.Builder<AppendRandomSpellFunction.Builder> {
		private final Rank rankBu;

		public Builder(Rank rankIn) {
			this.rankBu = rankIn;
		}

		@Override
		protected AppendRandomSpellFunction.Builder getThis() {
			return this;
		}

//		public AppendRandomSpell.Builder func_216059_e() {
//			this.isTreasureBu = true;
//			return this;
//		}

		@Override
		public LootItemFunction build() {
			return new AppendRandomSpellFunction(this.getConditions(), this.rankBu);
		}
	}

	public static class Serializer extends LootItemConditionalFunction.Serializer<AppendRandomSpellFunction> {
		@Override
		public void serialize(JsonObject json, AppendRandomSpellFunction function, JsonSerializationContext serializationContext) {
			super.serialize(json, function, serializationContext);
//			json.add("levels", RandomRanges.serialize(function.randomLevel, serializationContext));
			json.addProperty("rank", function.rank.getName());
		}

		@Override
		public AppendRandomSpellFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
//			IRandomRange irandomrange = RandomRanges.deserialize(object.get("levels"), deserializationContext);
			Rank rank = Rank.getRankFromString(GsonHelper.getAsString(object, "rank", Rank.BASIC.getName()));
			return new AppendRandomSpellFunction(conditionsIn, rank);
		}
	}
}
