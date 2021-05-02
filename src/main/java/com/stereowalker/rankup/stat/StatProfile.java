package com.stereowalker.rankup.stat;

import java.util.Map;

import com.google.common.collect.Maps;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.unionlib.util.NBTHelper.NbtType;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class StatProfile {
	int points;
	Map<String,Integer> modifiers;
	
	public StatProfile(int pointsIn, Map<String,Integer> modifiersIn) {
		this.points = pointsIn;
		this.modifiers = modifiersIn;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	public static void setPoints(LivingEntity entity, Stat stat, int points) {
		StatProfile statProfile = PlayerAttributeLevels.getStatPoints(entity, stat);
		statProfile.setPoints(points);
		PlayerAttributeLevels.setStatProfile(entity, stat, statProfile);
	}
	
	public static void addModifier(LivingEntity entity, Stat stat, String id, int points) {
		StatProfile statProfile = PlayerAttributeLevels.getStatPoints(entity, stat);
		statProfile.getModifiers().put(id, points);
		PlayerAttributeLevels.setStatProfile(entity, stat, statProfile);
	}
	
	public static void removeModifier(LivingEntity entity, Stat stat, String id) {
		StatProfile statProfile = PlayerAttributeLevels.getStatPoints(entity, stat);
		statProfile.getModifiers().remove(id);
		PlayerAttributeLevels.setStatProfile(entity, stat, statProfile);
	}

	/**
	 * @param modifiers the modifiers to set
	 */
	public void setModifiers(Map<String, Integer> modifiers) {
		this.modifiers = modifiers;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}
	
	public int getModifierPoints() {
		int points = 0;
		for (Integer mod : getModifiers().values()) {
			points+=mod;
		}
		return points;
	}
	
	public static int getTotalPoints(LivingEntity entity, Stat stat) {
		StatProfile statProfile = PlayerAttributeLevels.getStatPoints(entity, stat);
		int points = statProfile.getPoints();
		for (Integer mod : statProfile.getModifiers().values()) {
			points+=mod;
		}
		return points;
	}

	/**
	 * @return the modifiers
	 */
	public Map<String, Integer> getModifiers() {
		return modifiers;
	}
	


	/**
	 * Reads the water data for the player.
	 */
	public void read(CompoundNBT compound) {
		if (compound.contains("points", 99)) {
			this.points = compound.getInt("points");
			
			ListNBT modifiers = compound.getList("modifiers", NbtType.CompoundNBT);
			Map<String,Integer> modifiersMap = Maps.newHashMap();
			for(int i = 0; i < modifiers.size(); i++) {
				CompoundNBT nbt = modifiers.getCompound(i);
				modifiersMap.put(nbt.getString("id"), nbt.getInt("points"));
			}
			this.modifiers = modifiersMap;
		}
	}

	/**
	 * Writes the water data for the player.
	 */
	public void write(CompoundNBT compound) {
		compound.putInt("points", this.points);
		ListNBT modifiers = new ListNBT();
		for(String modifier : this.modifiers.keySet()) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putString("id", modifier);
			nbt.putInt("points", this.modifiers.get(modifier));
			modifiers.add(nbt);
		}
		compound.put("modifiers", modifiers);
	}
}
