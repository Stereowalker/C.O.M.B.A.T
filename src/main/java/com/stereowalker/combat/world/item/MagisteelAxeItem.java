package com.stereowalker.combat.world.item;

import java.util.Optional;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.stereowalker.combat.core.EnergyUtils;
import com.google.common.collect.Multimap;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class MagisteelAxeItem extends AxeItem implements Magisteel {

	private float attackSpeed;

	public MagisteelAxeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
		this.attackSpeed = attackSpeedIn;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (isUsingMana(stack)) {
			return state.is(this.blocks) ? this.speed * 2.0F : super.getDestroySpeed(stack, state) * 2.0F;
		} else {
			return super.getDestroySpeed(stack, state);
		}
	}

	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		if (isUsingMana(pContext.getItemInHand())) {
			Level level = pContext.getLevel();
		      BlockPos blockpos = pContext.getClickedPos();
		      Player player = pContext.getPlayer();
		      BlockState blockstate = level.getBlockState(blockpos);
		      Optional<BlockState> optional = Optional.ofNullable(blockstate.getToolModifiedState(pContext, net.minecraftforge.common.ToolActions.AXE_STRIP, false));
		      Optional<BlockState> optional1 = optional.isPresent() ? Optional.empty() : Optional.ofNullable(blockstate.getToolModifiedState(pContext, net.minecraftforge.common.ToolActions.AXE_SCRAPE, false));
		      Optional<BlockState> optional2 = optional.isPresent() || optional1.isPresent() ? Optional.empty() : Optional.ofNullable(blockstate.getToolModifiedState(pContext, net.minecraftforge.common.ToolActions.AXE_WAX_OFF, false));
		      ItemStack itemstack = pContext.getItemInHand();
		      Optional<BlockState> optional3 = Optional.empty();
		      if (optional.isPresent()) {
		         level.playSound(player, blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
		         optional3 = optional;
		      } else if (optional1.isPresent()) {
		         level.playSound(player, blockpos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
		         level.levelEvent(player, 3005, blockpos, 0);
		         optional3 = optional1;
		      } else if (optional2.isPresent()) {
		         level.playSound(player, blockpos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
		         level.levelEvent(player, 3004, blockpos, 0);
		         optional3 = optional2;
		      }

		      if (optional3.isPresent()) {
		         if (player instanceof ServerPlayer) {
		            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
		         }

		         level.setBlock(blockpos, optional3.get(), 11);
		         level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, optional3.get()));
				if (player != null) {
					EnergyUtils.addEnergyToItem(pContext.getItemInHand(), -10, EnergyUtils.EnergyType.MAGIC_ENERGY);
				}

				return InteractionResult.sidedSuccess(level.isClientSide);
			} else {
				return InteractionResult.PASS;
			}
		} else {
			return super.useOn(pContext);
		}
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
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", (double)this.getAttackDamage() * 2, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
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
