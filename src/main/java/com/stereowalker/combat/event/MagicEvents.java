package com.stereowalker.combat.event;

import com.stereowalker.combat.compat.origins.OriginsCompat;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.spell.SpellStats;
import com.stereowalker.combat.world.biome.CBiomes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.ModList;

public class MagicEvents {

	public static void manaUpdate(LivingEntity entity) {
		//Manage the amount of mana the player has
		if (!entity.world.isRemote && entity instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)entity;
			if (player != null && player.getAttribute(CAttributes.MAX_MANA) != null) {
				if (CombatEntityStats.getMana(player) <= 2) {
					if (!player.isPotionActive(Effects.SLOWNESS)) player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 40, 1, false, false, false));
					if (!player.isPotionActive(Effects.HUNGER)) player.addPotionEffect(new EffectInstance(Effects.HUNGER, 40, 1, false, false, false));
					if (!player.isPotionActive(Effects.WEAKNESS)) player.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 40, 1, false, false, false));
				}
				if (player.ticksExisted%40 == 20) {
					boolean shouldRegenerateMana = true;
					if (ModList.get().isLoaded("origins")) {
						if (OriginsCompat.hasNoManaAbsorbers(player)) {
							if (!CombatEntityStats.hasManabornBonus(player)) {
								System.out.println("Max Mana = "+MathHelper.floor(player.getAttributeValue(CAttributes.MAX_MANA)));
								CombatEntityStats.setMana(player, MathHelper.floor(player.getAttributeValue(CAttributes.MAX_MANA)));
								CombatEntityStats.setManabornBonus(player, true);
							}
							shouldRegenerateMana = false;
						}
						if (OriginsCompat.hasAlteredRegeneration(player)) {
							if (!CombatEntityStats.hasManabornBonus(player)) {
								System.out.println("Max Mana = "+MathHelper.floor(player.getAttributeValue(CAttributes.MAX_MANA)));
								CombatEntityStats.setMana(player, MathHelper.floor(player.getAttributeValue(CAttributes.MAX_MANA)));
								CombatEntityStats.setManabornBonus(player, true);
							}
							if (player.shouldHeal()) {
								if (player.getHealth() < player.getMaxHealth() - 1) {
									player.heal(1.0F);
									CombatEntityStats.addMana(player, -1.0F);
								} else {
									player.heal(player.getMaxHealth() - player.getHealth());
									CombatEntityStats.addMana(player, player.getHealth() - player.getMaxHealth());
								}
							}
						}
					}

					if (shouldRegenerateMana) {
						if (CombatEntityStats.getMana(player) > MathHelper.floor(player.getAttributeValue(CAttributes.MAX_MANA))) {
							CombatEntityStats.setMana(player, MathHelper.floor(player.getAttributeValue(CAttributes.MAX_MANA)));
						}
						float multiplier;
						if (CBiomes.getMagicBiomes().contains(entity.world.getBiome(entity.getPosition()).getRegistryName())) multiplier = 10;
						else multiplier = 0.02F;
						if (!player.getFoodStats().needFood()) multiplier*=5;
						float mana = multiplier*MathHelper.floor(player.getAttributeValue(CAttributes.MANA_REGENERATION));
						CombatEntityStats.addMana(entity, mana);
					}
				}
			}
		}

		//Manage the cooldown for all player spells
		if (entity instanceof PlayerEntity)
			CombatEntityStats.getSpellStats(entity).forEach((spell, stats) -> {
				if (stats.getCooldown() > 0 && entity.ticksExisted%20==0 && !entity.world.isRemote) {
					SpellStats.setCooldown(entity, spell, stats.getCooldown()-1, stats.onCooldown());
				}
			});
	}

	public static void replenishManaOnSleep(IWorld iWorld) {
		for (PlayerEntity player : iWorld.getPlayers()) {
			CombatEntityStats.setMana(player, MathHelper.floor(player.getAttributeValue(CAttributes.MAX_MANA)));
		}
	}
}
