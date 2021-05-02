package com.stereowalker.combat.network.client.play;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.compat.curios.CuriosStorageItem;
import com.stereowalker.unionlib.network.client.CUnionPacket;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class CStoreItemPacket extends CUnionPacket {

	public CStoreItemPacket() {
		super(Combat.getInstance().channel);
	}

	public CStoreItemPacket(final PacketBuffer packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
	}
	
	@Override
	public void encode(final PacketBuffer packetBuffer) {
	}


	@Override
	public boolean handleOnServer(ServerPlayerEntity sender) {
		if (ModHelper.isCuriosLoaded()) {
			Hand hand;
			if (sender.getHeldItemMainhand().isEmpty() && sender.getHeldItemOffhand().isEmpty()) hand = Hand.MAIN_HAND;
			else if (sender.getHeldItemMainhand().isEmpty()) hand = Hand.OFF_HAND;
			else hand = Hand.MAIN_HAND;
			CuriosStorageItem.putOrRetrieveItemInStorage(sender, hand);
		}
		return true;
	}
}
