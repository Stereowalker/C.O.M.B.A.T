package com.stereowalker.combat.world.item;

import java.util.function.Consumer;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.projectile.ThrownSpear;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentHelper;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SpearItem extends Item implements Vanishable  {
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	public SpearItem(Item.Properties properties) {
		super(properties);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 8.0D, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)-2.9F, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	//   @OnlyIn(Dist.CLIENT)
	//   @Override
	//   public final net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer getTileEntityItemStackRenderer() {
	//	   return TileEntityItemStackRenderForCombat.instance;
	//   }

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.SPEAR;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	/**
	 * Returns true if this item has an enchantment glint. By default, this returns <code>stack.isItemEnchanted()</code>,
	 * but other items can override it (for instance, written books always return true).
	 *  
	 * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
	 * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
	 */
	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse button).
	 */
	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player) {
			Player entityplayer = (Player)entityLiving;
			int i = this.getUseDuration(stack) - timeLeft;
			if (i >= 10 - (CEnchantmentHelper.getShortThrowModifier(stack) * 2)) {
				if (!worldIn.isClientSide) {
					stack.hurtAndBreak(1, entityplayer, (p_220009_1_) -> {
						p_220009_1_.broadcastBreakEvent(entityplayer.getUsedItemHand());
					});
					ThrownSpear entitySpear = new ThrownSpear(worldIn, entityplayer, stack);
					entitySpear.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(), 0.0F, 2.5F + (float)0.5F, 1.0F);
					if (entityplayer.getAbilities().instabuild) {
						entitySpear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
					}

					worldIn.addFreshEntity(entitySpear);
					if (!entityplayer.getAbilities().instabuild) {
						entityplayer.getInventory().removeItem(stack);
					}
				}
				entityplayer.awardStat(Stats.ITEM_USED.get(this));
				SoundEvent soundevent = SoundEvents.SNOWBALL_THROW;
				worldIn.playSound((Player)null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
			}
		}
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
			return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
		} else if (EnchantmentHelper.getRiptide(itemstack) > 0 && !playerIn.isInWaterOrRain()) {
			return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
		} else {
			playerIn.startUsingItem(handIn);
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
		}
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (p_220009_1_) -> {
			p_220009_1_.broadcastBreakEvent(attacker.getUsedItemHand());
		});
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	@Override
	public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if ((double)state.getDestroySpeed(worldIn, pos) != 0.0D) {
			stack.hurtAndBreak(2, entityLiving, (p_220009_1_) -> {
				p_220009_1_.broadcastBreakEvent(entityLiving.getUsedItemHand());
			});
		}

		return true;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	@Override
	public int getEnchantmentValue() {
		return 1;
	}
	
	//FORGE
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return Combat.itemStackRender;
			}
		});
	}
}