package com.stereowalker.combat.api.registries;

import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.rankup.api.job.Job;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class CombatRegistries {
	public static final IForgeRegistry<Spell> SPELLS = RegistryManager.ACTIVE.getRegistry(Spell.class);
	public static final IForgeRegistry<Skill> SKILLS = RegistryManager.ACTIVE.getRegistry(Skill.class);
	public static final IForgeRegistry<Stat> STATS = RegistryManager.ACTIVE.getRegistry(Stat.class);
	
	public static final ResourceKey<Registry<Stat>> STATS_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("combat:stats"));
	public static final ResourceKey<Registry<Job>> JOBS_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("combat:jobs"));
}
