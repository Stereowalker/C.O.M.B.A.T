package com.stereowalker.rankup.network.server;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.server.SUnionPacket;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SPlayerDisplayStatPacket extends SUnionPacket {
	ITextComponent component;
	
	public SPlayerDisplayStatPacket(ITextComponent componentIn) {
		super(Combat.getInstance().channel);
		this.component = componentIn;
		
	}
	
	public SPlayerDisplayStatPacket(final PacketBuffer packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.component = packetBuffer.readTextComponent();
	}
	
	@Override
	public void encode(final PacketBuffer packetBuffer) {
		packetBuffer.writeTextComponent(component);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean handleOnClient(ClientPlayerEntity player) {
		player.sendMessage(component, Util.DUMMY_UUID);
		return false;
	}
}
