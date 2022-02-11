package com.stereowalker.combat.world.spellcraft;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class SummonArmorSpell extends Spell {
	private Supplier<Item> helmet;
	private Supplier<Item> chestplate;
	private Supplier<Item> leggings;
	private Supplier<Item> boots;

	protected SummonArmorSpell(SpellCategory category, Rank tier, float cost, int cooldown, @Nullable Supplier<Item> helmet, @Nullable Supplier<Item> chestplate, @Nullable Supplier<Item> leggings, @Nullable Supplier<Item> boots, int castTime) {
		super(category, tier, CastType.SELF, cost, cooldown, castTime);
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		boolean flag = false;
		if(!caster.hasItemInSlot(EquipmentSlot.HEAD) && helmet != null && caster.getItemBySlot(EquipmentSlot.HEAD).getItem() != helmet) {
			if(!caster.level.isClientSide) {
				caster.setItemSlot(EquipmentSlot.HEAD, new ItemStack(helmet.get()));
			}
			flag = true;
		}
		if(!caster.hasItemInSlot(EquipmentSlot.CHEST) && chestplate != null && caster.getItemBySlot(EquipmentSlot.CHEST).getItem() != chestplate) {
			if(!caster.level.isClientSide) {
				caster.setItemSlot(EquipmentSlot.CHEST, new ItemStack(chestplate.get()));
			}
			flag = true;
		}
		if(!caster.hasItemInSlot(EquipmentSlot.LEGS) && leggings != null && caster.getItemBySlot(EquipmentSlot.LEGS).getItem() != leggings) {
			if(!caster.level.isClientSide) {
				caster.setItemSlot(EquipmentSlot.LEGS, new ItemStack(leggings.get()));
			}
			flag = true;
		}
		if(!caster.hasItemInSlot(EquipmentSlot.FEET) && boots != null && caster.getItemBySlot(EquipmentSlot.FEET).getItem() != boots) {
			if(!caster.level.isClientSide) {
				caster.setItemSlot(EquipmentSlot.FEET, new ItemStack(boots.get()));
			}
			flag = true;
		}
		return flag;
	}
}
