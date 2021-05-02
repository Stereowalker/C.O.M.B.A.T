package com.stereowalker.combat.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.SpellCategory.ClassType;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.unionlib.util.TextHelper;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class AffinityCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("affinity").then(Commands.literal("set").requires((command) -> {
			return command.hasPermissionLevel(2);
		}).then(Commands.literal("elemental").then(Commands.literal("Fire").executes((e) -> {
			return setAffinity(e.getSource(), ClassType.ELEMENTAL, e.getSource().asPlayer(), SpellCategory.FIRE);
		})).then(Commands.literal("Water").executes((f) -> {
			return setAffinity(f.getSource(), ClassType.ELEMENTAL, f.getSource().asPlayer(), SpellCategory.WATER);
		})).then(Commands.literal("Lightning").executes((f) -> {
			return setAffinity(f.getSource(), ClassType.ELEMENTAL, f.getSource().asPlayer(), SpellCategory.LIGHTNING);
		})).then(Commands.literal("Earth").executes((f) -> {
			return setAffinity(f.getSource(), ClassType.ELEMENTAL, f.getSource().asPlayer(), SpellCategory.EARTH);
		})).then(Commands.literal("Wind").executes((f) -> {
			return setAffinity(f.getSource(), ClassType.ELEMENTAL, f.getSource().asPlayer(), SpellCategory.WIND);
		})))
				
		.then(Commands.literal("special").then(Commands.literal("Enhancement").executes((e) -> {
			return setAffinity(e.getSource(), ClassType.SPECIAL, e.getSource().asPlayer(), SpellCategory.ENHANCEMENT);
		})).then(Commands.literal("Mind").executes((f) -> {
			return setAffinity(f.getSource(), ClassType.SPECIAL, f.getSource().asPlayer(), SpellCategory.MIND);
		})).then(Commands.literal("Nature").executes((f) -> {
			return setAffinity(f.getSource(), ClassType.SPECIAL, f.getSource().asPlayer(), SpellCategory.NATURE);
		})).then(Commands.literal("Space").executes((f) -> {
			return setAffinity(f.getSource(), ClassType.SPECIAL, f.getSource().asPlayer(), SpellCategory.SPACE);
		})))
		
		.then(Commands.literal("life").then(Commands.literal("Conjuration").executes((e) -> {
			return setAffinity(e.getSource(), ClassType.LIFE, e.getSource().asPlayer(), SpellCategory.CONJURATION);
		})).then(Commands.literal("Restoration").executes((f) -> {
			return setAffinity(f.getSource(), ClassType.LIFE, f.getSource().asPlayer(), SpellCategory.RESTORATION);
		})) )));
	}

	private static int setAffinity(CommandSource source, ClassType type, ServerPlayerEntity caster, SpellCategory affinity) throws CommandSyntaxException {
		int i = 0;
		if (type.equals(ClassType.ELEMENTAL)) {
			if (CombatEntityStats.getElementalAffinity(caster) == SpellCategory.NONE) {
				CombatEntityStats.setElementalAffinity(caster, affinity);
				i++;
			}
		}
		if (type.equals(ClassType.SPECIAL)) {
			if (CombatEntityStats.getSpecialAffinity(caster) == SpellCategory.NONE) {
				CombatEntityStats.setSpecialAffinity(caster, affinity);
				i++;
			}
		}
		if (type.equals(ClassType.LIFE)) {
			if (CombatEntityStats.getLifeAffinity(caster) == SpellCategory.NONE) {
				CombatEntityStats.setLifeAffinity(caster, affinity);
				i++;
			}
		}
		if (i == 0) {
			throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.affinity.failed", caster.getDisplayName(), affinity.getColoredDisplayName(), type.getName(), TextHelper.articulatedText(type.getName(), false))).create();
		} else {
			source.sendFeedback(new TranslationTextComponent("commands.affinity.success", caster.getDisplayName(), affinity.getColoredDisplayName(), type.getName()), true);
			return i;
		}
	}
}