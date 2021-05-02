package com.stereowalker.combat.item;

import java.util.List;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.spell.SpellStats;
import com.stereowalker.combat.spell.Spells;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ScrollItem extends Item{

	public ScrollItem(Properties properties) {
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	public ItemStack getDefaultInstance() {
		return SpellUtil.addSpellToStack(super.getDefaultInstance(), Spells.NONE);
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		addSpellTooltip(stack, tooltip, SpellUtil.getSpellFromItem(stack));
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			for(Spell spell : CombatRegistries.SPELLS) {
				if (spell != Spells.NONE) {
					items.add(SpellUtil.addSpellToStack(new ItemStack(this), spell));
				}
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!AbstractSpellBookItem.getMainSpellBook(playerIn).isEmpty()) {
			ItemStack book =  AbstractSpellBookItem.getMainSpellBook(playerIn);
			((AbstractSpellBookItem)book.getItem()).openGrimoireInSlot(book);
			return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
		}
		return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return SpellUtil.itemHasSpell(stack);
	}

	@OnlyIn(Dist.CLIENT)
	public static void addSpellTooltip(ItemStack itemIn, List<ITextComponent> lores, Spell spell) {
		if (SpellUtil.itemHasSpell(itemIn)) {
			lores.add(spell.getColoredName(SpellStats.getSpellKnowledge(Minecraft.getInstance().player, spell)));
			lores.add(spell.getRank().getDisplayName());
		}
	}

}
