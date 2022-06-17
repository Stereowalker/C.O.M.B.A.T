package com.stereowalker.combat.world.item;

import java.util.List;
import java.util.function.Supplier;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.tags.BlockCTags;
import com.stereowalker.combat.tags.ItemCTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

public enum CTiers implements Tier
{
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
//	PELGAN(4, 125, 9.0F, 1.0F, 21, () -> {
//	      return Ingredient.of(CItems.PELGAN_INGOT);
//	   }),
	SERABLE(1, 515, 18.5F, 0.5F, 30, () -> {
	      return Ingredient.of(CItems.SERABLE_INGOT);
	   }),
	LOZYNE(4, 2342, 12.0F, 4.5F, 15, () -> {
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
	@SuppressWarnings("deprecation")
	private final LazyLoadedValue<Ingredient> repairMaterial;
	@SuppressWarnings("deprecation")
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
	@SuppressWarnings("deprecation")
	@Override
	public Ingredient getRepairIngredient() 
	{
		return this.repairMaterial.get();
	}
	
	//Forge Method
	@Override
	@javax.annotation.Nullable public net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> getTag() { return getTagFromModdedTier(this); }
	
	public static void handleModdedTiers() {
		var lozyne = Combat.getInstance().location("lozyne");
		TierSortingRegistry.registerTier(LOZYNE, lozyne, List.of(new ResourceLocation("diamond")), List.of());
	}

    public static TagKey<Block> getTagFromModdedTier(CTiers tier)
    {
        return switch(tier)
                {
                    case BRONZE -> BlockTags.NEEDS_IRON_TOOL;
                    case STEEL -> BlockTags.NEEDS_IRON_TOOL;
                    case MAGISTEEL -> BlockTags.NEEDS_DIAMOND_TOOL;
                    case PASQUEM -> BlockTags.NEEDS_DIAMOND_TOOL;
                    case LOZYNE -> BlockCTags.NEEDS_LOZYNE_TOOL;
                    case SERABLE -> BlockTags.NEEDS_STONE_TOOL;
                    case MYTHRIL -> Tags.Blocks.NEEDS_NETHERITE_TOOL;
                    case POWERED_MYTHRIL -> Tags.Blocks.NEEDS_NETHERITE_TOOL;
                    case ETHERION -> Tags.Blocks.NEEDS_NETHERITE_TOOL;
                    case BLOOD -> Tags.Blocks.NEEDS_NETHERITE_TOOL;
                    case LIGHT_SABER -> Tags.Blocks.NEEDS_NETHERITE_TOOL;
                };
    }
}
