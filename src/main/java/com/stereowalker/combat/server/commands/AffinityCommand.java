package com.stereowalker.combat.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.unionlib.util.TextHelper;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public class AffinityCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("affinity").then(Commands.literal("set").requires((command) -> {
			return command.hasPermission(2);
		}).then(Commands.literal("elemental").then(Commands.literal("Fire").executes((e) -> {
			return setAffinity(e.getSource(), SpellCategory.ClassType.ELEMENTAL, e.getSource().getPlayerOrException(), SpellCategory.FIRE);
		})).then(Commands.literal("Water").executes((f) -> {
			return setAffinity(f.getSource(), SpellCategory.ClassType.ELEMENTAL, f.getSource().getPlayerOrException(), SpellCategory.WATER);
		})).then(Commands.literal("Lightning").executes((f) -> {
			return setAffinity(f.getSource(), SpellCategory.ClassType.ELEMENTAL, f.getSource().getPlayerOrException(), SpellCategory.LIGHTNING);
		})).then(Commands.literal("Earth").executes((f) -> {
			return setAffinity(f.getSource(), SpellCategory.ClassType.ELEMENTAL, f.getSource().getPlayerOrException(), SpellCategory.EARTH);
		})).then(Commands.literal("Wind").executes((f) -> {
			return setAffinity(f.getSource(), SpellCategory.ClassType.ELEMENTAL, f.getSource().getPlayerOrException(), SpellCategory.WIND);
		})))
				
		.then(Commands.literal("special").then(Commands.literal("Enhancement").executes((e) -> {
			return setAffinity(e.getSource(), SpellCategory.ClassType.SPECIAL, e.getSource().getPlayerOrException(), SpellCategory.ENHANCEMENT);
		})).then(Commands.literal("Mind").executes((f) -> {
			return setAffinity(f.getSource(), SpellCategory.ClassType.SPECIAL, f.getSource().getPlayerOrException(), SpellCategory.MIND);
		})).then(Commands.literal("Nature").executes((f) -> {
			return setAffinity(f.getSource(), SpellCategory.ClassType.SPECIAL, f.getSource().getPlayerOrException(), SpellCategory.NATURE);
		})).then(Commands.literal("Space").executes((f) -> {
			return setAffinity(f.getSource(), SpellCategory.ClassType.SPECIAL, f.getSource().getPlayerOrException(), SpellCategory.SPACE);
		})))
		
		.then(Commands.literal("life").then(Commands.literal("Conjuration").executes((e) -> {
			return setAffinity(e.getSource(), SpellCategory.ClassType.LIFE, e.getSource().getPlayerOrException(), SpellCategory.CONJURATION);
		})).then(Commands.literal("Restoration").executes((f) -> {
			return setAffinity(f.getSource(), SpellCategory.ClassType.LIFE, f.getSource().getPlayerOrException(), SpellCategory.RESTORATION);
		})) )));
	}

	private static int setAffinity(CommandSourceStack source, SpellCategory.ClassType type, ServerPlayer caster, SpellCategory affinity) throws CommandSyntaxException {
		int i = 0;
		if (type.equals(SpellCategory.ClassType.ELEMENTAL)) {
			if (CombatEntityStats.getElementalAffinity(caster) == SpellCategory.NONE) {
				CombatEntityStats.setElementalAffinity(caster, affinity);
				i++;
			}
		}
		if (type.equals(SpellCategory.ClassType.SPECIAL)) {
			if (CombatEntityStats.getSpecialAffinity(caster) == SpellCategory.NONE) {
				CombatEntityStats.setSpecialAffinity(caster, affinity);
				i++;
			}
		}
		if (type.equals(SpellCategory.ClassType.LIFE)) {
			if (CombatEntityStats.getLifeAffinity(caster) == SpellCategory.NONE) {
				CombatEntityStats.setLifeAffinity(caster, affinity);
				i++;
			}
		}
		if (i == 0) {
			throw new SimpleCommandExceptionType(new TranslatableComponent("commands.affinity.failed", caster.getDisplayName(), affinity.getColoredDisplayName(), type.getName(), TextHelper.articulatedText(type.getName(), false))).create();
		} else {
			source.sendSuccess(new TranslatableComponent("commands.affinity.success", caster.getDisplayName(), affinity.getColoredDisplayName(), type.getName()), true);
			return i;
		}
	}
}