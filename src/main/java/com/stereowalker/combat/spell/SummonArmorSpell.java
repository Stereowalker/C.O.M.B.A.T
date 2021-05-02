package com.stereowalker.combat.spell;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public class SummonArmorSpell extends Spell {
	private Item helmet;
	private Item chestplate;
	private Item leggings;
	private Item boots;

	protected SummonArmorSpell(SpellCategory category, Rank tier, float cost, int cooldown, @Nullable Item helmet, @Nullable Item chestplate, @Nullable Item leggings, @Nullable Item boots, int castTime) {
		super(category, tier, CastType.SELF, cost, cooldown, castTime);
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		boolean flag = false;
		if(!caster.hasItemInSlot(EquipmentSlotType.HEAD) && helmet != null && caster.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() != helmet) {
			if(!caster.world.isRemote) {
				caster.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(helmet));
			}
			flag = true;
		}
		if(!caster.hasItemInSlot(EquipmentSlotType.CHEST) && chestplate != null && caster.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() != chestplate) {
			if(!caster.world.isRemote) {
				caster.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(chestplate));
			}
			flag = true;
		}
		if(!caster.hasItemInSlot(EquipmentSlotType.LEGS) && leggings != null && caster.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() != leggings) {
			if(!caster.world.isRemote) {
				caster.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(leggings));
			}
			flag = true;
		}
		if(!caster.hasItemInSlot(EquipmentSlotType.FEET) && boots != null && caster.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() != boots) {
			if(!caster.world.isRemote) {
				caster.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(boots));
			}
			flag = true;
		}
		return flag;
	}
}
