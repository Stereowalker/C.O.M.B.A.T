package com.stereowalker.combat.api.world.spellcraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.api.world.spellcraft.Spell.CastType;
import com.stereowalker.combat.world.damagesource.CDamageSource;
import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.item.AbstractMagicCastingItem;
import com.stereowalker.combat.world.item.AbstractSpellBookItem;
import com.stereowalker.combat.world.item.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.world.spellcraft.SpellStats;
import com.stereowalker.combat.world.spellcraft.Spells;
import com.stereowalker.unionlib.util.TextHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpellUtil {

	public static Spell getSpellFromId(int id) {
		if(id <= CombatRegistries.SPELLS.getValues().size()) {
			return (Spell) CombatRegistries.SPELLS.getValues().toArray()[id];
		}
		return Spells.NONE;
	}

	public static Spell getSpellFromName(String name) {
		for(Spell spell : CombatRegistries.SPELLS) {
			if(spell.getKey().contentEquals(name)) {
				return spell;
			}
		}
		return Spells.NONE;
	}

	public static Spell getSpellFromItem(ItemStack stack) {
		return SpellUtil.getSpellFromNBT(stack.getOrCreateTag());
	}

	public static ItemStack addSpellToStack(ItemStack itemIn, Spell spellIn) {
		if (spellIn == Spells.NONE) {
			itemIn.removeTagKey("Spell");
		} else {
			itemIn.getOrCreateTag().putString("Spell", spellIn.getKey());
		}
		return itemIn;
	}

	public static boolean itemHasSpell(ItemStack stack) {
		return getSpellFromItem(stack) != Spells.NONE;
	}

	/**
	 * @return A random {@link Spell}
	 */
	public static Spell getRandomSpell(Random rand) {
		return getSpellFromId(rand.nextInt(CombatRegistries.SPELLS.getValues().size()));
	}

	/**
	 * @param tiers the filter to apply
	 * @return A random {@link Spell} based on the applied filter
	 */
	public static Spell getRandomSpell(Random rand, Rank... tiers) {
		List<Spell> elegibleSpells = new ArrayList<Spell>();
		for (Spell spell : CombatRegistries.SPELLS) {
			for (Rank tier : tiers) {
				if (spell.getRank() == tier) {
					if (!elegibleSpells.contains(spell))
						elegibleSpells.add(spell);
				}
			}
		}
		return elegibleSpells.get(rand.nextInt(elegibleSpells.size()));
	}

	/**
	 * @param categories the filter to apply
	 * @return A random {@link Spell} based on the applied filter
	 */
	public static Spell getRandomSpell(Random rand, SpellCategory... categories) {
		List<Spell> elegibleSpells = new ArrayList<Spell>();
		for (Spell spell : CombatRegistries.SPELLS) {
			for (SpellCategory category : categories) {
				if (spell.getCategory() == category) {
					if (!elegibleSpells.contains(spell))
						elegibleSpells.add(spell);
				}
			}
		}
		return elegibleSpells.get(rand.nextInt(elegibleSpells.size()));
	}

	/**
	 * @param castTypes the filter to apply
	 * @return A random {@link Spell} based on the applied filter
	 */
	public static Spell getRandomSpell(Random rand, CastType... castTypes) {
		List<Spell> elegibleSpells = new ArrayList<Spell>();
		for (Spell spell : CombatRegistries.SPELLS) {
			for (CastType castType : castTypes) {
				if (spell.getCastType() == castType) {
					if (!elegibleSpells.contains(spell))
						elegibleSpells.add(spell);
				}
			}
		}
		return elegibleSpells.get(rand.nextInt(elegibleSpells.size()));
	}

	/**
	 * @return A weighted random {@link Spell}
	 */
	public static Spell getWeightedRandomSpell(RandomSource rand) {
		int totalWeight = 0;
		for (Spell spell : CombatRegistries.SPELLS) {
			totalWeight += spell.getWeight();
		}
		int randomSpell = rand.nextInt(totalWeight);
		int i = 0;
		for (Spell spell : CombatRegistries.SPELLS) {
			i += spell.getWeight();
			if (i >= randomSpell) {
				return spell;
			}
		}
		return Spells.NONE;
	}


	/**
	 * @param tiers the filter to apply
	 * @return A weighted random {@link Spell} based on the applied filter
	 */
	public static Spell getWeightedRandomSpell(Random rand, Rank... tiers) {
		List<Spell> elegibleSpells = new ArrayList<Spell>();
		for (Spell spell : CombatRegistries.SPELLS) {
			for (Rank tier : tiers) {
				if (spell.getRank() == tier) {
					if (!elegibleSpells.contains(spell))
						elegibleSpells.add(spell);
				}
			}
		}
		int totalWeight = 0;
		for (Spell spell : elegibleSpells) {
			totalWeight += spell.getWeight();
		}
		int randomSpell = rand.nextInt(totalWeight);
		int i = 0;
		for (Spell spell : elegibleSpells) {
			i += spell.getWeight();
			if (i >= randomSpell) {
				return spell;
			}
		}
		return Spells.NONE;
	}

	/**
	 * @param categories The {@link SpellCategory} to filter for
	 * @param rand The random function
	 * @return A weighted random {@link Spell} based on the applied filter and the weights of the spells themselves
	 */
	public static Spell getWeightedRandomSpell(RandomSource rand, SpellCategory... categories) {
		return getWeightedRandomSpell(rand, null, categories, null);
	}

	/**
	 * If any of the filters are null or empty, it will skip that particular one
	 * @param castTypes The {@link CastType} to filter for
	 * @param rand The random function
	 * @return A weighted random {@link Spell} based on the applied filter and the weights of the spells themselves
	 */
	public static Spell getWeightedRandomSpell(RandomSource rand, CastType... castTypes) {
		return getWeightedRandomSpell(rand, castTypes, null, null);
	}
	
	/**
	 * If any of the filters are null or empty, it will skip that particular one
	 * @param castTypes The {@link CastType} to filter for
	 * @param categories The {@link SpellCategory} to filter for
	 * @param rand The random function
	 * @param ranks the filter to apply if null or empty, it will ignore the filter
	 * @return A weighted random {@link Spell} based on the applied filter and the weights of the spells themselves
	 */
	public static Spell getWeightedRandomSpell(RandomSource rand, CastType[] castTypes, SpellCategory[] categories, Rank[] ranks) {
		List<CastType> types = castTypes == null ? null : Lists.newArrayList(castTypes);
		List<SpellCategory> categ = categories == null ? null : Lists.newArrayList(categories);
		List<Rank> rank = ranks == null ? null : Lists.newArrayList(ranks);
		return getWeightedRandomSpell(rand, types, categ, rank);
	}

	/**
	 * If any of the filters are null or empty, it will skip that particular one
	 * @param castTypes The {@link CastType} to filter for
	 * @param categories The {@link SpellCategory} to filter for
	 * @param rand The random function
	 * @param ranks the filter to apply if null or empty, it will ignore the filter
	 * @return A weighted random {@link Spell} based on the applied filter and the weights of the spells themselves
	 */
	public static Spell getWeightedRandomSpell(RandomSource rand, List<CastType> castTypes, List<SpellCategory> categories, List<Rank> ranks) {
		List<Spell> elegibleSpells = new ArrayList<Spell>();
		for (Spell spell : CombatRegistries.SPELLS) {
			if (!elegibleSpells.contains(spell) && (
					(ranks == null || ranks.isEmpty() || ranks.contains(spell.getRank())) && 
					(castTypes == null || castTypes.isEmpty() || castTypes.contains(spell.getCastType())) && 
					(categories == null || categories.isEmpty() || categories.contains(spell.getCategory()))
							)) {
				elegibleSpells.add(spell);
			}
		}
		int totalWeight = 0;
		for (Spell spell : elegibleSpells) {
			totalWeight += spell.getWeight();
		}
		int randomSpell = rand.nextInt(totalWeight);
		int i = 0;
		for (Spell spell : elegibleSpells) {
			i += spell.getWeight();
			if (i >= randomSpell) {
				return spell;
			}
		}
		return Spells.NONE;
	}

	public static boolean canItemCastSpell(ItemStack stack, Spell spell) {
		if(stack.getItem() instanceof AbstractMagicCastingItem) {
			AbstractMagicCastingItem wand = (AbstractMagicCastingItem)stack.getItem();
			if ((spell.getRank().ordinal() <= wand.getTier().ordinal()) && ((stack.getDamageValue() < stack.getMaxDamage() - 1 && stack.isDamageableItem()) || !stack.isDamageableItem())) {
				return true;
			}
		}
		return false;
	}

	public static boolean canEntityCastSpell(LivingEntity entity, Spell spell, double costModifier) {
		boolean isAvailable = false;
		if (!entity.hasEffect(CMobEffects.MAGIC_JAMMING) && !CombatEntityStats.getSpellStats(entity, spell).onCooldown()) isAvailable = true;
		if(isAvailable) {
			if (entity instanceof Player) {
				Player player = (Player)entity;
				if (player.getAbilities().instabuild) return true;
				else if (Combat.MAGIC_CONFIG.toggle_affinities) {
					if (spell.getCategory() == SpellCategory.BLOOD) {
						return true;
					}
					if (spell.getCategory() == SpellCategory.NONE) {
						return canDecrementMana(player, spell, costModifier);
					}
					else if ((SpellCategory.canAccessElementalAffinity(player, spell.getCategory()) || spell.getCategory() == CombatEntityStats.getPrimevalAffinity(player))) {
						return canDecrementMana(player, spell, costModifier);
					} else {
						return false;
					}
				}
				else return canDecrementMana(player, spell, costModifier);
			}
			return canDecrementMana(entity, spell, costModifier);
		}
		else return false;
	}

	public static boolean canDecrementMana(LivingEntity entity, Spell spell, double costModifier) {
		return CombatEntityStats.getMana(entity) >= getModifiedSpellCost(spell, costModifier) && !(entity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AbstractSpellBookItem || entity.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof AbstractSpellBookItem);
	}

	/**
	 * If no correct spell is found, returns the default one : Spell.NONE
	 */
	public static Spell getSpellFromNBT(@Nullable CompoundTag tag) {
		return tag == null ? Spells.NONE : SpellUtil.getSpellFromName(tag.getString("Spell"));
	}

	@OnlyIn(Dist.CLIENT)
	public static void addEffects(LivingEntity caster, Spell spell) {
		Level world = caster.level;
		if(caster instanceof Player) world.playSound((Player) caster, (double)caster.blockPosition().getX(), (double)caster.blockPosition().getY(), (double)caster.blockPosition().getZ(), SoundEvents.NOTE_BLOCK_CHIME.get(), SoundSource.PLAYERS, 10.0F, 2.0F);
	}

	@OnlyIn(Dist.CLIENT)
	public static void printErrorMessages(LivingEntity caster, ItemStack stack, Spell spell) {
		Minecraft mc = Minecraft.getInstance();
		Player player = (Player)caster;
		Level world = caster.level;
		AbstractMagicCastingItem stackItem = (AbstractMagicCastingItem) stack.getItem();
		boolean sendStatus = true;
		if(world.isClientSide) {
			//			Blocks.SNOW
			//TODO: Remove Cooldown
			if (AbstractSpellBookItem.getMainSpellBook(player).isEmpty()) {
				mc.player.displayClientMessage(Component.translatable("spell.fail.no_book"), sendStatus);
			} else if (CombatEntityStats.getSpellStats(player, spell).getCooldown() > 0) {
				mc.player.displayClientMessage(Component.translatable("spell.fail.on_cooldown", CombatEntityStats.getSpellStats(player, spell).getCooldown(), spell.getColoredName(SpellStats.getSpellKnowledge(player, spell))), sendStatus);
			} else if ( spell.getRank().ordinal() > stackItem.getTier().ordinal()) {
				mc.player.displayClientMessage(Component.translatable("spell.fail.too_advanced", spell.getRank().getDisplayName()), sendStatus);
			} else if (getModifiedSpellCost(spell, stackItem.getCostModifier()) > CombatEntityStats.getMana(caster)) {
				mc.player.displayClientMessage(Component.translatable("spell.fail.not_enough_mana"), sendStatus);
			} else if (!AbstractSpellBookItem.doesSpellBookContainSpell(player, spell)) {
				mc.player.displayClientMessage(Component.translatable("spell.fail.not_in_book"), sendStatus);
			} else if (caster.hasEffect(CMobEffects.MAGIC_JAMMING)) {
				mc.player.displayClientMessage(Component.translatable("spell.fail.jammed"), sendStatus);
			} else if (stack.getDamageValue() >= stack.getMaxDamage() - 1 && stack.isDamageableItem()) {
				mc.player.displayClientMessage(Component.translatable("spell.fail.broken_wand"), sendStatus);
			} else if (Combat.MAGIC_CONFIG.toggle_affinities && !(SpellCategory.canAccessElementalAffinity(player, spell.getCategory()) || spell.getCategory() == CombatEntityStats.getPrimevalAffinity(player)) && spell != Spells.NONE) {
				mc.player.displayClientMessage(Component.translatable("spell.fail.no_affinity", articulatedComponent(spell.getCategory().getColoredDisplayName(), false, false)), sendStatus);
			} else if (caster.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AbstractSpellBookItem) {//TODO: Use UnionLib Articulated component
				Combat.debug("Book In Hand");
			} else mc.player.displayClientMessage(Component.translatable("spell.fail.unknown"),  sendStatus);
		}
	}

	//TODO: Use UnionLib Articulated component
	public static Component articulatedComponent(Component component, boolean iaArticleCapital, boolean shouldArticleUseComponentStyle) {
		MutableComponent comp;

		if (iaArticleCapital) {
			comp = Component.literal(TextHelper.isFirstLeterVowel(component) ? "An " : "A ");
		}
		else {
			comp = Component.literal(TextHelper.isFirstLeterVowel(component) ? "an " : "a ");
		}

		if (shouldArticleUseComponentStyle && component.getStyle() != null) {
			comp = (comp.withStyle(component.getStyle())).append(component);
		}
		else {
			comp = comp.append(component);
		}

		return comp;
	}

	public static float getModifiedSpellCost(Spell spell, double costModifier) {
		return (spell.getCost()+(Mth.floor(spell.getCost()*costModifier)));
	}

	public static void spellItemEffects(LivingEntity caster, ItemStack stack, Spell spell) {
		AbstractMagicCastingItem stackItem = (AbstractMagicCastingItem) stack.getItem();
		if (!caster.level.isClientSide) {
			if (!CombatEntityStats.getSpellStats(caster, spell).isKnown()) {
				SpellStats.setSpellKnown(caster, spell, true);
			}
			SpellStats.addTimesCast(caster, spell);
			if (spell.getCategory() == SpellCategory.BLOOD) {
				caster.hurt(CDamageSource.BLOOD_MAGIC, getModifiedSpellCost(spell, stackItem.getCostModifier()));
			}
			else {
				if(!((Player)caster).getAbilities().instabuild && stackItem.getTier() != Rank.GOD) {
					if (!spell.canBePrimed() || (spell.canBePrimed() && CombatEntityStats.getSpellStats(caster, spell).isPrimed()))
						CombatEntityStats.addMana(caster, -getModifiedSpellCost(spell, stackItem.getCostModifier()));
					if (!CEnchantmentHelper.hasNoCooldown(stack) && (!spell.canBePrimed() || (spell.canBePrimed() && !CombatEntityStats.getSpellStats(caster, spell).isPrimed()))) {
						int lvl = CEnchantmentHelper.getCooldownReductionModifier(stack);
						lvl = Mth.clamp(lvl, 0, 4);
						float removedCooldown = spell.getCooldown() * (lvl * 0.2F);
						int newCooldown = (int) (spell.getCooldown() - removedCooldown);
						//TODO: Remove Cooldown from Item
						//						stackItem.setCooldown(stack, 1, newCooldown);
						SpellStats.setCooldown(caster, spell, newCooldown, false);

					}
				}
			}
		}
	}

	public static void lightningAttack(LivingEntity target, LivingEntity attacker, float attackDamage, boolean causeLightning, int chainRange, int chainAmount) {
		target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 20, 1, false, false));
		target.hurt(CDamageSource.causeLightningDamage(attacker), target.isInWaterOrRain() ? attackDamage*2 : attackDamage);
		if (attacker.isEffectiveAi()) {			
			if (causeLightning) {
				LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(attacker.level);
				lightningboltentity.setDamage(0);
				target.thunderHit((ServerLevel)attacker.level,lightningboltentity);
			}
			target.clearFire();
		}
		List<LivingEntity> affectedEntities = target.level.getEntitiesOfClass(LivingEntity.class, new AABB(target.blockPosition().relative(Direction.NORTH, chainRange).relative(Direction.WEST, chainRange).relative(Direction.UP, chainRange), target.blockPosition().relative(Direction.SOUTH, chainRange).relative(Direction.EAST, chainRange).relative(Direction.DOWN, chainRange)));
		affectedEntities.remove(attacker);
		int i = 0;
		for (LivingEntity chainTarget : affectedEntities) {
			if (i == chainAmount) {
				break;
			}
			lightningAttack(chainTarget, attacker, Mth.clamp(attackDamage-(attackDamage/2), 0, attackDamage), false, Mth.clamp(chainAmount-1, 0, chainAmount), Mth.clamp(chainRange-1, 0, chainRange));
			i++;
		}
	}

	public static void fireAttack(LivingEntity target, LivingEntity attacker, float attackDamage) {
		if (target.getEffect(MobEffects.FIRE_RESISTANCE) == null) {
			target.setSecondsOnFire((int) attackDamage);
			target.hurt(CDamageSource.causeFireDamage(attacker), attackDamage);
		}
	}

	public static void iceAttack(LivingEntity target, LivingEntity attacker, float attackDamage) {
		setIce(target, (int) attackDamage);
		target.hurt(CDamageSource.causeIceDamage(attacker), attackDamage);
	}

	public static void setIce(Entity entity, int seconds) {
		if (entity instanceof LivingEntity)
			((LivingEntity)entity).addEffect(new MobEffectInstance(CMobEffects.FROSTBITE, seconds * 20, 1, false, false));
	}

	public static void waterAttack(LivingEntity target, LivingEntity attacker, float attackDamage) {
		target.hurt(CDamageSource.causeWaterDamage(attacker), attackDamage + 1.0F);
	}

	public static void earthAttack(LivingEntity target, LivingEntity attacker, float attackDamage) {
		target.hurt(CDamageSource.causeEarthDamage(attacker), attackDamage + (attackDamage*0.5F));
	}

	public static void airAttack(LivingEntity target, LivingEntity attacker, float attackDamage) {
		target.hurt(CDamageSource.causeAirDamage(attacker), attackDamage);
		double xRatio = attacker.getX() - target.getX();
		double zRatio = attacker.getZ() - target.getZ();
		target.knockback(0.75F, xRatio, zRatio);
	}
}
