package com.stereowalker.combat.item;

import java.util.Random;

import com.stereowalker.combat.compat.origins.OriginsCompat;
import com.stereowalker.combat.entity.CombatEntityStats;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

public class ManaPillIem extends Item {

	public ManaPillIem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		if(!worldIn.isRemote) {
			if (ModList.get().isLoaded("origins")) {
				if (OriginsCompat.hasMagicStomach(playerIn)) {
					if(CombatEntityStats.addMana(playerIn, 4.0F) && !playerIn.abilities.isCreativeMode) {
						playerIn.getFoodStats().addStats(4, 2.0F);
						itemStack.shrink(1);
						Random rand = new Random();
						int i = rand.nextInt(60);
						if (i == 0) playerIn.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 1200));
						if (i == 2) playerIn.addPotionEffect(new EffectInstance(Effects.STRENGTH, 1200));
						if (i == 4) playerIn.addPotionEffect(new EffectInstance(Effects.HASTE, 1200));
						if (i == 5) playerIn.addPotionEffect(new EffectInstance(Effects.SPEED, 1200));
						return ActionResult.resultSuccess(itemStack); 
					}
				}
			}
			if(CombatEntityStats.addMana(playerIn, 2.0F) && !playerIn.abilities.isCreativeMode) {
				itemStack.shrink(1);
				Random rand = new Random();
				int i = rand.nextInt(60);
				if (i == 0) playerIn.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 1200));
				if (i == 1) playerIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, 1200));
				if (i == 2) playerIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1200));
				if (i == 3) playerIn.addPotionEffect(new EffectInstance(Effects.GLOWING, 1200));
				if (i == 4) playerIn.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 1200));
				if (i == 5) playerIn.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1200));
				return ActionResult.resultSuccess(itemStack); 
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
