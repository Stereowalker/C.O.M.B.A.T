package com.stereowalker.combat.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class AlloySmeltingRecipeSerilaizer<T extends AbstractAlloyFurnaceRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
	private final boolean twoInputs;
	private final boolean oneOutput;
	private final AlloySmeltingRecipeSerilaizer.IFactory<T> factory;

	public AlloySmeltingRecipeSerilaizer(AlloySmeltingRecipeSerilaizer.IFactory<T> factory, boolean twoInputsIn, boolean oneOutputIn) {
		this.factory = factory;
		this.oneOutput = oneOutputIn;
		this.twoInputs = twoInputsIn;
	}
	
	public T read(ResourceLocation recipeId, JsonObject json) {
		Ingredient ingredient1 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient1"));
		Ingredient ingredient2 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient2"));
		Ingredient ingredient3 = !twoInputs ? Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient3")) : Ingredient.EMPTY;
		ItemStack itemstack1 = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result1"));
		ItemStack itemstack2 = !oneOutput ? ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result2")) : ItemStack.EMPTY;
		float f = JSONUtils.getFloat(json, "experience", 0.0F);
		int i = JSONUtils.getInt(json, "cookingtime", 200);
		return this.factory.create(recipeId, ingredient1, ingredient2, ingredient3, itemstack1, itemstack2, f, i);
	}

	public T read(ResourceLocation recipeId, PacketBuffer buffer) {
		Ingredient ingredient1 = Ingredient.read(buffer);
		Ingredient ingredient2 = Ingredient.read(buffer);
		Ingredient ingredient3 = !twoInputs ? Ingredient.read(buffer) : Ingredient.EMPTY;
		ItemStack itemstack1 = buffer.readItemStack();
		ItemStack itemstack2 = !oneOutput ? buffer.readItemStack() : ItemStack.EMPTY;
		float f = buffer.readFloat();
		int i = buffer.readVarInt();
		return this.factory.create(recipeId, ingredient1, ingredient2, ingredient3, itemstack1, itemstack2, f, i);
	}

	public void write(PacketBuffer buffer, AbstractAlloyFurnaceRecipe recipe) {
		recipe.ingredient1.write(buffer);
		recipe.ingredient2.write(buffer);
		if(!twoInputs)recipe.ingredient3.write(buffer);
		buffer.writeItemStack(recipe.result1);
		if(!oneOutput)buffer.writeItemStack(recipe.result2);
		buffer.writeFloat(recipe.experience);
		buffer.writeVarInt(recipe.cookTime);
	}

	interface IFactory<T extends AbstractAlloyFurnaceRecipe> {
		T create(ResourceLocation recipeIdIn, 
				Ingredient ingredient1In, Ingredient ingredient2In, Ingredient ingredient3In, 
				ItemStack result1In, ItemStack result2In, float experienceIn, int cookTimeIn);
	}
}
