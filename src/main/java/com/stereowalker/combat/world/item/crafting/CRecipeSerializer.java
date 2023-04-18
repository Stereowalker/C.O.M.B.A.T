package com.stereowalker.combat.world.item.crafting;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.stereowalker.combat.Combat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class CRecipeSerializer {
	public static Map<ResourceLocation, RecipeSerializer<?>> RECIPES = new HashMap<ResourceLocation, RecipeSerializer<?>>();

	public static final SimpleCraftingRecipeSerializer<WeaponDyeRecipe> CRAFTING_SPECIAL_WEAPONDYE = register("crafting_special_weapondye", new SimpleCraftingRecipeSerializer<>(WeaponDyeRecipe::new));
	public static final RecipeSerializer<WoodcuttingRecipe> WOODCUTTING = register("woodcutting", new SingleItemRecipe.Serializer<>(WoodcuttingRecipe::new));
	public static final RecipeSerializer<ShapedFletchingRecipe> FLETCHING = register("fletching", new ShapedFletchingRecipe.Serializer());
	public static final SimpleFletchingRecipeSerializer<TippedArrowFletchingRecipe> FLETCHING_SPECIAL_TIPPEDARROW = register("fletching_special_tippedarrow", new SimpleFletchingRecipeSerializer<>(TippedArrowFletchingRecipe::new));
	public static final RecipeSerializer<AlloyFurnace1Output2Inputs> ALLOY_SMELTING_1OU_2IN = register("alloy_smelting_1_output_2_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace1Output2Inputs::new, true, true));
	public static final RecipeSerializer<AlloyFurnace1Output3Inputs> ALLOY_SMELTING_1OU_3IN = register("alloy_smelting_1_output_3_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace1Output3Inputs::new, false, true));
	public static final RecipeSerializer<AlloyFurnace2Output2Inputs> ALLOY_SMELTING_2OU_2IN = register("alloy_smelting_2_outputs_2_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace2Output2Inputs::new, true, false));
	public static final RecipeSerializer<AlloyFurnace2Output3Inputs> ALLOY_SMELTING_2OU_3IN = register("alloy_smelting_2_outputs_3_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace2Output3Inputs::new, false, false));
	public static final RecipeSerializer<ArcaneWorkbench1Recipe> ARCANE_CONVERSION_1_INPUT = register("arcane_conversion_1_input", new ArcaneConversionRecipeSerializer<>(ArcaneWorkbench1Recipe::new,1));
	public static final RecipeSerializer<ArcaneWorkbench2Recipe> ARCANE_CONVERSION_2_INPUTS = register("arcane_conversion_2_inputs", new ArcaneConversionRecipeSerializer<>(ArcaneWorkbench2Recipe::new,2));
	public static final RecipeSerializer<ArcaneWorkbench3Recipe> ARCANE_CONVERSION_3_INPUTS = register("arcane_conversion_3_inputs", new ArcaneConversionRecipeSerializer<>(ArcaneWorkbench3Recipe::new,3));
	
	public static void registerAll(RegisterHelper<RecipeSerializer<?>> registry) {
		for(Entry<ResourceLocation, RecipeSerializer<?>> effect : RECIPES.entrySet()) {
			registry.register(effect.getKey(), effect.getValue());
			Combat.debug("Recipe Serializer: \""+effect.getKey().toString()+"\" registered");
		}
		Combat.debug("All Recipe Serializers Registered");
	}
	
	public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S recipeSerializer) {
		RECIPES.put(Combat.getInstance().location(name), recipeSerializer);
		return recipeSerializer;
	}
	
}
