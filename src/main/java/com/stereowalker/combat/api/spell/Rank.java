package com.stereowalker.combat.api.spell;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum Rank {
	NONE("none", 1),
	BASIC("basic", 32),
	NOVICE("novice", 16),
	APPRENTICE("apprentice", 8),
	ADVANCED("advanced", 4),
	MASTER("master", 2),
	GOD("god", 1);
	private String name;
	private int weight;

	private Rank(String name, int weight) {
		this.name= name;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("rank." + this.name);
	}

	public int getWeight() {
		return weight;
	}

	public static Rank getRankFromString(String nameIn) {
		Rank[] ranks = values();

		for(int i = 0; i < ranks.length; ++i) {
			if (ranks[i].getName().equals(nameIn)) {
				return ranks[i];
			}
		}

		return ranks[0];
	}
}