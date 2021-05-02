package com.stereowalker.combat.event;

import java.util.Random;

import com.stereowalker.combat.enchantment.CEnchantments;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.entity.ai.goal.FleeGoal;
import com.stereowalker.combat.potion.CEffects;
import com.stereowalker.combat.util.CDamageSource;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
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
		if (living.isPotionActive(CEffects.GRAVITY_PLUS)) isActive = true;
		if(isActive) {
			int amp = living.getActivePotionEffect(CEffects.GRAVITY_PLUS).getAmplifier() + 1;
			event.setDistance(((event.getDistance() * (0.08F * (1+(0.01F*amp)))) / 0.08F));
		}
	}
	@SubscribeEvent
	public static void gravityMinusFall(LivingFallEvent event) {
		LivingEntity living = event.getEntityLiving();
		boolean isActive = false;
		if (living.isPotionActive(CEffects.GRAVITY_MINUS)) isActive = true;
		if(isActive) {
			int amp = living.getActivePotionEffect(CEffects.GRAVITY_MINUS).getAmplifier() + 1;
			event.setDistance(((event.getDistance() * (0.08F * (1+(-0.01F*amp)))) / 0.08F));
		}
	}

	@SubscribeEvent
	public static void effectParalysis(LivingUpdateEvent event) {
		LivingEntity living = event.getEntityLiving();
		boolean isActive = false;
		if (living.isPotionActive(CEffects.PARALYSIS)) isActive = true;
		if(isActive) {
			living.rotationPitch = CombatEntityStats.getLockedPitch(living);
			living.rotationYaw = CombatEntityStats.getLockedPitch(living);
		} else {
			CombatEntityStats.setLockedPitch(living, living.rotationPitch);
			CombatEntityStats.setLockedYaw(living, living.rotationYaw);
		}
	}

	@SubscribeEvent
	public static void effectVampirism(LivingUpdateEvent event) 
	{
		LivingEntity living = event.getEntityLiving();
		Random rand = new Random();
		boolean isActive = false;
		if (living.isPotionActive(CEffects.VAMPIRISM)) isActive = true;
		int level = EnchantmentHelper.getMaxEnchantmentLevel(CEnchantments.SUN_SHIELD, living);
		if(isActive) {
			if(level == 0) {
				if (living.isAlive()) {
					boolean flag = isInDaylight(living);
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

						if (flag) {
							if (living instanceof PlayerEntity) {
								if(!((PlayerEntity)living).abilities.isCreativeMode) {
									living.setFire(8);
									living.attackEntityFrom(CDamageSource.SUNBURNED, 5.0F * (living.getActivePotionEffect(CEffects.VAMPIRISM).getAmplifier()+1));
								}
							} else {
								living.setFire(8);
								living.attackEntityFrom(CDamageSource.SUNBURNED, 5.0F * (living.getActivePotionEffect(CEffects.VAMPIRISM).getAmplifier()+1));
							}
						}
					} else {
						living.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 0, 1, false, false, false));
						if (living.getHealth() < living.getMaxHealth() && living.getHealth() != 0)
						{
							living.heal(0.1F * (living.getActivePotionEffect(CEffects.VAMPIRISM).getAmplifier()+1));
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
		if (living.isPotionActive(CEffects.FLAMMABLE)) isActive = true;
		if(isActive) {
			if(!living.world.isRemote) {
				if(living.isBurning()) {
					living.setFire(30);
				}
			}
		}
	}

	@SubscribeEvent
	public static void effectFear(LivingUpdateEvent event) {
		LivingEntity living = event.getEntityLiving();
		if(!living.world.isRemote) {
			boolean isActive = false;
			if (living.isPotionActive(CEffects.FEAR)) isActive = true;
			if (living instanceof CreatureEntity) {
				CreatureEntity mob = (CreatureEntity)living;
				Goal fearMe = new FleeGoal<>(mob, PlayerEntity.class, 16.0F, 1.0D, 1.5D);
				if(isActive) {
					mob.goalSelector.addGoal(1, fearMe);
				}
			}
		}
	}

	@SubscribeEvent
	public static void effectFrostbite(LivingUpdateEvent event) {
		LivingEntity living = event.getEntityLiving();
		if(!living.world.isRemote) {
			boolean isActive = false;
			if (living.isPotionActive(CEffects.FROSTBITE)) isActive = true;
			if(isActive) {
				if(living.ticksExisted%100 == 99) {
					living.attackEntityFrom(CDamageSource.FROSTBITE, 1.0F);
				}
			}
		}
	}

	@SubscribeEvent
	public static void effectInsulation(EntityStruckByLightningEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity living = (LivingEntity) event.getEntity();
			if(!living.world.isRemote) {
				boolean isActive = false;
				if (living.isPotionActive(CEffects.INSULATION)) isActive = true;
				if(isActive) {
					event.setCanceled(true);
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
