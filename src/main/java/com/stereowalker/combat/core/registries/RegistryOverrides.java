package com.stereowalker.combat.core.registries;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryOverrides {

	public static void override() {
		Attributes.ARMOR = CAttributes.registerMC("generic.armor", (new RangedAttribute("attribute.name.generic.armor", 0.0D, 0.0D, 1024.0D)).setSyncable(true));
	}
	
	public static void overrideAttributes(IForgeRegistry<Attribute> registry) {
		//		registry.register((new RangedAttribute("attribute.name.generic.armor", 0.0D, 0.0D, 1024.0D)).setSyncable(true).setRegistryName("minecraft:generic.armor"));
	}

	public static void overrideItems(IForgeRegistry<Item> registry) {
		registry.register(override("diamond_helmet", new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("diamond_chestplate", new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.CHEST, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("diamond_leggings", new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.LEGS, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("diamond_boots", new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.FEET, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));

		registry.register(override("chainmail_helmet", new ArmorItem(ArmorMaterials.CHAIN, EquipmentSlot.HEAD, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("chainmail_chestplate", new ArmorItem(ArmorMaterials.CHAIN, EquipmentSlot.CHEST, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("chainmail_leggings", new ArmorItem(ArmorMaterials.CHAIN, EquipmentSlot.LEGS, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("chainmail_boots", new ArmorItem(ArmorMaterials.CHAIN, EquipmentSlot.FEET, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));

		registry.register(override("golden_helmet", new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.HEAD, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("golden_chestplate", new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.CHEST, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("golden_leggings", new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.LEGS, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("golden_boots", new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.FEET, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));

		registry.register(override("iron_helmet", new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("iron_chestplate", new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.CHEST, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("iron_leggings", new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.LEGS, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("iron_boots", new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.FEET, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));

		registry.register(override("leather_helmet", new DyeableArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("leather_chestplate", new DyeableArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("leather_leggings", new DyeableArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.LEGS, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));
		registry.register(override("leather_boots", new DyeableArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));

		registry.register(override("netherite_helmet", new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT).fireResistant())));
		registry.register(override("netherite_chestplate", new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.CHEST, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT).fireResistant())));
		registry.register(override("netherite_leggings", new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.LEGS, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT).fireResistant())));
		registry.register(override("netherite_boots", new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.FEET, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT).fireResistant())));

		registry.register(override("turtle_helmet", new ArmorItem(ArmorMaterials.TURTLE, EquipmentSlot.HEAD, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT))));

	}

	public static void overrideBlocks(IForgeRegistry<Block> registry) {
	}

	@SuppressWarnings("unused")
	private static Item override(Block blockIn) {
		return override(new BlockItem(blockIn, new Item.Properties()));
	}

	@SuppressWarnings("unused")
	private static Item override(Block blockIn, CreativeModeTab itemGroupIn) {
		return override(new BlockItem(blockIn, (new Item.Properties()).tab(itemGroupIn)));
	}

	private static Item override(BlockItem blockItemIn) {
		return override(blockItemIn.getBlock(), blockItemIn);
	}

	protected static Item override(Block blockIn, Item itemIn) {
		return override(ForgeRegistries.BLOCKS.getKey(blockIn), itemIn);
	}

	public static <T extends ForgeRegistryEntry<T>> T override(String name, T entry) {
		return override(new ResourceLocation("minecraft",name), entry);
	}

	public static <T extends ForgeRegistryEntry<T>> T override(ResourceLocation key, T entry) {
		Combat.debug("Patching ["+key+"]");

		if (entry instanceof BlockItem) {
			((BlockItem)entry).registerBlocks(Item.BY_BLOCK, (Item) entry);
		}

		return entry.setRegistryName(key);

	}

}
