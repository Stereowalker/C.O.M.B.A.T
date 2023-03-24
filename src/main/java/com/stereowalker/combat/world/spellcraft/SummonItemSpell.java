package com.stereowalker.combat.world.spellcraft;

import java.util.function.Supplier;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class SummonItemSpell extends Spell {
	private Supplier<Item> item;

	protected SummonItemSpell(SpellCategory category, Rank tier, float cost, int cooldown, Supplier<Item> item, int castTime) {
		super(category, tier, CastType.SELF, cost, cooldown, castTime);
		this.item = item;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		if(!caster.level.isClientSide && caster instanceof Player) {
			return ((Player) caster).addItem(new ItemStack(item.get()));
		}
		return true;
	}

}
