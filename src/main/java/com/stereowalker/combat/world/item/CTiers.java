package com.stereowalker.combat.world.item;

import java.util.function.Supplier;

import com.stereowalker.combat.tags.CTags.ItemCTags;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum CTiers implements Tier
{
//	COPPER(2, 158, 6.0F, 1.5F, 19, CItems.COPPER_INGOT),
	BRONZE(2, 213, 5.1F, 2.3F, 18, () -> {
	      return Ingredient.of(ItemCTags.INGOTS_BRONZE);
	   }),
	STEEL(2, 314, 7.5F, 2.5F, 11, () -> {
	      return Ingredient.of(ItemCTags.INGOTS_STEEL);
	   }),
	MAGISTEEL(2, 282, 6.7F, 2.2F, 26, () -> {
	      return Ingredient.of(CItems.MAGISTEEL_INGOT);
	   }),
	PASQUEM(3, 1750, 10.0F, 3.5F, 5, () -> {
	      return Ingredient.of(CItems.PASQUEM_INGOT);
	   }),
	PELGAN(2, 125, 9.0F, 1.0F, 21, () -> {
	      return Ingredient.of(CItems.PELGAN_INGOT);
	   }),
	SERABLE(1, 515, 18.5F, 0.5F, 30, () -> {
	      return Ingredient.of(CItems.SERABLE_INGOT);
	   }),
	LOZYNE(3, 2342, 12.0F, 4.5F, 15, () -> {
	      return Ingredient.of(CItems.LOZYNE_INGOT);
	   }),
	//Endgame Metals
	MYTHRIL(2, 5513, 7.1F, 4.0F, 3, () -> {
		return Ingredient.of(CItems.MYTHRIL_INGOT);
	}),
	POWERED_MYTHRIL(4, 5513, 14.2F, 8.0F, 3, () -> {
		return Ingredient.of(CItems.MYTHRIL_INGOT);
	}),
	ETHERION(4, 7286, 16.9F, 6.1F, 29, () -> {
	      return Ingredient.of(CItems.ETHERION_INGOT);
	   }),
	BLOOD(4, 3513, 18.0F, 6.75F, 23, () -> {
	      return Ingredient.EMPTY;
	   }),
	LIGHT_SABER(4, 100, 18.0F, 5.0F, 23, () -> {
	      return Ingredient.EMPTY;
	   });
	
	private float attackDamage, efficiency;
	private int maxUses, harvestLevel, enchantability;
	private final LazyLoadedValue<Ingredient> repairMaterial;
	private CTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterialIn) 
	{
		this.attackDamage = attackDamage;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.enchantability = enchantability;
		this.harvestLevel = harvestLevel;
		this.repairMaterial = new LazyLoadedValue<>(repairMaterialIn);
	}
	@Override
	public float getAttackDamageBonus() 
	{
		return this.attackDamage;
	}
	@Override
	public float getSpeed() 
	{
		return this.efficiency;
	}
	@Override
	public int getEnchantmentValue() 
	{
		return this.enchantability;
	}
	@Override
	public int getLevel() 
	{
		return this.harvestLevel;
	}
	@Override
	public int getUses() 
	{
		return this.maxUses;
	}
	@Override
	public Ingredient getRepairIngredient() 
	{
		return this.repairMaterial.get();
	}
}
