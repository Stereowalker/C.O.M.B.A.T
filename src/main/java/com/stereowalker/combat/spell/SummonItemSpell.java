package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.SpellCategory;

import java.util.function.Supplier;

import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public class SummonItemSpell extends Spell {
	private Supplier<Item> item;

	protected SummonItemSpell(SpellCategory category, Rank tier, float cost, int cooldown, Supplier<Item> item, int castTime) {
		super(category, tier, CastType.SELF, cost, cooldown, castTime);
		this.item = item;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		if(!caster.world.isRemote && caster instanceof PlayerEntity) {
			return ((PlayerEntity) caster).addItemStackToInventory(new ItemStack(item.get()));
		}
		return true;
	}

}
