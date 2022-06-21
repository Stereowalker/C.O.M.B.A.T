package com.stereowalker.combat.world.item;

import com.google.common.collect.Multimap;
import com.stereowalker.combat.core.EnergyUtils;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

public class MythrilShovelItem extends ShovelItem implements Mythril {

	private float attackSpeed;

	public MythrilShovelItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties pProperties) {
		super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
		this.attackSpeed = pAttackSpeedModifier;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return super.getDestroySpeed(stack, state) * (isUsingEnergy(stack) ? 2.0F : 1.0F);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		InteractionResult result = super.useOn(context);
		if (isUsingEnergy(context.getItemInHand()) && result == InteractionResult.sidedSuccess(context.getLevel().isClientSide)) {
			if (context.getPlayer() != null) {
				EnergyUtils.addEnergyToItem(context.getItemInHand(), -10, EnergyUtils.EnergyType.DIVINE_ENERGY);
			}
		}
		return result;
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
