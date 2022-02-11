package com.stereowalker.combat.compat.jei;

import java.util.Collection;
import java.util.stream.Collectors;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.gui.screens.inventory.AlloyFurnaceScreen;
import com.stereowalker.combat.client.gui.screens.inventory.ArcaneWorkbenchScreen;
import com.stereowalker.combat.client.gui.screens.inventory.FletchingScreen;
import com.stereowalker.combat.world.inventory.AlloyFurnaceMenu;
import com.stereowalker.combat.world.inventory.ArcaneWorkbenchMenu;
import com.stereowalker.combat.world.inventory.FletchingMenu;
import com.stereowalker.combat.world.inventory.WoodcutterMenu;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.crafting.CRecipeType;
import com.stereowalker.combat.world.item.crafting.TippedArrowFletchingRecipe;
import com.stereowalker.combat.world.level.block.CBlocks;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
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

	@SuppressWarnings("resource")
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		Collection<Recipe<?>> collection = Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter((r) -> {
			return r.getType() == CRecipeType.FLETCHING && !(r.getType() instanceof TippedArrowFletchingRecipe);	
		}).collect(Collectors.toList());
		for (Item arrow : CItems.ARROW_MAP.keySet()) {
			for (Potion potion : ForgeRegistries.POTIONS) {
				TippedArrowFletchingRecipe recipes = new TippedArrowFletchingRecipe(new ResourceLocation(arrow.getRegistryName().getPath()+"_"+potion.getRegistryName().getPath())) {
					@Override
					public NonNullList<Ingredient> getIngredients() {
						NonNullList<Ingredient> ingredients = NonNullList.create();
						ingredients.add(0, Ingredient.of(new ItemStack(arrow)));
						ingredients.add(1, Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), potion)));
						ingredients.add(2, Ingredient.of(new ItemStack(arrow)));
						return ingredients;
					}
					@Override
					public ItemStack getResultItem() {
						ItemStack itemstack = PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
						ItemStack itemstack1 = new ItemStack(CItems.ARROW_MAP.get(arrow), 2);
						if (!itemstack1.isEmpty()) {
							PotionUtils.setPotion(itemstack1, PotionUtils.getPotion(itemstack));
							PotionUtils.setCustomEffects(itemstack1, PotionUtils.getCustomEffects(itemstack));
						}
						return itemstack1;
					}
				};
				collection.add(recipes);
			}
		}
		registration.addRecipes(collection, FletchingCategory.UID);
		registration.addRecipes(Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == CRecipeType.WOODCUTTING).collect(Collectors.toList()), WoodcuttingCategory.UID);
		registration.addRecipes(Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == CRecipeType.ALLOY_SMELTING).collect(Collectors.toList()), AlloySmeltingCategory.UID);
		registration.addRecipes(Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == CRecipeType.ARCANE_CONVERSION).collect(Collectors.toList()), ArcaneConversionCategory.UID);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(AlloyFurnaceMenu.class, AlloySmeltingCategory.UID, 0, 3, 6, 9*4);
		registration.addRecipeTransferHandler(FletchingMenu.class, FletchingCategory.UID, 1, 3, 4, 9*4);
		registration.addRecipeTransferHandler(WoodcutterMenu.class, WoodcuttingCategory.UID, 0, 1, 2, 9*4);
		registration.addRecipeTransferHandler(ArcaneWorkbenchMenu.class, ArcaneConversionCategory.UID, 1, 5, 6, 9*4);
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
