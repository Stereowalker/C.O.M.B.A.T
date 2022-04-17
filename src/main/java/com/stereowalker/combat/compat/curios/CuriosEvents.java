package com.stereowalker.combat.compat.curios;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.mixinshooks.IBackItemHolder;
import com.stereowalker.combat.world.item.InventoryItem;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentHelper;
import com.stereowalker.rankup.compat.curios.accessories.CuriosAccesories;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;

public class CuriosEvents {
	@SubscribeEvent
	public static void addEffectsToAccessories(LivingUpdateEvent event) {
		if (ModHelper.isCuriosLoaded() && Combat.RPG_CONFIG.enableLevelingSystem) {
			if (event.getEntityLiving() instanceof Player) {
				CuriosAccesories.addEffectsToAccessory((Player) event.getEntityLiving());
			}
		}
	}
	
	@SubscribeEvent
	public static void addModifiers(LivingUpdateEvent event) {
		if (ModHelper.isCuriosLoaded() && Combat.RPG_CONFIG.enableLevelingSystem) {
			if (event.getEntityLiving() instanceof Player) {
				CuriosAccesories.addModifiers((Player) event.getEntityLiving());
			}
		}
	}
	
	@SubscribeEvent
	public static void addModifiers(DropRulesEvent event) {
		event.addOverride((stack) -> {return CEnchantmentHelper.hasRetaining(stack);}, DropRule.ALWAYS_KEEP);
	}
	
	@SubscribeEvent
	public static void addModifiers(CurioChangeEvent event) {
		if ((Object) event.getEntityLiving() instanceof IBackItemHolder && event.getIdentifier() == "back" && event.getSlotIndex() == 0) {
			IBackItemHolder holder = (IBackItemHolder) (Object) event.getEntityLiving();
			
			if (event.getFrom().getItem() instanceof InventoryItem) {
				((InventoryItem<?>)event.getFrom().getItem()).saveInventory(event.getFrom(), holder.getItemInventory());
			}
			
			if (event.getTo().getItem() instanceof InventoryItem) {
				holder.setItemInventory(((InventoryItem<?>)event.getTo().getItem()).loadInventory(event.getTo()));
			} else {
				holder.setItemInventory(null);
			}
		}
	}
}
