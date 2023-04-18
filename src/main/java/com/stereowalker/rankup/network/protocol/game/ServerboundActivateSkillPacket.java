package com.stereowalker.rankup.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.unionlib.network.protocol.game.ServerboundUnionPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundActivateSkillPacket extends ServerboundUnionPacket {
	private Skill skill;
	
	public ServerboundActivateSkillPacket(final Skill skill) {
		super(Combat.getInstance().channel);
		this.skill = skill;
	}
	
	public ServerboundActivateSkillPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.skill = CombatRegistries.SKILLS.get().getValue(packetBuffer.readResourceLocation());
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeResourceLocation(CombatRegistries.SKILLS.get().getKey(this.skill));
	}
	
	@Override
	public boolean handleOnServer(ServerPlayer arg0) {
		PlayerSkills.setSkillActive(arg0, skill, !PlayerSkills.isSkillActive(arg0, skill));
		return true;
	}
}
