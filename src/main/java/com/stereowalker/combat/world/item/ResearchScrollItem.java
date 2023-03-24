package com.stereowalker.combat.world.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory.ClassType;
import com.stereowalker.combat.api.world.spellcraft.SpellInstance;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.world.entity.CombatEntityStats;

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
			ItemStack itemStack = playerIn.getItemInHand(handIn);
			Random rand = new Random();
			int chance = rand.nextInt(3);
			if (CombatEntityStats.addMana(playerIn, -2.0F)) {
				Spell spell = SpellUtil.getWeightedRandomSpell(rand, SpellCategory.values(ClassType.ELEMENTAL));
				itemStack.shrink(1);
				if (Combat.MAGIC_CONFIG.toggle_affinities) {
					
					List<SpellCategory> cats = new ArrayList<SpellCategory>();
					cats.add(CombatEntityStats.getPrimevalAffinity(playerIn));
					cats.add(SpellCategory.getStrongestElementalAffinity(playerIn));
					cats.addAll(Lists.newArrayList(SpellCategory.getNextStrongestElementalAffinities(playerIn)));
					
					spell = SpellUtil.getWeightedRandomSpell(rand, cats.toArray(new SpellCategory[0]));
				} else {
					spell = SpellUtil.getWeightedRandomSpell(rand);
				}
				ItemStack itemIn = SpellUtil.addSpellToStack(new ItemStack(CItems.SCROLL), spell);
				if (chance == 0) {
					if(!playerIn.getInventory().add(itemIn)) {
						worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), itemIn));
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
