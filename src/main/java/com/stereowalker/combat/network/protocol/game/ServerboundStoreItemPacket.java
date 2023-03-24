package com.stereowalker.combat.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.compat.curios.CuriosStorageItem;
import com.stereowalker.unionlib.network.protocol.game.ServerboundUnionPacket;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

public class ServerboundStoreItemPacket extends ServerboundUnionPacket {

	public ServerboundStoreItemPacket() {
		super(Combat.getInstance().channel);
	}

	public ServerboundStoreItemPacket(final FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
	}
	
	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
	}


	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		if (ModHelper.isCuriosLoaded()) {
			InteractionHand hand;
			if (sender.getMainHandItem().isEmpty() && sender.getOffhandItem().isEmpty()) hand = InteractionHand.MAIN_HAND;
			else if (sender.getMainHandItem().isEmpty()) hand = InteractionHand.OFF_HAND;
			else hand = InteractionHand.MAIN_HAND;
			CuriosStorageItem.putOrRetrieveItemInStorage(sender, hand);
		}
		return true;
	}
}
