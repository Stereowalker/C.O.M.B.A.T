package com.stereowalker.rankup.command.impl;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.stereowalker.rankup.stat.PlayerAttributeLevels;
import com.stereowalker.rankup.stat.StatEvents;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class LevelCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("level").requires((command) -> {
			return command.hasPermissionLevel(2);
		})
		.then(Commands.literal("add").then(Commands.argument("player", EntityArgument.players()).then(Commands.argument("amount", IntegerArgumentType.integer()).executes((e) -> {
			return addLevel(e.getSource(), EntityArgument.getPlayers(e, "player"), IntegerArgumentType.getInteger(e, "amount"));
		}))))
		.then(Commands.literal("query").then(Commands.argument("player", EntityArgument.player()).executes((e) -> {
			return queryLevel(e.getSource(), EntityArgument.getPlayer(e, "player"));
		}))));
	}

	private static int addLevel(CommandSource source, Collection<? extends ServerPlayerEntity> players, int level) throws CommandSyntaxException {
		int i = 0;
		for(PlayerEntity player : players) {
			int j = 0;
			while (j < level) {
				StatEvents.levelUp(player, true);
				j++;
			}
			i++;
		}
		if (i == 0) {
			if (players.size() == 1) {
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.level.add.failed.single", level, players.iterator().next().getDisplayName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.level.add.failed.multiple", level, players.size())).create();
			}
		} else {
			if (players.size() == 1) {
				source.sendFeedback(new TranslationTextComponent("commands.level.add.success.single", level, players.iterator().next().getDisplayName()), true);
			} else {
				source.sendFeedback(new TranslationTextComponent("commands.level.add.success.multiple", level, players.size()), true);
			}

			return i;
		}
	}

	private static int queryLevel(CommandSource source, ServerPlayerEntity player) throws CommandSyntaxException {
		int i = PlayerAttributeLevels.getLevel(player);
		source.sendFeedback(new TranslationTextComponent("commands.level.query", player.getDisplayName(), i), true);
		return i;
	}
}