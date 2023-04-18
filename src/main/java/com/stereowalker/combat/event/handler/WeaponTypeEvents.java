package com.stereowalker.combat.event.handler;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Triple;

import com.google.common.collect.Maps;
import com.stereowalker.combat.tags.ItemCTags;
import com.stereowalker.combat.util.UUIDS;
import com.stereowalker.combat.world.item.ItemFilters;
import com.stereowalker.unionlib.api.insert.InsertCanceller;
import com.stereowalker.unionlib.world.entity.ai.UAttributes;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class WeaponTypeEvents {
	public static void weaponUpdate(LivingEntity living) {
		Map<AttributeModifier, Triple<Attribute, Function<ItemStack,Boolean>, Function<Player,Boolean>>> A = Maps.newHashMap();
		A.put(new AttributeModifier(UUIDS.TWO_HANDED_DEBUFF, "Two_Weapon_penalty", -0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL),
				Triple.of(Attributes.ATTACK_SPEED, 
				ItemFilters.TWO_HANDED_WEAPONS::test,
				(x)->!x.getOffhandItem().isEmpty()));
		
		A.put(new AttributeModifier(UUIDS.TWO_HANDED_BUFF, "Two_Weapon_buff", 0.25D, AttributeModifier.Operation.MULTIPLY_TOTAL),
				Triple.of(Attributes.ATTACK_SPEED, 
				(x)->x.is(ItemCTags.DOUBLE_EDGE_STRAIGHT_WEAPON),
				(x)->!x.getOffhandItem().isEmpty()));
		
		A.put(new AttributeModifier(UUIDS.EDGELESS_BUFF_CR, "pointed_weapon_buff_critrate", 0.1D, AttributeModifier.Operation.ADDITION),
				Triple.of(UAttributes.CRITICAL_RATE, 
				(x)->x.is(ItemCTags.EDGELESS_THRUSTING_WEAPON),
				(x)->true));
		
		A.put(new AttributeModifier(UUIDS.EDGELESS_BUFF_CD, "pointed_weapon_buff_critdmg", 0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL),
				Triple.of(UAttributes.CRITICAL_DAMAGE, 
				(x)->x.is(ItemCTags.EDGELESS_THRUSTING_WEAPON),
				(x)->true));
		
		
		if (living instanceof Player) {
			Player player = (Player)living;
			A.forEach((x,v)->{
				boolean flag = false;
				if (v.getMiddle().apply(player.getMainHandItem())) {
					if (v.getRight().apply(player)) {
						if (!player.getAttribute(v.getLeft()).hasModifier(x))
							player.getAttribute(v.getLeft()).addPermanentModifier(x);
					} else {
						flag = true;
					}
				} else {
					flag = true;
				}
				if(flag) {
					if (player.getAttribute(v.getLeft()).hasModifier(x))
						player.getAttribute(v.getLeft()).removeModifier(x);
				}
			});
		}
	}

	public static void singleEdgeCurvedWeaponUpdate(Player player, Entity target, InsertCanceller canceller) {
		AttributeModifier bonus = new AttributeModifier(UUID.fromString("bc1fbf04-72ba-4e55-b52d-c9ec76c6535d"), "Curved_Blade_Bonus", 0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL);
		if (target instanceof LivingEntity) {
			LivingEntity ltarget = (LivingEntity)target;
			if (player.getMainHandItem().is(ItemCTags.SINGLE_EDGE_CURVED_WEAPON) && !ltarget.hasItemInSlot(EquipmentSlot.CHEST)) {
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

	public static void heavyWeaponUpdate(Player player, Entity target, InsertCanceller canceller) {
		if (ItemFilters.HEAVY_WEAPONS.test(player.getMainHandItem())) {
			if (target instanceof LivingEntity) {
				Random rand = new Random();
				if (rand.nextInt(2) == 0) {
					double xRatio = player.getX() - target.getX();
					double zRatio = player.getZ() - target.getZ();
					((LivingEntity) target).knockback(1, xRatio, zRatio);
				}
			}
		}
	}
}
