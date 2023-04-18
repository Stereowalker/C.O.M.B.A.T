package com.stereowalker.combat.world.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.stereowalker.combat.Combat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class CMenuType {
	public static final Map<ResourceLocation,MenuType<?>> CONTAINERTYPES = new HashMap<ResourceLocation,MenuType<?>>();
	public static final MenuType<CardboardBoxMenu> CARDBOARD_BOX =  register("cardboard_box", CardboardBoxMenu::new);
	public static final MenuType<AlloyFurnaceMenu> ALLOY_FURNACE = register("alloy_furnace", AlloyFurnaceMenu::new);
	public static final MenuType<ArcaneWorkbenchMenu> ARCANE_WORKBENCH = register("arcane_workbench", ArcaneWorkbenchMenu::new);
	public static final MenuType<ManaGeneratorMenu> MANA_GENERATOR = register("mana_generator", ManaGeneratorMenu::new);
	public static final MenuType<BatteryChargerMenu> BATTERY_CHARGER = register("battery_charger", BatteryChargerMenu::new);
	public static final MenuType<ElectricFurnaceMenu> ELECTRIC_FURNACE = register("electric_furnace", ElectricFurnaceMenu::new);
	public static final MenuType<WoodcutterMenu> WOODCUTTER = register("woodcutter", WoodcutterMenu::new);
	public static final MenuType<FletchingMenu> FLETCHING = register("fletching", FletchingMenu::new);
	public static final MenuType<DisenchantmentMenu> DISENCHANTMENT = register("disenchantment", DisenchantmentMenu::new);
	public static final MenuType<QuiverMenu> QUIVER = register("quiver", QuiverMenu::new);
	public static final MenuType<BackpackMenu> BACKPACK = register("backpack", BackpackMenu::new);
	public static final MenuType<SheathMenu> SHEATH = register("sheath", SheathMenu::new);

	public static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> factory) {
		MenuType<T> container = new MenuType<>(factory);
		CONTAINERTYPES.put(Combat.getInstance().location(name), container);
		return container;
	}

	public static void registerAll(RegisterHelper<MenuType<?>> registry) {
		for(Entry<ResourceLocation, MenuType<?>> container : CONTAINERTYPES.entrySet()) {
			registry.register(container.getKey(), container.getValue());
			Combat.debug("Menu: \""+container.getKey().toString()+"\" registered");
		}
		Combat.debug("All Menus Registered");
	}

}
