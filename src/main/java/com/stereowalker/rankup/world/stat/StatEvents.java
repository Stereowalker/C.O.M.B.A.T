package com.stereowalker.rankup.world.stat;

import java.util.Random;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.network.protocol.game.ClientboundEntityStatsPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerDisplayStatPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerLevelUpPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerStatsPacket;
import com.stereowalker.rankup.network.protocol.game.ClientboundStatManagerPacket;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.network.NetworkDirection;

public class StatEvents {

	public static void registerEntityStats(LivingEntity entity) {
		PlayerAttributeLevels.addLevelsOnSpawn(entity);
	}

	public static void sendPlayerToClient(ServerPlayer player) {
		Combat.getInstance().channel.sendTo(new ClientboundPlayerStatsPacket(player), player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendEntityToClient(ServerPlayer player, LivingEntity target) {
		Combat.getInstance().channel.sendTo(new ClientboundEntityStatsPacket(target), player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendStatsToClient(ServerPlayer player) {
		for (Stat stat : Rankup.statsManager.STATS.keySet()) {
			Combat.getInstance().channel.sendTo(new ClientboundStatManagerPacket(stat, Rankup.statsManager.STATS.get(stat)), player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
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
		for (Stat levels : CombatRegistries.STATS) {
			PlayerAttributeLevels.setStatProfile(player, levels, PlayerAttributeLevels.getStatPoints(original, levels));
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
		for (Stat stat : CombatRegistries.STATS) {
			int min = Rankup.statsManager.STATS.get(stat).getMinPointsPerLevel();
			int max = Rankup.statsManager.STATS.get(stat).getMaxPointsPerLevel();
			int i;
			if (max > 0)
				i = min + new Random().nextInt(Mth.clamp(max - min, 0, max));
			else
				i = 0;

			if (Combat.RPG_CONFIG.levelUpType == LevelType.ASSIGN_POINTS) {
				PlayerAttributeLevels.addStatPoints(player, stat, i);
				upText.append(stat.getName().append("+"+i).withStyle(ChatFormatting.GREEN)).append(", ");
			} else {
				upPoints += Rankup.statsManager.STATS.get(stat).getUpgradePointsPerLevel();
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
			if (PlayerAttributeLevels.getExperience(player) >= getMaxExperience(PlayerAttributeLevels.getLevel(player))) {
				PlayerAttributeLevels.setExperience(player, PlayerAttributeLevels.getExperience(player) - getMaxExperience(PlayerAttributeLevels.getLevel(player)));
				levelUp(player, true);
			}
		}

		for (Stat stat : CombatRegistries.STATS) {
			StatSettings statSettings = Rankup.statsManager.STATS.get(stat);
			double addition = StatProfile.getTotalPoints(entity, stat);

			if (entity instanceof ServerPlayer) {
				ServerPlayer player = (ServerPlayer) entity;
				if (statSettings.getEffortStat() != null) {
					if (Combat.RPG_CONFIG.enableTraining && player.getStats().getValue(net.minecraft.stats.Stats.CUSTOM.get(statSettings.getEffortStat())) > PlayerAttributeLevels.getStatPoints(player, stat).getEffortPoints() * statSettings.getEffortValueModifier()) {
						StatProfile.setEffortPoints(player, stat, PlayerAttributeLevels.getStatPoints(player, stat).getEffortPoints()+1);
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
		}
	}

	@SuppressWarnings("unchecked")
	public static int calculatePointsFromBase(LivingEntity entity, Stat stat) {
		Attribute attribute;

		if (stat == Stats.VITALITY) {
			attribute = Attributes.MAX_HEALTH;

		} else if (stat == Stats.AGILITY) {
			attribute = Attributes.MOVEMENT_SPEED;

		} else if (stat == Stats.DEFENCE) {
			attribute = CAttributes.PHYSICAL_RESISTANCE;

		} else if (stat == Stats.STRENGTH) {
			attribute = Attributes.ATTACK_DAMAGE;

		} else {
			attribute = null;
		}

		if (!entity.level.isClientSide() && Rankup.statsManager.STATS.get(stat).getAttributeMap().containsKey(attribute) && attribute != null && DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity.getType()).hasAttribute(attribute)) {
			double baseValue = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity.getType()).getBaseValue(attribute);
			return Mth.ceil(baseValue / Rankup.statsManager.STATS.get(stat).getAttributeMap().get(attribute));
		} else {
			return 0;
		}
	}

	public static void initializeAllStats(LivingEntity entity) {
		for (Stat stat : CombatRegistries.STATS) {
			StatSettings statSettings = Rankup.statsManager.STATS.get(stat);
			double points = PlayerAttributeLevels.getStatPoints(entity, stat).getPoints();

			statSettings.getAttributeMap().forEach((attribute, modifierPerPoint) -> {

				double baseValue = modifierPerPoint.doubleValue() * points;
				stat.init(entity, attribute, baseValue, points);
			});
		}
	}
}
