package com.stereowalker.combat.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ArcaneConversionRecipeSerializer<T extends AbstractArcaneWorkbenchRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
	private final int inputs;
	private final ArcaneConversionRecipeSerializer.IFactory<T> factory;

	public ArcaneConversionRecipeSerializer(ArcaneConversionRecipeSerializer.IFactory<T> factory, int inputsIn) {
		this.inputs = MathHelper.clamp(inputsIn, 1, 3);
		this.factory = factory;
		
	}

	public T read(ResourceLocation recipeId, JsonObject json) {
		Ingredient base = Ingredient.deserialize(JSONUtils.getJsonObject(json, "base"));
		Ingredient addition1 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "addition1"));
		int additionCost1 = JSONUtils.getInt(json, "additionCost1", 1);

		Ingredient addition2 = this.inputs > 1 ? Ingredient.deserialize(JSONUtils.getJsonObject(json, "addition2")) : Ingredient.EMPTY;
		int additionCost2 = this.inputs > 1 ? JSONUtils.getInt(json, "additionCost2", 1) : 0;

		Ingredient addition3 = this.inputs > 2 ? Ingredient.deserialize(JSONUtils.getJsonObject(json, "addition3")) : Ingredient.EMPTY;
		int additionCost3 = this.inputs > 2 ? JSONUtils.getInt(json, "additionCost3", 1) : 0;

		int tridoxCost = JSONUtils.getInt(json, "tridoxCost", 1);
		ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
		return this.factory.create(recipeId, base, addition1, additionCost1, addition2, additionCost2, addition3, additionCost3, tridoxCost, result);
	}

	public T read(ResourceLocation recipeId, PacketBuffer buffer) {
		Ingredient base = Ingredient.read(buffer);
		Ingredient addition1 = Ingredient.read(buffer);
		int additionCost1 = buffer.readVarInt();

		Ingredient addition2 = this.inputs > 1 ? Ingredient.read(buffer) : Ingredient.EMPTY;
		int additionCost2 = this.inputs > 1 ? buffer.readVarInt() : 0;

		Ingredient addition3 = this.inputs > 2 ? Ingredient.read(buffer) : Ingredient.EMPTY;
		int additionCost3 = this.inputs > 2 ? buffer.readVarInt() : 0;

		int tridoxCost = buffer.readVarInt();
		ItemStack result = buffer.readItemStack();
		return this.factory.create(recipeId, base, addition1, additionCost1, addition2, additionCost2, addition3, additionCost3, tridoxCost, result);
	}

	public void write(PacketBuffer buffer, T recipe) {
		recipe.base.write(buffer);
		recipe.addition1.write(buffer);
		buffer.writeVarInt(recipe.additionCost1);
		if (this.inputs > 1) {
			recipe.addition2.write(buffer);
			buffer.writeVarInt(recipe.additionCost2);
		}
		if (this.inputs > 2) {
			recipe.addition3.write(buffer);
			buffer.writeVarInt(recipe.additionCost3);
		}
		buffer.writeVarInt(recipe.tridoxCost);
		buffer.writeItemStack(recipe.result);
	}

	interface IFactory<T extends AbstractArcaneWorkbenchRecipe> {
		T create(ResourceLocation recipeIdIn, Ingredient baseIn, Ingredient addition1In,
				int additionCost1In, Ingredient addition2In, int additionCost2In, Ingredient addition3In,
				int additionCost3In, int tridoxCostIn, ItemStack resultIn);
	}
}
