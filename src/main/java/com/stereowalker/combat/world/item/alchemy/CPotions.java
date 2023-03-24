package com.stereowalker.combat.world.item.alchemy;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.effect.CMobEffects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.IForgeRegistry;

public class CPotions {
	public static List<Potion> POTIONS = new ArrayList<Potion>();

	public static final Potion PARALYSIS = register("paralysis", new Potion( new MobEffectInstance(CMobEffects.PARALYSIS, 3600)));
	public static final Potion LONG_PARALYSIS = register("long_paralysis", new Potion("paralysis", new MobEffectInstance(CMobEffects.PARALYSIS, 9600)));
	public static final Potion VAMPIRISM = register("vampirism", new Potion( new MobEffectInstance(CMobEffects.VAMPIRISM, 3600)));
	public static final Potion LONG_VAMPIRISM = register("long_vampirism", new Potion("vampirism", new MobEffectInstance(CMobEffects.VAMPIRISM, 9600)));
	public static final Potion STRONG_VAMPIRISM = register("strong_vampirism", new Potion("vampirism", new MobEffectInstance(CMobEffects.VAMPIRISM, 1800, 1)));
	public static final Potion MANA_REGENERATION = register("mana_regeneration", new Potion(new MobEffectInstance(CMobEffects.MANA_REGENERATION, 900)));
	public static final Potion LONG_MANA_REGENERATION = register("long_mana_regeneration", new Potion("mana_regeneration", new MobEffectInstance(CMobEffects.MANA_REGENERATION, 1800)));
	public static final Potion STRONG_MANA_REGENERATION = register("strong_mana_regeneration", new Potion("mana_regeneration", new MobEffectInstance(CMobEffects.MANA_REGENERATION, 450, 1)));

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
