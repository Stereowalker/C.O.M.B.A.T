package com.stereowalker.combat.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.stereowalker.combat.mixinhooks.CombatHooks;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(Inventory.class)
public class InventoryMixin {
	@Shadow @Final private List<NonNullList<ItemStack>> compartments;
	@Shadow @Final public Player player;
	/**
	 * Drop all armor and main inventory items.
	 */
	@Overwrite
	public void dropAll() {
		for(List<ItemStack> list : this.compartments) {
			for(int i = 0; i < list.size(); ++i) {
				ItemStack itemstack = list.get(i);
				if (!CombatHooks.isEmptyOrRetaining(itemstack.isEmpty(), itemstack)) {
					this.player.drop(itemstack, true, false);
					list.set(i, ItemStack.EMPTY);
				}
			}
		}

	}
}
