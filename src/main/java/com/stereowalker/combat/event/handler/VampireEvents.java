package com.stereowalker.combat.event.handler;

import java.util.Random;
import java.util.UUID;

import com.stereowalker.combat.enchantment.CEnchantments;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.entity.monster.VampireEntity;
import com.stereowalker.combat.item.CItems;
import com.stereowalker.combat.item.DaggerItem;
import com.stereowalker.combat.potion.CEffects;
import com.stereowalker.combat.util.CDamageSource;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
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
		return CombatEntityStats.isVampire(entity) || entity instanceof VampireEntity || entity.isPotionActive(CEffects.VAMPIRISM);
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
		AttributeModifier vampireSpeed = new AttributeModifier(UUID.fromString("57faea4b-d5fc-463a-b151-5f087a1f4f1c"), "Vampire_Speed", 1.0D, Operation.MULTIPLY_BASE);
		AttributeModifier vampireArmor = new AttributeModifier(UUID.fromString("5faa4365-b839-4ff2-aa07-ef5dbe98c383"), "Vampire_Armor", 1.0D, Operation.MULTIPLY_BASE);
		AttributeModifier vampireAttSpeed = new AttributeModifier(UUID.fromString("f5941d33-c1a3-48da-9726-58a386602740"), "Vampire_Att_Speed", 1.0D, Operation.MULTIPLY_BASE);
		AttributeModifier vampireHealth = new AttributeModifier(UUID.fromString("90ddb8ba-1be4-4b07-b31b-1c2e056b7041"), "Vampire_Health", 1.0D, Operation.MULTIPLY_BASE);
		AttributeModifier vampireKnoRes = new AttributeModifier(UUID.fromString("2946a5e3-b2dc-405e-90d6-892617a1225d"), "Vampire_Kno_Res", 1.0D, Operation.MULTIPLY_BASE);
		AttributeModifier vampireDamage = new AttributeModifier(UUID.fromString("cd9bb5e5-9df4-4afb-8c39-c25b07982cba"), "Vampire_Damage", 1.0D, Operation.MULTIPLY_BASE);
		if (CombatEntityStats.isVampire(living)) {
			if (!living.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(vampireSpeed))
				living.getAttribute(Attributes.MOVEMENT_SPEED).applyPersistentModifier(vampireSpeed);
			if (!living.getAttribute(Attributes.ARMOR).hasModifier(vampireArmor))
				living.getAttribute(Attributes.ARMOR).applyPersistentModifier(vampireArmor);
			if (!living.getAttribute(Attributes.ATTACK_SPEED).hasModifier(vampireAttSpeed))
				living.getAttribute(Attributes.ATTACK_SPEED).applyPersistentModifier(vampireAttSpeed);
			if (!living.getAttribute(Attributes.MAX_HEALTH).hasModifier(vampireHealth))
				living.getAttribute(Attributes.MAX_HEALTH).applyPersistentModifier(vampireHealth);
			if (!living.getAttribute(Attributes.KNOCKBACK_RESISTANCE).hasModifier(vampireKnoRes))
				living.getAttribute(Attributes.KNOCKBACK_RESISTANCE).applyPersistentModifier(vampireKnoRes);
			if (!living.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(vampireDamage))
				living.getAttribute(Attributes.ATTACK_DAMAGE).applyPersistentModifier(vampireDamage);
			Random rand = new Random();
			int level = EnchantmentHelper.getMaxEnchantmentLevel(CEnchantments.SUN_SHIELD, living);
			living.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 20, 1, false, false, false));
			if (living.isAlive()) {
				boolean flag = (isInDaylight(living) && level == 0) || living.getPosY() < 0;
				if (flag) {
					ItemStack itemstack = living.getItemStackFromSlot(EquipmentSlotType.HEAD);
					if (!itemstack.isEmpty()) {
						if (itemstack.isDamageable()) {
							itemstack.setDamage(itemstack.getDamage() + rand.nextInt(2));
							if (itemstack.getDamage() >= itemstack.getMaxDamage()) {
								living.sendBreakAnimation(EquipmentSlotType.HEAD);
								living.setItemStackToSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
							}
						}

						flag = false;
					}
					if ((living instanceof PlayerEntity && !((PlayerEntity)living).abilities.isCreativeMode) || !(living instanceof PlayerEntity)) {
						living.setFire(8);
						living.attackEntityFrom(CDamageSource.SUNBURNED, 20.0F);
					}
				} else {
					if (living.ticksExisted%10 == 0) living.heal(0.5f);
				}
			}
		} else {
			if(living.getAttributeManager().hasAttributeInstance(Attributes.MOVEMENT_SPEED))
				if (living.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(vampireSpeed.getID()) != null)
					if (living.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(vampireSpeed))
						living.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(vampireSpeed);
			if(living.getAttributeManager().hasAttributeInstance(Attributes.ARMOR))
				if (living.getAttribute(Attributes.ARMOR).getModifier(vampireArmor.getID()) != null)
					if (living.getAttribute(Attributes.ARMOR).hasModifier(vampireArmor))
						living.getAttribute(Attributes.ARMOR).removeModifier(vampireArmor);
			if(living.getAttributeManager().hasAttributeInstance(Attributes.ATTACK_SPEED))
				if (living.getAttribute(Attributes.ATTACK_SPEED).getModifier(vampireAttSpeed.getID()) != null)
					if (living.getAttribute(Attributes.ATTACK_SPEED).hasModifier(vampireAttSpeed))
						living.getAttribute(Attributes.ATTACK_SPEED).removeModifier(vampireAttSpeed);
			if(living.getAttributeManager().hasAttributeInstance(Attributes.MAX_HEALTH))
				if (living.getAttribute(Attributes.MAX_HEALTH).getModifier(vampireHealth.getID()) != null)
					if (living.getAttribute(Attributes.MAX_HEALTH).hasModifier(vampireHealth))
						living.getAttribute(Attributes.MAX_HEALTH).removeModifier(vampireHealth);
			if(living.getAttributeManager().hasAttributeInstance(Attributes.KNOCKBACK_RESISTANCE))
				if (living.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getModifier(vampireKnoRes.getID()) != null)
					if (living.getAttribute(Attributes.KNOCKBACK_RESISTANCE).hasModifier(vampireKnoRes))
						living.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(vampireKnoRes);
			if(living.getAttributeManager().hasAttributeInstance(Attributes.ATTACK_DAMAGE))
				if (living.getAttribute(Attributes.ATTACK_DAMAGE).getModifier(vampireDamage.getID()) != null)
					if (living.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(vampireDamage))
						living.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(vampireDamage);
		}
	}

	@SubscribeEvent
	public static void vampireEatFood(LivingEntityUseItemEvent.Finish event) {
		if (event.getResultStack().isFood()) {
			if (CombatEntityStats.isVampire(event.getEntityLiving())) {
				if (event.getResultStack().getItem() != CItems.BLOOD) {
					event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.NAUSEA, 1200));
					event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.POISON, 1200, 10));
				} else {
					((PlayerEntity)event.getEntityLiving()).getFoodStats().addStats(20, 10.0F);
				}
			}
		}

	}
	
	@SubscribeEvent
	public static void vampireEatEntities(AttackEntityEvent event) {
		if (isVampire(event.getEntityLiving())) {
			if (event.getTarget() instanceof LivingEntity && event.getEntityLiving() instanceof PlayerEntity) {
				if (!((LivingEntity)event.getTarget()).getCreatureAttribute().equals(CreatureAttribute.UNDEAD)) {
					((PlayerEntity)event.getEntityLiving()).getFoodStats().addStats(2, 1.0F);
				}
			}
		}
	}
	public boolean processInteract(PlayerEntity player, Hand hand) {
		ItemStack bottle = player.getHeldItem(Hand.OFF_HAND);
		ItemStack dagger = player.getHeldItem(Hand.MAIN_HAND);
		if (bottle.getItem() == Items.GLASS_BOTTLE && dagger.getItem() instanceof DaggerItem) {
			if (!player.abilities.isCreativeMode) {
				bottle.shrink(1);
			}
			player.addItemStackToInventory(new ItemStack(CItems.VAMPIRE_BLOOD));
			return true;
		} else {
			return false;
		}
	}

	@SubscribeEvent
	public static void woodenToolAttack(LivingAttackEvent event) {
		LivingEntity living = event.getEntityLiving();
		if (isVampire(living)) {
			if(event.getSource().getTrueSource() instanceof LivingEntity) {
				if(((LivingEntity)event.getSource().getTrueSource()).getHeldItemMainhand().isItemEqual(new ItemStack(Items.WOODEN_SWORD)) ||
						((LivingEntity)event.getSource().getTrueSource()).getHeldItemMainhand().isItemEqual(new ItemStack(Items.WOODEN_AXE)) ||
						((LivingEntity)event.getSource().getTrueSource()).getHeldItemMainhand().isItemEqual(new ItemStack(Items.WOODEN_HOE)) ||
						((LivingEntity)event.getSource().getTrueSource()).getHeldItemMainhand().isItemEqual(new ItemStack(Items.WOODEN_PICKAXE)) ||
						((LivingEntity)event.getSource().getTrueSource()).getHeldItemMainhand().isItemEqual(new ItemStack(Items.WOODEN_SHOVEL)) ||
						((LivingEntity)event.getSource().getTrueSource()).getHeldItemMainhand().isItemEqual(new ItemStack(CItems.WOODEN_DAGGER))) {
					living.setFire(60);
					living.attackEntityFrom(CDamageSource.STAKED, 1.0F);
				}
			}
		}
	}

	protected static boolean isInDaylight(LivingEntity mob) {
		Random rand = new Random();
		if (mob.world.isDaytime() && !mob.world.isRemote) {
			float f = mob.getBrightness();
			BlockPos blockpos = mob.getRidingEntity() instanceof BoatEntity ? (new BlockPos(mob.getPosX(), (double)Math.round(mob.getPosY()), mob.getPosZ())).up() : new BlockPos(mob.getPosX(), (double)Math.round(mob.getPosY()), mob.getPosZ());
			if (f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && mob.world.canSeeSky(blockpos)) {
				return true;
			}
		}

		return false;
	}
}
