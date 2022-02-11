package com.stereowalker.rankup.commands.arguments;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.stereowalker.combat.commands.arguments.SkillArgument;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class SkillCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("skill").requires((command) -> {
			return command.hasPermission(2);
		})
		.then(Commands.literal("grant").then(Commands.argument("player", EntityArgument.players()).then(Commands.argument("skill", SkillArgument.skill()).executes((e) -> {
			return grantSkill(e.getSource(), EntityArgument.getPlayers(e, "player"), SkillArgument.getSkill(e, "skill"));
		}))))
		.then(Commands.literal("revoke").then(Commands.argument("player", EntityArgument.players()).then(Commands.argument("skill", SkillArgument.skill()).executes((e) -> {
			return revokeSkill(e.getSource(), EntityArgument.getPlayers(e, "player"), SkillArgument.getSkill(e, "skill"));
		}))))
		.then(Commands.literal("query").then(Commands.argument("player", EntityArgument.player()).then(Commands.argument("skill", SkillArgument.skill()).executes((e) -> {
			return querySkill(e.getSource(), EntityArgument.getPlayer(e, "player"), SkillArgument.getSkill(e, "skill"));
		})))));
	}

	private static int grantSkill(CommandSourceStack source, Collection<? extends Player> players, Skill skill) throws CommandSyntaxException {
		int i = 0;
		PlayerSkills.SkillGrantAction action = null;
		for(Player player : players) {
			PlayerSkills.SkillGrantAction action2 = PlayerSkills.grantSkill(player, skill, true);
			if (action2 == PlayerSkills.SkillGrantAction.SUCCESS) {
				i++;
			}
			if (players.size() == 1) {
				action = action2;
			}
		}
		if (i == 0) {
			if (players.size() == 1) {
				throw new SimpleCommandExceptionType(new TranslatableComponent("commands.skill.grant."+action.getName()+".single", players.iterator().next().getDisplayName(), skill.getName(), skill.getSuperSkill().getName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslatableComponent("commands.skill.grant.failed.multiple", skill.getName(), players.size())).create();
			}
		} else {
			if (players.size() == 1) {
				source.sendSuccess(new TranslatableComponent("commands.skill.grant."+action.getName()+".single", players.iterator().next().getDisplayName(), skill.getName(), skill.getSuperSkill().getName()), true);
			} else {
				source.sendSuccess(new TranslatableComponent("commands.skill.grant.success.multiple", skill.getName(), players.size()), true);
			}

			return i;
		}
	}

	private static int revokeSkill(CommandSourceStack source, Collection<? extends Player> players, Skill skill) throws CommandSyntaxException {
		int i = 0;
		for(Player player : players) {
			if (PlayerSkills.revokeSkill(player, skill)) {
				i++;
			}
		}
		if (i == 0) {
			if (players.size() == 1) {
				throw new SimpleCommandExceptionType(new TranslatableComponent("commands.skill.revoke.failed.single", players.iterator().next().getDisplayName(), skill.getName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslatableComponent("commands.skill.revoke.failed.multiple", players.size(), skill.getName())).create();
			}
		} else {
			if (players.size() == 1) {
				source.sendSuccess(new TranslatableComponent("commands.skill.revoke.success.single", players.iterator().next().getDisplayName(), skill.getName()), true);
			} else {
				source.sendSuccess(new TranslatableComponent("commands.skill.revoke.success.multiple", players.size(), skill.getName()), true);
			}

			return i;
		}
	}

	private static int querySkill(CommandSourceStack source, Player player, Skill skill) throws CommandSyntaxException {
		int i = 0;
		if (PlayerSkills.hasSkill(player, skill)) {
			i++;
		}
		PlayerSkills.grantRandomSkill(player);
		if (i == 0) {
			throw new SimpleCommandExceptionType(new TranslatableComponent("commands.skill.query.failed", skill.getName(), player.getDisplayName())).create();
		} else {
			source.sendSuccess(new TranslatableComponent("commands.skill.query.success", skill.getName(), player.getDisplayName()), true);
		}
		return i;
	}
}