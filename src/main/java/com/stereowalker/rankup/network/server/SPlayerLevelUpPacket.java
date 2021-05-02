package com.stereowalker.rankup.network.server;

import com.stereowalker.combat.Combat;
import com.stereowalker.unionlib.network.server.SUnionPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class SPlayerLevelUpPacket extends SUnionPacket {
	int level;
	
	public SPlayerLevelUpPacket(int levelIn) {
		super(Combat.getInstance().channel);
		this.level = levelIn;
	}
	
	public SPlayerLevelUpPacket (final PacketBuffer packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.level = packetBuffer.readInt();
	}
	
	@Override
	public void encode(final PacketBuffer packetBuffer) {
		packetBuffer.writeInt(level);
	}

	@Override
	public boolean handleOnClient(ClientPlayerEntity player) {
		Minecraft.getInstance().ingameGUI.renderTitles(new TranslationTextComponent("Level Up").mergeStyle(TextFormatting.AQUA), null, 20, 60, 40);
		Minecraft.getInstance().ingameGUI.renderTitles(null, new StringTextComponent("You are now at level "+level).mergeStyle(TextFormatting.GRAY), 20, 60, 40);
		return false;
	}
}
