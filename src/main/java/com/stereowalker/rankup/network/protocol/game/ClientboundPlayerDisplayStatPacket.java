package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.protocol.game.ClientboundUnionPacket;

import net.minecraft.Util;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClientboundPlayerDisplayStatPacket extends ClientboundUnionPacket {
	Component component;
	
	public ClientboundPlayerDisplayStatPacket(Component componentIn) {
		super(Combat.getInstance().channel);
		this.component = componentIn;
		
	}
	
	public ClientboundPlayerDisplayStatPacket(final FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.component = packetBuffer.readComponent();
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeComponent(component);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean handleOnClient(LocalPlayer player) {
		player.sendMessage(component, Util.NIL_UUID);
		return false;
	}
}
