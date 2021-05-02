package com.stereowalker.combat.item;

import java.util.function.Supplier;

import com.stereowalker.combat.Combat;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum CArmorMaterial implements IArmorMaterial
{
	MOON_BOOTS("moon_boots", 100, new int[] {9,9,9,9}, 100, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 9, 0.0F, () -> {
		return null;
	}),
	SOUL("soul", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0.0F, () -> {
		return null;
	}),
	//	COPPER("copper", 7, new int[] {2, 5, 6, 2}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, CItems.COPPER_INGOT),
	BRONZE("bronze", 12, new int[] {2, 5, 6, 2}, 11, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0.0F, () -> {
		return Ingredient.fromItems(CItems.BRONZE_INGOT);
	}),
	STEEL("steel", 19, new int[]{3, 7, 8, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
		return Ingredient.fromItems(CItems.STEEL_INGOT);
	}),
	PASQUEM("pasquem", 20, new int[] {4, 7, 8, 5}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, 0.4F, () -> {
		return Ingredient.fromItems(CItems.PASQUEM_INGOT);
	}),
	PELGAN("pelgan", 15, new int[]{3, 8, 9, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5F, 0.1F, () -> {
		return Ingredient.fromItems(CItems.PELGAN_INGOT);
	}),
	SERABLE("serable", 25, new int[]{2, 4, 5, 3}, 35, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F, 0.2F, () -> {
		return Ingredient.fromItems(CItems.SERABLE_INGOT);
	}),
	LOZYNE("lozyne", 50, new int[]{5, 9, 12, 5}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, 0.3F, () -> {
		return Ingredient.fromItems(CItems.LOZYNE_INGOT);
	}),
	ETHERION("etherion", 75, new int[]{7, 12, 14, 7}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 5.0F, 0.5F, () -> {
		return Ingredient.fromItems(CItems.ETHERION_INGOT);
	});

	private static final int[] max_damage_array = new int[] {13, 15, 16, 11};
	private String name;
	private SoundEvent equipSound;
	private int durability, enchantability;
	private int[] damageReductionAmounts;
	private float toughness;
	private final float knockbackResistance;
	private LazyValue<Ingredient> repairMaterial;

	private CArmorMaterial(String name, int durability, int[] damageReductionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) 
	{
		this.damageReductionAmounts = damageReductionAmounts;
		this.durability = durability;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.name = name;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairMaterial = new LazyValue<>(repairMaterial);
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slot) 
	{
		return this.damageReductionAmounts[slot.getIndex()];
	}

	@Override
	public int getDurability(EquipmentSlotType slot) 
	{
		return max_damage_array[slot.getIndex()] * this.durability;
	}

	@Override
	public int getEnchantability() 
	{
		return this.enchantability;
	}

	@Override
	public String getName() 
	{
		return Combat.getInstance().locationString(this.name);
	}

	@Override
	public Ingredient getRepairMaterial() 
	{
		return this.repairMaterial.getValue();
	}

	@Override
	public SoundEvent getSoundEvent() 
	{
		return this.equipSound;
	}

	@Override
	public float getToughness() 
	{
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return knockbackResistance;
	}

}
