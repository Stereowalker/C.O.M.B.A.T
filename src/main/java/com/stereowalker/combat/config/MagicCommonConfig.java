package com.stereowalker.combat.config;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.unionlib.config.UnionValues;
import com.stereowalker.unionlib.util.ConfigHelper;

import net.minecraftforge.common.ForgeConfigSpec;

public class MagicCommonConfig {
	String ID = "Magic";
	//Magic
	public  UnionValues.BooleanValue toggle_affinities;
	public final UnionValues.BooleanValue enableSpellKnowledge;
	public final UnionValues.BooleanValue enableSpellTraining;
	public  UnionValues.IntValue scrollDropChanceFromKill;
	public  UnionValues.IntValue scrollDropChance;
	public  UnionValues.IntValue abominationChance;
	public  UnionValues.IntValue rareAbominationChance;
	public  UnionValues.ConfigValue<List<String>> treasureHuntingBlocks;
	
	public  UnionValues.ConfigValue<?> unclear;

	MagicCommonConfig(ForgeConfigSpec.Builder common) {
		List<String> blocks = new ArrayList<String>();
		blocks.add("minecraft:dirt");
		treasureHuntingBlocks = UnionValues.ConfigValue.build(ConfigHelper.listValue(ID, "Treasure Hunting Blocks", common, blocks, "This is a list of blocks that if broken with the correct tool and the Treasure Hunter skill will drop loot.","Entries are in the format \"namespace:path\""));
		
		
		if (common != null) {
			toggle_affinities = UnionValues.BooleanValue.build(common
					.comment("Disabling this will turn off the affinity system and will allow you to cast spells in every class")
					.define("Magic.Enable Affinities", true));
			enableSpellKnowledge = UnionValues.BooleanValue.build(common
					.comment("Disabling this will allow all spells to be viewable by everybody even if they've never used the spell before")
					.define("Magic.Enable Spell Knowledge", true));
			enableSpellTraining = UnionValues.BooleanValue.build(common
					.comment("Disabling this will prevent spells from gaining strength depending on how many times its cast")
					.define("Magic.Enable Spell Training", true));
			
			scrollDropChance = UnionValues.IntValue.build(ConfigHelper.probablilityValue(ID, "Scroll Drop Chance", common, 0, "The chance a monster will drop a spell scroll upon death when the cause of death is not by a player","The lower this value, the less likely a spell scroll will drop","Set to zero to prevent spell scrolls from dropping from monsters"));
			scrollDropChanceFromKill = UnionValues.IntValue.build(ConfigHelper.probablilityValue(ID, "Scroll Drop Chance From Kills", common, 99, "The chance a monster when killed by a player, will drop a spell scroll","The lower this value, the less likely a spell scroll will drop","Set to zero to prevent spell scrolls from dropping from vanquished monsters"));
			abominationChance = UnionValues.IntValue.build(ConfigHelper.probablilityValue(ID, "Abomination Chance", common, 20, "The chance a monster will be an abomination when spawned","The lower this value, the less likely one is to become one","Set to zero to prevent monsters from becoming abominations"));
			rareAbominationChance = UnionValues.IntValue.build(ConfigHelper.probablilityValue(ID, "Rare Abomination Chance", common, 2, "The chance that an abomination monster will become a rare abomination","The lower this value, the less likely one is to become one","Set to zero to prevent abominations from becoming rare abominations"));
		}
		else {
			
			enableSpellKnowledge = new UnionValues.BooleanValue(null, true);
			enableSpellTraining = new UnionValues.BooleanValue(null, true);
		}
	}
}
