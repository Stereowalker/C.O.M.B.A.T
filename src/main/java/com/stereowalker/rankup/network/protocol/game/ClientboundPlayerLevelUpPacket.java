package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.server.SUnionPacket;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ClientboundPlayerLevelUpPacket extends SUnionPacket {
	int level;
	
	public ClientboundPlayerLevelUpPacket(int levelIn) {
		super(Combat.getInstance().channel);
		this.level = levelIn;
	}
	
	public ClientboundPlayerLevelUpPacket (final FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.level = packetBuffer.readInt();
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeInt(level);
	}

	@SuppressWarnings("resource")
	@Override
	public boolean handleOnClient(LocalPlayer player) {
		Minecraft.getInstance().gui.setTitle(new TranslatableComponent("Level Up").withStyle(ChatFormatting.AQUA));
		Minecraft.getInstance().gui.setSubtitle(new TextComponent("You are now at level "+level).withStyle(ChatFormatting.GRAY));
		Minecraft.getInstance().gui.setTimes(20, 60, 40);
		return false;
	}
}
