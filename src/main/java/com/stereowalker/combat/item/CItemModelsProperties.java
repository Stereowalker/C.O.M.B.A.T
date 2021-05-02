package com.stereowalker.combat.item;

import javax.annotation.Nullable;

import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.util.EnergyUtils;
import com.stereowalker.combat.util.EnergyUtils.EnergyType;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CItemModelsProperties {
	public static void register() {
		ItemModelsProperties.registerProperty(CItems.MANA_ORB, new ResourceLocation("mana"), new IItemPropertyGetter() {
			@OnlyIn(Dist.CLIENT)
			public float call(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
				boolean flag = entity != null;
				if (flag) {
					if (EnergyUtils.getMaxEnergy(stack, EnergyType.MAGIC_ENERGY) == 0) {
						return 0;
					}
					else {
						float d0 =  EnergyUtils.getEnergy(stack, EnergyType.MAGIC_ENERGY) / EnergyUtils.getMaxEnergy(stack, EnergyType.MAGIC_ENERGY);
						return d0;
					}
				}
				else return 0;
			}
		});
		Item[] bows = {CItems.IRON_BOW, CItems.GOLDEN_BOW, CItems.BRONZE_BOW, CItems.STEEL_BOW, CItems.LOZYNE_BOW, CItems.SOUL_BOW};
		for (Item bow : bows) {
			ItemModelsProperties.registerProperty(bow, new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
				if (livingEntity == null) {
					return 0.0F;
				} else {
					return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
				}
			});
			ItemModelsProperties.registerProperty(bow, new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> {
				return p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F;
			});
		}

		Item[] longbows = {CItems.LONGBOW, CItems.IRON_LONGBOW, CItems.GOLDEN_LONGBOW, CItems.BRONZE_LONGBOW, CItems.STEEL_LONGBOW, CItems.LOZYNE_LONGBOW};
		for (Item longbow : longbows) {
			ItemModelsProperties.registerProperty(longbow, new ResourceLocation("pull"), (itemStack, p_210310_1_, p_210310_2_) -> {
				if (p_210310_2_ == null) {
					return 0.0F;
				} else {
					return p_210310_2_.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - p_210310_2_.getItemInUseCount()) / 30.0F;
				}
			});
			ItemModelsProperties.registerProperty(longbow, new ResourceLocation("pulling"), (p_210309_0_, p_210309_1_, p_210309_2_) -> {
				return p_210309_2_ != null && p_210309_2_.isHandActive() && p_210309_2_.getActiveItemStack() == p_210309_0_ ? 1.0F : 0.0F;
			});
		}

		Item[] crossbows = {CItems.IRON_CROSSBOW, CItems.GOLDEN_CROSSBOW, CItems.BRONZE_CROSSBOW, CItems.STEEL_CROSSBOW, CItems.LOZYNE_CROSSBOW};
		for (Item crossbow : crossbows) {
			ItemModelsProperties.registerProperty(crossbow, new ResourceLocation("pull"), (p_239427_0_, p_239427_1_, p_239427_2_) -> {
				if (p_239427_2_ == null) {
					return 0.0F;
				} else {
					return CrossbowItem.isCharged(p_239427_0_) ? 0.0F : (float)(p_239427_0_.getUseDuration() - p_239427_2_.getItemInUseCount()) / (float)CrossbowItem.getChargeTime(p_239427_0_);
				}
			});
			ItemModelsProperties.registerProperty(crossbow, new ResourceLocation("pulling"), (p_239426_0_, p_239426_1_, p_239426_2_) -> {
				return p_239426_2_ != null && p_239426_2_.isHandActive() && p_239426_2_.getActiveItemStack() == p_239426_0_ && !CrossbowItem.isCharged(p_239426_0_) ? 1.0F : 0.0F;
			});
			ItemModelsProperties.registerProperty(crossbow, new ResourceLocation("charged"), (p_239425_0_, p_239425_1_, p_239425_2_) -> {
				return p_239425_2_ != null && CrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
			});
			ItemModelsProperties.registerProperty(crossbow, new ResourceLocation("firework"), (p_239424_0_, p_239424_1_, p_239424_2_) -> {
				return p_239424_2_ != null && CrossbowItem.isCharged(p_239424_0_) && CrossbowItem.hasChargedProjectile(p_239424_0_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
			});
		}

		ItemModelsProperties.registerProperty(CItems.ARCH, new ResourceLocation("color"), (p_210309_0_, p_210309_1_, p_210309_2_) -> {
			if (p_210309_2_ != null && p_210309_2_.getActiveItemStack() != p_210309_0_) {
				return 0.0F;
			} else {
				return /* this.arrow_color */0;
			}
		});
		ItemModelsProperties.registerProperty(CItems.ARCH, new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
			if (livingEntity == null) {
				return 0.0F;
			} else {
				return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
			}
		});
		ItemModelsProperties.registerProperty(CItems.ARCH, new ResourceLocation("pulling"), (p_210309_0_, p_210309_1_, p_210309_2_) -> {
			return p_210309_2_ != null && p_210309_2_.isHandActive() && p_210309_2_.getActiveItemStack() == p_210309_0_ ? 1.0F : 0.0F;
		});

		Item[] towerShields = {CItems.IRON_TOWER_SHIELD, CItems.GOLDEN_TOWER_SHIELD, CItems.BRONZE_TOWER_SHIELD, CItems.STEEL_TOWER_SHIELD, CItems.LOZYNE_TOWER_SHIELD};
		Item[] roundShields = {CItems.IRON_ROUND_SHIELD, CItems.GOLDEN_ROUND_SHIELD, CItems.BRONZE_ROUND_SHIELD, CItems.STEEL_ROUND_SHIELD, CItems.LOZYNE_ROUND_SHIELD, CItems.ROUND_SHIELD};
		for (Item towerShield : towerShields) {
			ItemModelsProperties.registerProperty(towerShield, new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> {
				return p_239421_2_ != null && p_239421_2_.isHandActive() && p_239421_2_.getActiveItemStack() == p_239421_0_ ? 1.0F : 0.0F;
			});
		}
		for (Item roundShield : roundShields) {
			ItemModelsProperties.registerProperty(roundShield, new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> {
				return p_239421_2_ != null && p_239421_2_.isHandActive() && p_239421_2_.getActiveItemStack() == p_239421_0_ ? 1.0F : 0.0F;
			});
		}

		ItemModelsProperties.registerProperty(CItems.SPEAR, new ResourceLocation("throwing"), (p_239419_0_, p_239419_1_, p_239419_2_) -> {
			return p_239419_2_ != null && p_239419_2_.isHandActive() && p_239419_2_.getActiveItemStack() == p_239419_0_ ? 1.0F : 0.0F;
		});

		ItemModelsProperties.registerProperty(CItems.DESERT_DRAGON, new ResourceLocation("tilt"), (p_210310_0_, p_210310_1_, p_210310_2_) -> {
			return p_210310_2_ != null && p_210310_2_.isHandActive() && p_210310_2_.getActiveItemStack() == p_210310_0_ ? 1.0F : 0.0F;
		});

		ItemModelsProperties.registerProperty(CItems.ETHERION_COMPASS, new ResourceLocation("angle"), new IItemPropertyGetter() {
			@OnlyIn(Dist.CLIENT)
			private double rotation;
			@OnlyIn(Dist.CLIENT)
			private double rota;
			@OnlyIn(Dist.CLIENT)
			private long lastUpdateTick;

			@OnlyIn(Dist.CLIENT)
			public float call(ItemStack p_call_1_, @Nullable ClientWorld p_call_2_, @Nullable LivingEntity p_call_3_) {
				if (p_call_3_ == null && !p_call_1_.isOnItemFrame()) {
					return 0.0F;
				} else {
					boolean flag = p_call_3_ != null;
					Entity entity = (Entity)(flag ? p_call_3_ : p_call_1_.getItemFrame());
					if (p_call_2_ == null && entity.world instanceof ClientWorld) {
						p_call_2_ = (ClientWorld) entity.world;
					}

					double d0;
					boolean daytime = p_call_2_.getDayTime() % 24000 > 14000 && p_call_2_.getDayTime() % 24000 < 22000;
					//TODO: Figure out what this was
							if (/* p_call_2_.func_230315_m_().func_236043_f_() && */daytime) {
						double d1 = flag ? (double)entity.rotationYaw : this.getFrameRotation((ItemFrameEntity)entity);
						d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
						double d2 = this.getTowerToAngle(p_call_2_, entity) / (double)((float)Math.PI * 2F);
						d0 = 0.5D - (d1 - 0.25D - d2);
					} else {
						d0 = Math.random();
					}

					if (flag) {
						d0 = this.wobble(p_call_2_, d0);
					}

					return MathHelper.positiveModulo((float)d0, 1.0F);
				}
			}

			@OnlyIn(Dist.CLIENT)
			private double wobble(World worldIn, double p_185093_2_) {
				if (worldIn.getGameTime() != this.lastUpdateTick) {
					this.lastUpdateTick = worldIn.getGameTime();
					double d0 = p_185093_2_ - this.rotation;
					d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
					this.rota += d0 * 0.1D;
					this.rota *= 0.8D;
					this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
				}

				return this.rotation;
			}

			@OnlyIn(Dist.CLIENT)
			private double getFrameRotation(ItemFrameEntity p_185094_1_) {
				return (double)MathHelper.wrapDegrees(180 + p_185094_1_.getHorizontalFacing().getHorizontalIndex() * 90);
			}

			@OnlyIn(Dist.CLIENT)
			private double getTowerToAngle(IWorld worldIn, Entity entityIn) {
				BlockPos blockpos = CombatEntityStats.getNearestEtherionTowerPos(entityIn);
				return Math.atan2((double)blockpos.getZ() - entityIn.getPosZ(), (double)blockpos.getX() - entityIn.getPosX());
			}
		});

		ItemModelsProperties.registerProperty(CItems.MAGIC_ORB, new ResourceLocation("affinity"), new IItemPropertyGetter() {

			@OnlyIn(Dist.CLIENT)
			public float call(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
				boolean flag = entity != null;
				if (flag) {
					boolean flag2 = entity.getHeldItemMainhand() == stack || entity.getHeldItemOffhand() == stack;
					int d1 = CombatEntityStats.getSubElementalAffinity1(entity).ordinal();
					int d2 = CombatEntityStats.getSubElementalAffinity2(entity).ordinal();
					if (flag2) {
						float d0 = Float.parseFloat("0."+d1+d2);
						return d0;
					}
				}
				return 0;
			}
		});
		ItemModelsProperties.registerProperty(CItems.LIGHT_SABER, new ResourceLocation("drawn"), (itemStack, world, living) -> {
			return LightSaberItem.isSaberActive(itemStack) ? 1.0F : 0.0F;
		});
		ItemModelsProperties.registerProperty(CItems.MAGISTEEL_SWORD, new ResourceLocation("powered"), (itemStack, world, living) -> {
			return ((IMagisteelItem)itemStack.getItem()).isUsingMana(itemStack) ? 1.0F : 0.0F;
		});
		ItemModelsProperties.registerProperty(CItems.MAGISTEEL_AXE, new ResourceLocation("powered"), (itemStack, world, living) -> {
			return ((IMagisteelItem)itemStack.getItem()).isUsingMana(itemStack) ? 1.0F : 0.0F;
		});
	}
}
