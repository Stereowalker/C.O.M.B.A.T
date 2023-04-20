package com.stereowalker.rankup.world.stat;

import java.util.Map.Entry;
import java.util.Random;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerLevelUpPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerStatsPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundStatManagerPacket;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.PlayerSkills.SkillGrantReason;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;

public class StatEvents {

	public static void registerEntityStats(LivingEntity entity) {
		PlayerAttributeLevels.addLevelsOnSpawn(entity);
	}

	public static void sendStatsToClient(ServerPlayer player) {
		for (ResourceKey<Stat> stat : Rankup.statsManager.STATS.keySet()) {
			new ClientboundStatManagerPacket(stat.location(), Rankup.statsManager.STATS.get(stat)).send(player);
		}
	}

	public static void restoreStats(Player thisPlayer, Player thatPlayer, boolean keepEverything) {
		PlayerAttributeLevels.getOrCreateRankNBT(thisPlayer);
		if (thisPlayer.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || keepEverything) {
			PlayerAttributeLevels.setExperience(thisPlayer, PlayerAttributeLevels.getExperience(thatPlayer));
		}
		for (Entry<ResourceKey<Stat>, Stat> levels : thisPlayer.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY).entrySet()) {
			PlayerAttributeLevels.setStatProfile(thisPlayer, levels.getKey(), PlayerAttributeLevels.getStatProfile(thatPlayer, levels.getKey()));
		}
		PlayerAttributeLevels.setLevel(thisPlayer, PlayerAttributeLevels.getLevel(thatPlayer));
		PlayerAttributeLevels.setUpgradePoints(thisPlayer, PlayerAttributeLevels.getUpgradePoints(thatPlayer));
	}

	public static int getExperienceCost(int level) {
		int n = level + 1;
		int a = Combat.RPG_CONFIG.baseXpForLevelCost;
		int d = Combat.RPG_CONFIG.xpCostForLevelStep;

		int total = (n/2) * ((2*a) + (n - 1) * d);
		return total;
	}

	public static int getMaxExperience(int level) {
		int n = level + 1;
		int a = Combat.RPG_CONFIG.baseXpForLevelCost;
		int d = Combat.RPG_CONFIG.xpCostForLevelStep;

		int xp = a + (n - 1)*d;
		return xp;
	}

	public static void levelUp(LivingEntity player, boolean sendMessage) {
		PlayerAttributeLevels.addLevel(player);
		MutableComponent upText = Component.literal("You are now at Level "+PlayerAttributeLevels.getLevel(player)+" and have recieved [").withStyle(ChatFormatting.AQUA);
		int upPoints = 0;
		Registry<Stat> registry = player.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY);
		for (Entry<ResourceKey<Stat>, Stat> stat : registry.entrySet()) {
			int min = Rankup.statsManager.STATS.get(stat.getKey()).getMinPointsPerLevel();
			int max = Rankup.statsManager.STATS.get(stat.getKey()).getMaxPointsPerLevel();
			int i;
			if (max > 0)
				i = min + new Random().nextInt(Mth.clamp(max - min, 0, max));
			else
				i = 0;

			if (Combat.RPG_CONFIG.levelUpType == LevelType.ASSIGN_POINTS) {
				PlayerAttributeLevels.addStatPoints(player, stat.getKey(), i);
				upText.append(Component.translatable(Util.makeDescriptionId("stat", stat.getKey().location())).append("+"+i).withStyle(ChatFormatting.GREEN)).append(", ");
			} else {
				upPoints += Rankup.statsManager.STATS.get(stat.getKey()).getUpgradePointsPerLevel();
			}
		}
		if (Combat.RPG_CONFIG.levelUpType == LevelType.UPGRADE_POINTS) {
			PlayerAttributeLevels.addUpgradePoints(player, upPoints);
			upText.append(Component.literal("Upgrade Points +"+upPoints).withStyle(ChatFormatting.GREEN));
		}
		if (sendMessage) new ClientboundPlayerLevelUpPacket(PlayerAttributeLevels.getLevel(player), upText.append("]").withStyle(ChatFormatting.AQUA)).send((ServerPlayer)player);
	}

	@SuppressWarnings("unchecked")
	public static void statUpdate(LivingEntity entity) {
		if (entity instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer) entity;
			boolean shouldLevelUp = Combat.RPG_CONFIG.maxLevel > 0 && PlayerAttributeLevels.getLevel(player) < Combat.RPG_CONFIG.maxLevel;
			boolean nearingMaxLevel = Combat.RPG_CONFIG.maxLevel > 0 && PlayerAttributeLevels.getLevel(player) == Combat.RPG_CONFIG.maxLevel-1;
			if (shouldLevelUp && PlayerAttributeLevels.getExperience(player) >= getMaxExperience(PlayerAttributeLevels.getLevel(player))) {
				if (nearingMaxLevel)
					PlayerAttributeLevels.setExperience(player, 0);
				else	
					PlayerAttributeLevels.setExperience(player, PlayerAttributeLevels.getExperience(player) - getMaxExperience(PlayerAttributeLevels.getLevel(player)));
				levelUp(player, true);
			}
		}

		Registry<Stat> registry = entity.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY);
		for (Entry<ResourceKey<Stat>, Stat> stat : registry.entrySet()) {
			StatSettings statSettings = Rankup.statsManager.STATS.get(stat.getKey());
			if (statSettings != null) {
				double addition = StatProfile.getTotalPoints(entity, stat.getKey());

				if (entity instanceof ServerPlayer) {
					ServerPlayer player = (ServerPlayer) entity;
					if (statSettings.getEffortStat() != null) {
						if (Combat.RPG_CONFIG.enableTraining && player.getStats().getValue(net.minecraft.stats.Stats.CUSTOM.get(statSettings.getEffortStat())) > PlayerAttributeLevels.getStatProfile(player, stat.getKey()).getEffortPoints() * statSettings.getEffortValueModifier()) {
							StatProfile.setEffortPoints(player, stat.getKey(), PlayerAttributeLevels.getStatProfile(player, stat.getKey()).getEffortPoints()+1);
						}
					}
					if (addition > 110 && statSettings.isLimitable() && !PlayerSkills.hasSkill(player, Skills.LIMITER)) {
						PlayerSkills.grantSkill(player, Skills.LIMITER, SkillGrantReason.LIMITER);
					}
				}

				statSettings.getAttributeMap().forEach((attributeKey, modifierPerPoint) -> {
					Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(attributeKey);
					
					double baseValue = modifierPerPoint.doubleValue() * addition;


					if (entity.getAttribute(attribute) != null && entity.isAlive()) {
						if (addition > 0 && Combat.RPG_CONFIG.enableLevelingSystem && (!statSettings.isLimitable() || !PlayerSkills.isSkillActive(entity, Skills.LIMITER)) && statSettings.isEnabled()) {
							if (entity.getAttribute(attribute).getBaseValue() != baseValue) {
								entity.getAttribute(attribute).setBaseValue(baseValue);
							}
						}
						else {
							if (entity.getAttribute(attribute).getBaseValue() != DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity.getType()).getBaseValue(attribute)) {
								entity.getAttribute(attribute).setBaseValue(DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity.getType()).getBaseValue(attribute));
							}
						}
					}
				});
			} else {
				Combat.debug(stat.getKey()+"' settings are not set");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static int calculatePointsFromBase(LivingEntity entity, ResourceKey<Stat> stat) {
		Registry<Stat> registry = entity.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY);
		if (!entity.level.isClientSide()) {
			if (registry.containsKey(stat)) {
				Attribute attribute = registry.get(stat).getBaseAttribute();
				if (Rankup.statsManager.STATS.get(stat) != null) {
					if (attribute != null
							&& Rankup.statsManager.STATS.get(stat).getAttributeMap().containsKey(BuiltInRegistries.ATTRIBUTE.getKey(attribute)) 
							&& DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity.getType()).hasAttribute(attribute)) {
						double baseValue = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity.getType()).getBaseValue(attribute);
						return Mth.ceil(baseValue / Rankup.statsManager.STATS.get(stat).getAttributeMap().get(BuiltInRegistries.ATTRIBUTE.getKey(attribute)));
					}
				} else {
					Combat.debug(stat+"' settings are not set");
				}
			} else {
				Combat.getInstance().getLogger().warn("The stat {} does not exist in the registry", stat);
			}
		}
		return 0;
	}

	public static void initializeAllStats(LivingEntity entity) {
		System.out.println("ADUWA PLEASE");
		Registry<Stat> registry = entity.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY);
		for (Entry<ResourceKey<Stat>, Stat> stat : registry.entrySet()) {
			StatSettings statSettings = Rankup.statsManager.STATS.get(stat.getKey());
			double points = PlayerAttributeLevels.getStatProfile(entity, stat.getKey()).getPoints();

			if (statSettings != null)
				statSettings.getAttributeMap().forEach((attribute, modifierPerPoint) -> {

					double baseValue = modifierPerPoint.doubleValue() * points;
					stat.getValue().getType().init(entity, BuiltInRegistries.ATTRIBUTE.get(attribute), baseValue, points);
				});
			else
				Combat.debug(stat+"' settings are not set");
		}
	}
}
