package com.stereowalker.combat.command;

import com.mojang.brigadier.CommandDispatcher;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.command.impl.AffinityCommand;
import com.stereowalker.combat.command.impl.AllyCommand;
import com.stereowalker.combat.command.impl.CastCommand;
import com.stereowalker.combat.command.impl.SkillCommand;

import net.minecraft.command.CommandSource;

public class CCommands {
	public static void registerAll(CommandDispatcher<CommandSource> dispatcher) {
		CastCommand.register(dispatcher);
		AffinityCommand.register(dispatcher);
		AllyCommand.register(dispatcher);
		SkillCommand.register(dispatcher);
		Combat.debug("Registered All Commands");
	}
}
