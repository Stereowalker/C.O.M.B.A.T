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
import com.stereowalker.combat.mixinshooks.IBackItemHolder;
import com.stereowalker.combat.world.inventory.ItemContainer;
import com.stereowalker.combat.world.item.InventoryItem;
import com.stereowalker.combat.world.item.QuiverItem;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IBackItemHolder {
	@Shadow
	public Inventory inventory;

	ItemContainer<?> itemInventory;
	@Shadow
	public final Abilities abilities = new Abilities();

	public PlayerMixin(Level p_i241920_1_, BlockPos p_i241920_2_, float p_i241920_3_, GameProfile p_i241920_4_) {
		super(EntityType.PLAYER, p_i241920_1_);
		this.moveTo((double)p_i241920_2_.getX() + 0.5D, (double)(p_i241920_2_.getY() + 1), (double)p_i241920_2_.getZ() + 0.5D, p_i241920_3_, 0.0F);
		this.rotOffs = 180.0F;
		this.setupInventory();
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"), cancellable = false)
	public void readInventoryFromStack(CompoundTag compound, CallbackInfo info) {
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
	public ItemStack getProjectile(ItemStack shootable) {
		if (!(shootable.getItem() instanceof ProjectileWeaponItem)) {
			return ItemStack.EMPTY;
		} else {
			Predicate<ItemStack> predicate = ((ProjectileWeaponItem)shootable.getItem()).getSupportedHeldProjectiles();
			ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(this, predicate);
			if (!itemstack.isEmpty()) {
				return itemstack;
			} else {
				predicate = ((ProjectileWeaponItem)shootable.getItem()).getAllSupportedProjectiles();

				if (ModHelper.isCuriosLoaded() && itemInventory != null) {
					if (CuriosCompat.getSlotsForType(this, "back", 0).getItem() instanceof QuiverItem) {
						for(int i = 0; i < itemInventory.getContainerSize(); ++i) {
							ItemStack itemstack1 = itemInventory.getItem(i);
							if (predicate.test(itemstack1)) {
								return itemstack1;
							}
						}
					}
				}

				for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
					ItemStack itemstack1 = this.inventory.getItem(i);
					if (predicate.test(itemstack1)) {
						return itemstack1;
					}
				}

				return this.abilities.instabuild ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
			}
		}
	}

	@Override
	public ItemContainer<?> getItemInventory() {
		return itemInventory;
	}

	@Override
	public void setItemInventory(ItemContainer<?> input) {
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
