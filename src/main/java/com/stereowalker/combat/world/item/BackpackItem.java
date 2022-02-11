package com.stereowalker.combat.world.item;

import java.util.List;

import com.stereowalker.combat.world.inventory.BackpackContainer;
import com.stereowalker.combat.world.inventory.BackpackMenu;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BackpackItem extends InventoryItem<BackpackContainer> implements DyeableLeatherItem {

	public BackpackItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack quiver = playerIn.getItemInHand(handIn);
		if (playerIn.isCrouching()) {
			playerIn.openMenu(new SimpleMenuProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
				return new BackpackMenu(p_220270_2_, quiver, p_220270_3_);
			}, quiver.getDisplayName()));
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, quiver);
		} else {
			return new InteractionResultHolder<>(InteractionResult.PASS, quiver);
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		BackpackContainer inv = loadInventory(stack);
		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack item = inv.getItem(i);
			int count = 0;
			boolean flag = false;
			for (int j = i-1; j > -1; j--) {
				ItemStack arrow2 = inv.getItem(j);
				ItemStack compA = item.copy();
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
					ItemStack compA = item.copy();
					compA.setCount(1);
					ItemStack compB = arrow2.copy();
					compB.setCount(1);
					if (ItemStack.matches(compA, compB)) {
						count+=arrow2.getCount();
					}
				}
				if (count != 0) tooltip.add(new TranslatableComponent(item.getDescriptionId()).append(" x"+count));
			}
		}
	}

	@Override
	public BackpackContainer getInventoryInstance() {
		return new BackpackContainer();
	}

}
