package com.stereowalker.rankup.commands.arguments;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.rankup.world.stat.StatEvents;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class LevelCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("level").requires((command) -> {
			return command.hasPermission(2);
		})
		.then(Commands.literal("add").then(Commands.argument("player", EntityArgument.players()).then(Commands.argument("amount", IntegerArgumentType.integer()).executes((e) -> {
			return addLevel(e.getSource(), EntityArgument.getPlayers(e, "player"), IntegerArgumentType.getInteger(e, "amount"));
		}))))
		.then(Commands.literal("query").then(Commands.argument("player", EntityArgument.player()).executes((e) -> {
			return queryLevel(e.getSource(), EntityArgument.getPlayer(e, "player"));
		}))));
	}

	private static int addLevel(CommandSourceStack source, Collection<? extends ServerPlayer> players, int level) throws CommandSyntaxException {
		int i = 0;
		for(Player player : players) {
			int j = 0;
			while (j < level) {
				StatEvents.levelUp(player, true);
				j++;
			}
			i++;
		}
		if (i == 0) {
			if (players.size() == 1) {
				throw new SimpleCommandExceptionType(Component.translatable("commands.level.add.failed.single", level, players.iterator().next().getDisplayName())).create();
			} else {
				throw new SimpleCommandExceptionType(Component.translatable("commands.level.add.failed.multiple", level, players.size())).create();
			}
		} else {
			if (players.size() == 1) {
				source.sendSuccess(Component.translatable("commands.level.add.success.single", level, players.iterator().next().getDisplayName()), true);
			} else {
				source.sendSuccess(Component.translatable("commands.level.add.success.multiple", level, players.size()), true);
			}

			return i;
		}
	}

	private static int queryLevel(CommandSourceStack source, ServerPlayer player) throws CommandSyntaxException {
		int i = PlayerAttributeLevels.getLevel(player);
		source.sendSuccess(Component.translatable("commands.level.query", player.getDisplayName(), i), true);
		return i;
	}
}