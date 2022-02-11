package com.stereowalker.combat.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.core.EnergyUtils;

import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ItemChargedTrigger extends SimpleCriterionTrigger<ItemChargedTrigger.Instance> {
	private static final ResourceLocation ID = Combat.getInstance().location("item_electrically_charged");

	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public ItemChargedTrigger.Instance createInstance(JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext conditionsParser) {
		ItemPredicate[] aitempredicate = ItemPredicate.fromJsonArray(json.get("items"));
		return new ItemChargedTrigger.Instance(entityPredicate, aitempredicate);
	}

	public void trigger(ServerPlayer player, ItemStack itemIn) {
		this.trigger(player, (instance) -> {
			return instance.matches(itemIn);
		});
	}

	public static class Instance extends AbstractCriterionTriggerInstance {
		private final ItemPredicate[] items;

		public Instance(EntityPredicate.Composite player, ItemPredicate[] items) {
			super(ItemChargedTrigger.ID, player);
			this.items = items;
		}

		public static ItemChargedTrigger.Instance create(EntityPredicate.Composite player, ItemPredicate[] items) {
			return new ItemChargedTrigger.Instance(player, items);
		}

		public boolean matches(ItemStack item) {
			for (ItemPredicate pred: this.items) {
				if (pred.matches(item)) {
					return !EnergyUtils.isDrained(item, EnergyUtils.EnergyType.DIVINE_ENERGY);
				}
			}
			return false;
		}

		@Override
		public JsonObject serializeToJson(SerializationContext conditions) {
			JsonObject jsonobject = super.serializeToJson(conditions);
			if (this.items.length > 0) {
	            JsonArray jsonarray = new JsonArray();

	            for(ItemPredicate itempredicate : this.items) {
	               jsonarray.add(itempredicate.serializeToJson());
	            }

	            jsonobject.add("items", jsonarray);
	         }
			return jsonobject;
		}
	}
}