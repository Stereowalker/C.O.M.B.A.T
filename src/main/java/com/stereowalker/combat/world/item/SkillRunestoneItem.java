package com.stereowalker.combat.world.item;

import java.util.List;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.PlayerSkills.SkillGrantReason;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;

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

public class SkillRunestoneItem extends Item {

	public SkillRunestoneItem(Properties properties) {
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	public ItemStack getDefaultInstance() {
		return SkillUtil.addSkillToItemStack(super.getDefaultInstance(), Skills.EMPTY);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (PlayerSkills.grantSkill(playerIn, SkillUtil.getSkillFromItem(playerIn.getItemInHand(handIn)), SkillGrantReason.ITEM) == PlayerSkills.SkillGrantAction.SUCCESS) {
			playerIn.setItemInHand(handIn, ItemStack.EMPTY);
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
		}
		return new InteractionResultHolder<>(InteractionResult.PASS, playerIn.getItemInHand(handIn));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
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

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (this.allowdedIn(group)) {
			for(Skill spell : CombatRegistries.SKILLS) {
				if (spell != Skills.EMPTY) {
					items.add(SkillUtil.addSkillToItemStack(new ItemStack(this), spell));
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void addSkillTooltip(ItemStack itemIn, List<Component> lores, Skill skills) {
		lores.add(skills.getName());
	}

}
