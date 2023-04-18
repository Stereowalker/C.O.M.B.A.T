package com.stereowalker.combat.api.registries;

import java.util.function.Supplier;

import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.rankup.api.job.Job;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.api.stat.StatType;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class CombatRegistries {
	
	public static final ResourceKey<Registry<Job>> JOBS_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("combat:job"));
	public static final ResourceKey<Registry<Stat>> STATS_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("combat:stat"));
	public static final ResourceKey<Registry<StatType>> STAT_TYPES_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("combat:stat_type"));
	public static final ResourceKey<Registry<Spell>> SPELLS_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("combat:spell"));
	public static final ResourceKey<Registry<Skill>> SKILLS_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation("combat:skill"));
	public static IForgeRegistry<Job> JOBS = RegistryManager.ACTIVE.getRegistry(JOBS_REGISTRY);
	public static IForgeRegistry<StatType> STAT_TYPES = RegistryManager.ACTIVE.getRegistry(STAT_TYPES_REGISTRY);
	public static IForgeRegistry<Spell> SPELLS = RegistryManager.ACTIVE.getRegistry(SPELLS_REGISTRY);
	public static Supplier<IForgeRegistry<Skill>> SKILLS = null;
}
