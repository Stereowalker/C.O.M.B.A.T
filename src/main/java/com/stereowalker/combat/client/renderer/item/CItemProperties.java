package com.stereowalker.combat.client.renderer.item;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.core.EnergyUtils;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.Magisteel;
import com.stereowalker.combat.world.item.Mythril;
import com.stereowalker.combat.world.level.dimension.CDimensionType.CLevel;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CItemProperties {
	public static void register() {
		ItemProperties.register(CItems.MANA_ORB, new ResourceLocation("mana"), new ItemPropertyFunction() {
			@OnlyIn(Dist.CLIENT)
			public float call(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) {
				boolean flag = entity != null;
				if (flag) {
					if (EnergyUtils.getMaxEnergy(stack, EnergyUtils.EnergyType.MAGIC_ENERGY) == 0) {
						return 0;
					}
					else {
						float d0 =  EnergyUtils.getEnergy(stack, EnergyUtils.EnergyType.MAGIC_ENERGY) / EnergyUtils.getMaxEnergy(stack, EnergyUtils.EnergyType.MAGIC_ENERGY);
						return d0;
					}
				}
				else return 0;
			}
		});
		Item[] bows = {CItems.IRON_BOW, CItems.GOLDEN_BOW, CItems.BRONZE_BOW, CItems.STEEL_BOW, CItems.LOZYNE_BOW, CItems.SOUL_BOW};
		for (Item bow : bows) {
			ItemProperties.register(bow, new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity, seed) -> {
				if (livingEntity == null) {
					return 0.0F;
				} else {
					return livingEntity.getUseItem() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
				}
			});
			ItemProperties.register(bow, new ResourceLocation("pulling"), (itemStack, p_239428_1_, p_239428_2_, seed) -> {
				return p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == itemStack ? 1.0F : 0.0F;
			});
		}

		Item[] longbows = {CItems.LONGBOW, CItems.IRON_LONGBOW, CItems.GOLDEN_LONGBOW, CItems.BRONZE_LONGBOW, CItems.STEEL_LONGBOW, CItems.LOZYNE_LONGBOW};
		for (Item longbow : longbows) {
			ItemProperties.register(longbow, new ResourceLocation("pull"), (itemStack, p_210310_1_, p_210310_2_, seed) -> {
				if (p_210310_2_ == null) {
					return 0.0F;
				} else {
					return p_210310_2_.getUseItem() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - p_210310_2_.getUseItemRemainingTicks()) / 30.0F;
				}
			});
			ItemProperties.register(longbow, new ResourceLocation("pulling"), (p_210309_0_, p_210309_1_, p_210309_2_, seed) -> {
				return p_210309_2_ != null && p_210309_2_.isUsingItem() && p_210309_2_.getUseItem() == p_210309_0_ ? 1.0F : 0.0F;
			});
		}

		Item[] crossbows = {CItems.IRON_CROSSBOW, CItems.GOLDEN_CROSSBOW, CItems.BRONZE_CROSSBOW, CItems.STEEL_CROSSBOW, CItems.LOZYNE_CROSSBOW};
		for (Item crossbow : crossbows) {
			ItemProperties.register(crossbow, new ResourceLocation("pull"), (p_239427_0_, p_239427_1_, p_239427_2_, seed) -> {
				if (p_239427_2_ == null) {
					return 0.0F;
				} else {
					return CrossbowItem.isCharged(p_239427_0_) ? 0.0F : (float)(p_239427_0_.getUseDuration() - p_239427_2_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_239427_0_);
				}
			});
			ItemProperties.register(crossbow, new ResourceLocation("pulling"), (p_239426_0_, p_239426_1_, p_239426_2_, seed) -> {
				return p_239426_2_ != null && p_239426_2_.isUsingItem() && p_239426_2_.getUseItem() == p_239426_0_ && !CrossbowItem.isCharged(p_239426_0_) ? 1.0F : 0.0F;
			});
			ItemProperties.register(crossbow, new ResourceLocation("charged"), (p_239425_0_, p_239425_1_, p_239425_2_, seed) -> {
				return p_239425_2_ != null && CrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F;
			});
			ItemProperties.register(crossbow, new ResourceLocation("firework"), (p_239424_0_, p_239424_1_, p_239424_2_, seed) -> {
				return p_239424_2_ != null && CrossbowItem.isCharged(p_239424_0_) && CrossbowItem.containsChargedProjectile(p_239424_0_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
			});
		}

		ItemProperties.register(CItems.ARCH, new ResourceLocation("color"), (p_210309_0_, p_210309_1_, p_210309_2_, seed) -> {
			if (p_210309_2_ != null && p_210309_2_.getUseItem() != p_210309_0_) {
				return 0.0F;
			} else {
				return /* this.arrow_color */0;
			}
		});
		ItemProperties.register(CItems.ARCH, new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity, seed) -> {
			if (livingEntity == null) {
				return 0.0F;
			} else {
				return livingEntity.getUseItem() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
			}
		});
		ItemProperties.register(CItems.ARCH, new ResourceLocation("pulling"), (p_210309_0_, p_210309_1_, p_210309_2_, seed) -> {
			return p_210309_2_ != null && p_210309_2_.isUsingItem() && p_210309_2_.getUseItem() == p_210309_0_ ? 1.0F : 0.0F;
		});

		Item[] towerShields = {CItems.IRON_TOWER_SHIELD, CItems.GOLDEN_TOWER_SHIELD, CItems.BRONZE_TOWER_SHIELD, CItems.STEEL_TOWER_SHIELD, CItems.LOZYNE_TOWER_SHIELD};
		Item[] roundShields = {CItems.IRON_ROUND_SHIELD, CItems.GOLDEN_ROUND_SHIELD, CItems.BRONZE_ROUND_SHIELD, CItems.STEEL_ROUND_SHIELD, CItems.LOZYNE_ROUND_SHIELD, CItems.ROUND_SHIELD};
		for (Item towerShield : towerShields) {
			ItemProperties.register(towerShield, new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_, seed) -> {
				return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F;
			});
		}
		for (Item roundShield : roundShields) {
			ItemProperties.register(roundShield, new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_, seed) -> {
				return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F;
			});
		}

		ItemProperties.register(CItems.SPEAR, new ResourceLocation("throwing"), (p_239419_0_, p_239419_1_, p_239419_2_, seed) -> {
			return p_239419_2_ != null && p_239419_2_.isUsingItem() && p_239419_2_.getUseItem() == p_239419_0_ ? 1.0F : 0.0F;
		});

		ItemProperties.register(CItems.DESERT_DRAGON, new ResourceLocation("tilt"), (p_210310_0_, p_210310_1_, p_210310_2_, seed) -> {
			return p_210310_2_ != null && p_210310_2_.isUsingItem() && p_210310_2_.getUseItem() == p_210310_0_ ? 1.0F : 0.0F;
		});

		ItemProperties.register(CItems.ETHERION_COMPASS, new ResourceLocation("angle"), new CompassItemPropertyFunction((p_234983_, p_234984_, p_234985_) -> {
	         if (p_234985_ instanceof Player player) {
	             return GlobalPos.of(player.level.dimension() == Level.OVERWORLD ? Level.OVERWORLD : CLevel.ACROTLEST, CombatEntityStats.getNearestEtherionTowerPos(player));
	          } else {
	             return null;
	          }
	       }));

		ItemProperties.register(CItems.MAGIC_ORB, new ResourceLocation("affinity"), new ItemPropertyFunction() {

			@OnlyIn(Dist.CLIENT)
			public float call(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) {
				boolean flag = entity != null;
				if (flag) {
					boolean flag2 = entity.getMainHandItem() == stack || entity.getOffhandItem() == stack;
					int d1 = SpellCategory.getNextStrongestElementalAffinities(entity)[0].ordinal();
					int d2 = SpellCategory.getNextStrongestElementalAffinities(entity)[1].ordinal();
					if (flag2) {
						float d0 = Float.parseFloat("0."+d1+d2);
						return d0;
					}
				}
				return 0;
			}
		});
		ItemProperties.register(CItems.LIGHT_SABER, new ResourceLocation("drawn"), (itemStack, world, living, seed) -> {
			return ((Mythril)itemStack.getItem()).isUsingEnergy(itemStack) ? 1.0F : 0.0F;
		});
		ItemProperties.register(CItems.MAGISTEEL_SWORD, new ResourceLocation("powered"), (itemStack, world, living, seed) -> {
			return ((Magisteel)itemStack.getItem()).isUsingMana(itemStack) ? 1.0F : 0.0F;
		});
		ItemProperties.register(CItems.MAGISTEEL_AXE, new ResourceLocation("powered"), (itemStack, world, living, seed) -> {
			return ((Magisteel)itemStack.getItem()).isUsingMana(itemStack) ? 1.0F : 0.0F;
		});
	}
}
