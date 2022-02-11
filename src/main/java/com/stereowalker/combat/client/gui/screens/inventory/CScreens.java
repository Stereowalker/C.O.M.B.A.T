package com.stereowalker.combat.client.gui.screens.inventory;

import com.stereowalker.combat.world.inventory.CMenuType;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CScreens {
	public static final void registerScreens() {
		MenuScreens.register(CMenuType.CARDBOARD_BOX, CardboardBoxScreen::new);
		MenuScreens.register(CMenuType.ALLOY_FURNACE, AlloyFurnaceScreen::new);
		MenuScreens.register(CMenuType.ARCANE_WORKBENCH, ArcaneWorkbenchScreen::new);
		MenuScreens.register(CMenuType.MANA_GENERATOR, ManaGeneratorScreen::new);
		MenuScreens.register(CMenuType.ELECTRIC_FURNACE, ElectricFurnaceScreen::new);
		MenuScreens.register(CMenuType.WOODCUTTER, WoodcutterScreen::new);
		MenuScreens.register(CMenuType.FLETCHING, FletchingScreen::new);
		MenuScreens.register(CMenuType.DISENCHANTMENT, DisenchantmentScreen::new);
		MenuScreens.register(CMenuType.QUIVER, QuiverScreen::new);
		MenuScreens.register(CMenuType.BACKPACK, BackpackScreen::new);
		MenuScreens.register(CMenuType.SHEATH, SheathScreen::new);
		MenuScreens.register(CMenuType.BATTERY_CHARGER, BatteryChargerScreen::new);
	}
}
