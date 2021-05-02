package com.stereowalker.rankup.skill.api;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.skill.Skills;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class SkillUtil {
	public static Skill getSkillFromName(String name) {
		for(Skill skill : CombatRegistries.SKILLS) {
			if(skill.getKey().contentEquals(name)) {
				return skill;
			}
		}
		return Skills.EMPTY;
	}

	public static Skill getSkillFromItem(ItemStack stack) {
		return SkillUtil.getSkillFromNBT(stack.getOrCreateTag());
	}

	/**
	 * If no correct skill is found, returns the default one : {@link Skills#EMPTY}
	 */
	public static Skill getSkillFromNBT(@Nullable CompoundNBT tag) {
		return getSkillFromNBT(tag, "Skill");
	}

	/**
	 * If no correct skill is found, returns the default one : {@link Skills#EMPTY}
	 */
	public static Skill getSkillFromNBT(@Nullable CompoundNBT tag, String location) {
		return tag == null ? Skills.EMPTY : SkillUtil.getSkillFromName(tag.getString(location));
	}

	public static ItemStack addSkillToItemStack(ItemStack itemIn, Skill skillIn) {
		if (skillIn == Skills.EMPTY) {
			itemIn.removeChildTag("Skill");
		} else {
			itemIn.getOrCreateTag().putString("Skill", skillIn.getKey());
		}
		return itemIn;
	}
}
