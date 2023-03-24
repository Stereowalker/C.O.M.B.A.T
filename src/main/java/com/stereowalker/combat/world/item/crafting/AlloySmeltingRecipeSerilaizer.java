package com.stereowalker.combat.world.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class AlloySmeltingRecipeSerilaizer<T extends AbstractAlloyFurnaceRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
	private final boolean twoInputs;
	private final boolean oneOutput;
	private final AlloySmeltingRecipeSerilaizer.IFactory<T> factory;

	public AlloySmeltingRecipeSerilaizer(AlloySmeltingRecipeSerilaizer.IFactory<T> factory, boolean twoInputsIn, boolean oneOutputIn) {
		this.factory = factory;
		this.oneOutput = oneOutputIn;
		this.twoInputs = twoInputsIn;
	}
	
	@Override
	public T fromJson(ResourceLocation recipeId, JsonObject json) {
		JsonElement jsonelement1 = (JsonElement)(GsonHelper.isArrayNode(json, "ingredient1") ? GsonHelper.getAsJsonArray(json, "ingredient1") : GsonHelper.getAsJsonObject(json, "ingredient1"));
		JsonElement jsonelement2 = (JsonElement)(GsonHelper.isArrayNode(json, "ingredient2") ? GsonHelper.getAsJsonArray(json, "ingredient2") : GsonHelper.getAsJsonObject(json, "ingredient2"));
		JsonElement jsonelement3 = !twoInputs ? (JsonElement)(GsonHelper.isArrayNode(json, "ingredient3") ? GsonHelper.getAsJsonArray(json, "ingredient3") : GsonHelper.getAsJsonObject(json, "ingredient3")) : null;
	    Ingredient ingredient1 = Ingredient.fromJson(jsonelement1);
		Ingredient ingredient2 = Ingredient.fromJson(jsonelement2);
		Ingredient ingredient3 = !twoInputs ? Ingredient.fromJson(jsonelement3) : Ingredient.EMPTY;
		ItemStack itemstack1 = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result1"));
		ItemStack itemstack2 = !oneOutput ? ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result2")) : ItemStack.EMPTY;
		float f = GsonHelper.getAsFloat(json, "experience", 0.0F);
		int i = GsonHelper.getAsInt(json, "cookingtime", 200);
		return this.factory.create(recipeId, ingredient1, ingredient2, ingredient3, itemstack1, itemstack2, f, i);
	}

	@Override
	public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
		Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
		Ingredient ingredient2 = Ingredient.fromNetwork(buffer);
		Ingredient ingredient3 = !twoInputs ? Ingredient.fromNetwork(buffer) : Ingredient.EMPTY;
		ItemStack itemstack1 = buffer.readItem();
		ItemStack itemstack2 = !oneOutput ? buffer.readItem() : ItemStack.EMPTY;
		float f = buffer.readFloat();
		int i = buffer.readVarInt();
		return this.factory.create(recipeId, ingredient1, ingredient2, ingredient3, itemstack1, itemstack2, f, i);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, AbstractAlloyFurnaceRecipe recipe) {
		recipe.ingredient1.toNetwork(buffer);
		recipe.ingredient2.toNetwork(buffer);
		if(!twoInputs)recipe.ingredient3.toNetwork(buffer);
		buffer.writeItem(recipe.result1);
		if(!oneOutput)buffer.writeItem(recipe.result2);
		buffer.writeFloat(recipe.experience);
		buffer.writeVarInt(recipe.cookTime);
	}

	interface IFactory<T extends AbstractAlloyFurnaceRecipe> {
		T create(ResourceLocation recipeIdIn, 
				Ingredient ingredient1In, Ingredient ingredient2In, Ingredient ingredient3In, 
				ItemStack result1In, ItemStack result2In, float experienceIn, int cookTimeIn);
	}
}
