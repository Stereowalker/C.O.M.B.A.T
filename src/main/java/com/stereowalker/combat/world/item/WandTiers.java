 package com.stereowalker.combat.world.item;

public enum WandTiers implements WandTier {
	STICK(1, 439, 0.1F, -0.401F, 7),
	GOLD(3, 312, 0.6F, -0.204F, 20),
	BLAZE(2, 467, 0.4F, -0.097F, 13),
	SHULKER(5, 126, 0.8F, 0.248F, 16),
	SERABLE(3, 418, 1.0F, 0.477F, 15);

	private final int spellSlots;
	private final int maxUses;
	private final float strengthModifier;
	private final float costModifier;
	private final int enchantability;

	private WandTiers(int spellSlotsIn, int maxUsesIn, float strengthModifierIn, float costModifierIn, int enchantabilityIn) {
		this.spellSlots = spellSlotsIn;
		this.maxUses = maxUsesIn;
		this.strengthModifier = strengthModifierIn;
		this.costModifier = costModifierIn;
		this.enchantability = enchantabilityIn;
	}

//	@Override
//	public int getSpellSlots() {// 2 - 5 // CHORUS
//		return this.spellSlots;
//	}

	@Override
	public int getMaxUses() {// 50 - 500 // BLAZE
		return this.maxUses;
	}

	@Override
	public float getStrengthModifier() {// 0.1 - 1.0 // SERABLE
		return this.strengthModifier;
	}

	@Override
	public float getCostModifier() {// -0.800 - 0.800 // STICK
		return this.costModifier;
	}

	@Override
	public int getEnchantability() {// 1 - 20 // GOLD
		return this.enchantability;
	}
}