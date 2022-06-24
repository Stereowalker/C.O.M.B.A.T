package com.stereowalker.rankup.world.stat;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.util.NBTHelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;

public class StatSettings {
	private static final Marker STAT_SETTINGS = MarkerManager.getMarker("STAT_SETTINGS");

	private final boolean enabled;
	private final boolean limitable;
	private final int defaultPoints;
	private final int minPointsPerLevel;
	private final int maxPointsPerLevel;
	private final int upgradePointsPerLevel;
	private final ResourceLocation effortStat;
	private final double effortValue;
	private final ImmutableMap<Attribute,Double> attributeMap;

	public StatSettings(JsonObject object, ResourceLocation owner) {
		boolean enabledIn = false;
		boolean limitableIn = false;
		int defaultPointsIn = 0;
		int minPointsPerLevelIn = 0;
		int maxPointsPerLevelIn = 0;
		int upgradePointsPerLevelIn = 0;
		ResourceLocation effortStatIn = null;
		double effortValueIn = 0;
		
		ImmutableMap.Builder<Attribute,Double> attributeMapIn = ImmutableMap.builder();

		String NOTHING = "nothing";
		String ENABLED = "enabled";
		String LIMITABLE = "limitable";
		String DEFAULT_POINTS = "default_points";
		String MAX_POINTS_PER_LEVEL = "maximum_points_earned_per_level";
		String MIN_POINTS_PER_LEVEL = "minimum_points_earned_per_level";
		String UPGRADE_POINTS_PER_LEVEL = "upgrade_points_earned_per_level";
		String ATTRIBUTES = "attributes";
		String EFFORT_STAT = "effort_stat";
		String EFFORT_VALUE = "effort_value";

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
							Combat.getInstance().getLogger().warn("Loading stat settings %s from JSON: Attribute %d isn't a json object", owner,  i);
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
				
				if(object.has(UPGRADE_POINTS_PER_LEVEL) && object.get(UPGRADE_POINTS_PER_LEVEL).isJsonPrimitive()) {
					workingOn = UPGRADE_POINTS_PER_LEVEL;
					upgradePointsPerLevelIn = object.get(UPGRADE_POINTS_PER_LEVEL).getAsInt();
					workingOn = NOTHING;
				}

				if(object.has(EFFORT_STAT) && object.get(EFFORT_STAT).isJsonPrimitive()) {
					workingOn = EFFORT_STAT;
					effortStatIn = new ResourceLocation(object.get(EFFORT_STAT).getAsString());
					workingOn = NOTHING;
				}
				
				if(object.has(EFFORT_VALUE) && object.get(EFFORT_VALUE).isJsonPrimitive()) {
					workingOn = EFFORT_VALUE;
					effortValueIn = object.get(EFFORT_VALUE).getAsDouble();
					workingOn = NOTHING;
				}

			} catch (ClassCastException e) {
				Combat.getInstance().getLogger().warn(STAT_SETTINGS,
						"Loading stat settings $s from JSON: Parsing element %s: element was wrong type!", e, owner, workingOn);
			} catch (NumberFormatException e) {
				Combat.getInstance().getLogger().warn(STAT_SETTINGS,
						"Loading drink data $s from JSON: Parsing element %s: element was an invalid number!", e, owner, workingOn);
			}
		}

		this.enabled = enabledIn;
		this.limitable = limitableIn;
		this.defaultPoints = defaultPointsIn;
		this.minPointsPerLevel = minPointsPerLevelIn;
		this.maxPointsPerLevel = maxPointsPerLevelIn;
		this.upgradePointsPerLevel = upgradePointsPerLevelIn;
		this.attributeMap = attributeMapIn.build();
		this.effortStat = effortStatIn;
		this.effortValue = effortValueIn;

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

	public int getUpgradePointsPerLevel() {
		return upgradePointsPerLevel;
	}
	
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
	
	public ResourceLocation getEffortStat() {
		return effortStat;
	}

	public double getEffortValueModifier() {
		return effortValue;
	}
	
	public CompoundTag serialize() {
		CompoundTag nbt = new CompoundTag();
		nbt.putBoolean("enabled", this.enabled);
		nbt.putBoolean("limitable", this.limitable);
		nbt.putInt("defaultPoints", this.defaultPoints);
		nbt.putInt("minPointsPerLevel", this.minPointsPerLevel);
		nbt.putInt("maxPointsPerLevel", this.maxPointsPerLevel);
		nbt.putInt("upgradePointsPerLevel", this.upgradePointsPerLevel);
		nbt.putString("effortStat", this.effortStat+"");
		nbt.putDouble("effortValue", this.effortValue);
		
		ListTag list = new ListTag();
		for (Attribute attribute : this.attributeMap.keySet()) {
			CompoundTag nbt2 = new CompoundTag();
			nbt2.putString("attribute", attribute.getRegistryName().toString());
			nbt2.putDouble("value", this.attributeMap.get(attribute));
			list.add(nbt2);
		}
		
		nbt.put("attributeMap", list);
		
		return nbt;
	}
	
	public StatSettings(CompoundTag nbt) {
		this.enabled = nbt.getBoolean("enabled");
		this.limitable = nbt.getBoolean("limitable");
		this.defaultPoints = nbt.getInt("defaultPoints");
		this.minPointsPerLevel = nbt.getInt("minPointsPerLevel");
		this.maxPointsPerLevel = nbt.getInt("maxPointsPerLevel");
		this.upgradePointsPerLevel = nbt.getInt("upgradePointsPerLevel");
		this.effortStat = new ResourceLocation(nbt.getString("effortStat"));
		this.effortValue = nbt.getDouble("effortValue");
		
		ImmutableMap.Builder<Attribute,Double> attributeMapIn = ImmutableMap.builder();
		for (Tag nbt3 : nbt.getList("attributeMap", NBTHelper.NbtType.CompoundNBT)) {
			CompoundTag nbt2 = (CompoundTag)nbt3;
			attributeMapIn.put(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(nbt2.getString("attribute"))), nbt2.getDouble("value"));
		}
		this.attributeMap = attributeMapIn.build();
	}
}
