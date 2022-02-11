package com.stereowalker.combat.world.item.alchemy;

import com.stereowalker.combat.world.item.CItems;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

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
