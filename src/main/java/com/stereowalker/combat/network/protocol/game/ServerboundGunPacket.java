package com.stereowalker.combat.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.item.GunItem;
import com.stereowalker.unionlib.network.protocol.game.ServerboundUnionPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class ServerboundGunPacket extends ServerboundUnionPacket {
	private GunAction action;

	public ServerboundGunPacket(final GunAction action) {
		super(Combat.getInstance().channel);
		this.action = action;
	}
	
	public ServerboundGunPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.action = GunAction.byId(packetBuffer.readInt());
	}

	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeInt(this.action.ordinal());
	}

	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		final GunAction action = this.action;
		ItemStack stackMain = sender.getItemInHand(InteractionHand.MAIN_HAND);
		if(stackMain.getItem() instanceof GunItem) {
			GunItem gun = (GunItem)stackMain.getItem();
			if (action == GunAction.FIRE) {
				gun.shootGun(sender);
			}
			if (action == GunAction.RELOAD) {
				gun.reloadGun(sender);
			}
		}
		return true;
	}

	public enum GunAction {
		RELOAD,
		FIRE;

		public static GunAction byId(int id) {
			GunAction[] action = values();
			if (id < 0 || id >= action.length) {
				id = 0;
			}
			return action[id];
		}
	}
}
