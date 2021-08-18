package com.stereowalker.combat.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MythrilSwordItem extends SwordItem implements IMythrilItem {
	private final float attackSpeed;

	public MythrilSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		this.attackSpeed = attackSpeedIn;
	}
	
	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!attacker.world.isRemote && isUsingEnergy(stack) && !EnergyUtils.isDrained(stack, EnergyType.DIVINE_ENERGY) && (!(attacker instanceof PlayerEntity) || !((PlayerEntity)attacker).abilities.isCreativeMode)) {
			EnergyUtils.addEnergyToItem(stack, -10, EnergyType.DIVINE_ENERGY);
		}
		return super.hitEntity(stack, target, attacker);
	}
	
	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getBlockHardness(worldIn, pos) != 0.0F) {
			if (!entityLiving.world.isRemote && isUsingEnergy(stack) && !EnergyUtils.isDrained(stack, EnergyType.DIVINE_ENERGY) && (!(entityLiving instanceof PlayerEntity) || !((PlayerEntity)entityLiving).abilities.isCreativeMode)) {
				EnergyUtils.addEnergyToItem(stack, -10, EnergyType.DIVINE_ENERGY);
			}
		}
		
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> attributeModifiers;
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.getAttackDamage() * 2.0F, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
		attributeModifiers = builder.build();


		return equipmentSlot == EquipmentSlotType.MAINHAND && isUsingEnergy(stack) ? attributeModifiers : super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public int getMaxEnergy() {
		return 5000;
	}

}
