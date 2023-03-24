package com.stereowalker.combat.event;

import java.util.Random;

import com.stereowalker.combat.world.damagesource.CDamageSource;
import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.goal.FleeGoal;
import com.stereowalker.combat.world.item.enchantment.CEnchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class EffectEvents {
	@SubscribeEvent
	public static void gravityPlusFall(LivingFallEvent event) {
		LivingEntity living = event.getEntityLiving();
		boolean isActive = false;
		if (living.hasEffect(CMobEffects.GRAVITY_PLUS)) isActive = true;
		if(isActive) {
			int amp = living.getEffect(CMobEffects.GRAVITY_PLUS).getAmplifier() + 1;
			event.setDistance(((event.getDistance() * (0.08F * (1+(0.01F*amp)))) / 0.08F));
		}
	}
	@SubscribeEvent
	public static void gravityMinusFall(LivingFallEvent event) {
		LivingEntity living = event.getEntityLiving();
		boolean isActive = false;
		if (living.hasEffect(CMobEffects.GRAVITY_MINUS)) isActive = true;
		if(isActive) {
			int amp = living.getEffect(CMobEffects.GRAVITY_MINUS).getAmplifier() + 1;
			event.setDistance(((event.getDistance() * (0.08F * (1+(-0.01F*amp)))) / 0.08F));
		}
	}

	@SubscribeEvent
	public static void effectParalysis(LivingUpdateEvent event) {
		LivingEntity living = event.getEntityLiving();
		boolean isActive = false;
		if (living.hasEffect(CMobEffects.PARALYSIS)) isActive = true;
		if(isActive) {
			living.setXRot(CombatEntityStats.getLockedPitch(living));
			living.setYRot(CombatEntityStats.getLockedYaw(living));
		} else {
			CombatEntityStats.setLockedPitch(living, living.getXRot());
			CombatEntityStats.setLockedYaw(living, living.getYRot());
		}
	}

	@SubscribeEvent
	public static void effectVampirism(LivingUpdateEvent event) 
	{
		LivingEntity living = event.getEntityLiving();
		Random rand = new Random();
		boolean isActive = false;
		if (living.hasEffect(CMobEffects.VAMPIRISM)) isActive = true;
		int level = EnchantmentHelper.getEnchantmentLevel(CEnchantments.SUN_SHIELD, living);
		if(isActive) {
			if(level == 0) {
				if (living.isAlive()) {
					boolean flag = isInDaylight(living);
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

						if (flag) {
							if (living instanceof Player) {
								if(!((Player)living).getAbilities().instabuild) {
									living.setSecondsOnFire(8);
									living.hurt(CDamageSource.SUNBURNED, 5.0F * (living.getEffect(CMobEffects.VAMPIRISM).getAmplifier()+1));
								}
							} else {
								living.setSecondsOnFire(8);
								living.hurt(CDamageSource.SUNBURNED, 5.0F * (living.getEffect(CMobEffects.VAMPIRISM).getAmplifier()+1));
							}
						}
					} else {
						living.addEffect(new MobEffectInstance(MobEffects.JUMP, 0, 1, false, false, false));
						if (living.getHealth() < living.getMaxHealth() && living.getHealth() != 0)
						{
							living.heal(0.1F * (living.getEffect(CMobEffects.VAMPIRISM).getAmplifier()+1));
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void effectFlammable(LivingUpdateEvent event) {
		LivingEntity living = event.getEntityLiving();
		boolean isActive = false;
		if (living.hasEffect(CMobEffects.FLAMMABLE)) isActive = true;
		if(isActive) {
			if(!living.level.isClientSide) {
				if(living.isOnFire()) {
					living.setSecondsOnFire(30);
				}
			}
		}
	}

	@SubscribeEvent
	public static void effectFear(LivingUpdateEvent event) {
		LivingEntity living = event.getEntityLiving();
		if(!living.level.isClientSide) {
			boolean isActive = false;
			if (living.hasEffect(CMobEffects.FEAR)) isActive = true;
			if (living instanceof PathfinderMob) {
				PathfinderMob mob = (PathfinderMob)living;
				Goal fearMe = new FleeGoal<>(mob, Player.class, 16.0F, 1.0D, 1.5D);
				if(isActive) {
					mob.goalSelector.addGoal(1, fearMe);
				}
			}
		}
	}

	@SubscribeEvent
	public static void effectFrostbite(LivingUpdateEvent event) {
		LivingEntity living = event.getEntityLiving();
		if(!living.level.isClientSide) {
			boolean isActive = false;
			if (living.hasEffect(CMobEffects.FROSTBITE)) isActive = true;
			if(isActive) {
				if(living.tickCount%100 == 99) {
					living.hurt(CDamageSource.FROSTBITE, 1.0F);
				}
			}
		}
	}

	@SubscribeEvent
	public static void effectInsulation(EntityStruckByLightningEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity living = (LivingEntity) event.getEntity();
			if(!living.level.isClientSide) {
				boolean isActive = false;
				if (living.hasEffect(CMobEffects.INSULATION)) isActive = true;
				if(isActive) {
					event.setCanceled(true);
				}
			}
		}
	}

	protected static boolean isInDaylight(LivingEntity mob) {
		Random rand = new Random();
		if (mob.level.isDay() && !mob.level.isClientSide) {
			float f = mob.getBrightness();
			BlockPos blockpos = mob.getVehicle() instanceof Boat ? (new BlockPos(mob.getX(), (double)Math.round(mob.getY()), mob.getZ())).above() : new BlockPos(mob.getX(), (double)Math.round(mob.getY()), mob.getZ());
			if (f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && mob.level.canSeeSky(blockpos)) {
				return true;
			}
		}

		return false;
	}
}
