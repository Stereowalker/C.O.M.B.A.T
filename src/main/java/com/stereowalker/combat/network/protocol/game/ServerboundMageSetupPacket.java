package com.stereowalker.combat.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.unionlib.network.client.CUnionPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundMageSetupPacket extends CUnionPacket {
	private int colorR;
	private int colorG;
	private int colorB;
	private SpellCategory elemental;
	private SpellCategory life;
	private SpellCategory special;

	public ServerboundMageSetupPacket(final int r, final int g, final int b, SpellCategory elemental, SpellCategory life, SpellCategory special) {
		super(Combat.getInstance().channel);
		this.colorR = r;
		this.colorG = g;
		this.colorB = b;
		this.elemental = elemental;
		this.life = life;
		this.special = special;
	}
	
	public ServerboundMageSetupPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.colorR = packetBuffer.readInt();
		this.colorG = packetBuffer.readInt();
		this.colorB = packetBuffer.readInt();
		this.elemental = packetBuffer.readEnum(SpellCategory.class);
		this.life = packetBuffer.readEnum(SpellCategory.class);
		this.special = packetBuffer.readEnum(SpellCategory.class);
	}

	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeInt(this.colorR);
		packetBuffer.writeInt(this.colorG);
		packetBuffer.writeInt(this.colorB);
		packetBuffer.writeEnum(this.elemental);
		packetBuffer.writeEnum(this.life);
		packetBuffer.writeEnum(this.special);
	}

	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		CombatEntityStats.setElementalAffinity(sender, elemental);
		CombatEntityStats.setLifeAffinity(sender, life);
		CombatEntityStats.setSpecialAffinity(sender, special);
		CombatEntityStats.setManaColor(sender, colorR << 16 | colorG << 8 | colorB);
		return true;
	}
}