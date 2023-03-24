package com.stereowalker.combat.world.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.stereowalker.combat.advancements.CCriteriaTriggers;
import com.stereowalker.combat.core.EnergyUtils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface Mythril {

	public default boolean isUsingEnergy(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean("UsingEnergy");
	}

	public default void switchActivity(ItemStack stack, LivingEntity livingEntity) {
		stack.getOrCreateTag().putBoolean("UsingEnergy", !stack.getOrCreateTag().getBoolean("UsingEnergy"));
		stack.hurtAndBreak(1, livingEntity, (p_220045_0_) -> {
			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
	}

	public default void setUsingEnergy(ItemStack stack, boolean activity) {
		stack.getOrCreateTag().putBoolean("UsingEnergy", activity);
	}

	public default void handleInInventory(ItemStack stack, Entity entityIn) {
		EnergyUtils.setMaxEnergy(stack, getMaxEnergy(), EnergyUtils.EnergyType.DIVINE_ENERGY);
		if (entityIn instanceof LivingEntity) {
			if (entityIn.tickCount%20 == 0) {
				if (isUsingEnergy(stack)) {
					if (entityIn instanceof ServerPlayer) CCriteriaTriggers.ITEM_ELECTRICALLY_CHARGED.trigger((ServerPlayer)entityIn, stack);
					EnergyUtils.addEnergyToItem(stack, -1, EnergyUtils.EnergyType.DIVINE_ENERGY);
				}
			}
			if (entityIn instanceof Player && ((Player)entityIn).isCreative()) {
				EnergyUtils.fillToTheBrim(stack, EnergyUtils.EnergyType.DIVINE_ENERGY);
			}
		}
		if (EnergyUtils.isDrained(stack, EnergyUtils.EnergyType.DIVINE_ENERGY)) {
			setUsingEnergy(stack, false);
		}
	}

	public default Multimap<Attribute, AttributeModifier> getPoweredAttributeModifiers(float attackSpeed, float damageModifier, UUID BASE_ATTACK_DAMAGE_UUID, UUID BASE_ATTACK_SPEED_UUID) {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", damageModifier+CTiers.POWERED_MYTHRIL.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
		return builder.build();
	}
	
	public default boolean shouldDrain(LivingEntity entity, ItemStack stack) {
		return !entity.level.isClientSide && isUsingEnergy(stack) && !EnergyUtils.isDrained(stack, EnergyUtils.EnergyType.DIVINE_ENERGY) && (!(entity instanceof Player) || !((Player)entity).getAbilities().instabuild);
	}

	public abstract int getMaxEnergy();
}
