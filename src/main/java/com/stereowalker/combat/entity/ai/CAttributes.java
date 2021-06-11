package com.stereowalker.combat.entity.ai;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.IForgeRegistry;

public class CAttributes {
	public static List<Attribute> ATTRIBUTE = new ArrayList<Attribute>();
	
	public static final Attribute MAX_MANA = register("generic.max_mana", (new RangedAttribute("attribute.name.combat.max_mana", 20.0D, 0.0D, 10240.0D)).setShouldWatch(true));
	public static final Attribute MAGIC_STRENGTH = register("generic.magic_strength", (new RangedAttribute("attribute.name.combat.magic_strength", 1.0D, 0.0D, 1024.0D)).setShouldWatch(true));
	public static final Attribute MANA_REGENERATION = register("generic.mana_regeneration", (new RangedAttribute("attribute.name.combat.mana_regeneration", 1.0D, 0.0D, 1024.0D)).setShouldWatch(true));
	public static final Attribute HEALTH_REGENERATION = register("generic.health_regeneration", (new RangedAttribute("attribute.name.combat.health_regeneration", 1.0D, 0.0D, 1024.0D)).setShouldWatch(true));
	public static final Attribute JUMP_STRENGTH = register("generic.jump_strength", (new RangedAttribute("attribute.name.combat.jump_strength", 0.2D, -1024.0D, 1024.0D)).setShouldWatch(true));
	public static final Attribute PHYSICAL_RESISTANCE = register("generic.physical_resistance", (new RangedAttribute("attribute.name.combat.physical_resistance", 0.0D, 0.0D, 1024.0D)).setShouldWatch(true));
	public static final Attribute FIRE_RESISTANCE = register("generic.fire_resistance", (new RangedAttribute("attribute.name.combat.fire_resistance", 0.0D, 0.0D, 1024.0D)).setShouldWatch(true));
	public static final Attribute WATER_RESISTANCE = register("generic.water_resistance", (new RangedAttribute("attribute.name.combat.water_resistance", 0.0D, 0.0D, 1024.0D)).setShouldWatch(true));
	public static final Attribute EARTH_RESISTANCE = register("generic.earth_resistance", (new RangedAttribute("attribute.name.combat.earth_resistance", 0.0D, 0.0D, 1024.0D)).setShouldWatch(true));
	public static final Attribute LIGHTNING_RESISTANCE = register("generic.lightning_resistance", (new RangedAttribute("attribute.name.combat.lightning_resistance", 0.0D, 0.0D, 1024.0D)).setShouldWatch(true));
	public static final Attribute WIND_RESISTANCE = register("generic.wind_resistance", (new RangedAttribute("attribute.name.combat.wind_resistance", 0.0D, 0.0D, 1024.0D)).setShouldWatch(true));
	public static final Attribute ATTACK_REACH = register("generic.attack_reach", (new RangedAttribute("attribute.name.combat.attack_reach", 2.5D, 0.0D, 1024.0D)).setShouldWatch(true));
	
	public static void registerAll(IForgeRegistry<Attribute> registry) {
		for(Attribute attribute : ATTRIBUTE) {
			registry.register(attribute);
			Combat.debug("Attribute: \""+attribute.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Attributes Registered");
	}
	
	public static Attribute register(String name, Attribute attribute) {
		attribute.setRegistryName(Combat.getInstance().location(name));
		ATTRIBUTE.add(attribute);
		return attribute;
	}
	
	public static Attribute registerMC(String name, Attribute attribute) {
		attribute.setRegistryName("minecraft",name);
		ATTRIBUTE.add(attribute);
		return attribute;
	}
}
