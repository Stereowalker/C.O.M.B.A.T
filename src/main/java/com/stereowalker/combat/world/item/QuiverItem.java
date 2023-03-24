package com.stereowalker.combat.world.item;

import java.util.List;

import com.stereowalker.combat.world.inventory.QuiverContainer;
import com.stereowalker.combat.world.inventory.QuiverMenu;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class QuiverItem extends InventoryItem<QuiverContainer> implements DyeableLeatherItem  {

	public QuiverItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack quiver = playerIn.getItemInHand(handIn);
		if (playerIn.isCrouching()) {
			playerIn.openMenu(new SimpleMenuProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
				return new QuiverMenu(p_220270_2_, quiver, p_220270_3_);
			}, quiver.getDisplayName()));
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, quiver);
		} else {
			return new InteractionResultHolder<>(InteractionResult.PASS, quiver);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		QuiverContainer inv = loadInventory(stack);
		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack arrow = inv.getItem(i);
			int count = 0;
			boolean flag = false;
			for (int j = i-1; j > -1; j--) {
				ItemStack arrow2 = inv.getItem(j);
				ItemStack compA = arrow.copy();
				compA.setCount(1);
				ItemStack compB = arrow2.copy();
				compB.setCount(1);
				if (ItemStack.matches(compA, compB)) {
					flag = true;
				}
			}
			if (!flag) {
				for (int j = i; j < inv.getContainerSize(); j++) {
					ItemStack arrow2 = inv.getItem(j);
					ItemStack compA = arrow.copy();
					compA.setCount(1);
					ItemStack compB = arrow2.copy();
					compB.setCount(1);
					if (ItemStack.matches(compA, compB)) {
						count+=arrow2.getCount();
					}
				}
				if (count != 0) tooltip.add(new TranslatableComponent(arrow.getDescriptionId()).append(" x"+count));
			}
		}
	}

	@Override
	public boolean isItemValidForPackage(ItemStack stack, int index) {
		if (index == 0) {
			return stack.getItem() instanceof BowItem; 
		} else {
			return ProjectileWeaponItem.ARROW_ONLY.test(stack);
		}
	}

	@Override
	public QuiverContainer getInventoryInstance() {
		return new QuiverContainer();
	}
	
	@Override
	public boolean canRetrieveItemsWithKeybind() {
		return true;
	}

}
