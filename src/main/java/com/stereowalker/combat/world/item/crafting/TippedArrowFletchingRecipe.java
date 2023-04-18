package com.stereowalker.combat.world.item.crafting;

import java.util.Random;

import com.stereowalker.combat.world.item.CItems;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class TippedArrowFletchingRecipe extends SpecialFletchingRecipe {
	public TippedArrowFletchingRecipe(ResourceLocation idIn, CraftingBookCategory pCategory) {
		super(idIn);
	}

	@Override
	public boolean matches(CraftingContainer inv, Level worldIn) {
		ItemStack arrowstack1 = inv.getItem(0);
		ItemStack itemstack1 = inv.getItem(1);
		ItemStack arrowstack2 = inv.getItem(2);
		return itemstack1.getItem() == Items.POTION && CItems.ARROW_MAP.containsKey(arrowstack1.getItem()) && arrowstack1.sameItem(arrowstack2);
	}

	@Override
	public ItemStack assemble(CraftingContainer inv) {
		ItemStack itemstack = inv.getItem(1);
		ItemStack arrowstack = inv.getItem(0);
		if (itemstack.getItem() != Items.POTION) {
			return ItemStack.EMPTY;
		} else {
			ItemStack itemstack1 = new ItemStack(CItems.ARROW_MAP.get(arrowstack.getItem()), 2);
			if (!itemstack1.isEmpty()) {
				PotionUtils.setPotion(itemstack1, PotionUtils.getPotion(itemstack));
				PotionUtils.setCustomEffects(itemstack1, PotionUtils.getCustomEffects(itemstack));
			}
			return itemstack1;
		}
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

		for(int i = 0; i < nonnulllist.size(); ++i) {
			ItemStack item = inv.getItem(i);
			if (item.getItem() == Items.POTION) {
				Random rnd = new Random();
				boolean emptiesBottle = rnd.nextInt(4) == 0;
				if(emptiesBottle) nonnulllist.set(i, new ItemStack(Items.GLASS_BOTTLE));
				else nonnulllist.set(i, item.copy());
			}
		}

		return nonnulllist;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return CRecipeSerializer.FLETCHING_SPECIAL_TIPPEDARROW;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public CraftingBookCategory category() {
		return CraftingBookCategory.EQUIPMENT;
	}
}