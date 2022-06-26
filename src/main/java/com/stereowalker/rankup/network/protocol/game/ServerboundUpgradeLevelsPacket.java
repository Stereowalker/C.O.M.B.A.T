package com.stereowalker.rankup.network.protocol.game;

import java.util.UUID;
import java.util.function.Supplier;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.world.stat.LevelType;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.unionlib.util.EntityHelper;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class ServerboundUpgradeLevelsPacket {
	private ResourceLocation stat;
	private UUID uuid;

	public ServerboundUpgradeLevelsPacket(final ResourceLocation level, final UUID uuid) {
		this.uuid = uuid;
		this.stat = level;
	}

	public static void encode(final ServerboundUpgradeLevelsPacket msg, final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeResourceLocation(msg.stat);
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}

	public static ServerboundUpgradeLevelsPacket decode(final FriendlyByteBuf packetBuffer) {
		return new ServerboundUpgradeLevelsPacket(packetBuffer.readResourceLocation(), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}

	public static void handle(final ServerboundUpgradeLevelsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			final ServerPlayer sender = context.getSender();
			if (sender == null) {
				return;
			}
			final Stat stat = sender.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY).get(msg.stat);
			final ResourceKey<Stat> statKey = ResourceKey.create(CombatRegistries.STATS_REGISTRY, msg.stat);
			final UUID uuid = msg.uuid;
			if (uuid.equals(Player.createPlayerUUID(sender.getGameProfile()))) {
				if (Combat.RPG_CONFIG.enableLevelingSystem && Rankup.statsManager.STATS.get(statKey).isEnabled()) {
					if ((""+Combat.RPG_CONFIG.useXPToUpgrade).equals("true")) {
						int cost = stat.getExperienceCost(sender);
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
			}
		});
		context.setPacketHandled(true);
	}
}
