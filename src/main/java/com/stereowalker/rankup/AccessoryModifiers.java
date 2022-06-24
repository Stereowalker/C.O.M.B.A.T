package com.stereowalker.rankup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public enum AccessoryModifiers {
	NONE (null, -1, null, null, Rarity.VERY_COMMON),
	
	VITALITY_01 (new ResourceLocation("combat:vitality"),  20, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.VERY_COMMON),
	VITALITY_02 (new ResourceLocation("combat:vitality"),  40, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.COMMON),
	VITALITY_03 (new ResourceLocation("combat:vitality"),  60, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.COMMON),
	VITALITY_04 (new ResourceLocation("combat:vitality"),  80, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.COMMON),
	VITALITY_05 (new ResourceLocation("combat:vitality"), 100, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.UNCOMMON),
	VITALITY_06 (new ResourceLocation("combat:vitality"), 120, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.UNCOMMON),
	VITALITY_07 (new ResourceLocation("combat:vitality"), 140, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.RARE),
	VITALITY_08 (new ResourceLocation("combat:vitality"), 160, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.RARE),
	VITALITY_09 (new ResourceLocation("combat:vitality"), 180, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.VERY_RARE),
	VITALITY_10 (new ResourceLocation("combat:vitality"), 200, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.VERY_RARE),
	
	VITALITY_X1 (new ResourceLocation("combat:vitality"),  -20, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.VERY_COMMON),
	VITALITY_X2 (new ResourceLocation("combat:vitality"),  -40, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.COMMON),
	VITALITY_X3 (new ResourceLocation("combat:vitality"),  -60, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.UNCOMMON),
	VITALITY_X4 (new ResourceLocation("combat:vitality"),  -80, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.RARE),
	VITALITY_X5 (new ResourceLocation("combat:vitality"), -100, Operation.MULTIPLY_BASE, ModifierType.VITALITY, Rarity.VERY_RARE),
	
	AGILITY_01 (new ResourceLocation("combat:agility"),  20, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.VERY_COMMON),
	AGILITY_02 (new ResourceLocation("combat:agility"),  40, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.COMMON),
	AGILITY_03 (new ResourceLocation("combat:agility"),  60, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.COMMON),
	AGILITY_04 (new ResourceLocation("combat:agility"),  80, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.COMMON),
	AGILITY_05 (new ResourceLocation("combat:agility"), 100, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.UNCOMMON),
	AGILITY_06 (new ResourceLocation("combat:agility"), 120, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.UNCOMMON),
	AGILITY_07 (new ResourceLocation("combat:agility"), 140, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.RARE),
	AGILITY_08 (new ResourceLocation("combat:agility"), 160, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.RARE),
	AGILITY_09 (new ResourceLocation("combat:agility"), 180, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.VERY_RARE),
	AGILITY_10 (new ResourceLocation("combat:agility"), 200, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.VERY_RARE),
	
	AGILITY_X1 (new ResourceLocation("combat:agility"),  -20, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.VERY_COMMON),
	AGILITY_X2 (new ResourceLocation("combat:agility"),  -40, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.COMMON),
	AGILITY_X3 (new ResourceLocation("combat:agility"),  -60, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.UNCOMMON),
	AGILITY_X4 (new ResourceLocation("combat:agility"),  -80, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.RARE),
	AGILITY_X5 (new ResourceLocation("combat:agility"), -100, Operation.MULTIPLY_BASE, ModifierType.AGILITY, Rarity.VERY_RARE),
	
	MAGIC_01 (new ResourceLocation("combat:magic"),  20, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.VERY_COMMON),
	MAGIC_02 (new ResourceLocation("combat:magic"),  40, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.COMMON),
	MAGIC_03 (new ResourceLocation("combat:magic"),  60, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.COMMON),
	MAGIC_04 (new ResourceLocation("combat:magic"),  80, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.COMMON),
	MAGIC_05 (new ResourceLocation("combat:magic"), 100, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.UNCOMMON),
	MAGIC_06 (new ResourceLocation("combat:magic"), 120, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.UNCOMMON),
	MAGIC_07 (new ResourceLocation("combat:magic"), 140, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.RARE),
	MAGIC_08 (new ResourceLocation("combat:magic"), 160, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.RARE),
	MAGIC_09 (new ResourceLocation("combat:magic"), 180, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.VERY_RARE),
	MAGIC_10 (new ResourceLocation("combat:magic"), 200, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.VERY_RARE),
	
	MAGIC_X1 (new ResourceLocation("combat:magic"),  -20, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.VERY_COMMON),
	MAGIC_X2 (new ResourceLocation("combat:magic"),  -40, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.COMMON),
	MAGIC_X3 (new ResourceLocation("combat:magic"),  -60, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.UNCOMMON),
	MAGIC_X4 (new ResourceLocation("combat:magic"),  -80, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.RARE),
	MAGIC_X5 (new ResourceLocation("combat:magic"), -100, Operation.MULTIPLY_BASE, ModifierType.MAGIC, Rarity.VERY_RARE);
//	
//	ATTACK_DAMAGE_01 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b60", 0.01D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.VERY_COMMON),
//	ATTACK_DAMAGE_02 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b61", 0.02D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.COMMON),
//	ATTACK_DAMAGE_03 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b62", 0.03D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.COMMON),
//	ATTACK_DAMAGE_04 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b63", 0.04D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.COMMON),
//	ATTACK_DAMAGE_05 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b64", 0.05D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.UNCOMMON),
//	ATTACK_DAMAGE_06 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b65", 0.06D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.UNCOMMON),
//	ATTACK_DAMAGE_07 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b66", 0.07D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.RARE),
//	ATTACK_DAMAGE_08 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b67", 0.08D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.RARE),
//	ATTACK_DAMAGE_09 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b68", 0.09D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.VERY_RARE),
//	ATTACK_DAMAGE_10 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b69", 0.10D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.VERY_RARE),
//	
//	ATTACK_DAMAGE_X1 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b70", -0.01D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.VERY_COMMON),
//	ATTACK_DAMAGE_X2 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b71", -0.02D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.COMMON),
//	ATTACK_DAMAGE_X3 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b72", -0.03D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.UNCOMMON),
//	ATTACK_DAMAGE_X4 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b73", -0.04D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.RARE),
//	ATTACK_DAMAGE_X5 (Attributes.ATTACK_DAMAGE, "16b2d160-b860-48e6-98dd-5fa801bb3b74", -0.05D, Operation.MULTIPLY_BASE, ModifierType.ATTACK_DAMAGE, Rarity.VERY_RARE),
//	
//	JUMP_HEIGHT_01 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b80", 0.1D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.VERY_COMMON),
//	JUMP_HEIGHT_02 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b81", 0.2D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.COMMON),
//	JUMP_HEIGHT_03 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b82", 0.3D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.COMMON),
//	JUMP_HEIGHT_04 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b83", 0.4D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.COMMON),
//	JUMP_HEIGHT_05 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b84", 1.5D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.UNCOMMON),
//	JUMP_HEIGHT_06 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b85", 1.6D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.UNCOMMON),
//	JUMP_HEIGHT_07 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b86", 1.7D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.RARE),
//	JUMP_HEIGHT_08 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b87", 1.8D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.RARE),
//	JUMP_HEIGHT_09 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b88", 1.9D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.VERY_RARE),
//	JUMP_HEIGHT_10 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b89", 1.0D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.VERY_RARE),
//	
//	JUMP_HEIGHT_X1 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b90", -0.1D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.VERY_COMMON),
//	JUMP_HEIGHT_X2 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b91", -0.2D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.COMMON),
//	JUMP_HEIGHT_X3 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b92", -0.3D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.UNCOMMON),
//	JUMP_HEIGHT_X4 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b93", -0.4D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.RARE),
//	JUMP_HEIGHT_X5 (CAttributes.JUMP_STRENGTH, "16b2d160-b860-48e6-98dd-5fa801bb3b94", -0.5D, Operation.ADDITION, ModifierType.JUMP_HEIGHT, Rarity.VERY_RARE),
//	
//	ARMOR_01 (Attributes.ARMOR, "16b2d160-b860-48e6-98dd-5fa801bb3ba0", 1.0D, Operation.ADDITION, ModifierType.ARMOR, Rarity.VERY_COMMON),
//	ARMOR_02 (Attributes.ARMOR, "16b2d160-b860-48e6-98dd-5fa801bb3ba1", 2.0D, Operation.ADDITION, ModifierType.ARMOR, Rarity.COMMON),
//	ARMOR_03 (Attributes.ARMOR, "16b2d160-b860-48e6-98dd-5fa801bb3ba2", 3.0D, Operation.ADDITION, ModifierType.ARMOR, Rarity.UNCOMMON),
//	ARMOR_04 (Attributes.ARMOR, "16b2d160-b860-48e6-98dd-5fa801bb3ba3", 4.0D, Operation.ADDITION, ModifierType.ARMOR, Rarity.RARE),
//	ARMOR_05 (Attributes.ARMOR, "16b2d160-b860-48e6-98dd-5fa801bb3ba4", 5.0D, Operation.ADDITION, ModifierType.ARMOR, Rarity.RARE),
//	ARMOR_06 (Attributes.ARMOR, "16b2d160-b860-48e6-98dd-5fa801bb3ba5", 6.0D, Operation.ADDITION, ModifierType.ARMOR, Rarity.VERY_RARE),
//	ARMOR_07 (Attributes.ARMOR, "16b2d160-b860-48e6-98dd-5fa801bb3ba6", 7.0D, Operation.ADDITION, ModifierType.ARMOR, Rarity.VERY_RARE),
//	
//	HEALTH_01 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bb0", 1.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.VERY_COMMON),
//	HEALTH_02 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bb1", 2.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.COMMON),
//	HEALTH_03 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bb2", 3.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.UNCOMMON),
//	HEALTH_04 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bb3", 4.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.RARE),
//	HEALTH_05 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bb4", 5.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.RARE),
//	HEALTH_06 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bb5", 6.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.VERY_RARE),
//	HEALTH_07 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bb6", 7.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.VERY_RARE),
//	
//	HEALTH_X1 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bc0", -1.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.VERY_COMMON),
//	HEALTH_X2 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bc1", -2.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.COMMON),
//	HEALTH_X3 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bc2", -3.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.UNCOMMON),
//	HEALTH_X4 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bc3", -4.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.RARE),
//	HEALTH_X5 (Attributes.MAX_HEALTH, "16b2d160-b860-48e6-98dd-5fa801bb3bc4", -5.0D, Operation.ADDITION, ModifierType.MAX_HEALTH, Rarity.VERY_RARE),
//	
//	ARMOR_TOUGHNESS_01 (Attributes.ARMOR_TOUGHNESS, "16b2d160-b860-48e6-98dd-5fa801bb3bd0", 1.0D, Operation.ADDITION, ModifierType.ARMOR_TOUGHNESS, Rarity.VERY_COMMON),
//	ARMOR_TOUGHNESS_02 (Attributes.ARMOR_TOUGHNESS, "16b2d160-b860-48e6-98dd-5fa801bb3bd1", 2.0D, Operation.ADDITION, ModifierType.ARMOR_TOUGHNESS, Rarity.COMMON),
//	ARMOR_TOUGHNESS_03 (Attributes.ARMOR_TOUGHNESS, "16b2d160-b860-48e6-98dd-5fa801bb3bd2", 3.0D, Operation.ADDITION, ModifierType.ARMOR_TOUGHNESS, Rarity.UNCOMMON),
//	ARMOR_TOUGHNESS_04 (Attributes.ARMOR_TOUGHNESS, "16b2d160-b860-48e6-98dd-5fa801bb3bd3", 4.0D, Operation.ADDITION, ModifierType.ARMOR_TOUGHNESS, Rarity.RARE),
//	ARMOR_TOUGHNESS_05 (Attributes.ARMOR_TOUGHNESS, "16b2d160-b860-48e6-98dd-5fa801bb3bd4", 5.0D, Operation.ADDITION, ModifierType.ARMOR_TOUGHNESS, Rarity.RARE),
//	ARMOR_TOUGHNESS_06 (Attributes.ARMOR_TOUGHNESS, "16b2d160-b860-48e6-98dd-5fa801bb3bd5", 6.0D, Operation.ADDITION, ModifierType.ARMOR_TOUGHNESS, Rarity.VERY_RARE),
//	ARMOR_TOUGHNESS_07 (Attributes.ARMOR_TOUGHNESS, "16b2d160-b860-48e6-98dd-5fa801bb3bd6", 7.0D, Operation.ADDITION, ModifierType.ARMOR_TOUGHNESS, Rarity.VERY_RARE),
//	
//	KNOCKBACK_RESISTANCE_01 (Attributes.KNOCKBACK_RESISTANCE, "16b2d160-b860-48e6-98dd-5fa801bb3be0", 1.0D, Operation.ADDITION, ModifierType.KNOCKBACK_RESISTANCE, Rarity.VERY_COMMON),
//	KNOCKBACK_RESISTANCE_02 (Attributes.KNOCKBACK_RESISTANCE, "16b2d160-b860-48e6-98dd-5fa801bb3be1", 2.0D, Operation.ADDITION, ModifierType.KNOCKBACK_RESISTANCE, Rarity.COMMON),
//	KNOCKBACK_RESISTANCE_03 (Attributes.KNOCKBACK_RESISTANCE, "16b2d160-b860-48e6-98dd-5fa801bb3be2", 3.0D, Operation.ADDITION, ModifierType.KNOCKBACK_RESISTANCE, Rarity.UNCOMMON),
//	KNOCKBACK_RESISTANCE_04 (Attributes.KNOCKBACK_RESISTANCE, "16b2d160-b860-48e6-98dd-5fa801bb3be3", 4.0D, Operation.ADDITION, ModifierType.KNOCKBACK_RESISTANCE, Rarity.RARE),
//	KNOCKBACK_RESISTANCE_05 (Attributes.KNOCKBACK_RESISTANCE, "16b2d160-b860-48e6-98dd-5fa801bb3be4", 5.0D, Operation.ADDITION, ModifierType.KNOCKBACK_RESISTANCE, Rarity.RARE),
//	KNOCKBACK_RESISTANCE_06 (Attributes.KNOCKBACK_RESISTANCE, "16b2d160-b860-48e6-98dd-5fa801bb3be5", 6.0D, Operation.ADDITION, ModifierType.KNOCKBACK_RESISTANCE, Rarity.VERY_RARE),
//	KNOCKBACK_RESISTANCE_07 (Attributes.KNOCKBACK_RESISTANCE, "16b2d160-b860-48e6-98dd-5fa801bb3be6", 7.0D, Operation.ADDITION, ModifierType.KNOCKBACK_RESISTANCE, Rarity.VERY_RARE);
	
	ResourceLocation stat;
	int amount;
	boolean isDebuff;
	AttributeModifier.Operation operation;
	String text;
	ModifierType type;
	Rarity rarity;
	
	AccessoryModifiers(ResourceLocation statIn, int amount, AttributeModifier.Operation operation, ModifierType type, Rarity rarity) {
		this.amount = amount;
		this.stat = statIn;
		this.operation = operation;
		this.type = type;
		this.rarity = rarity;
	}
	
	public ResourceLocation getStat() {
		return stat;
	}

	public int getAmount() {
		return amount;
	}
	
	public boolean isDebuff() {
		return amount < 0;
	}
	
	public ModifierType getType() {
		return type;
	}
	
	public Rarity getRarity() {
		return rarity;
	}
	
	public String AmountText() {
		if (getOperation().equals(Operation.MULTIPLY_BASE)) {
			double pseudoAmount = getAmount();
			while (pseudoAmount >= 1.0D) {
				pseudoAmount -= 1.0D;
			}
			while (pseudoAmount <= -1.0D) {
				pseudoAmount += 1.0D;
			}
			if (pseudoAmount == 0.0D) {
				return (isDebuff()?"":"+")+(int)(getAmount());
			} else {
				return (isDebuff()?"":"+")+(getAmount());
			}
		} else {
			return (isDebuff()?"":"+")+(int)(getAmount() * 100.0D)+"%";
		}
	}

	public AttributeModifier.Operation getOperation() {
		return operation;
	}
	
	public static AccessoryModifiers getRandomModifier(Random random, boolean debuff, ModifierType conflict) {
		List<AccessoryModifiers> elegibleModifiers = new ArrayList<AccessoryModifiers>();
		for (AccessoryModifiers modifier : AccessoryModifiers.values()) {
			if (debuff == modifier.isDebuff() && modifier.getType() != conflict) {
				elegibleModifiers.add(modifier);
			}
		}
		int totalWeight = 0;
		for (AccessoryModifiers modifier : elegibleModifiers) {
			totalWeight += modifier.getRarity().getWeight();
		}
		int randomModifier = new Random().nextInt(totalWeight);
		int i = 0;
		for (AccessoryModifiers modifier : elegibleModifiers) {
			i += modifier.getRarity().getWeight();
			if (i >= randomModifier) {
				return modifier;
			}
		}
		return null;
	}
	
	public enum ModifierType {
		VITALITY("Vitality"),
		AGILITY("Agility"),
		MAGIC("Magic");
//		MAX_HEALTH("max health"),
//		ATTACK_DAMAGE("attack damage"),
//		ARMOR("armor"),
//		ARMOR_TOUGHNESS("armor toughness"), 
//		KNOCKBACK_RESISTANCE("knockback resistance"),
//		JUMP_HEIGHT("jump height");
		
		String name;
		
		ModifierType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	public enum Rarity {
		VERY_COMMON(16),
		COMMON(8),
		UNCOMMON(4),
		RARE(2),
		VERY_RARE(1);
		
		int weight;
		
		Rarity(int weight){
			this.weight = weight;
		}
		
		public int getWeight() {
			return weight;
		}
	}
}
