package com.stereowalker.rankup.network.client;

import java.util.UUID;
import java.util.function.Supplier;

import com.stereowalker.combat.config.Config;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.api.stat.StatUtil;
import com.stereowalker.rankup.stat.LevelType;
import com.stereowalker.rankup.stat.PlayerAttributeLevels;
import com.stereowalker.unionlib.util.EntityHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CUpgradeLevelsPacket {
	private Stat level;
	private UUID uuid;

	public CUpgradeLevelsPacket(final Stat level, final UUID uuid) {
		this.uuid = uuid;
		this.level = level;
	}

	public static void encode(final CUpgradeLevelsPacket msg, final PacketBuffer packetBuffer) {
		packetBuffer.writeString(msg.level.getKey());
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}

	public static CUpgradeLevelsPacket decode(final PacketBuffer packetBuffer) {
		return new CUpgradeLevelsPacket(StatUtil.getStatFromName(packetBuffer.readString(32767)), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}

	public static void handle(final CUpgradeLevelsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			final ServerPlayerEntity sender = context.getSender();
			if (sender == null) {
				return;
			}
			final Stat level = msg.level;
			final UUID uuid = msg.uuid;
			if (uuid.equals(PlayerEntity.getUUID(sender.getGameProfile()))) {
				if (Config.RPG_COMMON.enableLevelingSystem.get() && Rankup.statsManager.STATS.get(level).isEnabled()) {
					if ((""+Config.RPG_COMMON.useXPToUpgrade.get()).equals("true")) {
						int cost = level.getExperienceCost(sender);
						if (EntityHelper.getActualExperienceTotal(sender) >= cost) {
							sender.giveExperiencePoints(-cost);
							PlayerAttributeLevels.addStatPoints(sender, level, 1);
						}
					} else if (Config.RPG_COMMON.levelUpType.get() == LevelType.UPGRADE_POINTS) {
						if (PlayerAttributeLevels.getUpgradePoints(sender) > 0) {
							PlayerAttributeLevels.addStatPoints(sender, level, 1);
							PlayerAttributeLevels.addUpgradePoints(sender, -1);
						}
					}
				}
			}
		});
		context.setPacketHandled(true);
	}
}
