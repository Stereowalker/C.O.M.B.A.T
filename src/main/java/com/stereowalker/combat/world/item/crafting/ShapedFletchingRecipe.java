package com.stereowalker.combat.world.item.crafting;

import java.util.Map;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class ShapedFletchingRecipe implements FletchingRecipe, net.minecraftforge.common.crafting.IShapedRecipe<CraftingContainer> {
	static int MAX_WIDTH = 1;
	static int MAX_HEIGHT = 3;
	public static void setCraftingSize(int width, int height) {
		if (MAX_WIDTH < width) MAX_WIDTH = width;
		if (MAX_HEIGHT < height) MAX_HEIGHT = height;
	}

	private final int recipeWidth;
	private final int recipeHeight;
	private final NonNullList<Ingredient> recipeItems;
	private final ItemStack recipeOutput;
	private final ResourceLocation id;
	private final String group;

	public ShapedFletchingRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn) {
		this.id = idIn;
		this.group = groupIn;
		this.recipeWidth = recipeWidthIn;
		this.recipeHeight = recipeHeightIn;
		this.recipeItems = recipeItemsIn;
		this.recipeOutput = recipeOutputIn;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return CRecipeSerializer.FLETCHING;
	}

	@Override
	public String getGroup() {
		return this.group;
	}

	@Override
	public ItemStack getResultItem() {
		return this.recipeOutput;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.recipeItems;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width >= this.recipeWidth && height >= this.recipeHeight;
	}

	@Override
	public boolean matches(CraftingContainer inv, Level worldIn) {
		for(int i = 0; i <= inv.getWidth() - this.recipeWidth; ++i) {
			for(int j = 0; j <= inv.getHeight() - this.recipeHeight; ++j) {
				if (this.matches(inv, i, j, true)) {
					return true;
				}

				if (this.matches(inv, i, j, false)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean matches(CraftingContainer craftingInventory, int p_77573_2_, int p_77573_3_, boolean p_77573_4_) {
		for(int i = 0; i < craftingInventory.getWidth(); ++i) {
			for(int j = 0; j < craftingInventory.getHeight(); ++j) {
				int k = i - p_77573_2_;
				int l = j - p_77573_3_;
				Ingredient ingredient = Ingredient.EMPTY;
				if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
					if (p_77573_4_) {
						ingredient = this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
					} else {
						ingredient = this.recipeItems.get(k + l * this.recipeWidth);
					}
				}

				if (!ingredient.test(craftingInventory.getItem(i + j * craftingInventory.getWidth()))) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public ItemStack assemble(CraftingContainer inv) {
		return this.getResultItem().copy();
	}

	public int getWidth() {
		return this.recipeWidth;
	}

	@Override
	public int getRecipeWidth() {
		return getWidth();
	}

	public int getHeight() {
		return this.recipeHeight;
	}

	@Override
	public int getRecipeHeight() {
		return getHeight();
	}

	@VisibleForTesting
	static String[] shrink(String... toShrink) {
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;

		for(int i1 = 0; i1 < toShrink.length; ++i1) {
			String s = toShrink[i1];
			i = Math.min(i, firstNonSpace(s));
			int j1 = lastNonSpace(s);
			j = Math.max(j, j1);
			if (j1 < 0) {
				if (k == i1) {
					++k;
				}

				++l;
			} else {
				l = 0;
			}
		}

		if (toShrink.length == l) {
			return new String[0];
		} else {
			String[] astring = new String[toShrink.length - l - k];

			for(int k1 = 0; k1 < astring.length; ++k1) {
				astring[k1] = toShrink[k1 + k].substring(i, j + 1);
			}

			return astring;
		}
	}

	private static int firstNonSpace(String str) {
		int i;
		for(i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
			;
		}

		return i;
	}

	private static int lastNonSpace(String str) {
		int i;
		for(i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
			;
		}

		return i;
	}

	private static String[] patternFromJson(JsonArray jsonArr) {
		String[] astring = new String[jsonArr.size()];
		if (astring.length > MAX_HEIGHT) {
			throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
		} else if (astring.length == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		} else {
			for(int i = 0; i < astring.length; ++i) {
				String s = GsonHelper.convertToString(jsonArr.get(i), "pattern[" + i + "]");
				if (s.length() > MAX_WIDTH) {
					throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
				}

				if (i > 0 && astring[0].length() != s.length()) {
					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
				}

				astring[i] = s;
			}

			return astring;
		}
	}

	public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>>  implements RecipeSerializer<ShapedFletchingRecipe> {
		@SuppressWarnings("unused")
		private static final ResourceLocation NAME = new ResourceLocation("combat", "fletching");
		@Override
		public ShapedFletchingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			String s = GsonHelper.getAsString(json, "group", "");
			Map<String, Ingredient> map = ShapedRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
			String[] astring = ShapedFletchingRecipe.shrink(ShapedFletchingRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
			int i = astring[0].length();
			int j = astring.length;
			NonNullList<Ingredient> nonnulllist = ShapedRecipe.dissolvePattern(astring, map, i, j);
			ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			return new ShapedFletchingRecipe(recipeId, s, i, j, nonnulllist, itemstack);
		}

		@Override
		public ShapedFletchingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			int i = buffer.readVarInt();
			int j = buffer.readVarInt();
			String s = buffer.readUtf();
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

			for(int k = 0; k < nonnulllist.size(); ++k) {
				nonnulllist.set(k, Ingredient.fromNetwork(buffer));
			}

			ItemStack itemstack = buffer.readItem();
			return new ShapedFletchingRecipe(recipeId, s, i, j, nonnulllist, itemstack);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, ShapedFletchingRecipe recipe) {
			buffer.writeVarInt(recipe.recipeWidth);
			buffer.writeVarInt(recipe.recipeHeight);
			buffer.writeUtf(recipe.group);

			for(Ingredient ingredient : recipe.recipeItems) {
				ingredient.toNetwork(buffer);
			}

			buffer.writeItem(recipe.recipeOutput);
		}
	}
}