package com.stereowalker.combat.server.commands;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellInstance;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.commands.arguments.SpellArgument;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class CastCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("cast").requires((command) -> {
			return command.hasPermission(2);
		}).then(Commands.argument("casters", EntityArgument.entities()).then(Commands.argument("spell", SpellArgument.spell()).executes((e) -> {
			return castSpell(e.getSource(), EntityArgument.getEntities(e, "casters"), SpellArgument.getSpell(e, "spell"), 1.0D, Vec3.ZERO);
		}).then(Commands.argument("strength", DoubleArgumentType.doubleArg(0, 10)).executes((f) -> {
			return castSpell(f.getSource(), EntityArgument.getEntities(f, "casters"), SpellArgument.getSpell(f, "spell"), DoubleArgumentType.getDouble(f, "strength"), Vec3.ZERO);
		}).then(Commands.argument("pos", Vec3Argument.vec3()).executes((f) -> {
			return castSpell(f.getSource(), EntityArgument.getEntities(f, "casters"), SpellArgument.getSpell(f, "spell"), DoubleArgumentType.getDouble(f, "strength"), Vec3Argument.getVec3(f, "pos"));
		}))) )));
	}

	private static int castSpell(CommandSourceStack source, Collection<? extends Entity> casters, Spell spell, double strength, Vec3 vec3d) throws CommandSyntaxException {
		int i = 0;
		for(Entity entity : casters) {
			if (entity instanceof LivingEntity) {
				LivingEntity caster = (LivingEntity)entity;
//				if (vec3d == Vec3d.ZERO) spell.setLocation(caster.getPositionVector());
//				else spell.setLocation(vec3d);
				SpellUtil.addEffects(caster, spell);
				SpellInstance spellInstance = new SpellInstance(spell, strength, vec3d == Vec3.ZERO ? caster.position() : vec3d, caster.getUsedItemHand(), entity.getUUID());
				if (spellInstance.executeCommandSpell(caster)) {
					++i;
				}
			}
		}
		if (i == 0) {
			if (casters.size() == 1) {
				throw new SimpleCommandExceptionType(new TranslatableComponent("commands.cast.failed.single", spell.getKnownName(), casters.iterator().next().getDisplayName())).create();
			} else {
				throw new SimpleCommandExceptionType(new TranslatableComponent("commands.cast.failed.multiple", spell.getKnownName(), casters.size())).create();
			}
		} else {
			if (casters.size() == 1) {
				source.sendSuccess(new TranslatableComponent("commands.cast.success.single", spell.getKnownName(), casters.iterator().next().getDisplayName()), true);
			} else {
				source.sendSuccess(new TranslatableComponent("commands.cast.success.multiple", spell.getKnownName(), casters.size()), true);
			}

			return i;
		}
	}
}