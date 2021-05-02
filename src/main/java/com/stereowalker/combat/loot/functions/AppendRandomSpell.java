package com.stereowalker.combat.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.SpellUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.JSONUtils;

public class AppendRandomSpell extends LootFunction {
	private final Rank rank;

	private AppendRandomSpell(ILootCondition[] condition, Rank rankIn) {
		super(condition);
		this.rank = rankIn;
	}

	public LootFunctionType getFunctionType() {
		return CLootFunctionManager.APPEND_RANDOM_SPELL_WITH_RANK;
	}

	public ItemStack doApply(ItemStack stack, LootContext context) {
		Random random = context.getRandom();
		return SpellUtil.addSpellToStack(stack, SpellUtil.getWeightedRandomSpell(random, rank));
		//      return EnchantmentHelper.addRandomEnchantment(random, stack, this.randomLevel.generateInt(random), this.isTreasure);
	}

	public static AppendRandomSpell.Builder func_215895_a(Rank rankIn) {
		return new AppendRandomSpell.Builder(rankIn);
	}

	public static class Builder extends LootFunction.Builder<AppendRandomSpell.Builder> {
		private final Rank rankBu;

		public Builder(Rank rankIn) {
			this.rankBu = rankIn;
		}

		protected AppendRandomSpell.Builder doCast() {
			return this;
		}

//		public AppendRandomSpell.Builder func_216059_e() {
//			this.isTreasureBu = true;
//			return this;
//		}

		public ILootFunction build() {
			return new AppendRandomSpell(this.getConditions(), this.rankBu);
		}
	}

	public static class Serializer extends LootFunction.Serializer<AppendRandomSpell> {
		public void serialize(JsonObject json, AppendRandomSpell function, JsonSerializationContext serializationContext) {
			super.serialize(json, function, serializationContext);
//			json.add("levels", RandomRanges.serialize(function.randomLevel, serializationContext));
			json.addProperty("rank", function.rank.getName());
		}

		public AppendRandomSpell deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
//			IRandomRange irandomrange = RandomRanges.deserialize(object.get("levels"), deserializationContext);
			Rank rank = Rank.getRankFromString(JSONUtils.getString(object, "rank", Rank.BASIC.getName()));
			return new AppendRandomSpell(conditionsIn, rank);
		}
	}
}
