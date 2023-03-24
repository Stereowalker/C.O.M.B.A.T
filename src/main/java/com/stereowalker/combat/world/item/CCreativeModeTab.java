package com.stereowalker.combat.world.item;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentCategory;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class CCreativeModeTab extends CreativeModeTab {
	public CCreativeModeTab(String label) {
		super(label);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public net.minecraft.resources.ResourceLocation getBackgroundImage() {
		return Combat.getInstance().location("textures/gui/container/creative_inventory/tab_" + this.getBackgroundSuffix());
	}

	private static final net.minecraft.resources.ResourceLocation CREATIVE_INVENTORY_TABS = Combat.getInstance().location("textures/gui/container/creative_inventory/tabs.png");
	@Override
	@OnlyIn(Dist.CLIENT)
	public net.minecraft.resources.ResourceLocation getTabsImage() {
		return CREATIVE_INVENTORY_TABS;
	}


	public static final CreativeModeTab BUILDING_BLOCKS = (new CCreativeModeTab("combat_construction_blocks") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(CBlocks.MEZEPINE_BRICKS);
		}
	}).setBackgroundSuffix("combat.png");
	@SuppressWarnings("deprecation")
	public static final CreativeModeTab MAGIC = (new CCreativeModeTab("combat_magic") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(CItems.GRIMOIRE);
		}
	}).setBackgroundSuffix("magic.png").setEnchantmentCategories(new EnchantmentCategory[]{CEnchantmentCategory.WAND});
	public static final CreativeModeTab BATTLE = (new CCreativeModeTab("combat_battle") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(CItems.NETHERITE_KATANA);
		}
	}).setBackgroundSuffix("battle.png").setEnchantmentCategories(new EnchantmentCategory[]{CEnchantmentCategory.TWO_HAND, CEnchantmentCategory.CURVED, CEnchantmentCategory.EDGELESS, CEnchantmentCategory.THROWN, CEnchantmentCategory.SHIELD});
	public static final CreativeModeTab TECHNOLOGY = (new CCreativeModeTab("combat_machinery") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(CItems.MANA_GENERATOR);
		}
	}).setBackgroundSuffix("combat.png");
	public static final CreativeModeTab COMBAT_TAB_MISC = (new CCreativeModeTab("combat_misc") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(CItems.PEBBLE);
		}	      
	}).setBackgroundSuffix("combat.png");
	public static final CreativeModeTab TOOLS = (new CCreativeModeTab("combat_tools") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(CItems.STEEL_PICKAXE);
		}
	}).setBackgroundSuffix("combat.png");
}
