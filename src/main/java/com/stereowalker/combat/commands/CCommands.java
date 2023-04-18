package com.stereowalker.combat.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.server.commands.AffinityCommand;
import com.stereowalker.combat.server.commands.AllyCommand;
import com.stereowalker.combat.server.commands.CastCommand;
import com.stereowalker.rankup.commands.arguments.LevelCommand;
import com.stereowalker.rankup.commands.arguments.SkillCommand;

import net.minecraft.commands.CommandSourceStack;

public class CCommands {

	public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
//		CastCommand.register(dispatcher);
		AffinityCommand.register(dispatcher);
		AllyCommand.register(dispatcher);
		Combat.debug("Registered All Commands");
		if (Combat.RPG_CONFIG.enableLevelingSystem) {
//			SkillCommand.register(dispatcher);
			LevelCommand.register(dispatcher);
			Combat.debug("Registered All Commands for Rankup");
		}
	}

}
