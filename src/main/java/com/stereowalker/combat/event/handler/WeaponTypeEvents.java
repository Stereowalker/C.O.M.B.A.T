package com.stereowalker.combat.event.handler;

import java.util.Random;
import java.util.UUID;

import com.stereowalker.combat.tags.ItemCTags;
import com.stereowalker.combat.util.UUIDS;
import com.stereowalker.combat.world.item.ItemFilters;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class WeaponTypeEvents {
	@SubscribeEvent
	public static void twoHandedWeaponUpdate(LivingUpdateEvent event) {
		AttributeModifier penalty = new AttributeModifier(UUIDS.TWO_HANDED_DEBUFF, "Two_Weapon_penalty", -0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL);
		boolean flag = false;
		if (event.getEntityLiving() instanceof Player) {
			Player player = (Player)event.getEntityLiving();
			if (ItemFilters.TWO_HANDED_WEAPONS.test(player.getMainHandItem())) {
				if (!player.getOffhandItem().isEmpty()) {
					if (!player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(penalty))
						player.getAttribute(Attributes.ATTACK_SPEED).addPermanentModifier(penalty);
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
		AttributeModifier bonus = new AttributeModifier(UUID.fromString("bc1fbf04-72ba-4e55-b52d-c9ec76c6535d"), "Curved_Blade_Bonus", 0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL);
		if (event.getTarget() instanceof LivingEntity && event.getEntityLiving() instanceof Player) {
			Player player = (Player)event.getEntityLiving();
			LivingEntity target = (LivingEntity)event.getTarget();
			if (player.getMainHandItem().is(ItemCTags.SINGLE_EDGE_CURVED_WEAPON) && !target.hasItemInSlot(EquipmentSlot.CHEST)) {
				if (!player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(bonus)) {
					player.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(bonus);
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
		AttributeModifier penalty = new AttributeModifier(UUID.fromString("cd9bb5e5-9df4-4afb-8c39-c25b07982cba"), "Two_Weapon_penalty", 0.25D, AttributeModifier.Operation.MULTIPLY_TOTAL);
		boolean flag = false;
		if (event.getEntityLiving() instanceof Player) {
			Player player = (Player)event.getEntityLiving();
			if (player.getMainHandItem().is(ItemCTags.DOUBLE_EDGE_STRAIGHT_WEAPON)) {
				if (player.getOffhandItem().isEmpty()) {
					if (!player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(penalty))
						player.getAttribute(Attributes.ATTACK_SPEED).addPermanentModifier(penalty);
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
		if (ItemFilters.HEAVY_WEAPONS.test(event.getPlayer().getMainHandItem())) {
			if (event.getTarget() instanceof LivingEntity) {
				Random rand = new Random();
				if (rand.nextInt(2) == 0) {
					double xRatio = event.getEntityLiving().getX() - event.getTarget().getX();
					double zRatio = event.getEntityLiving().getZ() - event.getTarget().getZ();
					((LivingEntity) event.getTarget()).knockback(1, xRatio, zRatio);
				}
			}
		}
	}


	@SubscribeEvent
	public static void singleEdgeWeaponUpdate(CriticalHitEvent event) {
		if (event.getPlayer().getMainHandItem().is(ItemCTags.EDGELESS_THRUSTING_WEAPON)) {
			if (event.isVanillaCritical()) {
				event.setDamageModifier(3.0F);
			}
		}
	}
}
