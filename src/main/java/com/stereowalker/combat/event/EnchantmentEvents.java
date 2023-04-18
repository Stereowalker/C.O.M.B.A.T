package com.stereowalker.combat.event;

import java.util.Map.Entry;
import java.util.UUID;

import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.world.damagesource.CDamageSource;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.world.item.enchantment.CEnchantments;
import com.stereowalker.unionlib.api.insert.InsertCanceller;
import com.stereowalker.unionlib.util.EntityHelper;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ThornsEnchantment;
import net.minecraft.world.phys.Vec3;

public class EnchantmentEvents {
	public static void enchantmentIceAspect(Player player, Entity target, InsertCanceller canceller) {
		int i = CEnchantmentHelper.getIceAspectModifier(player);

		if (i > 0) {
			SpellUtil.setIce(target, i * 4);
		}
	}

	public static void enchantmentRestoring(LivingEntity living) {
		if (living instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer)living;
			Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(CEnchantments.RESTORING, player);
			if (entry != null) {
				ItemStack itemstack = entry.getValue();
				if (!itemstack.isEmpty() && itemstack.isDamaged()) {
					int i = Math.min((int)(EntityHelper.getActualExperienceTotal(player) * itemstack.getXpRepairRatio()), itemstack.getDamageValue());
					player.giveExperiencePoints(-durabilityToXp(i));
					itemstack.setDamageValue(itemstack.getDamageValue() - i);
				}
			}
		}
	}

	public static void enchantmentClone(Player thisPlayer, Player thatPlayer, boolean keepEverything) {
		for(int i = 0; i < thatPlayer.getInventory().getContainerSize(); ++i) {
			if (CEnchantmentHelper.hasRetaining(thatPlayer.getInventory().getItem(i))) {
				thisPlayer.getInventory().setItem(i, thatPlayer.getInventory().getItem(i));
			}
		}
	}

	private static int durabilityToXp(int durability) {
		return durability / 2;
	}

	//	private static int xpToDurability(int xp) {
	//		return xp * 2;
	//	}

	public static void enchantmentQuickSwing(LivingEntity living) {
		if (living instanceof Player) {
			Player player = (Player)living;
			int level = EnchantmentHelper.getEnchantmentLevel(CEnchantments.QUICK_DRAW, player);
			AttributeModifier bonus = new AttributeModifier(UUID.fromString("16b2d169-b860-48e6-98dd-5fa801bb3b71"), "Quick_Draw_Bonus", level, AttributeModifier.Operation.ADDITION);
			if (level > 0) {
				if (!player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(bonus))
					player.getAttribute(Attributes.ATTACK_SPEED).addPermanentModifier(bonus);
				if (player.getAttribute(Attributes.ATTACK_SPEED).getModifier(UUID.fromString("16b2d169-b860-48e6-98dd-5fa801bb3b71")).getAmount() != bonus.getAmount()) {
					player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(UUID.fromString("16b2d169-b860-48e6-98dd-5fa801bb3b71"));
					player.getAttribute(Attributes.ATTACK_SPEED).addPermanentModifier(bonus);
				}
			}
			else {
				if (player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(bonus))
					player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(bonus);
			}
		}
	}

	public static void enchantmentBurningSpikes(LivingEntity living, DamageSource source, float amount) {
		Entity attacker = source.getEntity();
		if (amount > 0.0F && canBlockDamageSource(living, source)) {
			RandomSource random = living.getRandom();
			Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(CEnchantments.BURNING_SPIKES, living);
			int level = EnchantmentHelper.getEnchantmentLevel(CEnchantments.BURNING_SPIKES, living);
			if (ThornsEnchantment.shouldHit(level, random)) {
				if (attacker != null) {
					attacker.setSecondsOnFire(level * 40);
					attacker.hurt(CDamageSource.causeBurningThornsDamage(living), (float)ThornsEnchantment.getDamage(level, random));
				}

				if (entry != null) {
					entry.getValue().hurtAndBreak(3, living, (p_222183_1_) -> {
						p_222183_1_.broadcastBreakEvent(entry.getKey());
					});
				}
			}
		}
	}

	public static void enchantmentSpikes(LivingEntity living, DamageSource source, float amount) {
		Entity attacker = source.getEntity();
		if (amount > 0.0F && canBlockDamageSource(living, source)) {
			RandomSource random = living.getRandom();
			Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(CEnchantments.SPIKES, living);
			int level = EnchantmentHelper.getEnchantmentLevel(CEnchantments.SPIKES, living);
			if (ThornsEnchantment.shouldHit(level, random)) {
				if (attacker != null) {
					attacker.hurt(DamageSource.thorns(living), (float)ThornsEnchantment.getDamage(level, random));
				}

				if (entry != null) {
					entry.getValue().hurtAndBreak(3, living, (p_222183_1_) -> {
						p_222183_1_.broadcastBreakEvent(entry.getKey());
					});
				}
			}
		}
	}

	public static void enchantmentAbsorption(LivingEntity living, DamageSource source, float amount) {
		Entity attacker = source.getEntity();
		if (amount > 0.0F && canBlockDamageSource(living, source)) {
			RandomSource random = living.getRandom();
			Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(CEnchantments.SPIKES, living);
			int level = EnchantmentHelper.getEnchantmentLevel(CEnchantments.SPIKES, living);
			if (ThornsEnchantment.shouldHit(level, random)) {
				if (attacker != null) {
					attacker.hurt(DamageSource.thorns(living), (float)ThornsEnchantment.getDamage(level, random));
				}

				if (entry != null) {
					entry.getValue().hurtAndBreak(3, living, (p_222183_1_) -> {
						p_222183_1_.broadcastBreakEvent(entry.getKey());
					});
				}
			}
		}
	}

	/**
	 * Determines whether the entity can block the damage source based on the damage source's location, whether the
	 * damage source is blockable, and whether the entity is blocking.
	 */
	private static boolean canBlockDamageSource(LivingEntity defender, DamageSource damageSourceIn) {
		Entity entity = damageSourceIn.getDirectEntity();
		boolean flag = false;
		if (entity instanceof AbstractArrow) {
			AbstractArrow abstractarrowentity = (AbstractArrow)entity;
			if (abstractarrowentity.getPierceLevel() > 0) {
				flag = true;
			}
		}

		if (!damageSourceIn.isBypassArmor() && defender.isBlocking() && !flag) {
			Vec3 vec3d2 = damageSourceIn.getSourcePosition();
			if (vec3d2 != null) {
				Vec3 vec3d = defender.getViewVector(1.0F);
				Vec3 vec3d1 = vec3d2.vectorTo(defender.position()).normalize();
				vec3d1 = new Vec3(vec3d1.x, 0.0D, vec3d1.z);
				if (vec3d1.dot(vec3d) < 0.0D) {
					return true;
				}
			}
		}

		return false;
	}
}
