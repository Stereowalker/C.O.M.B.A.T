package com.stereowalker.combat.event.handler;

import java.util.Random;
import java.util.UUID;

import com.stereowalker.combat.item.ItemFilters;
import com.stereowalker.combat.util.UUIDS;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class WeaponTypeEvents {
	@SubscribeEvent
	public static void twoHandedWeaponUpdate(LivingUpdateEvent event) {
		AttributeModifier penalty = new AttributeModifier(UUIDS.TWO_HANDED_DEBUFF, "Two_Weapon_penalty", -0.5D, Operation.MULTIPLY_TOTAL);
		boolean flag = false;
		if (event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntityLiving();
			if (ItemFilters.TWO_HANDED_WEAPONS.test(player.getHeldItemMainhand())) {
				if (!player.getHeldItemOffhand().isEmpty()) {
					if (!player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(penalty))
						player.getAttribute(Attributes.ATTACK_SPEED).applyPersistentModifier(penalty);
				} else {
					flag = true;
				}
			} else {
				flag = true;
			}
			if(flag) {
				if (player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(penalty))
					player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(penalty);
			}
		}
	}

	@SubscribeEvent
	public static void singleEdgeCurvedWeaponUpdate(AttackEntityEvent event) {
		AttributeModifier bonus = new AttributeModifier(UUID.fromString("bc1fbf04-72ba-4e55-b52d-c9ec76c6535d"), "Curved_Blade_Bonus", 0.5D, Operation.MULTIPLY_TOTAL);
		if (event.getTarget() instanceof LivingEntity && event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntityLiving();
			LivingEntity target = (LivingEntity)event.getTarget();
			if (ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(player.getHeldItemMainhand()) && !target.hasItemInSlot(EquipmentSlotType.CHEST)) {
				if (!player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(bonus)) {
					player.getAttribute(Attributes.ATTACK_DAMAGE).applyPersistentModifier(bonus);
				}
			}
			else {
				if (player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(bonus))
					player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(bonus);
			}
		}
	}

	@SubscribeEvent
	public static void doubleEdgeStraightWeaponUpdate(LivingUpdateEvent event) {
		AttributeModifier penalty = new AttributeModifier(UUID.fromString("cd9bb5e5-9df4-4afb-8c39-c25b07982cba"), "Two_Weapon_penalty", 0.25D, Operation.MULTIPLY_TOTAL);
		boolean flag = false;
		if (event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntityLiving();
			if (ItemFilters.DOUBLE_EDGE_STRAIGHT_WEAPONS.test(player.getHeldItemMainhand())) {
				if (player.getHeldItemOffhand().isEmpty()) {
					if (!player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(penalty))
						player.getAttribute(Attributes.ATTACK_SPEED).applyPersistentModifier(penalty);
				} else {
					flag = true;
				}
			} else {
				flag = true;
			}
			if(flag) {
				if (player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(penalty))
					player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(penalty);
			}
		}
	}

	@SubscribeEvent
	public static void heavyWeaponUpdate(AttackEntityEvent event) {
		if (ItemFilters.HEAVY_WEAPONS.test(event.getPlayer().getHeldItemMainhand())) {
			if (event.getTarget() instanceof LivingEntity) {
				Random rand = new Random();
				if (rand.nextInt(2) == 0) {
					double xRatio = event.getEntityLiving().getPosX() - event.getTarget().getPosX();
					double zRatio = event.getEntityLiving().getPosZ() - event.getTarget().getPosZ();
					((LivingEntity) event.getTarget()).applyKnockback(1, xRatio, zRatio);
				}
			}
		}
	}


	@SubscribeEvent
	public static void singleEdgeWeaponUpdate(CriticalHitEvent event) {
		if (ItemFilters.EDGELESS_THRUSTING_WEAPONS.test(event.getPlayer().getHeldItemMainhand())) {
			if (event.isVanillaCritical()) {
				event.setDamageModifier(3.0F);
			}
		}
	}
}
