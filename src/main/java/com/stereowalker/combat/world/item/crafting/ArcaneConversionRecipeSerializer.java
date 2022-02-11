package com.stereowalker.combat.world.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ArcaneConversionRecipeSerializer<T extends AbstractArcaneWorkbenchRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
	private final int inputs;
	private final ArcaneConversionRecipeSerializer.IFactory<T> factory;

	public ArcaneConversionRecipeSerializer(ArcaneConversionRecipeSerializer.IFactory<T> factory, int inputsIn) {
		this.inputs = Mth.clamp(inputsIn, 1, 3);
		this.factory = factory;
		
	}

	@Override
	public T fromJson(ResourceLocation recipeId, JsonObject json) {
		Ingredient base = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
		Ingredient addition1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition1"));
		int additionCost1 = GsonHelper.getAsInt(json, "additionCost1", 1);

		Ingredient addition2 = this.inputs > 1 ? Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition2")) : Ingredient.EMPTY;
		int additionCost2 = this.inputs > 1 ? GsonHelper.getAsInt(json, "additionCost2", 1) : 0;

		Ingredient addition3 = this.inputs > 2 ? Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition3")) : Ingredient.EMPTY;
		int additionCost3 = this.inputs > 2 ? GsonHelper.getAsInt(json, "additionCost3", 1) : 0;

		int tridoxCost = GsonHelper.getAsInt(json, "tridoxCost", 1);
		ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
		return this.factory.create(recipeId, base, addition1, additionCost1, addition2, additionCost2, addition3, additionCost3, tridoxCost, result);
	}

	@Override
	public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
		Ingredient base = Ingredient.fromNetwork(buffer);
		Ingredient addition1 = Ingredient.fromNetwork(buffer);
		int additionCost1 = buffer.readVarInt();

		Ingredient addition2 = this.inputs > 1 ? Ingredient.fromNetwork(buffer) : Ingredient.EMPTY;
		int additionCost2 = this.inputs > 1 ? buffer.readVarInt() : 0;

		Ingredient addition3 = this.inputs > 2 ? Ingredient.fromNetwork(buffer) : Ingredient.EMPTY;
		int additionCost3 = this.inputs > 2 ? buffer.readVarInt() : 0;

		int tridoxCost = buffer.readVarInt();
		ItemStack result = buffer.readItem();
		return this.factory.create(recipeId, base, addition1, additionCost1, addition2, additionCost2, addition3, additionCost3, tridoxCost, result);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, T recipe) {
		recipe.base.toNetwork(buffer);
		recipe.addition1.toNetwork(buffer);
		buffer.writeVarInt(recipe.additionCost1);
		if (this.inputs > 1) {
			recipe.addition2.toNetwork(buffer);
			buffer.writeVarInt(recipe.additionCost2);
		}
		if (this.inputs > 2) {
			recipe.addition3.toNetwork(buffer);
			buffer.writeVarInt(recipe.additionCost3);
		}
		buffer.writeVarInt(recipe.tridoxCost);
		buffer.writeItem(recipe.result);
	}

	interface IFactory<T extends AbstractArcaneWorkbenchRecipe> {
		T create(ResourceLocation recipeIdIn, Ingredient baseIn, Ingredient addition1In,
				int additionCost1In, Ingredient addition2In, int additionCost2In, Ingredient addition3In,
				int additionCost3In, int tridoxCostIn, ItemStack resultIn);
	}
}
