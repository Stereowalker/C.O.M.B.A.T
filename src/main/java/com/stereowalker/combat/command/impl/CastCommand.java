package com.stereowalker.combat.command.impl;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.api.spell.SpellInstance;
import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.command.arguments.SpellArgument;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class CastCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("cast").requires((command) -> {
			return command.hasPermissionLevel(2);
		}).then(Commands.argument("casters", EntityArgument.entities()).then(Commands.argument("spell", SpellArgument.spell()).executes((e) -> {
			return castSpell(e.getSource(), EntityArgument.getEntities(e, "casters"), SpellArgument.getSpell(e, "spell"), 1.0D, Vector3d.ZERO);
		}).then(Commands.argument("strength", DoubleArgumentType.doubleArg(0, 10)).executes((f) -> {
			return castSpell(f.getSource(), EntityArgument.getEntities(f, "casters"), SpellArgument.getSpell(f, "spell"), DoubleArgumentType.getDouble(f, "strength"), Vector3d.ZERO);
		}).then(Commands.argument("pos", Vec3Argument.vec3()).executes((f) -> {
			return castSpell(f.getSource(), EntityArgument.getEntities(f, "casters"), SpellArgument.getSpell(f, "spell"), DoubleArgumentType.getDouble(f, "strength"), Vec3Argument.getVec3(f, "pos"));
		}))) )));
	}

	private static int castSpell(CommandSource source, Collection<? extends Entity> casters, Spell spell, double strength, Vector3d vec3d) throws CommandSyntaxException {
		int i = 0;
		for(Entity entity : casters) {
			if (entity instanceof LivingEntity) {
				LivingEntity caster = (LivingEntity)entity;
//				if (vec3d == Vec3d.ZERO) spell.setLocation(caster.getPositionVector());
//				else spell.setLocation(vec3d);
				SpellUtil.addEffects(caster, spell);
				SpellInstance spellInstance = new SpellInstance(spell, strength, vec3d == Vector3d.ZERO ? caster.getPositionVec() : vec3d, caster.getActiveHand(), entity.getUniqueID());
				if (spellInstance.executeCommandSpell(caster)) {
					++i;
				}
			}
		}
		if (i == 0) {
			if (casters.size() == 1) {
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.cast.failed.single", spell.getKnownName(), casters.iterator().next().getDisplayName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.cast.failed.multiple", spell.getKnownName(), casters.size())).create();
			}
		} else {
			if (casters.size() == 1) {
				source.sendFeedback(new TranslationTextComponent("commands.cast.success.single", spell.getKnownName(), casters.iterator().next().getDisplayName()), true);
			} else {
				source.sendFeedback(new TranslationTextComponent("commands.cast.success.multiple", spell.getKnownName(), casters.size()), true);
			}

			return i;
		}
	}
}