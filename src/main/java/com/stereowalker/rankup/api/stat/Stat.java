package com.stereowalker.rankup.api.stat;

import java.util.UUID;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.config.Config;
import com.stereowalker.rankup.stat.PlayerAttributeLevels;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Stat extends ForgeRegistryEntry<Stat>{
	private String translationKey;
	UUID uniqueId;
	
	public Stat(String uniqueId) {
		this.uniqueId = UUID.fromString(uniqueId);
	}
	
	public String getTranslationKey() {
		return this.getDefaultTranslationKey();
	}

	public String getKey() {
		return getTranslationKey();
	}

	public IFormattableTextComponent getName() {
		return new TranslationTextComponent(this.getTranslationKey());
	}

	protected String getDefaultTranslationKey() {
		if (this.translationKey == null) {
			this.translationKey = Util.makeTranslationKey("stat", this.getRegistryName());
		}

		return this.translationKey;
	}
	
	/**
	 * Called once when the stat is first applied to the player
	 * @param player
	 */
	public void init(LivingEntity entity, Attribute attribute, double baseValue, double points){
//		System.out.println("INIT "+attribute.getRegistryName()+" "+baseValue);
	}
	
	public UUID getUniqueID() {
		return uniqueId;
	}
	
	public ResourceLocation getButtonTexture() {
		return new ResourceLocation(this.getRegistryName().getNamespace(),"textures/upgradeableattribute/"+this.getRegistryName().getPath()+".png");
	}
	
	public ResourceLocation getLockedButtonTexture() {
		return Combat.getInstance().location("textures/gui/locked_level_button.png");
	}
	
	public int getExperienceCost(int level) {
		int n = level + 1;
		int a = Config.RPG_COMMON.baseXpCost.get();
		int d = Config.RPG_COMMON.xpCostStep.get();
		
		int total = (n/2) * ((2*a) + (n - 1) * d);
		return total;
	}
	
	public int getExperienceCost(PlayerEntity player) {
		return getExperienceCost(PlayerAttributeLevels.getStatPoints(player, this).getPoints());
	}
	
	public int getCurrentPoints(PlayerEntity player) {
		return PlayerAttributeLevels.getStatPoints(player, this).getPoints();
	}
	
	public int getAdditionalPoints(PlayerEntity player) {
		return PlayerAttributeLevels.getStatPoints(player, this).getModifierPoints();
	}
	
	public int getEffortPoints(PlayerEntity player) {
		return PlayerAttributeLevels.getStatPoints(player, this).getEffortPoints();
	}
}
