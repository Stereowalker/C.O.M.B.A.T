package com.stereowalker.rankup.network.server;

import java.util.function.Supplier;

import com.stereowalker.rankup.stat.PlayerAttributeLevels;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class SEntityStatsPacket {
	private CompoundNBT stats;
	private int id;
	
	public SEntityStatsPacket(final CompoundNBT statsIn, final int id) {
		this.stats = statsIn;
		this.id = id;
	}
	
	public SEntityStatsPacket(final LivingEntity entity){
		this(PlayerAttributeLevels.getRankNBT(entity), entity.getEntityId());
	}
	
	public static void encode(final SEntityStatsPacket msg, final PacketBuffer packetBuffer) {
		packetBuffer.writeCompoundTag(msg.stats);
		packetBuffer.writeInt(msg.id);
	}
	
	public static SEntityStatsPacket decode(final PacketBuffer packetBuffer) {
		return new SEntityStatsPacket(packetBuffer.readCompoundTag(), packetBuffer.readInt());
	}
	
	@SuppressWarnings("deprecation")
	public static void handle(final SEntityStatsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			final CompoundNBT stats = msg.stats;
			final int id = msg.id;
			update(stats, id);
		}));
		context.setPacketHandled(true);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void update(final CompoundNBT stats, final int id) {
		if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.world.getEntityByID(id) != null) {
			PlayerAttributeLevels.setRankNBT(stats, Minecraft.getInstance().player.world.getEntityByID(id));
		}
	}
}
