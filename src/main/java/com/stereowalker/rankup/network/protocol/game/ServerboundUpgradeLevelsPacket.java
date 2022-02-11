package com.stereowalker.rankup.network.protocol.game;

import java.util.UUID;
import java.util.function.Supplier;

import com.stereowalker.old.combat.config.Config;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.api.stat.StatUtil;
import com.stereowalker.rankup.world.stat.LevelType;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.unionlib.util.EntityHelper;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class ServerboundUpgradeLevelsPacket {
	private Stat level;
	private UUID uuid;

	public ServerboundUpgradeLevelsPacket(final Stat level, final UUID uuid) {
		this.uuid = uuid;
		this.level = level;
	}

	public static void encode(final ServerboundUpgradeLevelsPacket msg, final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeUtf(msg.level.getKey());
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}

	public static ServerboundUpgradeLevelsPacket decode(final FriendlyByteBuf packetBuffer) {
		return new ServerboundUpgradeLevelsPacket(StatUtil.getStatFromName(packetBuffer.readUtf(32767)), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}

	public static void handle(final ServerboundUpgradeLevelsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			final ServerPlayer sender = context.getSender();
			if (sender == null) {
				return;
			}
			final Stat level = msg.level;
			final UUID uuid = msg.uuid;
			if (uuid.equals(Player.createPlayerUUID(sender.getGameProfile()))) {
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
