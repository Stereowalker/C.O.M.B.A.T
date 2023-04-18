package com.stereowalker.combat.world.item.alchemy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.effect.CMobEffects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class CPotions {
	public static Map<ResourceLocation,Potion> POTIONS = new HashMap<ResourceLocation,Potion>();

	public static final Potion PARALYSIS = register("paralysis", new Potion( new MobEffectInstance(CMobEffects.PARALYSIS, 3600)));
	public static final Potion LONG_PARALYSIS = register("long_paralysis", new Potion("paralysis", new MobEffectInstance(CMobEffects.PARALYSIS, 9600)));
	public static final Potion VAMPIRISM = register("vampirism", new Potion( new MobEffectInstance(CMobEffects.VAMPIRISM, 3600)));
	public static final Potion LONG_VAMPIRISM = register("long_vampirism", new Potion("vampirism", new MobEffectInstance(CMobEffects.VAMPIRISM, 9600)));
	public static final Potion STRONG_VAMPIRISM = register("strong_vampirism", new Potion("vampirism", new MobEffectInstance(CMobEffects.VAMPIRISM, 1800, 1)));
	public static final Potion MANA_REGENERATION = register("mana_regeneration", new Potion(new MobEffectInstance(CMobEffects.MANA_REGENERATION, 900)));
	public static final Potion LONG_MANA_REGENERATION = register("long_mana_regeneration", new Potion("mana_regeneration", new MobEffectInstance(CMobEffects.MANA_REGENERATION, 1800)));
	public static final Potion STRONG_MANA_REGENERATION = register("strong_mana_regeneration", new Potion("mana_regeneration", new MobEffectInstance(CMobEffects.MANA_REGENERATION, 450, 1)));

	public static Potion register(String name, Potion potion) {
		POTIONS.put(Combat.getInstance().location(name), potion);
		return potion;
	}

	public static void registerAll(RegisterHelper<Potion> registry) {
		for(Entry<ResourceLocation, Potion> potion : POTIONS.entrySet()) {
			registry.register(potion.getKey(), potion.getValue());
			Combat.debug("Potion: \""+potion.getKey().toString()+"\" registered");
		}
		Combat.debug("All Potions Registered");
	}

}
