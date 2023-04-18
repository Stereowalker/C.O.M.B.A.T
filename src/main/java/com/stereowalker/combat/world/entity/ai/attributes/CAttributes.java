package com.stereowalker.combat.world.entity.ai.attributes;

import com.stereowalker.unionlib.core.registries.RegistryHolder;
import com.stereowalker.unionlib.core.registries.RegistryObject;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

@RegistryHolder(namespace = "combat", registry = Attribute.class)
public class CAttributes {
	@RegistryObject("generic.max_mana")
	public static final Attribute MAX_MANA = new RangedAttribute("attribute.name.combat.max_mana", 20.0D, 0.0D, 10240.0D).setSyncable(true);
	@RegistryObject("generic.magic_strength")
	public static final Attribute MAGIC_STRENGTH = new RangedAttribute("attribute.name.combat.magic_strength", 1.0D, 0.0D, 1024.0D).setSyncable(true);
	@RegistryObject("generic.mana_regeneration")
	public static final Attribute MANA_REGENERATION = new RangedAttribute("attribute.name.combat.mana_regeneration", 1.0D, 0.0D, 1024.0D).setSyncable(true);
	@RegistryObject("generic.health_regeneration")
	public static final Attribute HEALTH_REGENERATION = new RangedAttribute("attribute.name.combat.health_regeneration", 1.0D, 0.0D, 1024.0D).setSyncable(true);
	@RegistryObject("generic.jump_strength")
	public static final Attribute JUMP_STRENGTH = new RangedAttribute("attribute.name.combat.jump_strength", 0.2D, -1024.0D, 1024.0D).setSyncable(true);
	@RegistryObject("generic.physical_resistance")
	public static final Attribute PHYSICAL_RESISTANCE = new RangedAttribute("attribute.name.combat.physical_resistance", 0.0D, 0.0D, 1024.0D).setSyncable(true);
	@RegistryObject("generic.fire_affinity")
	public static final Attribute FIRE_AFFINITY = new RangedAttribute("attribute.name.combat.fire_affinity", 0.1D, 0.0D, 1.0D).setSyncable(true);
	@RegistryObject("generic.water_affinity")
	public static final Attribute WATER_AFFINITY = new RangedAttribute("attribute.name.combat.water_affinity", 0.1D, 0.0D, 1.0D).setSyncable(true);
	@RegistryObject("generic.earth_affinity")
	public static final Attribute EARTH_AFFINITY = new RangedAttribute("attribute.name.combat.earth_affinity", 0.1D, 0.0D, 1.0D).setSyncable(true);
	@RegistryObject("generic.lightning_affinity")
	public static final Attribute LIGHTNING_AFFINITY = new RangedAttribute("attribute.name.combat.lightning_affinity", 0.1D, 0.0D, 1.0D).setSyncable(true);
	@RegistryObject("generic.wind_affinity")
	public static final Attribute WIND_AFFINITY = new RangedAttribute("attribute.name.combat.wind_affinity", 0.1D, 0.0D, 1.0D).setSyncable(true);
	@RegistryObject("generic.fire_resistance")
	public static final Attribute FIRE_RESISTANCE = new RangedAttribute("attribute.name.combat.fire_resistance", 0.0D, 0.0D, 1024.0D).setSyncable(true);
	@RegistryObject("generic.water_resistance")
	public static final Attribute WATER_RESISTANCE = new RangedAttribute("attribute.name.combat.water_resistance", 0.0D, 0.0D, 1024.0D).setSyncable(true);
	@RegistryObject("generic.earth_resistance")
	public static final Attribute EARTH_RESISTANCE = new RangedAttribute("attribute.name.combat.earth_resistance", 0.0D, 0.0D, 1024.0D).setSyncable(true);
	@RegistryObject("generic.lightning_resistance")
	public static final Attribute LIGHTNING_RESISTANCE = new RangedAttribute("attribute.name.combat.lightning_resistance", 0.0D, 0.0D, 1024.0D).setSyncable(true);
	@RegistryObject("generic.wind_resistance")
	public static final Attribute WIND_RESISTANCE = new RangedAttribute("attribute.name.combat.wind_resistance", 0.0D, 0.0D, 1024.0D).setSyncable(true);
	@RegistryObject("generic.attack_reach")
	public static final Attribute ATTACK_REACH = new RangedAttribute("attribute.name.combat.attack_reach", 2.5D, 0.0D, 1024.0D).setSyncable(true);
	
}
