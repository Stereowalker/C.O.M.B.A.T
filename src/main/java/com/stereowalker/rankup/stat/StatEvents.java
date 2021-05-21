package com.stereowalker.rankup.stat;

import java.util.Random;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.network.server.SEntityStatsPacket;
import com.stereowalker.rankup.network.server.SPlayerDisplayStatPacket;
import com.stereowalker.rankup.network.server.SPlayerLevelUpPacket;
import com.stereowalker.rankup.network.server.SPlayerStatsPacket;
import com.stereowalker.rankup.network.server.SStatManagerPacket;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.NetworkDirection;

public class StatEvents {

	public static void registerEntityStats(LivingEntity entity) {
		PlayerAttributeLevels.addLevelsOnSpawn(entity);
	}

	public static void sendPlayerToClient(ServerPlayerEntity player) {
		Combat.getInstance().channel.sendTo(new SPlayerStatsPacket(player), player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendEntityToClient(ServerPlayerEntity player, LivingEntity target) {
		Combat.getInstance().channel.sendTo(new SEntityStatsPacket(target), player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendStatsToClient(ServerPlayerEntity player) {
		for (Stat stat : Rankup.statsManager.STATS.keySet()) {
			Combat.getInstance().channel.sendTo(new SStatManagerPacket(stat, Rankup.statsManager.STATS.get(stat)), player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	public static void restoreStats(PlayerEntity player, PlayerEntity original, boolean wasDeath) {
		PlayerAttributeLevels.getOrCreateRankNBT(player);
		if (!wasDeath) {
			CombatEntityStats.setLimiter(player, CombatEntityStats.isLimiterOn(original));
		}
		for (Stat levels : CombatRegistries.STATS) {
			PlayerAttributeLevels.setStatProfile(player, levels, PlayerAttributeLevels.getStatPoints(original, levels));
		}
		PlayerAttributeLevels.setLevel(player, PlayerAttributeLevels.getLevel(original));
		PlayerAttributeLevels.setExperience(player, PlayerAttributeLevels.getExperience(original));
		PlayerAttributeLevels.setUpgradePoints(player, PlayerAttributeLevels.getUpgradePoints(original));
	}

	public static int getExperienceCost(int level) {
		int n = level + 1;
		int a = Config.RPG_COMMON.baseXpForLevelCost.get();
		int d = Config.RPG_COMMON.xpCostForLevelStep.get();

		int total = (n/2) * ((2*a) + (n - 1) * d);
		return total;
	}

	public static int getMaxExperience(int level) {
		int n = level + 1;
		int a = Config.RPG_COMMON.baseXpForLevelCost.get();
		int d = Config.RPG_COMMON.xpCostForLevelStep.get();

		int xp = a + (n - 1)*d;
		return xp;
	}

	public static void levelUp(LivingEntity player, boolean sendMessage) {
		PlayerAttributeLevels.addLevel(player);
		if (sendMessage) new SPlayerLevelUpPacket(PlayerAttributeLevels.getLevel(player)).send((ServerPlayerEntity)player);
		IFormattableTextComponent upText = new StringTextComponent("You are now at Level "+PlayerAttributeLevels.getLevel(player)+" and have recieved [").mergeStyle(TextFormatting.AQUA);
		int upPoints = 0;
		for (Stat stat : CombatRegistries.STATS) {
			int min = Rankup.statsManager.STATS.get(stat).getMinPointsPerLevel();
			int max = Rankup.statsManager.STATS.get(stat).getMaxPointsPerLevel();
			int i;
			if (max > 0)
				i = min + new Random().nextInt(MathHelper.clamp(max - min, 0, max));
			else
				i = 0;

			if (Config.RPG_COMMON.levelUpType.get() == LevelType.ASSIGN_POINTS) {
				PlayerAttributeLevels.addStatPoints(player, stat, i);
				upText.appendSibling(stat.getName().appendString("+"+i).mergeStyle(TextFormatting.GREEN)).appendString(", ");
			} else {
				upPoints += Rankup.statsManager.STATS.get(stat).getUpgradePointsPerLevel();
			}
		}
		if (Config.RPG_COMMON.levelUpType.get() == LevelType.UPGRADE_POINTS) {
			PlayerAttributeLevels.addUpgradePoints(player, upPoints);
			upText.appendSibling(new StringTextComponent("Upgrade Points +"+upPoints).mergeStyle(TextFormatting.GREEN));
		}
		if (sendMessage) new SPlayerDisplayStatPacket(upText.appendString("]").mergeStyle(TextFormatting.AQUA)).send((ServerPlayerEntity)player);
	}

	@SuppressWarnings("unchecked")
	public static void statUpdate(LivingEntity entity) {
		if (entity instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) entity;
			if (PlayerAttributeLevels.getExperience(player) >= getMaxExperience(PlayerAttributeLevels.getLevel(player))) {
				PlayerAttributeLevels.setExperience(player, PlayerAttributeLevels.getExperience(player) - getMaxExperience(PlayerAttributeLevels.getLevel(player)));
				levelUp(player, true);
			}
		}

		for (Stat stat : CombatRegistries.STATS) {
			StatSettings statSettings = Rankup.statsManager.STATS.get(stat);
			double addition = StatProfile.getTotalPoints(entity, stat);

			statSettings.getAttributeMap().forEach((attribute, modifierPerPoint) -> {

				double baseValue = modifierPerPoint.doubleValue() * addition;


				if (entity.getAttribute(attribute) != null && entity.isAlive()) {
					if (addition > 0 && Config.RPG_COMMON.enableLevelingSystem.get() && (!statSettings.isLimitable() || !CombatEntityStats.isLimiterOn(entity)) && statSettings.isEnabled()) {
						if (entity.getAttribute(attribute).getBaseValue() != baseValue) {
							entity.getAttribute(attribute).setBaseValue(baseValue);
						}
					}
					else {
						if (entity.getAttribute(attribute).getBaseValue() != GlobalEntityTypeAttributes.getAttributesForEntity((EntityType<? extends LivingEntity>) entity.getType()).getAttributeBaseValue(attribute)) {
							entity.getAttribute(attribute).setBaseValue(GlobalEntityTypeAttributes.getAttributesForEntity((EntityType<? extends LivingEntity>) entity.getType()).getAttributeBaseValue(attribute));
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

		if (!entity.world.isRemote() && Rankup.statsManager.STATS.get(stat).getAttributeMap().containsKey(attribute) && attribute != null && GlobalEntityTypeAttributes.getAttributesForEntity((EntityType<? extends LivingEntity>) entity.getType()).hasAttribute(attribute)) {
			double baseValue = GlobalEntityTypeAttributes.getAttributesForEntity((EntityType<? extends LivingEntity>) entity.getType()).getAttributeBaseValue(attribute);
			return MathHelper.ceil(baseValue / Rankup.statsManager.STATS.get(stat).getAttributeMap().get(attribute));
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
