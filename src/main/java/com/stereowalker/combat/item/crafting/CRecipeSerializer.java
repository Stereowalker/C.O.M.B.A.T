package com.stereowalker.combat.item.crafting;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.registries.IForgeRegistry;

public class CRecipeSerializer {
	public static List<IRecipeSerializer<?>> RECIPES = new ArrayList<IRecipeSerializer<?>>();

	public static final SpecialRecipeSerializer<WeaponDyeRecipe> CRAFTING_SPECIAL_WEAPONDYE = register("crafting_special_weapondye", new SpecialRecipeSerializer<>(WeaponDyeRecipe::new));
	public static final IRecipeSerializer<WoodcuttingRecipe> WOODCUTTING = register("woodcutting", new CSingleItemRecipe.Serializer<>(WoodcuttingRecipe::new));
	public static final IRecipeSerializer<FletchingRecipe> FLETCHING = register("fletching", new FletchingRecipe.Serializer());
	public static final SpecialRecipeSerializer<TippedArrowFletchingRecipe> FLETCHING_SPECIAL_TIPPEDARROW = register("fletching_special_tippedarrow", new SpecialRecipeSerializer<>(TippedArrowFletchingRecipe::new));
	public static final IRecipeSerializer<AlloyFurnace1Output2Inputs> ALLOY_SMELTING_1OU_2IN = register("alloy_smelting_1_output_2_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace1Output2Inputs::new, true, true));
	public static final IRecipeSerializer<AlloyFurnace1Output3Inputs> ALLOY_SMELTING_1OU_3IN = register("alloy_smelting_1_output_3_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace1Output3Inputs::new, false, true));
	public static final IRecipeSerializer<AlloyFurnace2Output2Inputs> ALLOY_SMELTING_2OU_2IN = register("alloy_smelting_2_outputs_2_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace2Output2Inputs::new, true, false));
	public static final IRecipeSerializer<AlloyFurnace2Output3Inputs> ALLOY_SMELTING_2OU_3IN = register("alloy_smelting_2_outputs_3_inputs", new AlloySmeltingRecipeSerilaizer<>(AlloyFurnace2Output3Inputs::new, false, false));
	public static final IRecipeSerializer<ArcaneWorkbench1Recipe> ARCANE_CONVERSION_1_INPUT = register("arcane_conversion_1_input", new ArcaneConversionRecipeSerializer<>(ArcaneWorkbench1Recipe::new,1));
	public static final IRecipeSerializer<ArcaneWorkbench2Recipe> ARCANE_CONVERSION_2_INPUTS = register("arcane_conversion_2_inputs", new ArcaneConversionRecipeSerializer<>(ArcaneWorkbench2Recipe::new,2));
	public static final IRecipeSerializer<ArcaneWorkbench3Recipe> ARCANE_CONVERSION_3_INPUTS = register("arcane_conversion_3_inputs", new ArcaneConversionRecipeSerializer<>(ArcaneWorkbench3Recipe::new,3));
	
	public static void registerAll(IForgeRegistry<IRecipeSerializer<?>> registry) {
		for(IRecipeSerializer<?> effect : RECIPES) {
			registry.register(effect);
			Combat.debug("Recipe Serializer: \""+effect.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Recipe Serializers Registered");
	}
	
	public static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S register(String name, S recipeSerializer) {
		recipeSerializer.setRegistryName(Combat.getInstance().location(name));
		RECIPES.add(recipeSerializer);
		return recipeSerializer;
	}
	
	public static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S register2(String name, S recipeSerializer) {
//		recipeSerializer.setRegistryName(Combat.location(name));
//		RECIPES.add(recipeSerializer);
		return recipeSerializer;
	}
	
	
}
