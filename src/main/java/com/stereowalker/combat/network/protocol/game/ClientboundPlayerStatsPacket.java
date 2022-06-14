package com.stereowalker.combat.network.protocol.game;

import java.util.UUID;
import java.util.function.Supplier;

import com.stereowalker.combat.world.entity.CombatEntityStats;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundPlayerStatsPacket {
	private CompoundTag stats;
	private UUID uuid;
	
	public ClientboundPlayerStatsPacket(final CompoundTag statsIn, final UUID uuid) {
		this.stats = statsIn;
		this.uuid = uuid;
	}
	
	public ClientboundPlayerStatsPacket(final ServerPlayer player){
		this(CombatEntityStats.getModNBT(player), player.getUUID());
	}
	
	public static void encode(final ClientboundPlayerStatsPacket msg, final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(msg.stats);
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
        packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}
	
	public static ClientboundPlayerStatsPacket decode(final FriendlyByteBuf packetBuffer) {
		return new ClientboundPlayerStatsPacket(packetBuffer.readNbt(), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}
	
	@SuppressWarnings("deprecation")
	public static void handle(final ClientboundPlayerStatsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			final CompoundTag stats = msg.stats;
			final UUID uuid = msg.uuid;
			update(stats, uuid);
		}));
		context.setPacketHandled(true);
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void update(final CompoundTag stats, final UUID uuid) {
		if (uuid.equals(Player.createPlayerUUID(Minecraft.getInstance().player.getGameProfile()))) {
			CombatEntityStats.setModNBT(stats, Minecraft.getInstance().player);
		}
	}
}
