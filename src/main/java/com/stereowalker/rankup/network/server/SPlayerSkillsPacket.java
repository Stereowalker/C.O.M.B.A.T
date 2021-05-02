package com.stereowalker.rankup.network.server;

import java.util.UUID;
import java.util.function.Supplier;

import com.stereowalker.rankup.skill.api.PlayerSkills;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class SPlayerSkillsPacket {
	private CompoundNBT stats;
	private UUID uuid;
	
	public SPlayerSkillsPacket(final CompoundNBT statsIn, final UUID uuid) {
		this.stats = statsIn;
		this.uuid = uuid;
	}
	
	public SPlayerSkillsPacket(final ServerPlayerEntity player){
		this(PlayerSkills.getRankSkillNBT(player), player.getUniqueID());
	}
	
	public static void encode(final SPlayerSkillsPacket msg, final PacketBuffer packetBuffer) {
		packetBuffer.writeCompoundTag(msg.stats);
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
        packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}
	
	public static SPlayerSkillsPacket decode(final PacketBuffer packetBuffer) {
		return new SPlayerSkillsPacket(packetBuffer.readCompoundTag(), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}
	
	@SuppressWarnings("deprecation")
	public static void handle(final SPlayerSkillsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			final CompoundNBT stats = msg.stats;
			final UUID uuid = msg.uuid;
			update(stats, uuid);
		}));
		context.setPacketHandled(true);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void update(final CompoundNBT stats, final UUID uuid) {
		if (uuid.equals(PlayerEntity.getUUID(Minecraft.getInstance().player.getGameProfile()))) {
			PlayerSkills.setRankSkillNBT(stats, Minecraft.getInstance().player);
		}
	}
}
