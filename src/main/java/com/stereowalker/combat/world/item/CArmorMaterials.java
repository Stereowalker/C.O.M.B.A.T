package com.stereowalker.combat.world.item;

import java.util.function.Supplier;

import com.stereowalker.combat.Combat;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public enum CArmorMaterials implements ArmorMaterial
{
	MOON_BOOTS("moon_boots", 100, new int[] {9,9,9,9}, 100, SoundEvents.ARMOR_EQUIP_IRON, 9, 0.0F, () -> {
		return null;
	}),
	SOUL("soul", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0, 0.0F, () -> {
		return null;
	}),
	BRONZE("bronze", 12, new int[] {2, 5, 6, 2}, 11, SoundEvents.ARMOR_EQUIP_IRON, 0, 0.0F, () -> {
		return Ingredient.of(CItems.BRONZE_INGOT);
	}),
	STEEL("steel", 19, new int[]{3, 7, 8, 3}, 7, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
		return Ingredient.of(CItems.STEEL_INGOT);
	}),
	PASQUEM("pasquem", 20, new int[] {4, 7, 8, 5}, 5, SoundEvents.ARMOR_EQUIP_IRON, 3.0F, 0.4F, () -> {
		return Ingredient.of(CItems.PASQUEM_INGOT);
	}),
//	PELGAN("pelgan", 15, new int[]{3, 8, 9, 3}, 14, SoundEvents.ARMOR_EQUIP_IRON, 0.5F, 0.1F, () -> {
//		return Ingredient.of(CItems.PELGAN_INGOT);
//	}),
	SERABLE("serable", 25, new int[]{2, 4, 5, 3}, 35, SoundEvents.ARMOR_EQUIP_GOLD, 0.0F, 0.2F, () -> {
		return Ingredient.of(CItems.SERABLE_INGOT);
	}),
	LOZYNE("lozyne", 50, new int[]{5, 9, 12, 5}, 15, SoundEvents.ARMOR_EQUIP_IRON, 3.0F, 0.3F, () -> {
		return Ingredient.of(CItems.LOZYNE_INGOT);
	}),
	ETHERION("etherion", 75, new int[]{7, 12, 14, 7}, 20, SoundEvents.ARMOR_EQUIP_DIAMOND, 5.0F, 0.5F, () -> {
		return Ingredient.of(CItems.ETHERION_INGOT);
	});

	private static final int[] max_damage_array = new int[] {13, 15, 16, 11};
	private String name;
	private SoundEvent equipSound;
	private int durability, enchantability;
	private int[] damageReductionAmounts;
	private float toughness;
	private final float knockbackResistance;
	private LazyLoadedValue<Ingredient> repairMaterial;

	private CArmorMaterials(String name, int durability, int[] damageReductionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) 
	{
		this.damageReductionAmounts = damageReductionAmounts;
		this.durability = durability;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.name = name;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slot) 
	{
		return this.damageReductionAmounts[slot.getIndex()];
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot slot) 
	{
		return max_damage_array[slot.getIndex()] * this.durability;
	}

	@Override
	public int getEnchantmentValue() 
	{
		return this.enchantability;
	}

	@Override
	public String getName() 
	{
		return Combat.getInstance().locationString(this.name);
	}

	@Override
	public Ingredient getRepairIngredient() 
	{
		return this.repairMaterial.get();
	}

	@Override
	public SoundEvent getEquipSound() 
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
