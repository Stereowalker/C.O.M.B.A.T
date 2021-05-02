package com.stereowalker.combat.item;

import java.util.Random;

import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.api.spell.SpellInstance;
import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.entity.CombatEntityStats;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public class ResearchScrollItem extends Item{

	public ResearchScrollItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isRemote) {
			ItemStack itemIn = new ItemStack(CItems.SCROLL);
			ItemStack itemStack = playerIn.getHeldItem(handIn);
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
					if(!playerIn.inventory.addItemStackToInventory(SpellUtil.addSpellToStack(itemIn, spell))) {
						worldIn.addEntity(new ItemEntity(worldIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SpellUtil.addSpellToStack(itemIn, spell)));
					}
				} else if (chance == 1){
					worldIn.createExplosion(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), 1, Mode.NONE);
				} else if (chance == 2){
					RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
//					spell.setLocation(raytraceresult.getHitVec());
					SpellInstance spellInstance = new SpellInstance(spell, 1.0D, raytraceresult.getHitVec(), handIn, playerIn.getUniqueID());
					spellInstance.executeSpell(playerIn);
				}
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
