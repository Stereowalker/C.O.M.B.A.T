package com.stereowalker.combat.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.stereowalker.combat.core.EnergyUtils;
import com.google.common.collect.Multimap;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MagisteelSwordItem extends SwordItem implements DyeableWeaponItem, Magisteel {
	private final float attackSpeed;

	public MagisteelSwordItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		this.attackSpeed = attackSpeedIn;
	}
	
	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!attacker.level.isClientSide && isUsingMana(stack) && !EnergyUtils.isDrained(stack, EnergyUtils.EnergyType.MAGIC_ENERGY) && (!(attacker instanceof Player) || !((Player)attacker).getAbilities().instabuild)) {
			EnergyUtils.addEnergyToItem(stack, -10, EnergyUtils.EnergyType.MAGIC_ENERGY);
		}
		return super.hurtEnemy(stack, target, attacker);
	}
	
	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	@Override
	public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
			if (!entityLiving.level.isClientSide && isUsingMana(stack) && !EnergyUtils.isDrained(stack, EnergyUtils.EnergyType.MAGIC_ENERGY) && (!(entityLiving instanceof Player) || !((Player)entityLiving).getAbilities().instabuild)) {
				EnergyUtils.addEnergyToItem(stack, -10, EnergyUtils.EnergyType.MAGIC_ENERGY);
			}
		}
		
		return super.mineBlock(stack, worldIn, state, pos, entityLiving);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (EnergyUtils.getEnergy(playerIn.getItemInHand(handIn), EnergyUtils.EnergyType.MAGIC_ENERGY) > 0) {
			switchActivity(playerIn.getItemInHand(handIn), playerIn);
			return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
		} else {
			return InteractionResultHolder.fail(playerIn.getItemInHand(handIn));
		}
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> attributeModifiers;
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.getDamage() * 2, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
		attributeModifiers = builder.build();


		return equipmentSlot == EquipmentSlot.MAINHAND && isUsingMana(stack) ? attributeModifiers : super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public int defaultColor(ItemStack stack) {
		return 0x57536B;
	}

	@Override
	public boolean usesDyeingRecipe() {
		return false;
	}

}
