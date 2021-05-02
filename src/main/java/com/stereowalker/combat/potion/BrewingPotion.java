package com.stereowalker.combat.potion;

import com.stereowalker.combat.item.CItems;

import net.minecraft.item.Items;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.Potions;

public class BrewingPotion {
	public static void registerBrewing()
    {
        addCraftingRecipies();
    }
     
    private static void addCraftingRecipies() {
    	PotionBrewing.addMix(Potions.AWKWARD, CItems.VAMPIRE_BLOOD, CPotions.VAMPIRISM);
    	PotionBrewing.addMix(CPotions.VAMPIRISM, Items.REDSTONE, CPotions.LONG_VAMPIRISM);
    	PotionBrewing.addMix(CPotions.VAMPIRISM, Items.GLOWSTONE_DUST, CPotions.STRONG_VAMPIRISM);
    	PotionBrewing.addMix(Potions.AWKWARD, Items.POPPY, CPotions.PARALYSIS);
    	PotionBrewing.addMix(CPotions.PARALYSIS, Items.REDSTONE, CPotions.LONG_PARALYSIS);
    }
}
