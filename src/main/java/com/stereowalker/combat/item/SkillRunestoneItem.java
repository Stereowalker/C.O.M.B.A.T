package com.stereowalker.combat.item;

import java.util.List;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;
import com.stereowalker.rankup.skill.api.PlayerSkills.SkillGrantAction;

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

public class SkillRunestoneItem extends Item{

	public SkillRunestoneItem(Properties properties) {
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	public ItemStack getDefaultInstance() {
		return SkillUtil.addSkillToItemStack(super.getDefaultInstance(), Skills.EMPTY);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (PlayerSkills.grantSkill(playerIn, SkillUtil.getSkillFromItem(playerIn.getHeldItem(handIn)), true) == SkillGrantAction.SUCCESS) {
			playerIn.setHeldItem(handIn, ItemStack.EMPTY);
			return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
		}
		return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		addSkillTooltip(stack, tooltip, SkillUtil.getSkillFromItem(stack));
	}
	
	public static boolean hasSkill(ItemStack stack) {
		if (stack.hasTag()) {
			return stack.getTag().contains("Skill");
		}
		else {
			return false;
		}
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			for(Skill spell : CombatRegistries.SKILLS) {
				if (spell != Skills.EMPTY) {
					items.add(SkillUtil.addSkillToItemStack(new ItemStack(this), spell));
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void addSkillTooltip(ItemStack itemIn, List<ITextComponent> lores, Skill skills) {
		lores.add(skills.getName());
	}

}
