package com.stereowalker.combat.world.item.crafting;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class CRecipeSerializer {
	public static List<RecipeSerializer<?>> RECIPES = new ArrayList<RecipeSerializer<?>>();

	public static final SimpleRecipeSerializer<WeaponDyeRecipe> CRAFTING_SPECIAL_WEAPONDYE = register("crafting_special_weapondye", new SimpleRecipeSerializer<>(WeaponDyeRecipe::new));
	public static final RecipeSerializer<WoodcuttingRecipe> WOODCUTTING = register("woodcutting", new SingleItemRecipe.Serializer<>(WoodcuttingRecipe::new));
	public static final RecipeSerializer<ShapedFletchingRecipe> FLETCHING = register("fletching", new ShapedFletchingRecipe.Serializer());
	public static final SimpleRecipeSerializer<TippedArrowFletchingRecipe> FLETCHING_SPECIAL_TIPPEDARROW = register("fletching_special_tippedarrow", new SimpleRecipeSerializer<>(TippedArrowFletchingRecipe::new));
	public static final RecipeSerializer<AlloyFurnace1Output2Inputs> ALLOY_SMELTING_1OU_2IN = register("alloy_smelting_1_output_2_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace1Output2Inputs::new, true, true));
	public static final RecipeSerializer<AlloyFurnace1Output3Inputs> ALLOY_SMELTING_1OU_3IN = register("alloy_smelting_1_output_3_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace1Output3Inputs::new, false, true));
	public static final RecipeSerializer<AlloyFurnace2Output2Inputs> ALLOY_SMELTING_2OU_2IN = register("alloy_smelting_2_outputs_2_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace2Output2Inputs::new, true, false));
	public static final RecipeSerializer<AlloyFurnace2Output3Inputs> ALLOY_SMELTING_2OU_3IN = register("alloy_smelting_2_outputs_3_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace2Output3Inputs::new, false, false));
	public static final RecipeSerializer<ArcaneWorkbench1Recipe> ARCANE_CONVERSION_1_INPUT = register("arcane_conversion_1_input", new ArcaneConversionRecipeSerializer<>(ArcaneWorkbench1Recipe::new,1));
	public static final RecipeSerializer<ArcaneWorkbench2Recipe> ARCANE_CONVERSION_2_INPUTS = register("arcane_conversion_2_inputs", new ArcaneConversionRecipeSerializer<>(ArcaneWorkbench2Recipe::new,2));
	public static final RecipeSerializer<ArcaneWorkbench3Recipe> ARCANE_CONVERSION_3_INPUTS = register("arcane_conversion_3_inputs", new ArcaneConversionRecipeSerializer<>(ArcaneWorkbench3Recipe::new,3));
	
	public static void registerAll(IForgeRegistry<RecipeSerializer<?>> registry) {
		for(RecipeSerializer<?> effect : RECIPES) {
			registry.register(effect);
			Combat.debug("Recipe Serializer: \""+effect.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Recipe Serializers Registered");
	}
	
	public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S recipeSerializer) {
		recipeSerializer.setRegistryName(Combat.getInstance().location(name));
		RECIPES.add(recipeSerializer);
		return recipeSerializer;
	}
	
	public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register2(String name, S recipeSerializer) {
//		recipeSerializer.setRegistryName(Combat.location(name));
//		RECIPES.add(recipeSerializer);
		return recipeSerializer;
	}
	
	
}
