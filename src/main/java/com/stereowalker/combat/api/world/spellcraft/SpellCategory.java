package com.stereowalker.combat.api.world.spellcraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;

public enum SpellCategory {
	//NONE
	NONE("none", TextColor.parseColor("#FFFFFF"), ClassType.UNCLASSED),
	//ELEMENTALS
	FIRE("fire", TextColor.parseColor("#FF6655"), () -> CAttributes.FIRE_AFFINITY, ClassType.ELEMENTAL),
	WATER("water", TextColor.parseColor("#44EEFF"), () -> CAttributes.WATER_AFFINITY, ClassType.ELEMENTAL),
	LIGHTNING("lightning", TextColor.parseColor("#6666FF"), () -> CAttributes.LIGHTNING_AFFINITY, ClassType.ELEMENTAL),
	EARTH("earth", TextColor.parseColor("#119900"), () -> CAttributes.EARTH_AFFINITY, ClassType.ELEMENTAL),
	WIND("wind", TextColor.parseColor("#999999"), () -> CAttributes.WIND_AFFINITY, ClassType.ELEMENTAL),
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
	private ClassType classType;
	private String name;
	private Supplier<Attribute> attachedAttribute;
	private float rColor;
	private float gColor;
	private float bColor;

	private SpellCategory(String name, TextColor categoryColorIn, Supplier<Attribute> attachedAttribute, @Nullable ClassType type) {
		this.name = name;
		this.categoryColor = categoryColorIn;
		this.classType = type;
		this.attachedAttribute = attachedAttribute;

		this.rColor = (float)((categoryColorIn.getValue() >> 16) & 0xFF) / 255.0F;
		this.gColor = (float)((categoryColorIn.getValue() >>  8) & 0xFF) / 255.0F;
		this.bColor = (float)((categoryColorIn.getValue() >>  0) & 0xFF) / 255.0F;
	}

	private SpellCategory(String name, TextColor categoryColorIn, @Nullable ClassType type) {
		this(name, categoryColorIn, () -> null, type);
	}

	public String getName() {
		return name;
	}

	public Component getDisplayName() {
		return Component.translatable("category." + this.name);
	}

	public Component getColoredDisplayName() {
		return Component.translatable("category." + this.name).withStyle(Style.EMPTY.withColor(getColor()));
	}

	public TextColor getColor() {
		return categoryColor;
	}

	public Attribute getAttachedAttribute() {
		return attachedAttribute.get();
	}

	public ClassType getClassType() {
		return classType;
	}
	
	public static SpellCategory[] values(ClassType classType) {
		List<SpellCategory> list = new ArrayList<SpellCategory>();
		for (SpellCategory category : values()) {
			if (category.getClassType() == classType) {
				list.add(category);
			}
		}
		return list.toArray(new SpellCategory[0]);
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

	public static SpellCategory getStrongestElementalAffinity(Player player) {
		for (SpellCategory category : values()) {
			if (category.getAttachedAttribute() != null && player.getAttributeBaseValue(category.getAttachedAttribute()) == 1.0f) {
				return category;
			}
		}
		return NONE;
	}

	public static SpellCategory[] getNextStrongestElementalAffinities(LivingEntity entity) {
		SpellCategory[] cats = new SpellCategory[3];
		boolean skip = false;
		for (SpellCategory category : values()) {
			if (category.getAttachedAttribute() != null && entity.getAttributeBaseValue(category.getAttachedAttribute()) > 0.2f) {
				if (entity.getAttributeBaseValue(category.getAttachedAttribute()) == 1.0f && !skip) {skip = true; continue;}
				if (cats[0] == null) cats[0] = category;
				if (cats[1] == null && cats[0] != category) cats[1] = category;
				if (cats[2] == null && cats[1] != category && cats[0] != category) cats[2] = category;
			}
		}
		if (cats[0] == null) cats[0] = NONE;
		if (cats[1] == null) cats[1] = NONE;
		if (cats[2] == null) cats[2] = NONE;
		return cats;
	}

	public static boolean canAccessElementalAffinity(Player player, SpellCategory category) {
		if (category.getClassType() == ClassType.ELEMENTAL)
			return player.getAttributeValue(category.getAttachedAttribute()) > 0.0f;
						else
				return false;
	}

	public static void setElementalAffinity(Player player, SpellCategory category) {
		player.getAttribute(category.getAttachedAttribute()).setBaseValue(1.0f);

		int elemental = 0;
		for (SpellCategory cat : SpellCategory.values()) {
			if (category == cat) break;
			elemental++;
		}
		elemental-=1;
		Random random = new Random();
		int a = random.nextInt(5);
		while (a == elemental) a = random.nextInt(5);
		player.getAttribute(values()[a+1].getAttachedAttribute()).setBaseValue(0.5f);
		//		Combat.debug("Set " + name + "'s 1st Sub Elemental Affinity to " + getSubElementalAffinity1(player));
		int b = random.nextInt(5);
		while (b == a || b == elemental) b = random.nextInt(5);
		player.getAttribute(values()[b+1].getAttachedAttribute()).setBaseValue(0.5f);
		//		Combat.debug("Set " + name + "'s 2nd Elemental Affinity to " + getSubElementalAffinity2(player));
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
