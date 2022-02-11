package com.stereowalker.combat.event;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.AbominationEnums.AbominationType;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class AbominationEvents {
	static Random random = new Random();

	public static void updateAbomination(Monster monster) {
		CombatEntityStats.addStatsToMonstersOnSpawn(monster, random);
	}

	public static final List<ItemStack> getAbominationLoot() {
		return Lists.newArrayList(
				EnchantmentHelper.enchantItem(random, new ItemStack(Items.BOOK), random.nextInt(100), true), 
				new ItemStack(CItems.XP_STORAGE_RING), 
				new ItemStack(CItems.POISON_CLEANSING_AMULET),
				new ItemStack(CItems.PYROMANCER_RING),
				new ItemStack(CItems.TERRAMANCER_RING));
	}

	public static void abominationDeath(DamageSource damageSource, Monster monster) {
		CompoundTag compound = CombatEntityStats.getModNBT(monster);
		if (compound.contains(CombatEntityStats.abominationID)) {
			if (CombatEntityStats.getAbomination(monster) != AbominationType.NORMAL) {
				if (damageSource.getEntity() instanceof Player) {
					int randomDrop = random.nextInt(getAbominationLoot().size());
					monster.spawnAtLocation(getAbominationLoot().get(randomDrop));
					CombatEntityStats.addMana((LivingEntity) damageSource.getEntity(), 20.0F);
				}
			}
		}
	}

	public static int abominationXP(Monster monster, int xp) {
		CompoundTag compound = CombatEntityStats.getModNBT(monster);
		if (compound.contains(CombatEntityStats.abominationID)) {
			if (CombatEntityStats.getAbomination(monster) != AbominationType.NORMAL) {
				return xp * 100;
			}
		}
		return xp;
	}
}
