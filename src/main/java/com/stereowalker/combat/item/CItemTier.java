package com.stereowalker.combat.item;

import java.util.function.Supplier;

import com.stereowalker.combat.tags.CTags.ItemCTags;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum CItemTier implements IItemTier
{
//	COPPER(2, 158, 6.0F, 1.5F, 19, CItems.COPPER_INGOT),
	BRONZE(2, 213, 5.1F, 2.3F, 18, () -> {
	      return Ingredient.fromTag(ItemCTags.INGOTS_BRONZE);
	   }),
	STEEL(2, 314, 7.5F, 2.5F, 11, () -> {
	      return Ingredient.fromTag(ItemCTags.INGOTS_STEEL);
	   }),
	MAGISTEEL(2, 282, 6.7F, 2.2F, 26, () -> {
	      return Ingredient.fromItems(CItems.MAGISTEEL_INGOT);
	   }),
	PASQUEM(3, 1750, 10.0F, 3.5F, 5, () -> {
	      return Ingredient.fromItems(CItems.PASQUEM_INGOT);
	   }),
	PELGAN(2, 125, 9.0F, 1.0F, 21, () -> {
	      return Ingredient.fromItems(CItems.PELGAN_INGOT);
	   }),
	SERABLE(1, 515, 18.5F, 0.5F, 30, () -> {
	      return Ingredient.fromItems(CItems.SERABLE_INGOT);
	   }),
	LOZYNE(3, 2342, 12.0F, 4.5F, 15, () -> {
	      return Ingredient.fromItems(CItems.LOZYNE_INGOT);
	   }),
	ETHERION(4, 7286, 16.9F, 6.1F, 29, () -> {
	      return Ingredient.fromItems(CItems.ETHERION_INGOT);
	   }),
	BLOOD(4, 3513, 18.0F, 6.75F, 23, null),
	LIGHT_SABER(4, 100, 18.0F, 5.0F, 23, null);
	
	private float attackDamage, efficiency;
	private int maxUses, harvestLevel, enchantability;
	private final LazyValue<Ingredient> repairMaterial;
	private CItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterialIn) 
	{
		this.attackDamage = attackDamage;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.enchantability = enchantability;
		this.harvestLevel = harvestLevel;
		this.repairMaterial = new LazyValue<>(repairMaterialIn);
	}
	@Override
	public float getAttackDamage() 
	{
		return this.attackDamage;
	}
	@Override
	public float getEfficiency() 
	{
		return this.efficiency;
	}
	@Override
	public int getEnchantability() 
	{
		return this.enchantability;
	}
	@Override
	public int getHarvestLevel() 
	{
		return this.harvestLevel;
	}
	@Override
	public int getMaxUses() 
	{
		return this.maxUses;
	}
	@Override
	public Ingredient getRepairMaterial() 
	{
		return this.repairMaterial.getValue();
	}
}
