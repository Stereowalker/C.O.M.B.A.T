package com.stereowalker.old.combat.config;

import com.stereowalker.old.rankup.api.stat.ConfigCreator;
import com.stereowalker.rankup.world.stat.LevelType;
import com.stereowalker.unionlib.config.UnionValues;

import net.minecraftforge.common.ForgeConfigSpec;

public class RpgCommonConfig {
	public  UnionValues.BooleanValue enableLevelingSystem;
	public  UnionValues.IntValue baseXpCost;
	public UnionValues.IntValue xpCostStep;
	public UnionValues.BooleanValue useXPToUpgrade;
	public UnionValues.EnumValue<LevelType> levelUpType;

	public  UnionValues.IntValue baseXpForLevelCost;
	public  UnionValues.IntValue xpCostForLevelStep;

	public  UnionValues.IntValue distanceForLevelIncrease;
	public  UnionValues.IntValue distanceForLevelIncreaseStep;
	public  UnionValues.BooleanValue keepMobsAtLevelOne;

	public  UnionValues.BooleanValue takeXpFromKilledPlayers;

	public  UnionValues.BooleanValue enableTraining;
	RpgCommonConfig(ForgeConfigSpec.Builder common) {
		if (common != null) {
			enableLevelingSystem = UnionValues.BooleanValue.build(common
					.comment("Disabling this will disable any effects from the leveling system"
							+"\nNOTE: The amount of a stat you recieve at certain level is ((currentLevel/maxLevel)*maxLevelModifier)+defaultModifier where currentLevel is the level you are currently at, maxLevel is the maximum level the stat can"
							+"\nreach, maxLevelModifer is the maximum you can add to your stat at the max level and defaultModifier is the amount added or removed by default")
					.define("Leveling Stats.Enable Leveling System", true));
			baseXpCost = UnionValues.IntValue.build(ConfigCreator.numberedValue("Leveling Stats", "Base Experience Cost", common, 7, 1, 1000, "The base amount of experience taken for each upgrade"));
			xpCostStep = UnionValues.IntValue.build(ConfigCreator.numberedValue("Leveling Stats", "Experience Cost Step", common, 2, 1, 1000, "The amount of experience added to the base cost taken for each upgrade","This number is multiplied by the current level of the stat to determine the cost of the next upgrade"));

			baseXpForLevelCost = UnionValues.IntValue.build(ConfigCreator.numberedValue("Leveling Stats", "Base Experience For Level Cost", common, 10, 1, 1000, "The base amount of experience taken for each upgrade"));
			xpCostForLevelStep = UnionValues.IntValue.build(ConfigCreator.numberedValue("Leveling Stats", "Experience Cost For Level Step", common, 10, 1, 1000, "The amount of experience added to the base cost taken for each upgrade","This number is multiplied by the current level of the stat to determine the cost of the next upgrade"));

			keepMobsAtLevelOne = UnionValues.BooleanValue.build(common
					.comment("Should all mobs be kept at level one? Do you really want them to stay vanilla?")
					.define("Leveling Stats.Keep Mobs At Level One", false));
			distanceForLevelIncrease = UnionValues.IntValue.build(ConfigCreator.numberedValue("Leveling Stats", "Distance From Spawn For Level Increase", common, 2500, 1, 1000, "The base amount of experience taken for each upgrade"));
			distanceForLevelIncreaseStep = UnionValues.IntValue.build(ConfigCreator.numberedValue("Leveling Stats", "Distance From Spawn For Level Increase Step", common, 1000, 1, 1000, "The amount of experience added to the base cost taken for each upgrade","This number is multiplied by the current level of the stat to determine the cost of the next upgrade"));

			takeXpFromKilledPlayers = UnionValues.BooleanValue.build(common
					.comment("Should players be able to take all the experience from any player they kill?")
					.define("Leveling Stats.Take Experience From Killed Players", true));
			useXPToUpgrade = UnionValues.BooleanValue.build(common
					.comment("Should we use XP to upgrade stats instead of upgrade points?")
					.define("Leveling Stats.Use XP To Upgrade Stats", false));
			enableTraining = UnionValues.BooleanValue.build(common
					.comment("Should the player be able to increase their stats via sheer training")
					.define("Leveling Stats.Enable Training", true));
			levelUpType = UnionValues.EnumValue.build(common
					.comment("How do you suppose you want to level up?")
					.defineEnum("Leveling Stats.Use XP To Upgrade Stats", LevelType.ASSIGN_POINTS));
		}
	}
}
