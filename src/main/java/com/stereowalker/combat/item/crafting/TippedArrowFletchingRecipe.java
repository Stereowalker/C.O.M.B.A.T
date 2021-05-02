package com.stereowalker.combat.item.crafting;

import java.util.Random;

import com.stereowalker.combat.item.CItems;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TippedArrowFletchingRecipe extends SpecialFletchingRecipe {
	public TippedArrowFletchingRecipe(ResourceLocation idIn) {
		super(idIn);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(CraftingInventory inv, World worldIn) {
		ItemStack arrowstack1 = inv.getStackInSlot(0);
		ItemStack itemstack1 = inv.getStackInSlot(1);
		ItemStack arrowstack2 = inv.getStackInSlot(2);
		return itemstack1.getItem() == Items.POTION && CItems.ARROW_MAP.containsKey(arrowstack1.getItem()) && arrowstack1.isItemEqual(arrowstack2);
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(CraftingInventory inv) {
		ItemStack itemstack = inv.getStackInSlot(1);
		ItemStack arrowstack = inv.getStackInSlot(0);
		if (itemstack.getItem() != Items.POTION) {
			return ItemStack.EMPTY;
		} else {
			ItemStack itemstack1 = new ItemStack(CItems.ARROW_MAP.get(arrowstack.getItem()), 2);
			if (!itemstack1.isEmpty()) {
				PotionUtils.addPotionToItemStack(itemstack1, PotionUtils.getPotionFromItem(itemstack));
				PotionUtils.appendEffects(itemstack1, PotionUtils.getFullEffectsFromItem(itemstack));
			}
			return itemstack1;
		}
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

		for(int i = 0; i < nonnulllist.size(); ++i) {
			ItemStack item = inv.getStackInSlot(i);
			if (item.getItem() == Items.POTION) {
				Random rnd = new Random();
				boolean emptiesBottle = rnd.nextInt(4) == 0;
				if(emptiesBottle) nonnulllist.set(i, new ItemStack(Items.GLASS_BOTTLE));
				else nonnulllist.set(i, item.copy());
			}
		}

		return nonnulllist;
	}

	public IRecipeSerializer<?> getSerializer() {
		return CRecipeSerializer.FLETCHING_SPECIAL_TIPPEDARROW;
	}

	@Override
	public boolean canFit(int width, int height) {
		return true;
	}
}