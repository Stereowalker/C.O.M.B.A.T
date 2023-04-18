package com.stereowalker.combat.world.item;

import java.util.EnumSet;
import java.util.Set;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory.ClassType;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentCategory;
import com.stereowalker.combat.world.spellcraft.Spells;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CCreativeModeTab {

	public static final CreativeModeTab BUILDING_BLOCKS = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).title(Component.translatable("itemGroup.combat_construction_blocks")).icon(() -> {
		return new ItemStack(CItems.MEZEPINE_BRICKS);
	}).backgroundSuffix("combat.png").displayItems((x,output,z) ->{
	}).build();
	public static final CreativeModeTab MAGIC = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).title(Component.translatable("itemGroup.combat_magic")).icon(() -> {
		return new ItemStack(CItems.GRIMOIRE);
	}).backgroundSuffix("magic.png").displayItems((x,output,z) ->{
		for(Spell spell : CombatRegistries.SPELLS) {
			if (spell != Spells.NONE && spell.getCategory().getClassType() == ClassType.ELEMENTAL) {
				output.accept(SpellUtil.addSpellToStack(new ItemStack(CItems.SCROLL), spell));
			}
			if (spell != Spells.NONE && spell.getCategory().getClassType() == ClassType.PRIMEVAL) {
				output.accept(SpellUtil.addSpellToStack(new ItemStack(CItems.ANCIENT_SCROLL), spell));
			}
		}
		Set<EnchantmentCategory> set = EnumSet.of(CEnchantmentCategory.WAND);
		CreativeModeTabs.generateEnchantmentBookTypesOnlyMaxLevel(output, set, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
		CreativeModeTabs.generateEnchantmentBookTypesAllLevels(output, set, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
	}).build();
	public static final CreativeModeTab BATTLE = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).title(Component.translatable("itemGroup.combat_battle")).icon(() -> {
		return new ItemStack(CItems.NETHERITE_KATANA);
	}).backgroundSuffix("battle.png").displayItems((x,output,z) ->{
		for(Potion potion : ForgeRegistries.POTIONS) {
			if (!potion.getEffects().isEmpty()) {
				output.accept(PotionUtils.setPotion(new ItemStack(CItems.WOODEN_TIPPED_ARROW), potion));
				output.accept(PotionUtils.setPotion(new ItemStack(CItems.QUARTZ_TIPPED_ARROW), potion));
				output.accept(PotionUtils.setPotion(new ItemStack(CItems.IRON_TIPPED_ARROW), potion));
				output.accept(PotionUtils.setPotion(new ItemStack(CItems.GOLDEN_TIPPED_ARROW), potion));
				output.accept(PotionUtils.setPotion(new ItemStack(CItems.DIAMOND_TIPPED_ARROW), potion));
				output.accept(PotionUtils.setPotion(new ItemStack(CItems.OBSIDIAN_TIPPED_ARROW), potion));
			}
		}
		Set<EnchantmentCategory> set = EnumSet.of(CEnchantmentCategory.TWO_HAND, CEnchantmentCategory.CURVED, CEnchantmentCategory.EDGELESS, CEnchantmentCategory.THROWN, CEnchantmentCategory.SHIELD);
		CreativeModeTabs.generateEnchantmentBookTypesOnlyMaxLevel(output, set, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
		CreativeModeTabs.generateEnchantmentBookTypesAllLevels(output, set, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
	}).build();
	public static final CreativeModeTab TECHNOLOGY = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).title(Component.translatable("itemGroup.combat_machinery")).icon(() -> {
		return new ItemStack(CItems.MANA_GENERATOR);
	}).backgroundSuffix("combat.png").displayItems((x,output,z) ->{
	}).build();
	public static final CreativeModeTab COMBAT_TAB_MISC = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).title(Component.translatable("itemGroup.combat_misc")).icon(() -> {
		return new ItemStack(CItems.PEBBLE);
	}).backgroundSuffix("combat.png").displayItems((x,output,z) ->{
	}).build();
	public static final CreativeModeTab TOOLS = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).title(Component.translatable("itemGroup.combat_tools")).icon(() -> {
		return new ItemStack(CItems.STEEL_PICKAXE);
	}).backgroundSuffix("combat.png").displayItems((x,output,z) ->{
	}).build();
}
