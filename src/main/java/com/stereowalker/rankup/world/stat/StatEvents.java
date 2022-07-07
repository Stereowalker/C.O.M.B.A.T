package com.stereowalker.rankup.world.stat;

import java.util.Map.Entry;
import java.util.Random;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.network.protocol.game.ClientboundEntityStatsPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerDisplayStatPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerLevelUpPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerStatsPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundStatManagerPacket;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.network.NetworkDirection;

public class StatEvents {

	public static void registerEntityStats(LivingEntity entity) {
		PlayerAttributeLevels.addLevelsOnSpawn(entity);
	}

	public static void playerToClient(ServerPlayer player) {
		Combat.getInstance().channel.sendTo(new ClientboundPlayerStatsPacket(player), player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendEntityToClient(ServerPlayer player, LivingEntity target) {
		new ClientboundEntityStatsPacket(target).send(player);
	}

	public static void sendStatsToClient(ServerPlayer player) {
		for (ResourceKey<Stat> stat : Rankup.statsManager.STATS.keySet()) {
			Combat.getInstance().channel.sendTo(new ClientboundStatManagerPacket(stat.location(), Rankup.statsManager.STATS.get(stat)), player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	public static void restoreStats(Player player, Player original, boolean wasDeath) {
		PlayerAttributeLevels.getOrCreateRankNBT(player);
		if (!wasDeath) {
			CombatEntityStats.setLimiter(player, CombatEntityStats.isLimiterOn(original));
		}
		if (player.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || !wasDeath) {
			PlayerAttributeLevels.setExperience(player, PlayerAttributeLevels.getExperience(original));
		}
		for (Entry<ResourceKey<Stat>, Stat> levels : player.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY).entrySet()) {
			PlayerAttributeLevels.setStatProfile(player, levels.getKey(), PlayerAttributeLevels.getStatProfile(original, levels.getKey()));
		}
		PlayerAttributeLevels.setLevel(player, PlayerAttributeLevels.getLevel(original));
		PlayerAttributeLevels.setUpgradePoints(player, PlayerAttributeLevels.getUpgradePoints(original));
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
		if (sendMessage) new ClientboundPlayerLevelUpPacket(PlayerAttributeLevels.getLevel(player)).send((ServerPlayer)player);
		MutableComponent upText = new TextComponent("You are now at Level "+PlayerAttributeLevels.getLevel(player)+" and have recieved [").withStyle(ChatFormatting.AQUA);
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
				upText.append(new TranslatableComponent(Util.makeDescriptionId("stat", stat.getKey().location())).append("+"+i).withStyle(ChatFormatting.GREEN)).append(", ");
			} else {
				upPoints += Rankup.statsManager.STATS.get(stat.getKey()).getUpgradePointsPerLevel();
			}
		}
		if (Combat.RPG_CONFIG.levelUpType == LevelType.UPGRADE_POINTS) {
			PlayerAttributeLevels.addUpgradePoints(player, upPoints);
			upText.append(new TextComponent("Upgrade Points +"+upPoints).withStyle(ChatFormatting.GREEN));
		}
		if (sendMessage) new ClientboundPlayerDisplayStatPacket(upText.append("]").withStyle(ChatFormatting.AQUA)).send((ServerPlayer)player);
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

				}

				statSettings.getAttributeMap().forEach((attribute, modifierPerPoint) -> {

					double baseValue = modifierPerPoint.doubleValue() * addition;


					if (entity.getAttribute(attribute) != null && entity.isAlive()) {
						if (addition > 0 && Combat.RPG_CONFIG.enableLevelingSystem && (!statSettings.isLimitable() || !CombatEntityStats.isLimiterOn(entity)) && statSettings.isEnabled()) {
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
							&& Rankup.statsManager.STATS.get(stat).getAttributeMap().containsKey(attribute) 
							&& DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity.getType()).hasAttribute(attribute)) {
						double baseValue = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity.getType()).getBaseValue(attribute);
						return Mth.ceil(baseValue / Rankup.statsManager.STATS.get(stat).getAttributeMap().get(attribute));
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
		Registry<Stat> registry = entity.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY);
		for (Entry<ResourceKey<Stat>, Stat> stat : registry.entrySet()) {
			StatSettings statSettings = Rankup.statsManager.STATS.get(stat.getKey());
			double points = PlayerAttributeLevels.getStatProfile(entity, stat.getKey()).getPoints();

			if (statSettings != null)
				statSettings.getAttributeMap().forEach((attribute, modifierPerPoint) -> {

					double baseValue = modifierPerPoint.doubleValue() * points;
					stat.getValue().init(entity, attribute, baseValue, points);
				});
			else
				Combat.debug(stat+"' settings are not set");
		}
	}
}
