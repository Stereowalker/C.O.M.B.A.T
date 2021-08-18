package com.stereowalker.combat.advancements;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;

public class CCriteriaTriggers {
	public static final List<ICriterionTrigger<?>> TRIGGERS = new ArrayList<ICriterionTrigger<?>>();
	
	public static final ItemChargedTrigger ITEM_ELECTRICALLY_CHARGED = register(new ItemChargedTrigger());
	
	public static <T extends ICriterionTrigger<?>> T register(T criterion) {
	      TRIGGERS.add(criterion);
	      return criterion;
	   }
	
	public static void registerAll() {
		for (ICriterionTrigger<?> trigger : TRIGGERS) {
			CriteriaTriggers.register(trigger);
		}
	}
}
