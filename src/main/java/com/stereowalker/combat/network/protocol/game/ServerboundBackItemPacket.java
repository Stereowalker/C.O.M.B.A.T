package com.stereowalker.combat.network.protocol.game;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.world.inventory.BackpackMenu;
import com.stereowalker.combat.world.inventory.QuiverMenu;
import com.stereowalker.combat.world.inventory.SheathMenu;
import com.stereowalker.combat.world.item.BackpackItem;
import com.stereowalker.combat.world.item.QuiverItem;
import com.stereowalker.combat.world.item.SheathItem;
import com.stereowalker.unionlib.network.client.CUnionPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;

public class ServerboundBackItemPacket extends CUnionPacket {

	public ServerboundBackItemPacket() {
		super(Combat.getInstance().channel);
	}
	
	public ServerboundBackItemPacket(FriendlyByteBuf packetBuffer) {
		super(packetBuffer, Combat.getInstance().channel);
	}

	@Override
	public void encode(final FriendlyByteBuf packetBuffer) {
	}
	
	@Override
	public boolean handleOnServer(ServerPlayer sender) {
		ItemStack backStack = CuriosCompat.getSlotsForType(sender, "back", 0);
		if (backStack.getItem() instanceof QuiverItem) {
			sender.openMenu(new SimpleMenuProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
				return new QuiverMenu(p_220270_2_, backStack, p_220270_3_);
			}, backStack.getDisplayName()));
		}
		if (backStack.getItem() instanceof BackpackItem) {
			sender.openMenu(new SimpleMenuProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
				return new BackpackMenu(p_220270_2_, backStack, p_220270_3_);
			}, backStack.getDisplayName()));
		}
		if (backStack.getItem() instanceof SheathItem) {
			sender.openMenu(new SimpleMenuProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
				return new SheathMenu(p_220270_2_, backStack, p_220270_3_);
			}, backStack.getDisplayName()));
		}
		return true;
	}
}
