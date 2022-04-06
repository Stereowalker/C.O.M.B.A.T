package com.stereowalker.combat.api.world.spellcraft;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;

public enum SpellCategory {
	//NONE
	NONE("none", TextColor.fromLegacyFormat(ChatFormatting.WHITE), ClassType.UNCLASSED, 1.0F, 1.0F, 1.0F),
	//ELEMENTALS
	FIRE("fire", TextColor.fromLegacyFormat(ChatFormatting.RED), ClassType.ELEMENTAL, 1.0F, 0.333F, 0.333F),
	WATER("water", TextColor.fromLegacyFormat(ChatFormatting.AQUA), ClassType.ELEMENTAL, 0.333F, 1.0F, 1.0F),
	LIGHTNING("lightning", TextColor.fromLegacyFormat(ChatFormatting.BLUE), ClassType.ELEMENTAL, 0.333F, 0.333F, 1.0F),
	EARTH("earth", TextColor.fromLegacyFormat(ChatFormatting.DARK_GREEN), ClassType.ELEMENTAL, 0.0F, 0.667F, 0.0F),
	WIND("wind", TextColor.fromLegacyFormat(ChatFormatting.GRAY), ClassType.ELEMENTAL, 0.667F, 0.667F, 0.667F),
	//SPECIAL
	RESTORATION("restoration", TextColor.fromLegacyFormat(ChatFormatting.YELLOW), ClassType.PRIMEVAL, 1.0F, 1.0F, 0.333F),
	CONJURATION("conjuration", TextColor.fromLegacyFormat(ChatFormatting.DARK_PURPLE), ClassType.PRIMEVAL, 0.667F, 0.0F, 0.667F),
	MIND("mind", TextColor.fromLegacyFormat(ChatFormatting.LIGHT_PURPLE), ClassType.PRIMEVAL, 1.0F, 0.333F, 1.0F),
	NATURE("nature", TextColor.fromLegacyFormat(ChatFormatting.GREEN), ClassType.PRIMEVAL, 0.333F, 1.0F, 0.333F),
	SPACE("space", TextColor.fromLegacyFormat(ChatFormatting.DARK_BLUE), ClassType.PRIMEVAL, 0.0F, 0.0F, 0.667F),
	ENHANCEMENT("enhancement", TextColor.fromLegacyFormat(ChatFormatting.DARK_AQUA), ClassType.PRIMEVAL, 0.0F, 0.667F, 0.667F),
	//UNCLASSED
	BLOOD("blood", TextColor.fromLegacyFormat(ChatFormatting.DARK_RED), ClassType.UNCLASSED, 0.667F, 0.0F, 0.0F),
	HOLY("holy", TextColor.fromLegacyFormat(ChatFormatting.GOLD), ClassType.UNCLASSED, 1.0F, 0.667F, 0.0F);
	
	private TextColor categoryColor;
	private ClassType classType;;
	private String name;
	private float rColor;
	private float gColor;
	private float bColor;
	
	private SpellCategory(String name, TextColor categoryColorIn, @Nullable ClassType type, float rColor, float gColor, float bColor) {
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
