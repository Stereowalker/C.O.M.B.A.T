package com.stereowalker.combat.network.client.play;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.unionlib.network.client.CUnionPacket;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class CMageSetupPacket extends CUnionPacket {
	private int colorR;
	private int colorG;
	private int colorB;
	private SpellCategory elemental;
	private SpellCategory life;
	private SpellCategory special;

	public CMageSetupPacket(final int r, final int g, final int b, SpellCategory elemental, SpellCategory life, SpellCategory special) {
		super(Combat.getInstance().channel);
		this.colorR = r;
		this.colorG = g;
		this.colorB = b;
		this.elemental = elemental;
		this.life = life;
		this.special = special;
	}
	
	public CMageSetupPacket(PacketBuffer packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.colorR = packetBuffer.readInt();
		this.colorG = packetBuffer.readInt();
		this.colorB = packetBuffer.readInt();
		this.elemental = packetBuffer.readEnumValue(SpellCategory.class);
		this.life = packetBuffer.readEnumValue(SpellCategory.class);
		this.special = packetBuffer.readEnumValue(SpellCategory.class);
	}

	@Override
	public void encode(final PacketBuffer packetBuffer) {
		packetBuffer.writeInt(this.colorR);
		packetBuffer.writeInt(this.colorG);
		packetBuffer.writeInt(this.colorB);
		packetBuffer.writeEnumValue(this.elemental);
		packetBuffer.writeEnumValue(this.life);
		packetBuffer.writeEnumValue(this.special);
	}

	@Override
	public boolean handleOnServer(ServerPlayerEntity sender) {
		CombatEntityStats.setElementalAffinity(sender, elemental);
		CombatEntityStats.setLifeAffinity(sender, life);
		CombatEntityStats.setSpecialAffinity(sender, special);
		CombatEntityStats.setManaColor(sender, colorR << 16 | colorG << 8 | colorB);
		return true;
	}
}
