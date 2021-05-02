package com.stereowalker.rankup.stat;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stereowalker.combat.Combat;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class StatSettings {
	private static final Marker STAT_SETTINGS = MarkerManager.getMarker("STAT_SETTINGS");

	private final boolean enabled;
	private final boolean limitable;
	private final int defaultPoints;
	private final int minPointsPerLevel;
	private final int maxPointsPerLevel;
	private final ImmutableMap<Attribute,Double> attributeMap;

	public StatSettings(JsonObject object, ResourceLocation owner) {
		boolean enabledIn = false;
		boolean limitableIn = false;
		int defaultPointsIn = 0;
		int minPointsPerLevelIn = 0;
		int maxPointsPerLevelIn = 0;
		ImmutableMap.Builder<Attribute,Double> attributeMapIn = ImmutableMap.builder();

		String NOTHING = "nothing";
		String ENABLED = "enabled";
		String LIMITABLE = "limitable";
		String DEFAULT_POINTS = "default_points";
		String MAX_POINTS_PER_LEVEL = "maximum_points_earned_per_level";
		String MIN_POINTS_PER_LEVEL = "minimum_points_earned_per_level";
		String ATTRIBUTES = "attributes";


		if(object != null && object.entrySet().size() != 0) {
			String workingOn = NOTHING;
			try {

				if(object.has(ATTRIBUTES) && object.get(ATTRIBUTES).isJsonArray()) {
					workingOn = ATTRIBUTES;
					int i = 0;
					for (JsonElement elem : object.get(ATTRIBUTES).getAsJsonArray()) {
						if (elem.isJsonObject()) {
							JsonObject object2 = elem.getAsJsonObject();
							Attribute attribute = null;
							double value = 0;

							String ATTRIBUTE = "attribute";
							String MODIFIER_PER_POINT = "modifier_per_point";
							if(object2 != null && object2.entrySet().size() != 0) {
								if(object2.has(ATTRIBUTE) && object2.get(ATTRIBUTE).isJsonPrimitive()) {
									workingOn = ATTRIBUTE;
									attribute = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(object2.get(ATTRIBUTE).getAsString()));
									workingOn = NOTHING;
								}
								if(object2.has(MODIFIER_PER_POINT) && object2.get(MODIFIER_PER_POINT).isJsonPrimitive()) {
									workingOn = MODIFIER_PER_POINT;
									value = object2.get(MODIFIER_PER_POINT).getAsDouble();
									workingOn = NOTHING;
								}
								attributeMapIn.put(attribute, value);
							}
						} else {
							Combat.getInstance().LOGGER.warn("Loading stat settings %s from JSON: Attribute %d isn't a json object", owner,  i);
						}
						i++;
					}
					workingOn = NOTHING;
				}

				if(object.has(ENABLED) && object.get(ENABLED).isJsonPrimitive()) {
					workingOn = ENABLED;
					enabledIn = object.get(ENABLED).getAsBoolean();
					workingOn = NOTHING;
				}

				if(object.has(LIMITABLE) && object.get(LIMITABLE).isJsonPrimitive()) {
					workingOn = LIMITABLE;
					limitableIn = object.get(LIMITABLE).getAsBoolean();
					workingOn = NOTHING;
				}

				if(object.has(DEFAULT_POINTS) && object.get(DEFAULT_POINTS).isJsonPrimitive()) {
					workingOn = DEFAULT_POINTS;
					defaultPointsIn = object.get(DEFAULT_POINTS).getAsInt();
					workingOn = NOTHING;
				}

				if(object.has(MAX_POINTS_PER_LEVEL) && object.get(MAX_POINTS_PER_LEVEL).isJsonPrimitive()) {
					workingOn = MAX_POINTS_PER_LEVEL;
					maxPointsPerLevelIn = object.get(MAX_POINTS_PER_LEVEL).getAsInt();
					workingOn = NOTHING;
				}

				if(object.has(MIN_POINTS_PER_LEVEL) && object.get(MIN_POINTS_PER_LEVEL).isJsonPrimitive()) {
					workingOn = MIN_POINTS_PER_LEVEL;
					minPointsPerLevelIn = object.get(MIN_POINTS_PER_LEVEL).getAsInt();
					workingOn = NOTHING;
				}

			} catch (ClassCastException e) {
				Combat.getInstance().LOGGER.warn(STAT_SETTINGS,
						"Loading stat settings $s from JSON: Parsing element %s: element was wrong type!", e, owner, workingOn);
			} catch (NumberFormatException e) {
				Combat.getInstance().LOGGER.warn(STAT_SETTINGS,
						"Loading drink data $s from JSON: Parsing element %s: element was an invalid number!", e, owner, workingOn);
			}
		}

		this.enabled = enabledIn;
		this.limitable = limitableIn;
		this.defaultPoints = defaultPointsIn;
		this.minPointsPerLevel = minPointsPerLevelIn;
		this.maxPointsPerLevel = maxPointsPerLevelIn;
		this.attributeMap = attributeMapIn.build();

		if (minPointsPerLevel > maxPointsPerLevel) {
			throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
		} else if (attributeMap.containsValue(0.0D)) {
			throw new IllegalArgumentException("Modifier per Point cannot be zero!");
//		} else if (defaultValue > maximumValue) {
//			throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
		}
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return the limitable
	 */
	public boolean isLimitable() {
		return limitable;
	}

	/**
	 * @return the defaultValue
	 */
	public int getDefaultPoints() {
		return defaultPoints;
	}

	//	/**
	//	 * @return the maxLevel
	//	 */
	//	public int getMaxPoints() {
	//		return maxPoints;
	//	}
	//
	//	public float getLevelModifier() {
	//		return (float)(1.0F/(float)getMaxPoints());
	//	}

	/**
	 * @return the attributeMap
	 */
	public ImmutableMap<Attribute,Double> getAttributeMap() {
		return attributeMap;
	}

	/**
	 * @return the minPointsPerLevel
	 */
	public int getMinPointsPerLevel() {
		return minPointsPerLevel;
	}

	/**
	 * @return the maxPointsPerLevel
	 */
	public int getMaxPointsPerLevel() {
		return maxPointsPerLevel;
	}


}
