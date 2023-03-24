package com.stereowalker.combat.world.item;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;

public class TieredWandItem extends AbstractMagicCastingItem implements Vanishable {
	private final WandTier wandTier;

	public TieredWandItem(Properties properties, Rank tier, WandTier wandTierIn) {
		super(properties.defaultDurability(wandTierIn.getMaxUses()*(tier.ordinal())), tier, wandTierIn.getStrengthModifier()*(tier.ordinal()*0.2F), wandTierIn.getCostModifier());
		this.wandTier = wandTierIn;
	}

	//TODO: Change Tier back to Rank
	public WandTier getWandTier() {
		return this.wandTier;
	}

	@Override
	public int getEnchantmentValue() {
		return this.wandTier.getEnchantability();
	}

	@Override
	public boolean canCast(Player player, ItemStack wand) {
		if (AbstractSpellBookItem.getMainSpellBookItem(player) != null) {
			return SpellUtil.canItemCastSpell(wand, AbstractSpellBookItem.getMainSpellBookItem(player).getCurrentSpell(player));
		}
		return false;
	}
}
