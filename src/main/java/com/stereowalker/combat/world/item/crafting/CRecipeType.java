package com.stereowalker.combat.world.item.crafting;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.stereowalker.combat.Combat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class CRecipeType {
	public static Map<ResourceLocation, RecipeType<?>> RECIPES_TYPES = new HashMap<ResourceLocation, RecipeType<?>>();
	public static final RecipeType<WoodcuttingRecipe> WOODCUTTING = register("woodcutting");
	public static final RecipeType<FletchingRecipe> FLETCHING = register("fletching");
	public static final RecipeType<AbstractAlloyFurnaceRecipe> ALLOY_SMELTING = register("alloy_smelting");
	public static final RecipeType<AbstractArcaneWorkbenchRecipe> ARCANE_CONVERSION = register("arcane_conversion");

	public static <T extends Recipe<?>> RecipeType<T> register(String name){
		RecipeType<T> recipe_type = new RecipeType<T>() {
			public String toString() {
				return name;
			}
		};
		RECIPES_TYPES.put(Combat.getInstance().location(name), recipe_type);
		return recipe_type;
	}
	public static void registerAll(RegisterHelper<RecipeType<?>> registry) {
		for(Entry<ResourceLocation, RecipeType<?>> effect : RECIPES_TYPES.entrySet()) {
			registry.register(effect.getKey(), effect.getValue());
			Combat.debug("Recipe Serializer: \""+effect.getKey().toString()+"\" registered");
		}
		Combat.debug("All Recipe Serializers Registered");
	}
}
