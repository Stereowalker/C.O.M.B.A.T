package com.stereowalker.combat.commands.arguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;

import net.minecraft.ResourceLocationException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class SpellArgument implements ArgumentType<Spell> {
	private static final SimpleCommandExceptionType INVALID_EXCEPTION = new SimpleCommandExceptionType(new TranslatableComponent("argument.id.invalid"));
	private static final Collection<String> EXAMPLES = Arrays.asList("spooky", "spell");
	public static final DynamicCommandExceptionType SPELL_NOT_FOUND = new DynamicCommandExceptionType((p_208663_0_) -> {
		return new TranslatableComponent("spell.effectNotFound", p_208663_0_);
	});

	public static SpellArgument spell() {
		return new SpellArgument();
	}

	public static Spell getSpell(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
		return context.getArgument(name, Spell.class);
	}

	public Spell parse(StringReader string) throws CommandSyntaxException {
		ResourceLocation resourcelocation = ResourceLocation.read(string);
		return CombatRegistries.SPELLS.getValue(resourcelocation)/* .orElseThrow(() -> { */
//	         return EFFECT_NOT_FOUND.create(resourcelocation);
	      /*})*/;
	}

	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_listSuggestions_1_, SuggestionsBuilder p_listSuggestions_2_) {
		return SharedSuggestionProvider.suggestResource(CombatRegistries.SPELLS.getKeys(), p_listSuggestions_2_);
	}


	//TODO: Temporary method


	public static Spell read(StringReader reader) throws CommandSyntaxException {
		int i = reader.getCursor();

		while(reader.canRead() && ResourceLocation.isAllowedInResourceLocation(reader.peek())) {
			reader.skip();
		}

		String s = reader.getString().substring(i, reader.getCursor());

		try {
			return SpellUtil.getSpellFromName(s);
		} catch (ResourceLocationException var4) {
			reader.setCursor(i);
			throw INVALID_EXCEPTION.createWithContext(reader);
		}
	}

	public Collection<String> getExamples() {
		return EXAMPLES;
	}
}