package com.stereowalker.combat.command.impl;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.stereowalker.combat.command.arguments.SkillArgument;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.PlayerSkills.SkillGrantAction;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class SkillCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("skill").requires((command) -> {
			return command.hasPermissionLevel(2);
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

	private static int grantSkill(CommandSource source, Collection<? extends PlayerEntity> players, Skill skill) throws CommandSyntaxException {
		int i = 0;
		SkillGrantAction action = null;
		for(PlayerEntity player : players) {
			SkillGrantAction action2 = PlayerSkills.grantSkill(player, skill, true);
			if (action2 == SkillGrantAction.SUCCESS) {
				i++;
			}
			if (players.size() == 1) {
				action = action2;
			}
		}
		if (i == 0) {
			if (players.size() == 1) {
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.skill.grant."+action.getName()+".single", players.iterator().next().getDisplayName(), skill.getName(), skill.getSuperSkill().getName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.skill.grant.failed.multiple", skill.getName(), players.size())).create();
			}
		} else {
			if (players.size() == 1) {
				source.sendFeedback(new TranslationTextComponent("commands.skill.grant."+action.getName()+".single", players.iterator().next().getDisplayName(), skill.getName(), skill.getSuperSkill().getName()), true);
			} else {
				source.sendFeedback(new TranslationTextComponent("commands.skill.grant.success.multiple", skill.getName(), players.size()), true);
			}

			return i;
		}
	}

	private static int revokeSkill(CommandSource source, Collection<? extends PlayerEntity> players, Skill skill) throws CommandSyntaxException {
		int i = 0;
		for(PlayerEntity player : players) {
			if (PlayerSkills.revokeSkill(player, skill)) {
				i++;
			}
		}
		if (i == 0) {
			if (players.size() == 1) {
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.skill.revoke.failed.single", players.iterator().next().getDisplayName(), skill.getName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.skill.revoke.failed.multiple", players.size(), skill.getName())).create();
			}
		} else {
			if (players.size() == 1) {
				source.sendFeedback(new TranslationTextComponent("commands.skill.revoke.success.single", players.iterator().next().getDisplayName(), skill.getName()), true);
			} else {
				source.sendFeedback(new TranslationTextComponent("commands.skill.revoke.success.multiple", players.size(), skill.getName()), true);
			}

			return i;
		}
	}

	private static int querySkill(CommandSource source, PlayerEntity player, Skill skill) throws CommandSyntaxException {
		int i = 0;
		if (PlayerSkills.hasSkill(player, skill)) {
			i++;
		}
		PlayerSkills.grantRandomSkill(player);
		if (i == 0) {
			throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.skill.query.failed", skill.getName(), player.getDisplayName())).create();
		} else {
			source.sendFeedback(new TranslationTextComponent("commands.skill.query.success", skill.getName(), player.getDisplayName()), true);
		}
		return i;
	}
}