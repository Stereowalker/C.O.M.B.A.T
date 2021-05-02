package com.stereowalker.combat.registries;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.entity.ai.CAttributes;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryOverrides {

	public static void override() {
		Attributes.ARMOR = CAttributes.registerMC("generic.armor", (new RangedAttribute("attribute.name.generic.armor", 0.0D, 0.0D, 1024.0D)).setShouldWatch(true));
	}
	
	public static void overrideAttributes(IForgeRegistry<Attribute> registry) {
		//		registry.register((new RangedAttribute("attribute.name.generic.armor", 0.0D, 0.0D, 1024.0D)).setShouldWatch(true).setRegistryName("minecraft:generic.armor"));
	}

	public static void overrideItems(IForgeRegistry<Item> registry) {
		registry.register(override("diamond_helmet", new ArmorItem(ArmorMaterial.DIAMOND, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("diamond_chestplate", new ArmorItem(ArmorMaterial.DIAMOND, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("diamond_leggings", new ArmorItem(ArmorMaterial.DIAMOND, EquipmentSlotType.LEGS, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("diamond_boots", new ArmorItem(ArmorMaterial.DIAMOND, EquipmentSlotType.FEET, (new Item.Properties()).group(ItemGroup.COMBAT))));

		registry.register(override("chainmail_helmet", new ArmorItem(ArmorMaterial.CHAIN, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("chainmail_chestplate", new ArmorItem(ArmorMaterial.CHAIN, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("chainmail_leggings", new ArmorItem(ArmorMaterial.CHAIN, EquipmentSlotType.LEGS, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("chainmail_boots", new ArmorItem(ArmorMaterial.CHAIN, EquipmentSlotType.FEET, (new Item.Properties()).group(ItemGroup.COMBAT))));

		registry.register(override("golden_helmet", new ArmorItem(ArmorMaterial.GOLD, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("golden_chestplate", new ArmorItem(ArmorMaterial.GOLD, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("golden_leggings", new ArmorItem(ArmorMaterial.GOLD, EquipmentSlotType.LEGS, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("golden_boots", new ArmorItem(ArmorMaterial.GOLD, EquipmentSlotType.FEET, (new Item.Properties()).group(ItemGroup.COMBAT))));

		registry.register(override("iron_helmet", new ArmorItem(ArmorMaterial.IRON, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("iron_chestplate", new ArmorItem(ArmorMaterial.IRON, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("iron_leggings", new ArmorItem(ArmorMaterial.IRON, EquipmentSlotType.LEGS, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("iron_boots", new ArmorItem(ArmorMaterial.IRON, EquipmentSlotType.FEET, (new Item.Properties()).group(ItemGroup.COMBAT))));

		registry.register(override("leather_helmet", new DyeableArmorItem(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("leather_chestplate", new DyeableArmorItem(ArmorMaterial.LEATHER, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("leather_leggings", new DyeableArmorItem(ArmorMaterial.LEATHER, EquipmentSlotType.LEGS, (new Item.Properties()).group(ItemGroup.COMBAT))));
		registry.register(override("leather_boots", new DyeableArmorItem(ArmorMaterial.LEATHER, EquipmentSlotType.FEET, (new Item.Properties()).group(ItemGroup.COMBAT))));

		registry.register(override("netherite_helmet", new ArmorItem(ArmorMaterial.NETHERITE, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT).isImmuneToFire())));
		registry.register(override("netherite_chestplate", new ArmorItem(ArmorMaterial.NETHERITE, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT).isImmuneToFire())));
		registry.register(override("netherite_leggings", new ArmorItem(ArmorMaterial.NETHERITE, EquipmentSlotType.LEGS, (new Item.Properties()).group(ItemGroup.COMBAT).isImmuneToFire())));
		registry.register(override("netherite_boots", new ArmorItem(ArmorMaterial.NETHERITE, EquipmentSlotType.FEET, (new Item.Properties()).group(ItemGroup.COMBAT).isImmuneToFire())));

		registry.register(override("turtle_helmet", new ArmorItem(ArmorMaterial.TURTLE, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT))));

	}

	public static void overrideBlocks(IForgeRegistry<Block> registry) {
	}

	private static Item override(Block blockIn) {
		return override(new BlockItem(blockIn, new Item.Properties()));
	}

	private static Item override(Block blockIn, ItemGroup itemGroupIn) {
		return override(new BlockItem(blockIn, (new Item.Properties()).group(itemGroupIn)));
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
			((BlockItem)entry).addToBlockToItemMap(Item.BLOCK_TO_ITEM, (Item) entry);
		}

		return entry.setRegistryName(key);

	}

}
