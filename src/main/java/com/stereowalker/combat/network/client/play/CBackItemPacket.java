package com.stereowalker.combat.network.client.play;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.inventory.container.BackpackContainer;
import com.stereowalker.combat.inventory.container.QuiverContainer;
import com.stereowalker.combat.inventory.container.SheathContainer;
import com.stereowalker.combat.item.BackpackItem;
import com.stereowalker.combat.item.QuiverItem;
import com.stereowalker.combat.item.SheathItem;
import com.stereowalker.unionlib.network.client.CUnionPacket;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class CBackItemPacket extends CUnionPacket {

	public CBackItemPacket() {
		super(Combat.getInstance().channel);
	}
	
	public CBackItemPacket(PacketBuffer packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
	}

	@Override
	public void encode(final PacketBuffer packetBuffer) {
	}
	
	@Override
	public boolean handleOnServer(ServerPlayerEntity sender) {
		ItemStack backStack = CuriosCompat.getSlotsForType(sender, "back", 0);
		if (backStack.getItem() instanceof QuiverItem) {
			sender.openContainer(new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
				return new QuiverContainer(p_220270_2_, backStack, p_220270_3_);
			}, backStack.getDisplayName()));
		}
		if (backStack.getItem() instanceof BackpackItem) {
			sender.openContainer(new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
				return new BackpackContainer(p_220270_2_, backStack, p_220270_3_);
			}, backStack.getDisplayName()));
		}
		if (backStack.getItem() instanceof SheathItem) {
			sender.openContainer(new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
				return new SheathContainer(p_220270_2_, backStack, p_220270_3_);
			}, backStack.getDisplayName()));
		}
		return true;
	}
}
