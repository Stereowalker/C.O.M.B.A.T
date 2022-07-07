package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.world.stat.LevelType;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.unionlib.network.protocol.game.ServerboundUnionPacket;
import com.stereowalker.unionlib.util.EntityHelper;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundUpgradeLevelsPacket extends ServerboundUnionPacket {
	private ResourceLocation stat;
	
	public ServerboundUpgradeLevelsPacket(final ResourceLocation stat) {
		super(Combat.getInstance().channel);
		this.stat = stat;
	}
	
	public ServerboundUpgradeLevelsPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.stat = packetBuffer.readResourceLocation();
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeResourceLocation(this.stat);
	}
	
	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		final ResourceKey<Stat> statKey = ResourceKey.create(CombatRegistries.STATS_REGISTRY, this.stat);
		if (Combat.RPG_CONFIG.enableLevelingSystem && Rankup.statsManager.STATS.get(statKey).isEnabled()) {
			if ((""+Combat.RPG_CONFIG.useXPToUpgrade).equals("true")) {
				int cost = Stat.getExperienceCost(sender, statKey);
				if (EntityHelper.getActualExperienceTotal(sender) >= cost) {
					sender.giveExperiencePoints(-cost);
					PlayerAttributeLevels.addStatPoints(sender, statKey, 1);
				}
			} else if (Combat.RPG_CONFIG.levelUpType == LevelType.UPGRADE_POINTS) {
				if (PlayerAttributeLevels.getUpgradePoints(sender) > 0) {
					PlayerAttributeLevels.addStatPoints(sender, statKey, 1);
					PlayerAttributeLevels.addUpgradePoints(sender, -1);
				}
			}
		}
		return true;
	}
}
