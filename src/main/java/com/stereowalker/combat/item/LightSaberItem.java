package com.stereowalker.combat.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightSaberItem extends Item implements IVanishable, IDyeableWeaponItem, IMythrilItem {
	private final float attackDamage;
	/** Modifiers applied when the item is in the mainhand of a user. */
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	private final Multimap<Attribute, AttributeModifier> sheathedAttributeModifiers;

	public LightSaberItem(int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
		super(builderIn);
		this.attackDamage = (float)attackDamageIn;
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
		//Attribute Modifiers For when the light saber is off
		Builder<Attribute, AttributeModifier> builder2 = ImmutableMultimap.builder();
		builder2.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0.0D, AttributeModifier.Operation.ADDITION));
		builder2.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 0.0D, AttributeModifier.Operation.ADDITION));
		this.sheathedAttributeModifiers = builder2.build();
	}

	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative() && isUsingEnergy(player.getHeldItemMainhand());
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (EnergyUtils.getEnergy(playerIn.getHeldItem(handIn), EnergyType.DIVINE_ENERGY) > 0) {
			switchActivity(playerIn.getHeldItem(handIn), playerIn);
			return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
		} else {
			return ActionResult.resultFail(playerIn.getHeldItem(handIn));
		}
	}

	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (state.matchesBlock(Blocks.COBWEB)) {
			return 15.0F;
		} else {
			Material material = state.getMaterial();
			return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
		}
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!attacker.world.isRemote && (!(attacker instanceof PlayerEntity) || !((PlayerEntity)attacker).abilities.isCreativeMode)) {
			EnergyUtils.addEnergyToItem(stack, -20, EnergyType.DIVINE_ENERGY);
		}
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getBlockHardness(worldIn, pos) != 0.0F) {
			if (!entityLiving.world.isRemote && (!(entityLiving instanceof PlayerEntity) || !((PlayerEntity)entityLiving).abilities.isCreativeMode)) {
				EnergyUtils.addEnergyToItem(stack, -20, EnergyType.DIVINE_ENERGY);
			}
		}

		return true;
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	public boolean canHarvestBlock(BlockState blockIn) {
		return blockIn.matchesBlock(Blocks.COBWEB);
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? (isUsingEnergy(stack) ? this.attributeModifiers : this.sheathedAttributeModifiers) : super.getAttributeModifiers(equipmentSlot, stack);
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