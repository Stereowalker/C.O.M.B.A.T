package com.stereowalker.combat.core.registries;

import com.stereowalker.combat.Combat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class RegistryOverrides {

	public static void overrideAttributes(RegisterHelper<Attribute> registry) {
		override(registry, "generic.armor", (new RangedAttribute("attribute.name.generic.armor", 0.0D, 0.0D, 1024.0D)).setSyncable(true));
	}

	public static void overrideItems(RegisterHelper<Item> registry) {
		override(registry, "diamond_helmet", new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD, (new Item.Properties())));
		override(registry, "diamond_chestplate", new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.CHEST, (new Item.Properties())));
		override(registry, "diamond_leggings", new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.LEGS, (new Item.Properties())));
		override(registry, "diamond_boots", new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.FEET, (new Item.Properties())));

		override(registry, "chainmail_helmet", new ArmorItem(ArmorMaterials.CHAIN, EquipmentSlot.HEAD, (new Item.Properties())));
		override(registry, "chainmail_chestplate", new ArmorItem(ArmorMaterials.CHAIN, EquipmentSlot.CHEST, (new Item.Properties())));
		override(registry, "chainmail_leggings", new ArmorItem(ArmorMaterials.CHAIN, EquipmentSlot.LEGS, (new Item.Properties())));
		override(registry, "chainmail_boots", new ArmorItem(ArmorMaterials.CHAIN, EquipmentSlot.FEET, (new Item.Properties())));

		override(registry, "golden_helmet", new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.HEAD, (new Item.Properties())));
		override(registry, "golden_chestplate", new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.CHEST, (new Item.Properties())));
		override(registry, "golden_leggings", new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.LEGS, (new Item.Properties())));
		override(registry, "golden_boots", new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.FEET, (new Item.Properties())));

		override(registry, "iron_helmet", new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, (new Item.Properties())));
		override(registry, "iron_chestplate", new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.CHEST, (new Item.Properties())));
		override(registry, "iron_leggings", new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.LEGS, (new Item.Properties())));
		override(registry, "iron_boots", new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.FEET, (new Item.Properties())));

		override(registry, "leather_helmet", new DyeableArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, (new Item.Properties())));
		override(registry, "leather_chestplate", new DyeableArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, (new Item.Properties())));
		override(registry, "leather_leggings", new DyeableArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.LEGS, (new Item.Properties())));
		override(registry, "leather_boots", new DyeableArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, (new Item.Properties())));

		override(registry, "netherite_helmet", new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD, (new Item.Properties()).fireResistant()));
		override(registry, "netherite_chestplate", new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.CHEST, (new Item.Properties()).fireResistant()));
		override(registry, "netherite_leggings", new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.LEGS, (new Item.Properties()).fireResistant()));
		override(registry, "netherite_boots", new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.FEET, (new Item.Properties()).fireResistant()));

		override(registry, "turtle_helmet", new ArmorItem(ArmorMaterials.TURTLE, EquipmentSlot.HEAD, (new Item.Properties())));

	}

	public static void overrideBlocks(RegisterHelper<Block> registry) {
	}

	@SuppressWarnings("unused")
//	private static Item override(Block blockIn) {
//		return override(new BlockItem(blockIn, new Item.Properties()));
//	}
//
//	private static Item override(BlockItem blockItemIn) {
//		return override(blockItemIn.getBlock(), blockItemIn);
//	}
//
//	protected static Item override(Block blockIn, Item itemIn) {
//		return override(ForgeRegistries.BLOCKS.getKey(blockIn), itemIn);
//	}

//	public static Item override(RegisterHelper<Item> registry, String name, Item entry) {
//		return override(registry, new ResourceLocation("minecraft",name), entry);
//	}
	
	public static <T> T override(RegisterHelper<T> registry, String name, T entry) {
		return override(registry, new ResourceLocation("minecraft",name), entry);
	}

	public static <T> T override(RegisterHelper<T> registry, ResourceLocation key, T entry) {
		Combat.debug("Patching ["+key+"]");

		if (entry instanceof BlockItem) {
			((BlockItem)entry).registerBlocks(Item.BY_BLOCK, (Item) entry);
		}
		registry.register(key, entry);
		return entry;

	}

}
