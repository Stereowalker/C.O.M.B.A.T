package com.stereowalker.combat.command.impl;

import java.util.Collection;
import java.util.List;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.stereowalker.combat.entity.CombatEntityStats;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class AllyCommand {
	private static final SimpleCommandExceptionType ADD_SLEF_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.ally.add.failed.self"));
	private static final SimpleCommandExceptionType REMOVE_SLEF_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.ally.remove.failed.self"));

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("ally").requires((command) -> {
			return command.hasPermissionLevel(0);
		}).then(Commands.literal("add").then(Commands.argument("player", EntityArgument.players()).executes((command) -> {
			return addAlly(command.getSource(), EntityArgument.getPlayers(command, "player"));
		})
		)).then(Commands.literal("remove").then(Commands.argument("player", EntityArgument.players()).executes((command) -> {
			return removeAlly(command.getSource(), EntityArgument.getPlayers(command, "player"));
		}))
		));
	}
	
	private static int addAlly(CommandSource source, Collection<? extends PlayerEntity> allies)  throws CommandSyntaxException {
		int i = 0;
		boolean flag = false;
		PlayerEntity player = source.asPlayer();
		List<PlayerEntity> currentAllies = CombatEntityStats.getAllies(player);
		for(PlayerEntity ally : allies) {
			if (!PlayerEntity.getUUID(player.getGameProfile()).equals(PlayerEntity.getUUID(ally.getGameProfile()))) {
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
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.ally.add.failed.single", allies.iterator().next().getDisplayName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.ally.add.failed.multiple", allies.size())).create();
			}
		} else if (i == 0 && flag) {
			throw ADD_SLEF_FAILED_EXCEPTION.create();
		} else {
			if (allies.size() == 1) {
				source.sendFeedback(new TranslationTextComponent("commands.ally.add.success.single", allies.iterator().next().getDisplayName()), true);
			} else {
				source.sendFeedback(new TranslationTextComponent("commands.ally.add.success.multiple", allies.size()), true);
			}

			return i;
		}
	}
	
	private static int removeAlly(CommandSource source, Collection<? extends PlayerEntity> allies)  throws CommandSyntaxException {
		int i = 0;
		boolean flag = false;
		PlayerEntity player = source.asPlayer();
		List<PlayerEntity> currentAllies = CombatEntityStats.getAllies(player);
		for(PlayerEntity ally : allies) {
			if (!PlayerEntity.getUUID(player.getGameProfile()).equals(PlayerEntity.getUUID(ally.getGameProfile()))) {
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
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.ally.remove.failed.single", allies.iterator().next().getDisplayName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.ally.remove.failed.multiple", allies.size())).create();
			}
		} else if (i == 0 && flag) {
			throw REMOVE_SLEF_FAILED_EXCEPTION.create();
		} else {
			if (allies.size() == 1) {
				source.sendFeedback(new TranslationTextComponent("commands.ally.remove.success.single", allies.iterator().next().getDisplayName()), true);
			} else {
				source.sendFeedback(new TranslationTextComponent("commands.ally.remove.success.multiple", allies.size()), true);
			}

			return i;
		}
	}
}