package com.stereowalker.combat.world.inventory;

import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.world.item.BackpackItem;

import net.minecraft.world.entity.player.Player;


public class BackpackContainer extends ItemContainer<BackpackItem> {
	   public BackpackContainer() {
	      super(9);
	   }

	   @Override
	   public boolean stillValid(Player player) {
	      return player.getMainHandItem().getItem() instanceof BackpackItem || player.getOffhandItem().getItem() instanceof BackpackItem || CuriosCompat.getSlotsForType(player, "back", 0).getItem() instanceof BackpackItem;
	   }
}
