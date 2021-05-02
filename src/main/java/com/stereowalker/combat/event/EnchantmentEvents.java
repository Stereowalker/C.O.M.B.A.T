package com.stereowalker.combat.event;

import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.enchantment.CEnchantments;
import com.stereowalker.combat.util.CDamageSource;
import com.stereowalker.unionlib.util.EntityHelper;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class EnchantmentEvents {
	@SubscribeEvent
	public static void enchantmentIceAspect(AttackEntityEvent event) {
		int i = CEnchantmentHelper.getIceAspectModifier(event.getEntityLiving());

		if (i > 0) {
			SpellUtil.setIce(event.getTarget(), i * 4);
		}
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void enchantmentMagmaWalker(LivingUpdateEvent event) 
	{	
		LivingEntity living = event.getEntityLiving();
		int level = EnchantmentHelper.getMaxEnchantmentLevel(CEnchantments.MAGMA_WALKER, living);
		BlockPos pos = living.getPosition();

		if(level > 0)
		{
			World worldIn = event.getEntity().world;
			if (living.isOnGround()) {
				BlockState blockstate = Blocks.MAGMA_BLOCK.getDefaultState();
				float f = (float)Math.min(16, 2 + level);
				BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

				for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add((double)(-f), -1.0D, (double)(-f)), pos.add((double)f, -1.0D, (double)f))) {
					if (blockpos.withinDistance(living.getPositionVec(), (double)f)) {
						blockpos$mutableblockpos.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
						BlockState blockstate1 = worldIn.getBlockState(blockpos$mutableblockpos);
						if (blockstate1.isAir(worldIn, blockpos$mutableblockpos)) {
							BlockState blockstate2 = worldIn.getBlockState(blockpos);
							boolean isFull = blockstate2.getBlock() == Blocks.LAVA && blockstate2.get(FlowingFluidBlock.LEVEL) == 0; //TODO: Forge, modded lava?
							if (blockstate2.getMaterial() == Material.LAVA && isFull && blockstate.isValidPosition(worldIn, blockpos) && worldIn.placedBlockCollides(blockstate, blockpos, ISelectionContext.dummy()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(living, net.minecraftforge.common.util.BlockSnapshot.create(worldIn.getDimensionKey(), worldIn, blockpos), net.minecraft.util.Direction.UP)) {
								worldIn.setBlockState(blockpos, blockstate);
								worldIn.getPendingBlockTicks().scheduleTick(blockpos, Blocks.MAGMA_BLOCK, MathHelper.nextInt(living.getRNG(), 60, 120));
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void enchantmentRestoring(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)event.getEntityLiving();
			Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWithEnchantment(CEnchantments.RESTORING, player);
			if (entry != null) {
				ItemStack itemstack = entry.getValue();
				if (!itemstack.isEmpty() && itemstack.isDamaged()) {
					int i = Math.min((int)(EntityHelper.getActualExperienceTotal(player) * itemstack.getXpRepairRatio()), itemstack.getDamage());
					player.giveExperiencePoints(-durabilityToXp(i));
					itemstack.setDamage(itemstack.getDamage() - i);
				}
			}
		}
	}

	@SubscribeEvent
	public static void enchantmentClone(Clone event) {
		PlayerEntity oldPlayer = event.getOriginal();
		PlayerEntity newPlayer = event.getPlayer();
		for(int i = 0; i < oldPlayer.inventory.getSizeInventory(); ++i) {
			if (CEnchantmentHelper.hasRetaining(oldPlayer.inventory.getStackInSlot(i))) {
				newPlayer.inventory.setInventorySlotContents(i, oldPlayer.inventory.getStackInSlot(i));
			}
		}
	}

	private static int durabilityToXp(int durability) {
		return durability / 2;
	}

	//	private static int xpToDurability(int xp) {
	//		return xp * 2;
	//	}

	@SubscribeEvent
	public static void enchantmentQuickSwing(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntityLiving();
			int level = EnchantmentHelper.getMaxEnchantmentLevel(CEnchantments.QUICK_DRAW, player);
			AttributeModifier bonus = new AttributeModifier(UUID.fromString("16b2d169-b860-48e6-98dd-5fa801bb3b71"), "Quick_Draw_Bonus", level, Operation.ADDITION);
			if (level > 0) {
				if (!player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(bonus))
					player.getAttribute(Attributes.ATTACK_SPEED).applyPersistentModifier(bonus);
				if (player.getAttribute(Attributes.ATTACK_SPEED).getModifier(UUID.fromString("16b2d169-b860-48e6-98dd-5fa801bb3b71")).getAmount() != bonus.getAmount()) {
					player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(UUID.fromString("16b2d169-b860-48e6-98dd-5fa801bb3b71"));
					player.getAttribute(Attributes.ATTACK_SPEED).applyPersistentModifier(bonus);
				}
			}
			else {
				if (player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(bonus))
					player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(bonus);
			}
		}
	}

	@SubscribeEvent
	public static void enchantmentBurningSpikes(LivingAttackEvent event) {
		Entity attacker = event.getSource().getTrueSource();
		LivingEntity user = event.getEntityLiving();
		if (event.getAmount() > 0.0F && canBlockDamageSource(event.getEntityLiving(), event.getSource())) {
			Random random = user.getRNG();
			Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWithEnchantment(CEnchantments.BURNING_SPIKES, user);
			int level = EnchantmentHelper.getMaxEnchantmentLevel(CEnchantments.BURNING_SPIKES, user);
			if (shouldHit(level, random)) {
				Combat.debug("RETALIATE");
				if (attacker != null) {
					attacker.setFire(level * 40);
					attacker.attackEntityFrom(CDamageSource.causeBurningThornsDamage(user), (float)getDamage(level, random));
				}

				if (entry != null) {
					entry.getValue().damageItem(3, user, (p_222183_1_) -> {
						p_222183_1_.sendBreakAnimation(entry.getKey());
					});
				}
			}
		}
	}

	@SubscribeEvent
	public static void enchantmentSpikes(LivingAttackEvent event) {
		Entity attacker = event.getSource().getTrueSource();
		LivingEntity user = event.getEntityLiving();
		if (event.getAmount() > 0.0F && canBlockDamageSource(event.getEntityLiving(), event.getSource())) {
			Random random = user.getRNG();
			Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWithEnchantment(CEnchantments.SPIKES, user);
			int level = EnchantmentHelper.getMaxEnchantmentLevel(CEnchantments.SPIKES, user);
			if (shouldHit(level, random)) {
				if (attacker != null) {
					attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float)getDamage(level, random));
				}

				if (entry != null) {
					entry.getValue().damageItem(3, user, (p_222183_1_) -> {
						p_222183_1_.sendBreakAnimation(entry.getKey());
					});
				}
			}
		}
	}

	public static boolean shouldHit(int level, Random rnd) {
		if (level <= 0) {
			return false;
		} else {
			return rnd.nextFloat() < 0.33F * (float)level;
		}
	}

	public static int getDamage(int level, Random rnd) {
		return level > 10 ? level - 10 : 5 + rnd.nextInt(8);
	}

	/**
	 * Determines whether the entity can block the damage source based on the damage source's location, whether the
	 * damage source is blockable, and whether the entity is blocking.
	 */
	private static boolean canBlockDamageSource(LivingEntity defender, DamageSource damageSourceIn) {
		Entity entity = damageSourceIn.getImmediateSource();
		boolean flag = false;
		if (entity instanceof AbstractArrowEntity) {
			AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)entity;
			if (abstractarrowentity.getPierceLevel() > 0) {
				flag = true;
			}
		}

		if (!damageSourceIn.isUnblockable() && defender.isActiveItemStackBlocking() && !flag) {
			Vector3d vec3d2 = damageSourceIn.getDamageLocation();
			if (vec3d2 != null) {
				Vector3d vec3d = defender.getLook(1.0F);
				Vector3d vec3d1 = vec3d2.subtractReverse(defender.getPositionVec()).normalize();
				vec3d1 = new Vector3d(vec3d1.x, 0.0D, vec3d1.z);
				if (vec3d1.dotProduct(vec3d) < 0.0D) {
					return true;
				}
			}
		}

		return false;
	}
}
