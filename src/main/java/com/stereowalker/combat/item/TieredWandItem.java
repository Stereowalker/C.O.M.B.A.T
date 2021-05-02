package com.stereowalker.combat.item;

import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.SpellUtil;

import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TieredWandItem extends AbstractMagicCastingItem implements IVanishable {
	private final IWandTier wandTier;

	public TieredWandItem(Properties properties, Rank tier, IWandTier wandTierIn) {
		super(properties.defaultMaxDamage(wandTierIn.getMaxUses()*(tier.ordinal())), tier, wandTierIn.getStrengthModifier()*(tier.ordinal()*0.2F), wandTierIn.getCostModifier());
		this.wandTier = wandTierIn;
	}

	//TODO: Change Tier back to Rank
	public IWandTier getWandTier() {
		return this.wandTier;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getItemEnchantability() {
		return this.wandTier.getEnchantability();
	}

	@Override
	public boolean canCast(PlayerEntity player, ItemStack wand) {
		if (AbstractSpellBookItem.getMainSpellBookItem(player) != null) {
			return SpellUtil.canItemCastSpell(wand, AbstractSpellBookItem.getMainSpellBookItem(player).getCurrentSpell(player));
		}
		return false;
	}
}
