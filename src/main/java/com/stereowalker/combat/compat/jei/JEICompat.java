package com.stereowalker.combat.compat.jei;

import java.util.Collection;
import java.util.stream.Collectors;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.client.gui.screen.inventory.AlloyFurnaceScreen;
import com.stereowalker.combat.client.gui.screen.inventory.ArcaneWorkbenchScreen;
import com.stereowalker.combat.client.gui.screen.inventory.FletchingScreen;
import com.stereowalker.combat.inventory.container.AlloyFurnaceContainer;
import com.stereowalker.combat.inventory.container.ArcaneWorkbenchContainer;
import com.stereowalker.combat.inventory.container.FletchingContainer;
import com.stereowalker.combat.inventory.container.WoodcutterContainer;
import com.stereowalker.combat.item.CItems;
import com.stereowalker.combat.item.crafting.CRecipeType;
import com.stereowalker.combat.item.crafting.TippedArrowFletchingRecipe;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

@JeiPlugin
public class JEICompat implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return Combat.getInstance().location("recipe_handler");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new FletchingCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new WoodcuttingCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new AlloySmeltingCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new ArcaneConversionCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(Blocks.FLETCHING_TABLE), FletchingCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(CBlocks.WOODCUTTER), WoodcuttingCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(CBlocks.ALLOY_FURNACE), AlloySmeltingCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(CBlocks.ARCANE_WORKBENCH), ArcaneConversionCategory.UID);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		Collection<IRecipe<?>> collection = Minecraft.getInstance().world.getRecipeManager().getRecipes().stream().filter((r) -> {
			return r.getType() == CRecipeType.FLETCHING && !(r.getType() instanceof TippedArrowFletchingRecipe);	
		}).collect(Collectors.toList());
		for (Item arrow : CItems.ARROW_MAP.keySet()) {
			for (Potion potion : ForgeRegistries.POTION_TYPES) {
				TippedArrowFletchingRecipe recipes = new TippedArrowFletchingRecipe(new ResourceLocation(arrow.getRegistryName().getPath()+"_"+potion.getRegistryName().getPath())) {
					@Override
					public NonNullList<Ingredient> getIngredients() {
						NonNullList<Ingredient> ingredients = NonNullList.create();
						ingredients.add(0, Ingredient.fromStacks(new ItemStack(arrow)));
						ingredients.add(1, Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potion)));
						ingredients.add(2, Ingredient.fromStacks(new ItemStack(arrow)));
						return ingredients;
					}
					@Override
					public ItemStack getRecipeOutput() {
						ItemStack itemstack = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potion);
						ItemStack itemstack1 = new ItemStack(CItems.ARROW_MAP.get(arrow), 2);
						if (!itemstack1.isEmpty()) {
							PotionUtils.addPotionToItemStack(itemstack1, PotionUtils.getPotionFromItem(itemstack));
							PotionUtils.appendEffects(itemstack1, PotionUtils.getFullEffectsFromItem(itemstack));
						}
						return itemstack1;
					}
				};
				collection.add(recipes);
			}
		}
		registration.addRecipes(collection, FletchingCategory.UID);
		registration.addRecipes(Minecraft.getInstance().world.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == CRecipeType.WOODCUTTING).collect(Collectors.toList()), WoodcuttingCategory.UID);
		registration.addRecipes(Minecraft.getInstance().world.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == CRecipeType.ALLOY_SMELTING).collect(Collectors.toList()), AlloySmeltingCategory.UID);
		registration.addRecipes(Minecraft.getInstance().world.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == CRecipeType.ARCANE_CONVERSION).collect(Collectors.toList()), ArcaneConversionCategory.UID);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(AlloyFurnaceContainer.class, AlloySmeltingCategory.UID, 0, 3, 6, 9*4);
		registration.addRecipeTransferHandler(FletchingContainer.class, FletchingCategory.UID, 1, 3, 4, 9*4);
		registration.addRecipeTransferHandler(WoodcutterContainer.class, WoodcuttingCategory.UID, 0, 1, 2, 9*4);
		registration.addRecipeTransferHandler(ArcaneWorkbenchContainer.class, ArcaneConversionCategory.UID, 1, 5, 6, 9*4);
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.useNbtForSubtypes(CItems.DIAMOND_TIPPED_ARROW, CItems.OBSIDIAN_TIPPED_ARROW, CItems.IRON_TIPPED_ARROW, CItems.QUARTZ_TIPPED_ARROW, CItems.WOODEN_TIPPED_ARROW, CItems.GOLDEN_TIPPED_ARROW, CItems.SCROLL, CItems.SKILL_RUNESTONE);
	}
	
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(AlloyFurnaceScreen.class, 79, 34, 24, 17, AlloySmeltingCategory.UID);
		registration.addRecipeClickArea(ArcaneWorkbenchScreen.class, 100, 34, 24, 17, ArcaneConversionCategory.UID);
		registration.addRecipeClickArea(FletchingScreen.class, 88, 34, 24, 17, FletchingCategory.UID);
	}



}
