package com.stereowalker.rankup.api.stat;

import java.util.UUID;

import com.stereowalker.combat.Combat;
import com.stereowalker.old.combat.config.Config;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;

import net.minecraft.Util;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
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

	public MutableComponent getName() {
		return new TranslatableComponent(this.getTranslationKey());
	}

	protected String getDefaultTranslationKey() {
		if (this.translationKey == null) {
			this.translationKey = Util.makeDescriptionId("stat", this.getRegistryName());
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
		int a = Combat.RPG_CONFIG.baseXpCost;
		int d = Combat.RPG_CONFIG.xpCostStep;
		
		int total = (n/2) * ((2*a) + (n - 1) * d);
		return total;
	}
	
	public int getExperienceCost(Player player) {
		return getExperienceCost(PlayerAttributeLevels.getStatPoints(player, this).getPoints());
	}
	
	public int getCurrentPoints(Player player) {
		return PlayerAttributeLevels.getStatPoints(player, this).getPoints();
	}
	
	public int getAdditionalPoints(Player player) {
		return PlayerAttributeLevels.getStatPoints(player, this).getModifierPoints();
	}
	
	public int getEffortPoints(Player player) {
		return PlayerAttributeLevels.getStatPoints(player, this).getEffortPoints();
	}
}
