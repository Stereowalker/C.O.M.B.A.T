package com.stereowalker.combat.command.arguments;

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
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;

import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.text.TranslationTextComponent;

public class SkillArgument implements ArgumentType<Skill> {
	private static final SimpleCommandExceptionType INVALID_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("argument.id.invalid"));
	private static final Collection<String> EXAMPLES = Arrays.asList("spooky", "spell");
	public static final DynamicCommandExceptionType SPELL_NOT_FOUND = new DynamicCommandExceptionType((p_208663_0_) -> {
		return new TranslationTextComponent("skill.effectNotFound", p_208663_0_);
	});

	public static SkillArgument skill() {
		return new SkillArgument();
	}

	public static Skill getSkill(CommandContext<CommandSource> context, String name) throws CommandSyntaxException {
		return context.getArgument(name, Skill.class);
	}

	public Skill parse(StringReader string) throws CommandSyntaxException {
		ResourceLocation resourcelocation = ResourceLocation.read(string);
		return CombatRegistries.SKILLS.getValue(resourcelocation)/* .orElseThrow(() -> { */
//	         return EFFECT_NOT_FOUND.create(resourcelocation);
	      /*})*/;
	}

	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_listSuggestions_1_, SuggestionsBuilder p_listSuggestions_2_) {
		return ISuggestionProvider.suggestIterable(CombatRegistries.SKILLS.getKeys(), p_listSuggestions_2_);
	}


	//TODO: Temporary method


	public static Skill read(StringReader reader) throws CommandSyntaxException {
		int i = reader.getCursor();

		while(reader.canRead() && ResourceLocation.isValidPathCharacter(reader.peek())) {
			reader.skip();
		}

		String s = reader.getString().substring(i, reader.getCursor());

		try {
			return SkillUtil.getSkillFromName(s);
		} catch (ResourceLocationException var4) {
			reader.setCursor(i);
			throw INVALID_EXCEPTION.createWithContext(reader);
		}
	}

	public Collection<String> getExamples() {
		return EXAMPLES;
	}
}