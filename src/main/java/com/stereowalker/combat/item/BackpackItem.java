package com.stereowalker.combat.item;

import java.util.List;

import com.stereowalker.combat.inventory.BackpackInventory;
import com.stereowalker.combat.inventory.container.BackpackContainer;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BackpackItem extends InventoryItem<BackpackInventory> implements IDyeableArmorItem {

	public BackpackItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack quiver = playerIn.getHeldItem(handIn);
		if (playerIn.isCrouching()) {
			playerIn.openContainer(new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
				return new BackpackContainer(p_220270_2_, quiver, p_220270_3_);
			}, quiver.getDisplayName()));
			return new ActionResult<>(ActionResultType.SUCCESS, quiver);
		} else {
			return new ActionResult<>(ActionResultType.PASS, quiver);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		BackpackInventory inv = loadInventory(stack);
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack item = inv.getStackInSlot(i);
			int count = 0;
			boolean flag = false;
			for (int j = i-1; j > -1; j--) {
				ItemStack arrow2 = inv.getStackInSlot(j);
				ItemStack compA = item.copy();
				compA.setCount(1);
				ItemStack compB = arrow2.copy();
				compB.setCount(1);
				if (ItemStack.areItemStacksEqual(compA, compB)) {
					flag = true;
				}
			}
			if (!flag) {
				for (int j = i; j < inv.getSizeInventory(); j++) {
					ItemStack arrow2 = inv.getStackInSlot(j);
					ItemStack compA = item.copy();
					compA.setCount(1);
					ItemStack compB = arrow2.copy();
					compB.setCount(1);
					if (ItemStack.areItemStacksEqual(compA, compB)) {
						count+=arrow2.getCount();
					}
				}
				if (count != 0) tooltip.add(new TranslationTextComponent(item.getTranslationKey()).appendString(" x"+count));
			}
		}
	}

	@Override
	public BackpackInventory getInventoryInstance() {
		return new BackpackInventory();
	}

}
