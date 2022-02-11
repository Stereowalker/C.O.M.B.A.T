package com.stereowalker.combat.server.commands;

import java.util.Collection;
import java.util.List;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.stereowalker.combat.world.entity.CombatEntityStats;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class AllyCommand {
	private static final SimpleCommandExceptionType ADD_SLEF_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableComponent("commands.ally.add.failed.self"));
	private static final SimpleCommandExceptionType REMOVE_SLEF_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableComponent("commands.ally.remove.failed.self"));

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("ally").requires((command) -> {
			return command.hasPermission(0);
		}).then(Commands.literal("add").then(Commands.argument("player", EntityArgument.players()).executes((command) -> {
			return addAlly(command.getSource(), EntityArgument.getPlayers(command, "player"));
		})
		)).then(Commands.literal("remove").then(Commands.argument("player", EntityArgument.players()).executes((command) -> {
			return removeAlly(command.getSource(), EntityArgument.getPlayers(command, "player"));
		}))
		));
	}
	
	private static int addAlly(CommandSourceStack source, Collection<? extends Player> allies)  throws CommandSyntaxException {
		int i = 0;
		boolean flag = false;
		Player player = source.getPlayerOrException();
		List<Player> currentAllies = CombatEntityStats.getAllies(player);
		for(Player ally : allies) {
			if (!Player.createPlayerUUID(player.getGameProfile()).equals(Player.createPlayerUUID(ally.getGameProfile()))) {
				if (!currentAllies.contains(ally)) {
					currentAllies.add(ally);
					i++;
				}
			} else {
				flag = true;
			}
		}
		CombatEntityStats.setAllies(player, currentAllies);
		if (i == 0 && !flag) {
			if (allies.size() == 1) {
				throw new SimpleCommandExceptionType(new TranslatableComponent("commands.ally.add.failed.single", allies.iterator().next().getDisplayName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslatableComponent("commands.ally.add.failed.multiple", allies.size())).create();
			}
		} else if (i == 0 && flag) {
			throw ADD_SLEF_FAILED_EXCEPTION.create();
		} else {
			if (allies.size() == 1) {
				source.sendSuccess(new TranslatableComponent("commands.ally.add.success.single", allies.iterator().next().getDisplayName()), true);
			} else {
				source.sendSuccess(new TranslatableComponent("commands.ally.add.success.multiple", allies.size()), true);
			}

			return i;
		}
	}
	
	private static int removeAlly(CommandSourceStack source, Collection<? extends Player> allies)  throws CommandSyntaxException {
		int i = 0;
		boolean flag = false;
		Player player = source.getPlayerOrException();
		List<Player> currentAllies = CombatEntityStats.getAllies(player);
		for(Player ally : allies) {
			if (!Player.createPlayerUUID(player.getGameProfile()).equals(Player.createPlayerUUID(ally.getGameProfile()))) {
				if (currentAllies.contains(ally)) {
					currentAllies.remove(ally);
					i++;
				}
			} else {
				flag = true;
			}
		}
		CombatEntityStats.setAllies(player, currentAllies);
		if (i == 0 && !flag) {
			if (allies.size() == 1) {
				throw new SimpleCommandExceptionType(new TranslatableComponent("commands.ally.remove.failed.single", allies.iterator().next().getDisplayName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslatableComponent("commands.ally.remove.failed.multiple", allies.size())).create();
			}
		} else if (i == 0 && flag) {
			throw REMOVE_SLEF_FAILED_EXCEPTION.create();
		} else {
			if (allies.size() == 1) {
				source.sendSuccess(new TranslatableComponent("commands.ally.remove.success.single", allies.iterator().next().getDisplayName()), true);
			} else {
				source.sendSuccess(new TranslatableComponent("commands.ally.remove.success.multiple", allies.size()), true);
			}

			return i;
		}
	}
}