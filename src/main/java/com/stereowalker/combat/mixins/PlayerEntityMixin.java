package com.stereowalker.combat.mixins;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;
import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.inventory.ItemInventory;
import com.stereowalker.combat.item.InventoryItem;
import com.stereowalker.combat.item.QuiverItem;
import com.stereowalker.combat.mixinshooks.IBackItemHolder;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IBackItemHolder {
	@Shadow
	public PlayerInventory inventory;

	ItemInventory<?> itemInventory;
	@Shadow
	public final PlayerAbilities abilities = new PlayerAbilities();

	public PlayerEntityMixin(World p_i241920_1_, BlockPos p_i241920_2_, float p_i241920_3_, GameProfile p_i241920_4_) {
		super(EntityType.PLAYER, p_i241920_1_);
		this.setLocationAndAngles((double)p_i241920_2_.getX() + 0.5D, (double)(p_i241920_2_.getY() + 1), (double)p_i241920_2_.getZ() + 0.5D, p_i241920_3_, 0.0F);
		this.unused180 = 180.0F;
		this.setupInventory();
	}

	@Inject(method = "readAdditional", at = @At("TAIL"), cancellable = false)
	public void readInventoryFromStack(CompoundNBT compound, CallbackInfo info) {
		this.setupInventory();
	}

	@Inject(method = "tick", at = @At("TAIL"), cancellable = false)
	public void writeInventoryToStack(CallbackInfo info) {
		if (ModHelper.isCuriosLoaded() && itemInventory != null) {
			if (CuriosCompat.getSlotsForType(this, "back", 0).getItem() instanceof InventoryItem) {
				ItemStack quiver = CuriosCompat.getSlotsForType(this, "back", 0);
				((InventoryItem<?>)quiver.getItem()).saveInventory(quiver, itemInventory);
			}
		}
	}

	//	ItemStack arrow;
	@Overwrite
	public ItemStack findAmmo(ItemStack shootable) {
		if (!(shootable.getItem() instanceof ShootableItem)) {
			return ItemStack.EMPTY;
		} else {
			Predicate<ItemStack> predicate = ((ShootableItem)shootable.getItem()).getAmmoPredicate();
			ItemStack itemstack = ShootableItem.getHeldAmmo(this, predicate);
			if (!itemstack.isEmpty()) {
				return itemstack;
			} else {
				predicate = ((ShootableItem)shootable.getItem()).getInventoryAmmoPredicate();

				if (ModHelper.isCuriosLoaded() && itemInventory != null) {
					if (CuriosCompat.getSlotsForType(this, "back", 0).getItem() instanceof QuiverItem) {
						for(int i = 0; i < itemInventory.getSizeInventory(); ++i) {
							ItemStack itemstack1 = itemInventory.getStackInSlot(i);
							if (predicate.test(itemstack1)) {
								return itemstack1;
							}
						}
					}
				}

				for(int i = 0; i < this.inventory.getSizeInventory(); ++i) {
					ItemStack itemstack1 = this.inventory.getStackInSlot(i);
					if (predicate.test(itemstack1)) {
						return itemstack1;
					}
				}

				return this.abilities.isCreativeMode ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
			}
		}
	}

	@Override
	public ItemInventory<?> getItemInventory() {
		return itemInventory;
	}

	@Override
	public void setItemInventory(ItemInventory<?> input) {
		itemInventory = input;
	}
	
	public void setupInventory() {
		if (ModHelper.isCuriosLoaded() && CuriosCompat.getSlotsForType(this, "back", 0).getItem() instanceof InventoryItem) {
			ItemStack quiver = CuriosCompat.getSlotsForType(this, "back", 0);
			itemInventory = ((InventoryItem<?>)quiver.getItem()).loadInventory(quiver);
		} else {
			itemInventory = null;
		}
	}
}
