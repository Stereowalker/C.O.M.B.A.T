package com.stereowalker.combat.advancements;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.advancements.critereon.ItemChargedTrigger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;

public class CCriteriaTriggers {
	public static final List<CriterionTrigger<?>> TRIGGERS = new ArrayList<CriterionTrigger<?>>();
	
	public static final ItemChargedTrigger ITEM_ELECTRICALLY_CHARGED = register(new ItemChargedTrigger());
	
	public static <T extends CriterionTrigger<?>> T register(T criterion) {
	      TRIGGERS.add(criterion);
	      return criterion;
	   }
	
	public static void registerAll() {
		for (CriterionTrigger<?> trigger : TRIGGERS) {
			CriteriaTriggers.register(trigger);
		}
	}
}
