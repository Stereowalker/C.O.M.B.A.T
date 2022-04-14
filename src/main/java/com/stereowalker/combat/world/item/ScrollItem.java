package com.stereowalker.combat.world.item;

import java.util.List;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.world.spellcraft.SpellStats;
import com.stereowalker.combat.world.spellcraft.Spells;

import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ScrollItem extends Item {
	private final SpellCategory.ClassType type;

	public ScrollItem(SpellCategory.ClassType type, Properties properties) {
		super(properties);
		this.type = type;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack getDefaultInstance() {
		return SpellUtil.addSpellToStack(super.getDefaultInstance(), Spells.NONE);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		addSpellTooltip(stack, tooltip, SpellUtil.getSpellFromItem(stack));
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (group == CCreativeModeTab.MAGIC) {
			for(Spell spell : CombatRegistries.SPELLS) {
				if (spell != Spells.NONE && spell.getCategory().getClassType() == type) {
					items.add(SpellUtil.addSpellToStack(new ItemStack(this), spell));
				}
			}
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (worldIn.isClientSide && !AbstractSpellBookItem.getMainSpellBook(playerIn).isEmpty()) {
			ItemStack book =  AbstractSpellBookItem.getMainSpellBook(playerIn);
			((AbstractSpellBookItem)book.getItem()).openGrimoireInSlot(book);
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
		}
		return new InteractionResultHolder<>(InteractionResult.PASS, playerIn.getItemInHand(handIn));
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return SpellUtil.itemHasSpell(stack);
	}

	@OnlyIn(Dist.CLIENT)
	public static void addSpellTooltip(ItemStack itemIn, List<Component> lores, Spell spell) {
		if (SpellUtil.itemHasSpell(itemIn)) {
			lores.add(spell.getColoredName(SpellStats.getSpellKnowledge(Minecraft.getInstance().player, spell)));
			lores.add(spell.getRank().getDisplayName());
		}
	}

}
