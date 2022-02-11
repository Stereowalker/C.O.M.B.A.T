package com.stereowalker.combat.world.inventory;

import java.util.List;

import com.google.common.collect.Lists;
import com.stereowalker.combat.world.item.crafting.CRecipeType;
import com.stereowalker.combat.world.item.crafting.WoodcuttingRecipe;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WoodcutterMenu extends AbstractContainerMenu {
   private final ContainerLevelAccess worldPosCallable;
   /** The index of the selected recipe in the GUI. */
   private final DataSlot selectedRecipe = DataSlot.standalone();
   private final Level world;
   private List<WoodcuttingRecipe> recipes = Lists.newArrayList();
   /** The {@plainlink ItemStack} set in the input slot by the player. */
   private ItemStack itemStackInput = ItemStack.EMPTY;
   /**
    * Stores the game time of the last time the player took items from the the crafting result slot. This is used to
    * prevent the sound from being played multiple times on the same tick.
    */
   private long lastOnTake;
   final Slot inputInventorySlot;
   /** The inventory slot that stores the output of the crafting recipe. */
   final Slot outputInventorySlot;
   private Runnable inventoryUpdateListener = () -> {
   };
   public final Container inputInventory = new SimpleContainer(1) {
      @Override
      public void setChanged() {
         super.setChanged();
         WoodcutterMenu.this.slotsChanged(this);
         WoodcutterMenu.this.inventoryUpdateListener.run();
      }
   };
   /** The inventory that stores the output of the crafting recipe. */
   private final ResultContainer inventory = new ResultContainer();

   public WoodcutterMenu(int windowIdIn, Inventory playerInventoryIn) {
      this(windowIdIn, playerInventoryIn, ContainerLevelAccess.NULL);
   }

   public WoodcutterMenu(int windowIdIn, Inventory playerInventoryIn, final ContainerLevelAccess worldPosCallableIn) {
      super(CMenuType.WOODCUTTER, windowIdIn);
      this.worldPosCallable = worldPosCallableIn;
      this.world = playerInventoryIn.player.level;
      this.inputInventorySlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));
      this.outputInventorySlot = this.addSlot(new Slot(this.inventory, 1, 143, 33) {
         @Override
         public boolean mayPlace(ItemStack stack) {
            return false;
         }

         @Override
         public void onTake(Player thePlayer, ItemStack stack) {
            ItemStack itemstack = WoodcutterMenu.this.inputInventorySlot.remove(1);
            if (!itemstack.isEmpty()) {
               WoodcutterMenu.this.setupResultSlot();
            }

            stack.getItem().onCraftedBy(stack, thePlayer.level, thePlayer);
            worldPosCallableIn.execute((p_216954_1_, p_216954_2_) -> {
               long l = p_216954_1_.getGameTime();
               if (WoodcutterMenu.this.lastOnTake != l) {
                  p_216954_1_.playSound((Player)null, p_216954_2_, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                  WoodcutterMenu.this.lastOnTake = l;
               }

            });
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
      }

      this.addDataSlot(this.selectedRecipe);
   }

   @OnlyIn(Dist.CLIENT)
   public int getSelectedRecipeIndex() {
      return this.selectedRecipe.get();
   }

   @OnlyIn(Dist.CLIENT)
   public List<WoodcuttingRecipe> getRecipeCollection() {
      return this.recipes;
   }

   @OnlyIn(Dist.CLIENT)
   public int getNumRecipes() {
      return this.recipes.size();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasInputItem() {
      return this.inputInventorySlot.hasItem() && !this.recipes.isEmpty();
   }

   @Override
   public boolean stillValid(Player playerIn) {
      return stillValid(this.worldPosCallable, playerIn, CBlocks.WOODCUTTER);
   }

   @Override
   public boolean clickMenuButton(Player playerIn, int id) {
      if (id >= 0 && id < this.recipes.size()) {
         this.selectedRecipe.set(id);
         this.setupResultSlot();
      }

      return true;
   }

   @Override
   public void slotsChanged(Container inventoryIn) {
      ItemStack itemstack = this.inputInventorySlot.getItem();
      if (itemstack.getItem() != this.itemStackInput.getItem()) {
         this.itemStackInput = itemstack.copy();
         this.setupRecipeList(inventoryIn, itemstack);
      }

   }

   private void setupRecipeList(Container inventoryIn, ItemStack stack) {
      this.recipes.clear();
      this.selectedRecipe.set(-1);
      this.outputInventorySlot.set(ItemStack.EMPTY);
      if (!stack.isEmpty()) {
         this.recipes = this.world.getRecipeManager().getRecipesFor(CRecipeType.WOODCUTTING, inventoryIn, this.world);
      }

   }

   private void setupResultSlot() {
      if (!this.recipes.isEmpty()) {
    	  WoodcuttingRecipe stonecuttingrecipe = this.recipes.get(this.selectedRecipe.get());
         this.outputInventorySlot.set(stonecuttingrecipe.assemble(this.inputInventory));
      } else {
         this.outputInventorySlot.set(ItemStack.EMPTY);
      }

      this.broadcastChanges();
   }

   @Override
   public MenuType<?> getType() {
      return CMenuType.WOODCUTTER;
   }

   @OnlyIn(Dist.CLIENT)
   public void registerUpdateListener(Runnable listenerIn) {
      this.inventoryUpdateListener = listenerIn;
   }

   @Override
   public boolean canTakeItemForPickAll(ItemStack stack, Slot slotIn) {
      return slotIn.container != this.inventory && super.canTakeItemForPickAll(stack, slotIn);
   }

   @Override
   public ItemStack quickMoveStack(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(index);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         Item item = itemstack1.getItem();
         itemstack = itemstack1.copy();
         if (index == 1) {
            item.onCraftedBy(itemstack1, playerIn.level, playerIn);
            if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
               return ItemStack.EMPTY;
            }

            slot.onQuickCraft(itemstack1, itemstack);
         } else if (index == 0) {
            if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.world.getRecipeManager().getRecipeFor(CRecipeType.WOODCUTTING, new SimpleContainer(itemstack1), this.world).isPresent()) {
            if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 2 && index < 29) {
            if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         }

         slot.setChanged();
         if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(playerIn, itemstack1);
         this.broadcastChanges();
      }

      return itemstack;
   }

   @Override
   public void removed(Player playerIn) {
      super.removed(playerIn);
      this.inventory.removeItemNoUpdate(1);
      this.worldPosCallable.execute((p_217079_2_, p_217079_3_) -> {
         this.clearContainer(playerIn, this.inputInventory);
      });
   }
}