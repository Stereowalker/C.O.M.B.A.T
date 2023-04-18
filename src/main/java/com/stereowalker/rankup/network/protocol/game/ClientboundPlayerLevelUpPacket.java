package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.protocol.game.ClientboundUnionPacket;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

public class ClientboundPlayerLevelUpPacket extends ClientboundUnionPacket {
	int level;
	Component component;
	
	public ClientboundPlayerLevelUpPacket(int levelIn, Component componentIn) {
		super(Combat.getInstance().channel);
		this.level = levelIn;
		this.component = componentIn;
	}
	
	public ClientboundPlayerLevelUpPacket (final FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.level = packetBuffer.readInt();
		this.component = packetBuffer.readComponent();
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeInt(level);
		packetBuffer.writeComponent(component);
	}

	@SuppressWarnings("resource")
	@Override
	public boolean handleOnClient(LocalPlayer player) {
		Minecraft.getInstance().gui.setTitle(Component.translatable("Level Up").withStyle(ChatFormatting.AQUA));
		Minecraft.getInstance().gui.setSubtitle(Component.literal("You are now at level "+level).withStyle(ChatFormatting.GRAY));
		Minecraft.getInstance().gui.setTimes(20, 60, 40);
		player.sendSystemMessage(component);
		return true;
	}
	
	@Override
	public boolean verifyIfHandled() {
		return true;
	}
}
