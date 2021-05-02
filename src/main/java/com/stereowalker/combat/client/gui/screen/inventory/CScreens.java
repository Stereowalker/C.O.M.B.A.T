package com.stereowalker.combat.client.gui.screen.inventory;

import com.stereowalker.combat.inventory.container.CContainerType;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CScreens {
	public static final void registerScreens() {
		ScreenManager.registerFactory(CContainerType.CARDBOARD_BOX, CardboardBoxScreen::new);
		ScreenManager.registerFactory(CContainerType.ALLOY_FURNACE, AlloyFurnaceScreen::new);
		ScreenManager.registerFactory(CContainerType.ARCANE_WORKBENCH, ArcaneWorkbenchScreen::new);
		ScreenManager.registerFactory(CContainerType.MANA_GENERATOR, ManaGeneratorScreen::new);
		ScreenManager.registerFactory(CContainerType.ELECTRIC_FURNACE, ElectricFurnaceScreen::new);
		ScreenManager.registerFactory(CContainerType.WOODCUTTER, WoodcutterScreen::new);
		ScreenManager.registerFactory(CContainerType.FLETCHING, FletchingScreen::new);
		ScreenManager.registerFactory(CContainerType.DISENCHANTMENT, DisenchantmentScreen::new);
		ScreenManager.registerFactory(CContainerType.QUIVER, QuiverScreen::new);
		ScreenManager.registerFactory(CContainerType.BACKPACK, BackpackScreen::new);
		ScreenManager.registerFactory(CContainerType.SHEATH, SheathScreen::new);
		ScreenManager.registerFactory(CContainerType.BATTERY_CHARGER, BatteryChargerScreen::new);
	}
}
