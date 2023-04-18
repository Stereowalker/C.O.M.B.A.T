package com.stereowalker.combat.config;

import com.stereowalker.unionlib.config.ConfigObject;
import com.stereowalker.unionlib.config.UnionConfig;

import net.minecraftforge.fml.config.ModConfig.Type;

@UnionConfig(folder = "Combat Config", name = "magic", translatableName = "config.combat.magic.file", autoReload = true)
public class MagicConfig implements ConfigObject {
	@UnionConfig.Entry(name = "Enable Affinities", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"Disabling this will turn off the affinity system and will allow you to cast spells in every class"})
	public boolean toggle_affinities = true;

	@UnionConfig.Entry(name = "Enable Spell Knowledge", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"Disabling this will allow all spells to be viewable by everybody even if they've never used the spell before"})
	public boolean enableSpellKnowledge = true;
	
	@UnionConfig.Entry(name = "Enable Spell Training", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"Disabling this will prevent spells from gaining strength depending on how many times its cast"})
	public boolean enableSpellTraining = true;
	
	@UnionConfig.Entry(name = "Scroll Drop Chance", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 1000)
	@UnionConfig.Comment(comment = {"The chance a monster will drop a spell scroll upon death when the cause of death is not by a player","The lower this value, the less likely a spell scroll will drop","Set to zero to prevent spell scrolls from dropping from monsters"})
	public int scrollDropChance = 0;
	
	@UnionConfig.Entry(name = "Scroll Drop Chance From Kills", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 1000)
	@UnionConfig.Comment(comment = {"The chance a monster when killed by a player, will drop a spell scroll","The lower this value, the less likely a spell scroll will drop","Set to zero to prevent spell scrolls from dropping from vanquished monsters"})
	public int scrollDropChanceFromKill = 66;
	
	@UnionConfig.Entry(name = "Abomination Chance", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 1000)
	@UnionConfig.Comment(comment = {"The chance a monster will be an abomination when spawned","The lower this value, the less likely one is to become one","Set to zero to prevent monsters from becoming abominations"})
	public int abominationChance = 20;
	
	@UnionConfig.Entry(name = "Rare Abomination Chance", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 1000)
	@UnionConfig.Comment(comment = {"The chance that an abomination monster will become a rare abomination","The lower this value, the less likely one is to become one","Set to zero to prevent abominations from becoming rare abominations"})
	public int rareAbominationChance = 2;
}
