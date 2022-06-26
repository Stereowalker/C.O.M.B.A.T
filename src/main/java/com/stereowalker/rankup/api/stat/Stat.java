package com.stereowalker.rankup.api.stat;

import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;

import net.minecraft.Util;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Stat extends ForgeRegistryEntry<Stat>{
	
	public static final Codec<Stat> CODEC = RecordCodecBuilder.create((p_67790_) -> {
		return p_67790_.group(Codec.STRING.fieldOf("uniqueId").forGetter((p_160992_) -> {
			return p_160992_.getUniqueID().toString();
		}), ResourceLocation.CODEC.fieldOf("baseAttribute").forGetter((p_160992_) -> {
			return p_160992_.getBaseAttribute().getRegistryName();
		})).apply(p_67790_, Stat::new);
	});
	
	private String translationKey;
	UUID uniqueId;
	Attribute baseAttribute;
	
	public Stat(String uniqueId, ResourceLocation baseAttribute) {
		this(uniqueId, ForgeRegistries.ATTRIBUTES.getValue(baseAttribute));
	}
	
	public Stat(String uniqueId, Attribute baseAttribute) {
		this.uniqueId = UUID.fromString(uniqueId);
		this.baseAttribute = baseAttribute;
	}
	
	public Stat() {
		this.uniqueId = null;
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
	}
	
	public UUID getUniqueID() {
		return uniqueId;
	}
	
	public Attribute getBaseAttribute() {
		return baseAttribute;
	}
	
	public ResourceLocation getButtonTexture() {
		return new ResourceLocation(this.getRegistryName().getNamespace(),"textures/upgradeableattribute/"+this.getRegistryName().getPath()+".png");
	}
	
	public ResourceLocation getLockedButtonTexture() {
		return Combat.getInstance().location("textures/gui/locked_level_button.png");
	}
	
	public static int getExperienceCost(int level) {
		int n = level + 1;
		int a = Combat.RPG_CONFIG.baseXpCost;
		int d = Combat.RPG_CONFIG.xpCostStep;
		
		int total = (n/2) * ((2*a) + (n - 1) * d);
		return total;
	}
	
	public static int getExperienceCost(Player player, ResourceKey<Stat> stat) {
		return getExperienceCost(PlayerAttributeLevels.getStatProfile(player, stat).getPoints());
	}
	
	public int getCurrentPoints(Player player) {
		return PlayerAttributeLevels.getStatProfile(player, player.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY).getResourceKey(this).get()).getPoints();
	}
	
	public int getAdditionalPoints(Player player) {
		return PlayerAttributeLevels.getStatProfile(player, player.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY).getResourceKey(this).get()).getModifierPoints();
	}
	
	public int getEffortPoints(Player player) {
		return PlayerAttributeLevels.getStatProfile(player, player.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY).getResourceKey(this).get()).getEffortPoints();
	}
}
