package com.stereowalker.combat.config;

import com.stereowalker.rankup.world.stat.LevelType;
import com.stereowalker.unionlib.config.ConfigObject;
import com.stereowalker.unionlib.config.UnionConfig;

import net.minecraftforge.fml.config.ModConfig.Type;

@UnionConfig(folder = "Combat Config", name = "rpg", translatableName = "config.combat.rpg.file", autoReload = true)
public class RpgConfig implements ConfigObject {
	@UnionConfig.Entry(name = "Enable Leveling System", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"Disabling this will disable any effects from the leveling system","NOTE: The amount of a stat you recieve at certain level is ((currentLevel/maxLevel)*maxLevelModifier)+defaultModifier where currentLevel is the level you are currently at, maxLevel is the maximum level the stat can","reach, maxLevelModifer is the maximum you can add to your stat at the max level and defaultModifier is the amount added or removed by default"})
	public boolean enableLevelingSystem = true;
	
	@UnionConfig.Entry(name = "Base Experience Cost", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 1000)
	@UnionConfig.Comment(comment = {"The base amount of experience taken for each upgrade"})
	public int baseXpCost = 7;
	
	@UnionConfig.Entry(name = "Experience Cost Step", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 1000)
	@UnionConfig.Comment(comment = {"The amount of experience added to the base cost taken for each upgrade","This number is multiplied by the current level of the stat to determine the cost of the next upgrade"})
	public int xpCostStep = 2;
	
	@UnionConfig.Entry(name = "Use XP To Upgrade Stats", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"Should we use XP to upgrade stats instead of upgrade points?"})
	public boolean useXPToUpgrade = false;
	
	@UnionConfig.Entry(name = "Level UP Style", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"How do you suppose you want to level up?"})
	public LevelType levelUpType = LevelType.ASSIGN_POINTS;

	@UnionConfig.Entry(name = "Base Experience For Level Cost", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 1000)
	@UnionConfig.Comment(comment = {"The base amount of experience taken for each upgrade"})
	public int baseXpForLevelCost = 20;
	
	@UnionConfig.Entry(name = "Experience Cost For Level Step", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 1000)
	@UnionConfig.Comment(comment = {"The amount of experience added to the base cost taken for each upgrade","This number is multiplied by the current level of the stat to determine the cost of the next upgrade"})
	public int xpCostForLevelStep = 20;

	@UnionConfig.Entry(name = "Distance From Spawn For Level Increase", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 10000)
	@UnionConfig.Comment(comment = {"The distance from spawn that mobs naturally spawened will increase in level"})
	public int distanceForLevelIncrease = 2500;
	
	@UnionConfig.Entry(name = "Distance From Spawn For Level Increase Step", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 10000)
	@UnionConfig.Comment(comment = {"How much further do we want to go before we increase the level of the mobs that spawn there"})
	public int distanceForLevelIncreaseStep = 1000;
	
	@UnionConfig.Entry(name = "Keep Mobs At Level One", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"Should all mobs be kept at level one? Do you really want them to stay vanilla?"})
	public boolean keepMobsAtLevelOne = false;

	@UnionConfig.Entry(name = "Take Experience From Killed Players", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"Should players be able to take all the experience from any player they kill?"})
	public boolean takeXpFromKilledPlayers;

	@UnionConfig.Entry(name = "Enable Training", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"Should the player be able to increase their stats via sheer training"})
	public boolean enableTraining;
	
	@UnionConfig.Entry(name = "Experience Gained From Farming", type = Type.COMMON)
	@UnionConfig.Range(min = 1, max = 1000)
	@UnionConfig.Comment(comment = {"This determines the amount of experience you gain from breaking crops","Set this to zero to disable this feature"})
	public int xpFromFarming = 1;
	
	@UnionConfig.Entry(name = "Maximum Level Attainable", type = Type.SERVER)
	@UnionConfig.Range(min = 0, max = 1000)
	@UnionConfig.Comment(comment = {
			"This governs the highest level the player can ever reach",
			"Any attempts to gain any levels after this limit will be futile",
			"Set this to zero to disable this feature"})
	public int maxLevel = 100;
}
