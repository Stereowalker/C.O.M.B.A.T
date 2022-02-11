package com.stereowalker.combat.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.stereowalker.combat.core.EnergyUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class LightSaberItem extends Item implements Vanishable, DyeableWeaponItem, Mythril {
	private final float attackDamage;
	/** Modifiers applied when the item is in the mainhand of a user. */
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	private final Multimap<Attribute, AttributeModifier> sheathedAttributeModifiers;

	public LightSaberItem(int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
		super(builderIn);
		this.attackDamage = (float)attackDamageIn;
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
		//Attribute Modifiers For when the light saber is off
		Builder<Attribute, AttributeModifier> builder2 = ImmutableMultimap.builder();
		builder2.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 0.0D, AttributeModifier.Operation.ADDITION));
		builder2.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", 0.0D, AttributeModifier.Operation.ADDITION));
		this.sheathedAttributeModifiers = builder2.build();
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return !player.isCreative() && isUsingEnergy(player.getMainHandItem());
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (EnergyUtils.getEnergy(playerIn.getItemInHand(handIn), EnergyUtils.EnergyType.DIVINE_ENERGY) > 0) {
			switchActivity(playerIn.getItemInHand(handIn), playerIn);
			return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
		} else {
			return InteractionResultHolder.fail(playerIn.getItemInHand(handIn));
		}
	}

	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (state.is(Blocks.COBWEB)) {
			return 15.0F;
		} else {
			Material material = state.getMaterial();
			return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
		}
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!attacker.level.isClientSide && (!(attacker instanceof Player) || !((Player)attacker).getAbilities().instabuild)) {
			EnergyUtils.addEnergyToItem(stack, -20, EnergyUtils.EnergyType.DIVINE_ENERGY);
		}
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
			if (!entityLiving.level.isClientSide && (!(entityLiving instanceof Player) || !((Player)entityLiving).getAbilities().instabuild)) {
				EnergyUtils.addEnergyToItem(stack, -20, EnergyUtils.EnergyType.DIVINE_ENERGY);
			}
		}

		return true;
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	public boolean canHarvestBlock(BlockState blockIn) {
		return blockIn.is(Blocks.COBWEB);
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? (isUsingEnergy(stack) ? this.attributeModifiers : this.sheathedAttributeModifiers) : super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public int defaultColor(ItemStack stack) {
		return 0x57536B;
	}

	@Override
	public boolean usesDyeingRecipe() {
		return true;
	}

	@Override
	public int getMaxEnergy() {
		return 1000;
	}
}