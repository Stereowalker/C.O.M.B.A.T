package com.stereowalker.combat.compat.curios;

import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.item.InventoryItem;
import com.stereowalker.combat.mixinshooks.IBackItemHolder;
import com.stereowalker.rankup.compat.curios.accessories.CuriosAccesories;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;

@EventBusSubscriber(modid = "combat")
public class CuriosEvents {
	@SubscribeEvent
	public static void addEffectsToAccessories(LivingUpdateEvent event) {
		if (ModHelper.isCuriosLoaded() && Config.RPG_COMMON.enableLevelingSystem.get()) {
			if (event.getEntityLiving() instanceof PlayerEntity) {
				CuriosAccesories.addEffectsToAccessory((PlayerEntity) event.getEntityLiving());
			}
		}
	}
	
	@SubscribeEvent
	public static void addEffectsToChestItems(PlayerContainerEvent.Open event) {
		if (ModHelper.isCuriosLoaded() && Config.RPG_COMMON.enableLevelingSystem.get()) {
			if (event.getEntityLiving() instanceof PlayerEntity) {
				CuriosAccesories.addEffectsToChestItems(event.getContainer());
			}
		}
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void modifierTooltip(ItemTooltipEvent event) {
		if (ModHelper.isCuriosLoaded() && Config.RPG_COMMON.enableLevelingSystem.get()) {
			if (event.getEntityLiving() instanceof PlayerEntity) {
				CuriosAccesories.modifierTooltip(event.getToolTip(), event.getItemStack());
			}
		}
	}
	
	@SubscribeEvent
	public static void addModifiers(LivingUpdateEvent event) {
		if (ModHelper.isCuriosLoaded() && Config.RPG_COMMON.enableLevelingSystem.get()) {
			if (event.getEntityLiving() instanceof PlayerEntity) {
				CuriosAccesories.addModifiers((PlayerEntity) event.getEntityLiving());
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
