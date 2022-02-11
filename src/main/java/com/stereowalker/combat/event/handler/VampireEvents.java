package com.stereowalker.combat.event.handler;

import java.util.Random;
import java.util.UUID;

import com.stereowalker.combat.world.damagesource.CDamageSource;
import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.monster.Vampire;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.DaggerItem;
import com.stereowalker.combat.world.item.enchantment.CEnchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class VampireEvents {
	public static boolean isVampire(LivingEntity entity) {
		return CombatEntityStats.isVampire(entity) || entity instanceof Vampire || entity.hasEffect(CMobEffects.VAMPIRISM);
	}
	
	
	@SubscribeEvent
	public static void vampireFall(LivingFallEvent event) {
		if (isVampire(event.getEntityLiving())) {
			event.setDistance(event.getDistance()/2);
			event.setDamageMultiplier(event.getDamageMultiplier()/2);
		}
	}

	@SubscribeEvent
	public static void vampireTick(LivingUpdateEvent event) {
		LivingEntity living = event.getEntityLiving();
		AttributeModifier vampireSpeed = new AttributeModifier(UUID.fromString("57faea4b-d5fc-463a-b151-5f087a1f4f1c"), "Vampire_Speed", 1.0D, AttributeModifier.Operation.MULTIPLY_BASE);
		AttributeModifier vampireArmor = new AttributeModifier(UUID.fromString("5faa4365-b839-4ff2-aa07-ef5dbe98c383"), "Vampire_Armor", 1.0D, AttributeModifier.Operation.MULTIPLY_BASE);
		AttributeModifier vampireAttSpeed = new AttributeModifier(UUID.fromString("f5941d33-c1a3-48da-9726-58a386602740"), "Vampire_Att_Speed", 1.0D, AttributeModifier.Operation.MULTIPLY_BASE);
		AttributeModifier vampireHealth = new AttributeModifier(UUID.fromString("90ddb8ba-1be4-4b07-b31b-1c2e056b7041"), "Vampire_Health", 1.0D, AttributeModifier.Operation.MULTIPLY_BASE);
		AttributeModifier vampireKnoRes = new AttributeModifier(UUID.fromString("2946a5e3-b2dc-405e-90d6-892617a1225d"), "Vampire_Kno_Res", 1.0D, AttributeModifier.Operation.MULTIPLY_BASE);
		AttributeModifier vampireDamage = new AttributeModifier(UUID.fromString("cd9bb5e5-9df4-4afb-8c39-c25b07982cba"), "Vampire_Damage", 1.0D, AttributeModifier.Operation.MULTIPLY_BASE);
		if (CombatEntityStats.isVampire(living)) {
			if (!living.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(vampireSpeed))
				living.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(vampireSpeed);
			if (!living.getAttribute(Attributes.ARMOR).hasModifier(vampireArmor))
				living.getAttribute(Attributes.ARMOR).addPermanentModifier(vampireArmor);
			if (!living.getAttribute(Attributes.ATTACK_SPEED).hasModifier(vampireAttSpeed))
				living.getAttribute(Attributes.ATTACK_SPEED).addPermanentModifier(vampireAttSpeed);
			if (!living.getAttribute(Attributes.MAX_HEALTH).hasModifier(vampireHealth))
				living.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(vampireHealth);
			if (!living.getAttribute(Attributes.KNOCKBACK_RESISTANCE).hasModifier(vampireKnoRes))
				living.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(vampireKnoRes);
			if (!living.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(vampireDamage))
				living.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(vampireDamage);
			Random rand = new Random();
			int level = EnchantmentHelper.getEnchantmentLevel(CEnchantments.SUN_SHIELD, living);
			living.addEffect(new MobEffectInstance(MobEffects.JUMP, 20, 1, false, false, false));
			if (living.isAlive()) {
				boolean flag = (isInDaylight(living) && level == 0) || living.getY() < 0;
				if (flag) {
					ItemStack itemstack = living.getItemBySlot(EquipmentSlot.HEAD);
					if (!itemstack.isEmpty()) {
						if (itemstack.isDamageableItem()) {
							itemstack.setDamageValue(itemstack.getDamageValue() + rand.nextInt(2));
							if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
								living.broadcastBreakEvent(EquipmentSlot.HEAD);
								living.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
							}
						}

						flag = false;
					}
					if ((living instanceof Player && !((Player)living).getAbilities().instabuild) || !(living instanceof Player)) {
						living.setSecondsOnFire(8);
						living.hurt(CDamageSource.SUNBURNED, 20.0F);
					}
				} else {
					if (living.tickCount%10 == 0) living.heal(0.5f);
				}
			}
		} else {
			if(living.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED))
				if (living.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(vampireSpeed.getId()) != null)
					if (living.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(vampireSpeed))
						living.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(vampireSpeed);
			if(living.getAttributes().hasAttribute(Attributes.ARMOR))
				if (living.getAttribute(Attributes.ARMOR).getModifier(vampireArmor.getId()) != null)
					if (living.getAttribute(Attributes.ARMOR).hasModifier(vampireArmor))
						living.getAttribute(Attributes.ARMOR).removeModifier(vampireArmor);
			if(living.getAttributes().hasAttribute(Attributes.ATTACK_SPEED))
				if (living.getAttribute(Attributes.ATTACK_SPEED).getModifier(vampireAttSpeed.getId()) != null)
					if (living.getAttribute(Attributes.ATTACK_SPEED).hasModifier(vampireAttSpeed))
						living.getAttribute(Attributes.ATTACK_SPEED).removeModifier(vampireAttSpeed);
			if(living.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				if (living.getAttribute(Attributes.MAX_HEALTH).getModifier(vampireHealth.getId()) != null)
					if (living.getAttribute(Attributes.MAX_HEALTH).hasModifier(vampireHealth))
						living.getAttribute(Attributes.MAX_HEALTH).removeModifier(vampireHealth);
			if(living.getAttributes().hasAttribute(Attributes.KNOCKBACK_RESISTANCE))
				if (living.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getModifier(vampireKnoRes.getId()) != null)
					if (living.getAttribute(Attributes.KNOCKBACK_RESISTANCE).hasModifier(vampireKnoRes))
						living.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(vampireKnoRes);
			if(living.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				if (living.getAttribute(Attributes.ATTACK_DAMAGE).getModifier(vampireDamage.getId()) != null)
					if (living.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(vampireDamage))
						living.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(vampireDamage);
		}
	}

	@SubscribeEvent
	public static void vampireEatFood(LivingEntityUseItemEvent.Finish event) {
		if (event.getResultStack().isEdible()) {
			if (CombatEntityStats.isVampire(event.getEntityLiving())) {
				if (event.getResultStack().getItem() != CItems.BLOOD) {
					event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1200));
					event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.POISON, 1200, 10));
				} else {
					((Player)event.getEntityLiving()).getFoodData().eat(20, 10.0F);
				}
			}
		}

	}
	
	@SubscribeEvent
	public static void vampireEatEntities(AttackEntityEvent event) {
		if (isVampire(event.getEntityLiving())) {
			if (event.getTarget() instanceof LivingEntity && event.getEntityLiving() instanceof Player) {
				if (!((LivingEntity)event.getTarget()).getMobType().equals(MobType.UNDEAD)) {
					((Player)event.getEntityLiving()).getFoodData().eat(2, 1.0F);
				}
			}
		}
	}
	public boolean processInteract(Player player, InteractionHand hand) {
		ItemStack bottle = player.getItemInHand(InteractionHand.OFF_HAND);
		ItemStack dagger = player.getItemInHand(InteractionHand.MAIN_HAND);
		if (bottle.getItem() == Items.GLASS_BOTTLE && dagger.getItem() instanceof DaggerItem) {
			if (!player.getAbilities().instabuild) {
				bottle.shrink(1);
			}
			player.addItem(new ItemStack(CItems.VAMPIRE_BLOOD));
			return true;
		} else {
			return false;
		}
	}

	@SubscribeEvent
	public static void woodenToolAttack(LivingAttackEvent event) {
		LivingEntity living = event.getEntityLiving();
		if (isVampire(living)) {
			if(event.getSource().getEntity() instanceof LivingEntity) {
				if(((LivingEntity)event.getSource().getEntity()).getMainHandItem().sameItem(new ItemStack(Items.WOODEN_SWORD)) ||
						((LivingEntity)event.getSource().getEntity()).getMainHandItem().sameItem(new ItemStack(Items.WOODEN_AXE)) ||
						((LivingEntity)event.getSource().getEntity()).getMainHandItem().sameItem(new ItemStack(Items.WOODEN_HOE)) ||
						((LivingEntity)event.getSource().getEntity()).getMainHandItem().sameItem(new ItemStack(Items.WOODEN_PICKAXE)) ||
						((LivingEntity)event.getSource().getEntity()).getMainHandItem().sameItem(new ItemStack(Items.WOODEN_SHOVEL)) ||
						((LivingEntity)event.getSource().getEntity()).getMainHandItem().sameItem(new ItemStack(CItems.WOODEN_DAGGER))) {
					living.setSecondsOnFire(60);
					living.hurt(CDamageSource.STAKED, 1.0F);
				}
			}
		}
	}

	protected static boolean isInDaylight(LivingEntity mob) {
		Random rand = new Random();
		if (!mob.level.isClientSide && mob.level.isDay()) {
			float f = mob.getBrightness();
			BlockPos blockpos = mob.getVehicle() instanceof Boat ? (new BlockPos(mob.getX(), (double)Math.round(mob.getY()), mob.getZ())).above() : new BlockPos(mob.getX(), (double)Math.round(mob.getY()), mob.getZ());
			if (f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && mob.level.canSeeSky(blockpos)) {
				return true;
			}
		}

		return false;
	}
}
