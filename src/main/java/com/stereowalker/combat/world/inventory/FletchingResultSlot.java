package com.stereowalker.combat.world.inventory;

import com.stereowalker.combat.world.item.crafting.CRecipeType;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FletchingResultSlot extends Slot {
   private final CraftingContainer craftMatrix;
   private final Player player;
   private int amountCrafted;

   public FletchingResultSlot(Player player, CraftingContainer craftingInventory, Container inventoryIn, int slotIndex, int xPosition, int yPosition) {
      super(inventoryIn, slotIndex, xPosition, yPosition);
      this.player = player;
      this.craftMatrix = craftingInventory;
   }

   @Override
   public boolean mayPlace(ItemStack stack) {
      return false;
   }

   @Override
   public ItemStack remove(int amount) {
      if (this.hasItem()) {
         this.amountCrafted += Math.min(amount, this.getItem().getCount());
      }

      return super.remove(amount);
   }

   @Override
   protected void onQuickCraft(ItemStack stack, int amount) {
      this.amountCrafted += amount;
      this.checkTakeAchievements(stack);
   }

   @Override
   protected void onSwapCraft(int p_190900_1_) {
      this.amountCrafted += p_190900_1_;
   }

   @Override
   protected void checkTakeAchievements(ItemStack stack) {
      if (this.amountCrafted > 0) {
         stack.onCraftedBy(this.player.level, this.player, this.amountCrafted);
         net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(this.player, stack, this.craftMatrix);
      }

      if (this.container instanceof RecipeHolder) {
         ((RecipeHolder)this.container).awardUsedRecipes(this.player);
      }

      this.amountCrafted = 0;
   }

   @Override
   public void onTake(Player thePlayer, ItemStack stack) {
      this.checkTakeAchievements(stack);
      net.minecraftforge.common.ForgeHooks.setCraftingPlayer(thePlayer);
      NonNullList<ItemStack> nonnulllist = thePlayer.level.getRecipeManager().getRemainingItemsFor(CRecipeType.FLETCHING, this.craftMatrix, thePlayer.level);
      net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);
      for(int i = 0; i < nonnulllist.size(); ++i) {
         ItemStack itemstack = this.craftMatrix.getItem(i);
         ItemStack itemstack1 = nonnulllist.get(i);
         if (!itemstack.isEmpty()) {
            this.craftMatrix.removeItem(i, 1);
            itemstack = this.craftMatrix.getItem(i);
         }

         if (!itemstack1.isEmpty()) {
            if (itemstack.isEmpty()) {
               this.craftMatrix.setItem(i, itemstack1);
            } else if (ItemStack.isSame(itemstack, itemstack1) && ItemStack.tagMatches(itemstack, itemstack1)) {
               itemstack1.grow(itemstack.getCount());
               this.craftMatrix.setItem(i, itemstack1);
            } else if (!this.player.getInventory().add(itemstack1)) {
               this.player.drop(itemstack1, false);
            }
         }
      }
   }
}