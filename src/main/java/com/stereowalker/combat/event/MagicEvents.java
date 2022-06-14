package com.stereowalker.combat.event;

import com.stereowalker.combat.compat.origins.OriginsCompat;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.level.biome.CBiomes;
import com.stereowalker.combat.world.spellcraft.SpellStats;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fml.ModList;

public class MagicEvents {

	public static void manaUpdate(LivingEntity entity) {
		//Manage the amount of mana the player has
		if (!entity.level.isClientSide && entity instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer)entity;
			if (player != null && player.getAttribute(CAttributes.MAX_MANA) != null) {
				if (CombatEntityStats.getMana(player) <= 2) {
					if (!player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1, false, false, false));
					if (!player.hasEffect(MobEffects.HUNGER)) player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 40, 1, false, false, false));
					if (!player.hasEffect(MobEffects.WEAKNESS)) player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 1, false, false, false));
				}
				if (player.tickCount%40 == 20) {
					boolean shouldRegenerateMana = CombatEntityStats.getMana(player) < player.getAttributeValue(CAttributes.MAX_MANA);
					if (ModList.get().isLoaded("origins")) {
						if (OriginsCompat.hasNoManaAbsorbers(player)) {
							if (!CombatEntityStats.hasManabornBonus(player)) {
								CombatEntityStats.setMana(player, Mth.floor(player.getAttributeValue(CAttributes.MAX_MANA)));
								CombatEntityStats.setManabornBonus(player, true);
							}
							shouldRegenerateMana = false;
						}
						if (OriginsCompat.hasAlteredRegeneration(player)) {
							if (player.getHealth() > 0.0F && player.getHealth() < player.getMaxHealth()) {
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
						if (CombatEntityStats.getMana(player) > Mth.floor(player.getAttributeValue(CAttributes.MAX_MANA))) {
							CombatEntityStats.setMana(player, Mth.floor(player.getAttributeValue(CAttributes.MAX_MANA)));
						}
						double multiplier;
						if (CBiomes.getMagicBiomes().contains(entity.level.getBiome(entity.blockPosition()).value().getRegistryName())) multiplier = 1.0D;
						else multiplier = 0.1D;
						if (!player.getFoodData().needsFood()) multiplier*=5.0D;
						float mana = (float) (multiplier*player.getAttributeValue(CAttributes.MANA_REGENERATION));
						CombatEntityStats.addMana(entity, mana);
					}
				}
			}
		}

		//Manage the cooldown for all player spells
		if (entity instanceof Player)
			CombatEntityStats.getSpellStats(entity).forEach((spell, stats) -> {
				if (stats.getCooldown() > 0 && entity.tickCount%20==0 && !entity.level.isClientSide) {
					SpellStats.setCooldown(entity, spell, stats.getCooldown()-1, stats.onCooldown());
				}
			});
	}

	public static void replenishManaOnSleep(LevelAccessor iWorld) {
		for (Player player : iWorld.players()) {
			boolean shouldRegenerateMana = true;
			if (ModList.get().isLoaded("origins")) {
				if (OriginsCompat.hasNoManaAbsorbers(player)) {
					if (!CombatEntityStats.hasManabornBonus(player)) {
						shouldRegenerateMana = false;
					}
				}
			}
			if (shouldRegenerateMana)
				CombatEntityStats.setMana(player, Mth.floor(player.getAttributeValue(CAttributes.MAX_MANA)));
		}
	}
}
