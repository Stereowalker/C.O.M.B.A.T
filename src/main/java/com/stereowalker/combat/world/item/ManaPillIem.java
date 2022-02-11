package com.stereowalker.combat.world.item;

import java.util.Random;

import com.stereowalker.combat.compat.origins.OriginsCompat;
import com.stereowalker.combat.world.entity.CombatEntityStats;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

public class ManaPillIem extends Item {

	public ManaPillIem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemStack = playerIn.getItemInHand(handIn);
		if(!worldIn.isClientSide) {
			if (ModList.get().isLoaded("origins")) {
				if (OriginsCompat.hasMagicStomach(playerIn)) {
					if(CombatEntityStats.addMana(playerIn, 4.0F) && !playerIn.getAbilities().instabuild) {
						playerIn.getFoodData().eat(4, 2.0F);
						itemStack.shrink(1);
						Random rand = new Random();
						int i = rand.nextInt(40);
						if (i == 0) playerIn.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1200));
						if (i == 1) playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200));
						if (i == 2) playerIn.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200));
						if (i == 3) playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200));
						return InteractionResultHolder.success(itemStack); 
					}
				}
			}
			if(CombatEntityStats.addMana(playerIn, 2.0F) && !playerIn.getAbilities().instabuild) {
				itemStack.shrink(1);
				Random rand = new Random();
				int i = rand.nextInt(60);
				if (i == 0) playerIn.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1200));
				if (i == 1) playerIn.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1200));
				if (i == 2) playerIn.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1200));
				if (i == 3) playerIn.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1200));
				if (i == 4) playerIn.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1200));
				if (i == 5) playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1200));
				return InteractionResultHolder.success(itemStack); 
			}
		}
		return super.use(worldIn, playerIn, handIn);
	}

}
