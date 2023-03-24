package com.stereowalker.combat.world.item;

import com.google.common.collect.Multimap;
import com.stereowalker.combat.core.EnergyUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MythrilScytheItem extends ScytheItem implements Mythril {
	private final float attackSpeed;

	public MythrilScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		this.attackSpeed = attackSpeedIn;
	}
	
	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!attacker.level.isClientSide && isUsingEnergy(stack) && !EnergyUtils.isDrained(stack, EnergyUtils.EnergyType.DIVINE_ENERGY) && (!(attacker instanceof Player) || !((Player)attacker).getAbilities().instabuild)) {
			EnergyUtils.addEnergyToItem(stack, -10, EnergyUtils.EnergyType.DIVINE_ENERGY);
		}
		return super.hurtEnemy(stack, target, attacker);
	}
	
	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	@Override
	public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
			if (!entityLiving.level.isClientSide && isUsingEnergy(stack) && !EnergyUtils.isDrained(stack, EnergyUtils.EnergyType.DIVINE_ENERGY) && (!(entityLiving instanceof Player) || !((Player)entityLiving).getAbilities().instabuild)) {
				EnergyUtils.addEnergyToItem(stack, -10, EnergyUtils.EnergyType.DIVINE_ENERGY);
			}
		}
		
		return super.mineBlock(stack, worldIn, state, pos, entityLiving);
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
		return equipmentSlot == EquipmentSlot.MAINHAND && isUsingEnergy(stack) ? 
				getPoweredAttributeModifiers(attackSpeed, this.getDamage()-this.getTier().getAttackDamageBonus(), BASE_ATTACK_DAMAGE_UUID, BASE_ATTACK_SPEED_UUID) : 
					super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public int getMaxEnergy() {
		return 5000;
	}

}
