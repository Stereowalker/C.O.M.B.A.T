package com.stereowalker.combat.potion;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.registries.IForgeRegistry;

public class CPotions {
	public static List<Potion> POTIONS = new ArrayList<Potion>();

	public static final Potion PARALYSIS = register("paralysis", new Potion( new EffectInstance(CEffects.PARALYSIS, 3600)));
	public static final Potion LONG_PARALYSIS = register("long_paralysis", new Potion("paralysis", new EffectInstance(CEffects.PARALYSIS, 9600)));
	public static final Potion VAMPIRISM = register("vampirism", new Potion( new EffectInstance(CEffects.VAMPIRISM, 3600)));
	public static final Potion LONG_VAMPIRISM = register("long_vampirism", new Potion("vampirism", new EffectInstance(CEffects.VAMPIRISM, 9600)));
	public static final Potion STRONG_VAMPIRISM = register("strong_vampirism", new Potion("vampirism", new EffectInstance(CEffects.VAMPIRISM, 1800, 1)));
	public static final Potion MANA_REGENERATION = register("mana_regeneration", new Potion(new EffectInstance(CEffects.MANA_REGENERATION, 900)));
	public static final Potion LONG_MANA_REGENERATION = register("long_mana_regeneration", new Potion("mana_regeneration", new EffectInstance(CEffects.MANA_REGENERATION, 1800)));
	public static final Potion STRONG_MANA_REGENERATION = register("strong_mana_regeneration", new Potion("mana_regeneration", new EffectInstance(CEffects.MANA_REGENERATION, 450, 1)));

	public static Potion register(String name, Potion potion) {
		potion.setRegistryName(Combat.getInstance().location(name));
		POTIONS.add(potion);
		return potion;
	}

	public static void registerAll(IForgeRegistry<Potion> registry) {
		for(Potion potion : POTIONS) {
			registry.register(potion);
			Combat.debug("Potion: \""+potion.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Potions Registered");
	}

}
