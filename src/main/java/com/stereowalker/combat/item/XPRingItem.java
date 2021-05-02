package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.CombatEntityStats;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class XPRingItem extends AbstractRingItem {

	public XPRingItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (playerIn.isCrouching()) {
			playerIn.giveExperiencePoints(CombatEntityStats.getStoredXP(playerIn));
			CombatEntityStats.setStoredXP(playerIn, 0);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void accessoryTick(World world, LivingEntity entity, ItemStack stack, int slot) {
		
	}

	@Override
	public ITextComponent accessoryInformation() {
		return new TranslationTextComponent("Stores your experience in an ender storage when equipped. Stored experience can be retrieved by crouching and right-clicking with the ring in your hand. Experience stored can be retrieved from any XP Ring");
	}

}
