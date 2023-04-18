package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.unionlib.world.entity.AccessorySlot.Group;
import com.stereowalker.unionlib.world.item.AccessoryItem;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class XPRingItem extends AccessoryItem {

	public XPRingItem(Properties properties) {
		super(properties, Group.FINGER);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (playerIn.isCrouching()) {
			playerIn.giveExperiencePoints(CombatEntityStats.getStoredXP(playerIn));
			CombatEntityStats.setStoredXP(playerIn, 0);
		}
		return super.use(worldIn, playerIn, handIn);
	}

	@Override
	public void accessoryTick(Level world, LivingEntity entity, ItemStack stack, int slot) {
		
	}

	@Override
	public Component accessoryInformation() {
		return Component.translatable("Stores your experience in an ender storage when equipped. Stored experience can be retrieved by crouching and right-clicking with the ring in your hand. Experience stored can be retrieved from any XP Ring");
	}

}
