package com.stereowalker.combat.network.client.play;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.item.GunItem;
import com.stereowalker.unionlib.network.client.CUnionPacket;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class CGunPacket extends CUnionPacket {
	private GunAction action;

	public CGunPacket(final GunAction action) {
		super(Combat.getInstance().channel);
		this.action = action;
	}
	
	public CGunPacket(PacketBuffer packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
		this.action = GunAction.byId(packetBuffer.readInt());
	}

	@Override
	public void encode(final PacketBuffer packetBuffer) {
		packetBuffer.writeInt(this.action.ordinal());
	}

	@Override
	public boolean handleOnServer(ServerPlayerEntity sender) {
		final GunAction action = this.action;
		ItemStack stackMain = sender.getHeldItem(Hand.MAIN_HAND);
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
