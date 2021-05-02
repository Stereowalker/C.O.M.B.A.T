package com.stereowalker.combat.event;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.stereowalker.combat.entity.AbominationEnums.AbominationType;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.item.CItems;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;

public class AbominationEvents {
	static Random random = new Random();

	public static void updateAbomination(MonsterEntity monster) {
		CombatEntityStats.addStatsToMonstersOnSpawn(monster, random);
	}

	public static final List<ItemStack> getAbominationLoot() {
		return Lists.newArrayList(
				EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.BOOK), random.nextInt(100), true), 
				new ItemStack(CItems.XP_STORAGE_RING), 
				new ItemStack(CItems.POISON_CLEANSING_AMULET),
				new ItemStack(CItems.PYROMANCER_RING),
				new ItemStack(CItems.TERRAMANCER_RING));
	}

	public static void abominationDeath(DamageSource damageSource, MonsterEntity monster) {
		CompoundNBT compound = CombatEntityStats.getModNBT(monster);
		if (compound.contains(CombatEntityStats.abominationID)) {
			if (CombatEntityStats.getAbomination(monster) != AbominationType.NORMAL) {
				if (damageSource.getTrueSource() instanceof PlayerEntity) {
					int randomDrop = random.nextInt(getAbominationLoot().size());
					monster.entityDropItem(getAbominationLoot().get(randomDrop));
					CombatEntityStats.addMana((LivingEntity) damageSource.getTrueSource(), 20.0F);
				}
			}
		}
	}

	public static int abominationXP(MonsterEntity monster, int xp) {
		CompoundNBT compound = CombatEntityStats.getModNBT(monster);
		if (compound.contains(CombatEntityStats.abominationID)) {
			if (CombatEntityStats.getAbomination(monster) != AbominationType.NORMAL) {
				return xp * 100;
			}
		}
		return xp;
	}
}
