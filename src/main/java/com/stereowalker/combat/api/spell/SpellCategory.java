package com.stereowalker.combat.api.spell;

import javax.annotation.Nullable;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum SpellCategory {
	//NONE
	NONE("none", TextFormatting.WHITE, ClassType.UNCLASSED, 1.0F, 1.0F, 1.0F),
	//ELEMENTALS
	FIRE("fire", TextFormatting.RED, ClassType.ELEMENTAL, 1.0F, 0.333F, 0.333F),
	WATER("water", TextFormatting.AQUA, ClassType.ELEMENTAL, 0.333F, 1.0F, 1.0F),
	LIGHTNING("lightning", TextFormatting.BLUE, ClassType.ELEMENTAL, 0.333F, 0.333F, 1.0F),
	EARTH("earth", TextFormatting.DARK_GREEN, ClassType.ELEMENTAL, 0.0F, 0.667F, 0.0F),
	WIND("wind", TextFormatting.GRAY, ClassType.ELEMENTAL, 0.667F, 0.667F, 0.667F),
//	WIND("Wind", TextFormatting.DARK_GRAY, ClassType.ELEMENTAL, 0.333F, 0.333F, 0.333F),
	//LIFE
	RESTORATION("restoration", TextFormatting.YELLOW, ClassType.LIFE, 1.0F, 1.0F, 0.333F),
	CONJURATION("conjuration", TextFormatting.DARK_PURPLE, ClassType.LIFE, 0.667F, 0.0F, 0.667F),
	//SPECIAL
	MIND("mind", TextFormatting.LIGHT_PURPLE, ClassType.SPECIAL, 1.0F, 0.333F, 1.0F),
	NATURE("nature", TextFormatting.GREEN, ClassType.SPECIAL, 0.333F, 1.0F, 0.333F),
	SPACE("space", TextFormatting.DARK_BLUE, ClassType.SPECIAL, 0.0F, 0.0F, 0.667F),
	ENHANCEMENT("enhancement", TextFormatting.DARK_AQUA, ClassType.SPECIAL, 0.0F, 0.667F, 0.667F),
//	ADVANCED9,
//	NULL("Null", TextFormatting.GRAY);
	BLOOD("blood", TextFormatting.DARK_RED, ClassType.UNCLASSED, 0.667F, 0.0F, 0.0F);
//	BLACK("BLACK", '0', 0, 0),
//	GOLD("GOLD", '6', 6, 16755200),
	
	private TextFormatting categoryColor;
	private ClassType classType;;
	private String name;
	private float rColor;
	private float gColor;
	private float bColor;
	
	private SpellCategory(String name, TextFormatting categoryColorIn, @Nullable ClassType type, float rColor, float gColor, float bColor) {
		this.name = name;
		this.categoryColor = categoryColorIn;
		this.classType = type;
		this.rColor = rColor;
		this.gColor = gColor;
		this.bColor = bColor;
	}
	
	public String getName() {
		return name;
	}

	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("category." + this.name);
	}

	public ITextComponent getColoredDisplayName() {
		return new TranslationTextComponent("category." + this.name).mergeStyle(getTextFormatting());
	}
	
	public TextFormatting getTextFormatting() {
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
		LIFE("Life"),
		SPECIAL("Special");
		
		private String name;
		
		private ClassType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
}
