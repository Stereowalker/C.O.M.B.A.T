package com.stereowalker.combat.api.world.spellcraft;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;

public enum SpellCategory {
	//NONE
	NONE("none", TextColor.parseColor("#FFFFFF"), ClassType.UNCLASSED),
	//ELEMENTALS
	FIRE("fire", TextColor.parseColor("#FF5555"), ClassType.ELEMENTAL),
	WATER("water", TextColor.parseColor("#55FFFF"), ClassType.ELEMENTAL),
	LIGHTNING("lightning", TextColor.parseColor("#5555FF"), ClassType.ELEMENTAL),
	EARTH("earth", TextColor.parseColor("#00AA00"), ClassType.ELEMENTAL),
	WIND("wind", TextColor.parseColor("#AAAAAA"), ClassType.ELEMENTAL),
	//SPECIAL
	RESTORATION("restoration", TextColor.parseColor("#FFFF55"), ClassType.PRIMEVAL),
	CONJURATION("conjuration", TextColor.parseColor("#AA00AA"), ClassType.PRIMEVAL),
	NATURE("nature", TextColor.parseColor("#55FF55"), ClassType.PRIMEVAL),
	SPACE("space", TextColor.parseColor("#0000AA"), ClassType.PRIMEVAL),
	ENHANCEMENT("enhancement", TextColor.parseColor("#00AAAA"), ClassType.PRIMEVAL),
	//UNCLASSED
	BLOOD("blood", TextColor.parseColor("#AA0000"), ClassType.UNCLASSED),
	HOLY("holy", TextColor.parseColor("#FFAA00"), ClassType.UNCLASSED);
	
	private TextColor categoryColor;
	private ClassType classType;;
	private String name;
	private float rColor;
	private float gColor;
	private float bColor;
	
	private SpellCategory(String name, TextColor categoryColorIn, @Nullable ClassType type) {
		this.name = name;
		this.categoryColor = categoryColorIn;
		this.classType = type;
		
		this.rColor = (float)((categoryColorIn.getValue() >> 16) & 0xFF) / 255.0F;
		this.gColor = (float)((categoryColorIn.getValue() >>  8) & 0xFF) / 255.0F;
		this.bColor = (float)((categoryColorIn.getValue() >>  0) & 0xFF) / 255.0F;
	}
	
	public String getName() {
		return name;
	}

	public Component getDisplayName() {
		return new TranslatableComponent("category." + this.name);
	}

	public Component getColoredDisplayName() {
		return new TranslatableComponent("category." + this.name).withStyle(Style.EMPTY.withColor(getColor()));
	}
	
	public TextColor getColor() {
		return categoryColor;
	}
	
	public ClassType getClassType() {
		return classType;
	}
	
	/**
	 * Get a category type by it's enum ordinal
	 */
	public static SpellCategory byId(int id) {
		SpellCategory[] category = values();
		if (id < 0 || id >= category.length) {
			id = 0;
		}
		return category[id];
	}
	
	public static SpellCategory getCategoryFromString(String nameIn) {
		SpellCategory[] category = values();
		for(int i = 0; i < category.length; ++i) {
			if (category[i].getName().equals(nameIn)) {
				return category[i];
			}
		}
		return category[0];
	}
	
	public float getrCOlor() {
		return rColor;
	}
	
	public float getgCOlor() {
		return gColor;
	}
	
	public float getbCOlor() {
		return bColor;
	}

	public enum ClassType {
		UNCLASSED("Unclassified"),
		ELEMENTAL("Elemental"),
		PRIMEVAL("Primeval");
		
		private String name;
		
		private ClassType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
}
