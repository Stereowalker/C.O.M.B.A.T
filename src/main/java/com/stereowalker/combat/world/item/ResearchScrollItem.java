package com.stereowalker.combat.world.item;

import java.util.Random;

import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellInstance;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.old.combat.config.Config;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ResearchScrollItem extends Item {

	public ResearchScrollItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (!worldIn.isClientSide) {
			ItemStack itemIn = new ItemStack(CItems.SCROLL);
			ItemStack itemStack = playerIn.getItemInHand(handIn);
			Random rand = new Random();
			int chance = rand.nextInt(3);
			if (CombatEntityStats.addMana(playerIn, -2.0F)) {
				Spell spell;
				itemStack.shrink(1);
				if (Config.MAGIC_COMMON.toggle_affinities.get()) {
					spell = SpellUtil.getWeightedRandomSpell(rand, CombatEntityStats.getElementalAffinity(playerIn), CombatEntityStats.getSubElementalAffinity1(playerIn), CombatEntityStats.getSubElementalAffinity2(playerIn), CombatEntityStats.getLifeAffinity(playerIn), CombatEntityStats.getSpecialAffinity(playerIn));
				} else {
					spell = SpellUtil.getWeightedRandomSpell(rand);
				}
				if (chance == 0) {
					if(!playerIn.getInventory().add(SpellUtil.addSpellToStack(itemIn, spell))) {
						worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SpellUtil.addSpellToStack(itemIn, spell)));
					}
				} else if (chance == 1){
					worldIn.explode(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), 1, Explosion.BlockInteraction.NONE);
				} else if (chance == 2){
					HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.ANY);
//					spell.setLocation(raytraceresult.getHitVec());
					SpellInstance spellInstance = new SpellInstance(spell, 1.0D, raytraceresult.getLocation(), handIn, playerIn.getUUID());
					spellInstance.executeSpell(playerIn);
				}
			}
		}
		return super.use(worldIn, playerIn, handIn);
	}

}
