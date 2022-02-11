package com.stereowalker.rankup.skill.api;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.skill.Skills;

import net.minecraft.Util;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Skill extends ForgeRegistryEntry<Skill> {

	private String translationKey;
	private Skill superSkill;
	private int primaryColor;
	private int secondaryColor;
	private int level;
	private boolean isSpawnSkill;

	public Skill(Builder builder) {
		this.superSkill = builder.superSkill;
		this.primaryColor = builder.primaryColor;
		this.secondaryColor = builder.secondaryColor;
		this.level = builder.level;
		this.isSpawnSkill = builder.isSpawnSkill;
	}
	
	public Skill getSuperSkill() {
		return superSkill == null ? Skills.EMPTY : superSkill;
	}
	
	public int getPrimaryColor() {
		return primaryColor;
	}
	
	public int getSecondaryColor() {
		return secondaryColor;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean isSpawnSkill() {
		return isSpawnSkill;
	}
	
	public boolean isSubSkill() {
		return getSuperSkill() != null && getSuperSkill() != Skills.EMPTY;
	}

	public String getTranslationKey() {
		return this.getDefaultTranslationKey();
	}

	public String getKey() {
		return getTranslationKey();
	}

	public MutableComponent getName() {
		return new TranslatableComponent(this.getTranslationKey());
	}

	protected String getDefaultTranslationKey() {
		if (this.translationKey == null) {
			this.translationKey = Util.makeDescriptionId("skill", this.getRegistryName());
		}

		return this.translationKey;
	}

	public ResourceLocation getButtonTexture() {
		return Combat.getInstance().location("textures/skill/"+this.getRegistryName().getPath()+".png");
	}

	public ResourceLocation getLockedButtonTexture() {
		return Combat.getInstance().location("textures/gui/locked_level_button.png");
	}

	public boolean isEnabled() {
		return true;
	}
	
	public List<Skill> getSubSkills(){
		List<Skill> skills = new ArrayList<Skill>();
		for (Skill skill : CombatRegistries.SKILLS) {
			if (skill.getSuperSkill().equals(this)) {
				skills.add(skill);
			}
		}
		return skills;
	}
	
	/**
	 * Called every tick for the player that has the skill
	 * @param entity
	 */
	public void playerTick(LivingEntity entity) {
		
	}

	
	/**
	 * Called once the player attacks a target
	 * @param player - The one attacking
	 * @param target - The one being attacked
	 */
	public void onAttackEntity(Player player, Entity target) {
		
	}
	
	public static class Builder {
		Skill superSkill;
		int primaryColor;
		int secondaryColor;
		int level;
		boolean isSpawnSkill;
		
		public Builder setSuperSkill(Skill superSkill) {
			this.superSkill = superSkill;
			return this;
		}
		
		public Builder setPrimaryColor(int primaryColor) {
			this.primaryColor = primaryColor;
			return this;
		}
		
		public Builder setSecondaryColor(int secondaryColor) {
			this.secondaryColor = secondaryColor;
			return this;
		}
		
		public Builder setLevel(int level) {
			this.level = level;
			return this;
		}
		
		public Builder setNoLevels() {
			this.level = -1;
			return this;
		}
		
		public Builder isSpawnSkill() {
			this.isSpawnSkill = true;
			return this;
		}
	}
}
