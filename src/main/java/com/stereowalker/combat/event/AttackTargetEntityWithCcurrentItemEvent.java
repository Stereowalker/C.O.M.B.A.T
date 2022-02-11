package com.stereowalker.combat.event;

import com.stereowalker.combat.world.item.ScytheItem;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class AttackTargetEntityWithCcurrentItemEvent {

	@SubscribeEvent
	public void attackTargetEntityWithCurrentItem(AttackEntityEvent event) {
		Entity targetEntity = event.getTarget();
		Player player = event.getPlayer();

		if (shouldOverrideAttack(player)) {
			event.setCanceled(true);
			if (targetEntity.isAttackable()) {
				if (!targetEntity.skipAttackInteraction(player)) {
					float f = (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE);
					float f1;
					if (targetEntity instanceof LivingEntity) {
						f1 = EnchantmentHelper.getDamageBonus(player.getMainHandItem(), ((LivingEntity)targetEntity).getMobType());
					} else {
						f1 = EnchantmentHelper.getDamageBonus(player.getMainHandItem(), MobType.UNDEFINED);
					}

					float f2 = player.getAttackStrengthScale(0.5F);
					f = f * (0.2F + f2 * f2 * 0.8F);
					f1 = f1 * f2;
					player.resetAttackStrengthTicker();
					if (f > 0.0F || f1 > 0.0F) {
						boolean flag = f2 > 0.9F;
						boolean flag1 = false;
						int i = 0;
						i = i + EnchantmentHelper.getKnockbackBonus(player);
						if (player.isSprinting() && flag) {
							player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, player.getSoundSource(), 1.0F, 1.0F);
							++i;
							flag1 = true;
						}

						boolean flag2 = flag && player.fallDistance > 0.0F && !player.isOnGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) && !player.isPassenger() && targetEntity instanceof LivingEntity;
						flag2 = flag2 && !player.isSprinting();
						net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(player, targetEntity, flag2, flag2 ? 1.5F : 1.0F);
						flag2 = hitResult != null;
						if (flag2) {
							f *= hitResult.getDamageModifier();
						}

						f = f + f1;
						
						boolean flag3 = false;
						double d0 = (double)(player.walkDist - player.walkDistO);
						if (flag && !flag2 && !flag1 && player.isOnGround() && d0 < (double)player.getSpeed()) {
							ItemStack itemstack = player.getItemInHand(InteractionHand.MAIN_HAND);
							if (sweepingArcs(itemstack) != Vec3.ZERO) {
								flag3 = true;
							}
						}

						float f4 = 0.0F;
						boolean flag4 = false;
						int j = EnchantmentHelper.getFireAspect(player) + (PlayerSkills.hasSkill(player, Skills.BURNING_STRIKE) ? 1 : 0);
						if (targetEntity instanceof LivingEntity) {
							f4 = ((LivingEntity)targetEntity).getHealth();
							if (j > 0 && !targetEntity.isOnFire()) {
								flag4 = true;
								targetEntity.setSecondsOnFire(1);
							}
						}

						Vec3 vector3d = targetEntity.getDeltaMovement();
						boolean flag5 = targetEntity.hurt(DamageSource.playerAttack(player), f);
						if (flag5) {
							if (i > 0) {
								if (targetEntity instanceof LivingEntity) {
									((LivingEntity)targetEntity).knockback((float)i * 0.5F, (double)Mth.sin(player.getYRot() * ((float)Math.PI / 180F)), (double)(-Mth.cos(player.getYRot() * ((float)Math.PI / 180F))));
								} else {
									targetEntity.push((double)(-Mth.sin(player.getYRot() * ((float)Math.PI / 180F)) * (float)i * 0.5F), 0.1D, (double)(Mth.cos(player.getYRot() * ((float)Math.PI / 180F)) * (float)i * 0.5F));
								}

								player.setDeltaMovement(player.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
								player.setSprinting(false);
							}

							if (flag3) {
								float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * f;
								for(LivingEntity livingentity : player.level.getEntitiesOfClass(LivingEntity.class, targetEntity.getBoundingBox().inflate(sweepingArcs(player.getItemInHand(InteractionHand.MAIN_HAND)).x, sweepingArcs(player.getItemInHand(InteractionHand.MAIN_HAND)).y, sweepingArcs(player.getItemInHand(InteractionHand.MAIN_HAND)).z))) {
//									h++;dlivingentity instanceof AbstractSkeletonEntity) q++;
									if (livingentity != player && livingentity != targetEntity && !player.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand)livingentity).isMarker()) && player.distanceToSqr(livingentity) < 9.0D) {
										livingentity.knockback(0.4F, (double)Mth.sin(player.getYRot() * ((float)Math.PI / 180F)), (double)(-Mth.cos(player.getYRot() * ((float)Math.PI / 180F))));
										livingentity.hurt(DamageSource.playerAttack(player), f3);
									}
								}

								player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
								player.sweepAttack();
							}

							if (targetEntity instanceof ServerPlayer && targetEntity.hurtMarked) {
								((ServerPlayer)targetEntity).connection.send(new ClientboundSetEntityMotionPacket(targetEntity));
								targetEntity.hurtMarked = false;
								targetEntity.setDeltaMovement(vector3d);
							}

							if (flag2) {
								player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, player.getSoundSource(), 1.0F, 1.0F);
								player.crit(targetEntity);
							}

							if (!flag2 && !flag3) {
								if (flag) {
									player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, player.getSoundSource(), 1.0F, 1.0F);
								} else {
									player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, player.getSoundSource(), 1.0F, 1.0F);
								}
							}

							if (f1 > 0.0F) {
								player.magicCrit(targetEntity);
							}

							player.setLastHurtMob(targetEntity);
							if (targetEntity instanceof LivingEntity) {
								EnchantmentHelper.doPostHurtEffects((LivingEntity)targetEntity, player);
							}

							EnchantmentHelper.doPostDamageEffects(player, targetEntity);
							ItemStack itemstack1 = player.getMainHandItem();
							Entity entity = targetEntity;
							if (targetEntity instanceof EnderDragonPart) {
								entity = ((EnderDragonPart)targetEntity).parentMob;
							}

							if (!player.level.isClientSide && !itemstack1.isEmpty() && entity instanceof LivingEntity) {
								ItemStack copy = itemstack1.copy();
								itemstack1.hurtEnemy((LivingEntity)entity, player);
								if (itemstack1.isEmpty()) {
									net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, copy, InteractionHand.MAIN_HAND);
									player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
								}
							}

							if (targetEntity instanceof LivingEntity) {
								float f5 = f4 - ((LivingEntity)targetEntity).getHealth();
								player.awardStat(Stats.DAMAGE_DEALT, Math.round(f5 * 10.0F));
								if (j > 0) {
									targetEntity.setSecondsOnFire(j * 4);
								}

								if (player.level instanceof ServerLevel && f5 > 2.0F) {
									int k = (int)((double)f5 * 0.5D);
									((ServerLevel)player.level).sendParticles(ParticleTypes.DAMAGE_INDICATOR, targetEntity.getX(), targetEntity.getY(0.5D), targetEntity.getZ(), k, 0.1D, 0.0D, 0.1D, 0.2D);
								}
							}

							player.causeFoodExhaustion(0.1F);
						} else {
							player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, player.getSoundSource(), 1.0F, 1.0F);
							if (flag4) {
								targetEntity.clearFire();
							}
						}
					}
				}
			}
		}
	}

	public static boolean shouldOverrideAttack(Player player) {
		return false;
//		return 
//				PlayerSkills.hasSkill(player, Skills.BURNING_STRIKE) || 
//				player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ScytheItem;
	}
	
	public static Vec3 sweepingArcs(ItemStack stack) {
		if (stack.getItem() instanceof SwordItem) {
			return new Vec3(1.0D, 0.25D, 1.0D);
		} else if (stack.getItem() instanceof ScytheItem) {
			return new Vec3(100.5D, 50.50D, 100.5D);
		} else return Vec3.ZERO;
	}
}
