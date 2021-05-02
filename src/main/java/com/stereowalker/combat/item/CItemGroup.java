package com.stereowalker.combat.item;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.enchantment.CEnchantmentType;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class CItemGroup extends ItemGroup {
	public CItemGroup(String label) {
		super(label);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public net.minecraft.util.ResourceLocation getBackgroundImage() {
		return Combat.getInstance().location("textures/gui/container/creative_inventory/tab_" + this.getBackgroundImageName());
	}

	private static final net.minecraft.util.ResourceLocation CREATIVE_INVENTORY_TABS = Combat.getInstance().location("textures/gui/container/creative_inventory/tabs.png");
	@Override
	@OnlyIn(Dist.CLIENT)
	public net.minecraft.util.ResourceLocation getTabsImage() {
		return CREATIVE_INVENTORY_TABS;
	}


	public static final ItemGroup BUILDING_BLOCKS = (new CItemGroup("combat_construction_blocks") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(CBlocks.MEZEPINE_BRICKS);
		}
	}).setBackgroundImageName("combat.png");
	public static final ItemGroup MAGIC = (new CItemGroup("combat_magic") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(CItems.GRIMOIRE);
		}
	}).setBackgroundImageName("magic.png").setRelevantEnchantmentTypes(new EnchantmentType[]{CEnchantmentType.WAND});
	public static final ItemGroup BATTLE = (new CItemGroup("combat_battle") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(CItems.NETHERITE_KATANA);
		}
	}).setBackgroundImageName("battle.png").setRelevantEnchantmentTypes(new EnchantmentType[]{CEnchantmentType.TWO_HAND, CEnchantmentType.CURVED, CEnchantmentType.SINGLE_EDGE, CEnchantmentType.THROWN, CEnchantmentType.SHIELD});
	public static final ItemGroup TECHNOLOGY = (new CItemGroup("combat_machinery") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(CItems.MANA_GENERATOR);
		}
	}).setBackgroundImageName("combat.png");
	public static final ItemGroup MISC = (new CItemGroup("combat_misc") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(CItems.PEBBLE);
		}	      
	}).setBackgroundImageName("combat.png");
	public static final ItemGroup TOOLS = (new CItemGroup("combat_tools") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(CItems.STEEL_PICKAXE);
		}
	}).setBackgroundImageName("combat.png");
}
