package com.stereowalker.combat.world.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleFletchingRecipeSerializer<T extends FletchingRecipe> implements RecipeSerializer<T> {
   private final SimpleFletchingRecipeSerializer.Factory<T> constructor;

   public SimpleFletchingRecipeSerializer(SimpleFletchingRecipeSerializer.Factory<T> pConstructor) {
      this.constructor = pConstructor;
   }

   public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
      CraftingBookCategory craftingbookcategory = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(pSerializedRecipe, "category", (String)null), CraftingBookCategory.MISC);
      return this.constructor.create(pRecipeId, craftingbookcategory);
   }

   public T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
      CraftingBookCategory craftingbookcategory = pBuffer.readEnum(CraftingBookCategory.class);
      return this.constructor.create(pRecipeId, craftingbookcategory);
   }

   public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
      pBuffer.writeEnum(pRecipe.category());
   }

   @FunctionalInterface
   public interface Factory<T extends FletchingRecipe> {
      T create(ResourceLocation pId, CraftingBookCategory pCategory);
   }
}