package com.stereowalker.combat.api.registries;

import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class CombatRegistries {
	public static final IForgeRegistry<Spell> SPELLS = RegistryManager.ACTIVE.getRegistry(Spell.class);
	public static final IForgeRegistry<Skill> SKILLS = RegistryManager.ACTIVE.getRegistry(Skill.class);
	public static final IForgeRegistry<Stat> STATS = RegistryManager.ACTIVE.getRegistry(Stat.class);
}
