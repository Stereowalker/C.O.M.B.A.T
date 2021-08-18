package com.stereowalker.combat.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MythrilAxeItem extends AxeItem implements IMythrilItem {

	private float attackSpeed;

	public MythrilAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
		this.attackSpeed = attackSpeedIn;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return isUsingEnergy(stack) ? super.getDestroySpeed(stack, state) * 2.0F : super.getDestroySpeed(stack, state);
	}

	/**
	 * Called when this item is used when targetting a Block
	 */
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (isUsingEnergy(context.getItem())) {
			World world = context.getWorld();
			BlockPos blockpos = context.getPos();
			BlockState blockstate = world.getBlockState(blockpos);
			BlockState block = blockstate.getToolModifiedState(world, blockpos, context.getPlayer(), context.getItem(), net.minecraftforge.common.ToolType.AXE);
			if (block != null) {
				PlayerEntity playerentity = context.getPlayer();
				world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!world.isRemote) {
					world.setBlockState(blockpos, block, 11);
					if (playerentity != null) {
						EnergyUtils.addEnergyToItem(context.getItem(), -10, EnergyType.DIVINE_ENERGY);
					}
				}

				return ActionResultType.func_233537_a_(world.isRemote);
			} else {
				return ActionResultType.PASS;
			}
		} else {
			return super.onItemUse(context);
		}
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> attributeModifiers;
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.getAttackDamage() * 2, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
		attributeModifiers = builder.build();


		return equipmentSlot == EquipmentSlotType.MAINHAND && isUsingEnergy(stack) ? attributeModifiers : super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public int getMaxEnergy() {
		return 5000;
	}
}
