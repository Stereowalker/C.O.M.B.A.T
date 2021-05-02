package com.stereowalker.combat.inventory.container;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.IForgeRegistry;

public class CContainerType {
	public static final List<ContainerType<?>> CONTAINERTYPES = new ArrayList<ContainerType<?>>();
	public static final ContainerType<CardboardBoxContainer> CARDBOARD_BOX =  register("cardboard_box", CardboardBoxContainer::new);
	public static final ContainerType<AlloyFurnaceContainer> ALLOY_FURNACE = register("alloy_furnace", AlloyFurnaceContainer::new);
	public static final ContainerType<ArcaneWorkbenchContainer> ARCANE_WORKBENCH = register("arcane_workbench", ArcaneWorkbenchContainer::new);
	public static final ContainerType<ManaGeneratorContainer> MANA_GENERATOR = register("mana_generator", ManaGeneratorContainer::new);
	public static final ContainerType<BatteryChargerContainer> BATTERY_CHARGER = register("battery_charger", BatteryChargerContainer::new);
	public static final ContainerType<ElectricFurnaceContainer> ELECTRIC_FURNACE = register("electric_furnace", ElectricFurnaceContainer::new);
	public static final ContainerType<WoodcutterContainer> WOODCUTTER = register("woodcutter", WoodcutterContainer::new);
	public static final ContainerType<FletchingContainer> FLETCHING = register("fletching", FletchingContainer::new);
	public static final ContainerType<DisenchantmentContainer> DISENCHANTMENT = register("disenchantment", DisenchantmentContainer::new);
	public static final ContainerType<QuiverContainer> QUIVER = register("quiver", QuiverContainer::new);
	public static final ContainerType<BackpackContainer> BACKPACK = register("backpack", BackpackContainer::new);
	public static final ContainerType<SheathContainer> SHEATH = register("sheath", SheathContainer::new);

	public static <T extends Container> ContainerType<T> register(String name, ContainerType.IFactory<T> factory) {
		ContainerType<T> container = new ContainerType<>(factory);
		container.setRegistryName(Combat.getInstance().location(name));
		CONTAINERTYPES.add(container);
		return container;
	}

	public static void registerAll(IForgeRegistry<ContainerType<?>> registry) {
		for(ContainerType<?> container : CONTAINERTYPES) {
			registry.register(container);
			Combat.debug("Container: \""+container.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Containers Registered");
	}

}
