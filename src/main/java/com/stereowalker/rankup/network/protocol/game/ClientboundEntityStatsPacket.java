package com.stereowalker.rankup.network.protocol.game;

import java.util.function.Supplier;

import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundEntityStatsPacket {
	private CompoundTag stats;
	private int id;
	
	public ClientboundEntityStatsPacket(final CompoundTag statsIn, final int id) {
		this.stats = statsIn;
		this.id = id;
	}
	
	public ClientboundEntityStatsPacket(final LivingEntity entity){
		this(PlayerAttributeLevels.getRankNBT(entity), entity.getId());
	}
	
	public static void encode(final ClientboundEntityStatsPacket msg, final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(msg.stats);
		packetBuffer.writeInt(msg.id);
	}
	
	public static ClientboundEntityStatsPacket decode(final FriendlyByteBuf packetBuffer) {
		return new ClientboundEntityStatsPacket(packetBuffer.readNbt(), packetBuffer.readInt());
	}
	
	@SuppressWarnings("deprecation")
	public static void handle(final ClientboundEntityStatsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			final CompoundTag stats = msg.stats;
			final int id = msg.id;
			update(stats, id);
		}));
		context.setPacketHandled(true);
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void update(final CompoundTag stats, final int id) {
		if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level.getEntity(id) != null) {
			PlayerAttributeLevels.setRankNBT(stats, Minecraft.getInstance().player.level.getEntity(id));
		}
	}
}
