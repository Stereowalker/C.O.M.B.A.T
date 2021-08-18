package com.stereowalker.combat.advancements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class ItemChargedTrigger extends AbstractCriterionTrigger<ItemChargedTrigger.Instance> {
	private static final ResourceLocation ID = Combat.getInstance().location("item_electrically_charged");

	public ResourceLocation getId() {
		return ID;
	}

	public ItemChargedTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
		ItemPredicate[] aitempredicate = ItemPredicate.deserializeArray(json.get("items"));
		return new ItemChargedTrigger.Instance(entityPredicate, aitempredicate);
	}

	public void trigger(ServerPlayerEntity player, ItemStack itemIn) {
		this.triggerListeners(player, (instance) -> {
			return instance.test(itemIn);
		});
	}

	public static class Instance extends CriterionInstance {
		private final ItemPredicate[] items;

		public Instance(EntityPredicate.AndPredicate player, ItemPredicate[] items) {
			super(ItemChargedTrigger.ID, player);
			this.items = items;
		}

		public static ItemChargedTrigger.Instance create(EntityPredicate.AndPredicate player, ItemPredicate[] items) {
			return new ItemChargedTrigger.Instance(player, items);
		}

		public boolean test(ItemStack item) {
			for (ItemPredicate pred: this.items) {
				if (pred.test(item)) {
					return !EnergyUtils.isDrained(item, EnergyType.DIVINE_ENERGY);
				}
			}
			return false;
		}

		public JsonObject serialize(ConditionArraySerializer conditions) {
			JsonObject jsonobject = super.serialize(conditions);
			if (this.items.length > 0) {
	            JsonArray jsonarray = new JsonArray();

	            for(ItemPredicate itempredicate : this.items) {
	               jsonarray.add(itempredicate.serialize());
	            }

	            jsonobject.add("items", jsonarray);
	         }
			return jsonobject;
		}
	}
}