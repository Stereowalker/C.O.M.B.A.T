package com.stereowalker.combat.inventory;

import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.item.BackpackItem;

import net.minecraft.entity.player.PlayerEntity;

public class BackpackInventory extends ItemInventory<BackpackItem> {
	   public BackpackInventory() {
	      super(9);
	   }

	   /**
	    * Don't rename this method to canInteractWith due to conflicts with Container
	    */
	   public boolean isUsableByPlayer(PlayerEntity player) {
	      return player.getHeldItemMainhand().getItem() instanceof BackpackItem || player.getHeldItemOffhand().getItem() instanceof BackpackItem || CuriosCompat.getSlotsForType(player, "back", 0).getItem() instanceof BackpackItem;
	   }
}
