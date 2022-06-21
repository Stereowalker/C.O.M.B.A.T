package com.stereowalker.combat.world.item;

import com.google.common.collect.Multimap;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

public class MythrilHoeItem extends CustomHoeItem implements Mythril {

	private float attackSpeed;

	public MythrilHoeItem(Tier itemTier, int attackDamage, float attackSpeed, Properties properties) {
		super(itemTier, attackDamage, attackSpeed, properties);
		this.attackSpeed = attackSpeed;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return super.getDestroySpeed(stack, state) * (isUsingEnergy(stack) ? 2.0F : 1.0F);
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
		return equipmentSlot == EquipmentSlot.MAINHAND && isUsingEnergy(stack) ? 
				getPoweredAttributeModifiers(attackSpeed, this.getAttackDamage()-this.getTier().getAttackDamageBonus(), BASE_ATTACK_DAMAGE_UUID, BASE_ATTACK_SPEED_UUID) : 
					super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public int getMaxEnergy() {
		return 5000;
	}
}
