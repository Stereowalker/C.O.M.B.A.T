package com.stereowalker.combat.api.spell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.api.spell.Spell.CastType;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.enchantment.CEnchantmentHelper;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.item.AbstractMagicCastingItem;
import com.stereowalker.combat.item.AbstractSpellBookItem;
import com.stereowalker.combat.item.AccessoryItemCheck;
import com.stereowalker.combat.item.CItems;
import com.stereowalker.combat.potion.CEffects;
import com.stereowalker.combat.spell.SpellStats;
import com.stereowalker.combat.spell.Spells;
import com.stereowalker.combat.util.CDamageSource;
import com.stereowalker.unionlib.util.TextHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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
			itemIn.removeChildTag("Spell");
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
	public static Spell getWeightedRandomSpell(Random rand) {
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
	 * @param categories the filter to apply
	 * @return A weighted random {@link Spell} based on the applied filter
	 */
	public static Spell getWeightedRandomSpell(Random rand, SpellCategory... categories) {
		List<Spell> elegibleSpells = new ArrayList<Spell>();
		for (Spell spell : CombatRegistries.SPELLS) {
			for (SpellCategory category : categories) {
				if (spell.getCategory() == category) {
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
	 * @param castTypes the filter to apply
	 * @return A weighted random {@link Spell} based on the applied filter
	 */
	public static Spell getWeightedRandomSpell(Random rand, CastType... castTypes) {
		List<Spell> elegibleSpells = new ArrayList<Spell>();
		for (Spell spell : CombatRegistries.SPELLS) {
			for (CastType castType : castTypes) {
				if (spell.getCastType() == castType) {
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

	public static boolean canItemCastSpell(ItemStack stack, Spell spell) {
		if(stack.getItem() instanceof AbstractMagicCastingItem) {
			AbstractMagicCastingItem wand = (AbstractMagicCastingItem)stack.getItem();
			if ((spell.getRank().ordinal() <= wand.getTier().ordinal()) && ((stack.getDamage() < stack.getMaxDamage() - 1 && stack.isDamageable()) || !stack.isDamageable())) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean canEntityCastSpell(LivingEntity entity, Spell spell, double costModifier) {
		boolean isAvailable = false;
		if (!entity.isPotionActive(CEffects.MAGIC_JAMMING) && !CombatEntityStats.getSpellStats(entity, spell).onCooldown()) isAvailable = true;
		if(isAvailable) {
			if (entity instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)entity;
				if (player.abilities.isCreativeMode) return true;
				else if (Config.MAGIC_COMMON.toggle_affinities.get()) {
					if (spell.getCategory() == SpellCategory.BLOOD) {
						return true;
					}
					if (spell.getCategory() == SpellCategory.FIRE && AccessoryItemCheck.isEquipped(player, CItems.PYROMANCER_RING)) {
						return canDecrementMana(player, spell, costModifier);
					}
					else if (spell.getCategory() == SpellCategory.WATER && AccessoryItemCheck.isEquipped(player, CItems.HYDROMANCER_RING)) {
						return canDecrementMana(player, spell, costModifier);
					}
					else if (spell.getCategory() == SpellCategory.LIGHTNING && AccessoryItemCheck.isEquipped(player, CItems.ELECTROMANCER_RING)) {
						return canDecrementMana(player, spell, costModifier);
					}
					else if (spell.getCategory() == SpellCategory.EARTH && AccessoryItemCheck.isEquipped(player, CItems.TERRAMANCER_RING)) {
						return canDecrementMana(player, spell, costModifier);
					}
					else if (spell.getCategory() == SpellCategory.WIND && AccessoryItemCheck.isEquipped(player, CItems.AEROMANCER_RING)) {
						return canDecrementMana(player, spell, costModifier);
					}
					else if (spell.getCategory() == SpellCategory.NONE) {
						return canDecrementMana(player, spell, costModifier);
					}
					else if ((spell.getCategory() == CombatEntityStats.getElementalAffinity(player) || spell.getCategory() == CombatEntityStats.getSubElementalAffinity1(player) || spell.getCategory() == CombatEntityStats.getSubElementalAffinity2(player) || spell.getCategory() == CombatEntityStats.getLifeAffinity(player) || spell.getCategory() == CombatEntityStats.getSpecialAffinity(player))) {
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
		return CombatEntityStats.getMana(entity) >= getModifiedSpellCost(spell, costModifier) && !(entity.getHeldItem(Hand.MAIN_HAND).getItem() instanceof AbstractSpellBookItem || entity.getHeldItem(Hand.OFF_HAND).getItem() instanceof AbstractSpellBookItem);
	}

	/**
	 * If no correct spell is found, returns the default one : Spell.NONE
	 */
	public static Spell getSpellFromNBT(@Nullable CompoundNBT tag) {
		return tag == null ? Spells.NONE : SpellUtil.getSpellFromName(tag.getString("Spell"));
	}

	@OnlyIn(Dist.CLIENT)
	public static void addEffects(LivingEntity caster, Spell spell) {
		World world = caster.world;
		if(caster instanceof PlayerEntity) world.playSound((PlayerEntity) caster, caster.getPosition().getX(), caster.getPosition().getY(), caster.getPosition().getZ(), SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.PLAYERS, 10.0F, 2.0F);
	}

	@OnlyIn(Dist.CLIENT)
	public static void printErrorMessages(LivingEntity caster, ItemStack stack, Spell spell) {
		PlayerEntity player = (PlayerEntity)caster;
		World world = caster.world;
		AbstractMagicCastingItem stackItem = (AbstractMagicCastingItem) stack.getItem();
		boolean sendStatus = true;
		if(world.isRemote) {
//			Blocks.SNOW
			//TODO: Remove Cooldown
			if (AbstractSpellBookItem.getMainSpellBook(player).isEmpty()) {
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("spell.fail.no_book"), sendStatus);
			} else if (CombatEntityStats.getSpellStats(player, spell).getCooldown() > 0) {
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("spell.fail.on_cooldown", CombatEntityStats.getSpellStats(player, spell).getCooldown(), spell.getColoredName(SpellStats.getSpellKnowledge(player, spell))), sendStatus);
			} else if ( spell.getRank().ordinal() > stackItem.getTier().ordinal()) {
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("spell.fail.too_advanced", spell.getRank().getDisplayName()), sendStatus);
			} else if (getModifiedSpellCost(spell, stackItem.getCostModifier()) > CombatEntityStats.getMana(caster)) {
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("spell.fail.not_enough_mana"), sendStatus);
			} else if (!AbstractSpellBookItem.doesSpellBookContainSpell(player, spell)) {
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("spell.fail.not_in_book"), sendStatus);
			} else if (caster.isPotionActive(CEffects.MAGIC_JAMMING)) {
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("spell.fail.jammed"), sendStatus);
			} else if (stack.getDamage() >= stack.getMaxDamage() - 1 && stack.isDamageable()) {
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("spell.fail.broken_wand"), sendStatus);
			} else if (Config.MAGIC_COMMON.toggle_affinities.get() && !(spell.getCategory() == CombatEntityStats.getElementalAffinity(player) || spell.getCategory() == CombatEntityStats.getSubElementalAffinity1(player) || spell.getCategory() == CombatEntityStats.getSubElementalAffinity2(player) || spell.getCategory() == CombatEntityStats.getLifeAffinity(player) || spell.getCategory() == CombatEntityStats.getSpecialAffinity(player)) && spell != Spells.NONE) {
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("spell.fail.no_affinity", articulatedComponent(spell.getCategory().getColoredDisplayName(), false, false)), sendStatus);
			} else if (caster.getHeldItem(Hand.MAIN_HAND).getItem() instanceof AbstractSpellBookItem) {//TODO: Use UnionLib Articulated component
				Combat.debug("Book In Hand");
			} else Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("spell.fail.unknown"),  sendStatus);
		}
	}
	
	//TODO: Use UnionLib Articulated component
	public static ITextComponent articulatedComponent(ITextComponent component, boolean iaArticleCapital, boolean shouldArticleUseComponentStyle) {
		IFormattableTextComponent comp;
		
		if (iaArticleCapital) {
			comp = new StringTextComponent(TextHelper.isFirstLeterVowel(component) ? "An " : "A ");
		}
		else {
			comp = new StringTextComponent(TextHelper.isFirstLeterVowel(component) ? "an " : "a ");
		}
		
		if (shouldArticleUseComponentStyle && component.getStyle() != null) {
			comp = (comp.mergeStyle(component.getStyle())).appendSibling(component);
		}
		else {
			comp = comp.appendSibling(component);
		}
		
		return comp;
	}

	public static float getModifiedSpellCost(Spell spell, double costModifier) {
		return (spell.getCost()+(MathHelper.floor(spell.getCost()*costModifier)));
	}

	public static void spellItemEffects(LivingEntity caster, ItemStack stack, Spell spell) {
		AbstractMagicCastingItem stackItem = (AbstractMagicCastingItem) stack.getItem();
		if (!caster.world.isRemote) {
			if (!CombatEntityStats.getSpellStats(caster, spell).isKnown()) {
				SpellStats.setSpellKnown(caster, spell, true);
			}
			SpellStats.addTimesCast(caster, spell);
			if (spell.getCategory() == SpellCategory.BLOOD) {
				caster.attackEntityFrom(CDamageSource.BLOOD_MAGIC, getModifiedSpellCost(spell, stackItem.getCostModifier()));
			}
			else {
				if(!((PlayerEntity)caster).abilities.isCreativeMode && stackItem.getTier() != Rank.GOD) {
					if (!spell.canBePrimed() || (spell.canBePrimed() && CombatEntityStats.getSpellStats(caster, spell).isPrimed()))
						CombatEntityStats.addMana(caster, -getModifiedSpellCost(spell, stackItem.getCostModifier()));
					if (!CEnchantmentHelper.hasNoCooldown(stack) && (!spell.canBePrimed() || (spell.canBePrimed() && !CombatEntityStats.getSpellStats(caster, spell).isPrimed()))) {
						int lvl = CEnchantmentHelper.getCooldownReductionModifier(stack);
						lvl = MathHelper.clamp(lvl, 0, 4);
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
		target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20 * 20, 1, false, false));
		target.attackEntityFrom(CDamageSource.causeLightningDamage(attacker), target.isWet() ? attackDamage*2 : attackDamage);
		if (attacker.isServerWorld()) {			
			if (causeLightning)
				target.causeLightningStrike((ServerWorld)attacker.world,null);
			target.extinguish();
		}
		List<LivingEntity> affectedEntities = target.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(target.getPosition().offset(Direction.NORTH, chainRange).offset(Direction.WEST, chainRange).offset(Direction.UP, chainRange), target.getPosition().offset(Direction.SOUTH, chainRange).offset(Direction.EAST, chainRange).offset(Direction.DOWN, chainRange)));
		affectedEntities.remove(attacker);
		int i = 0;
		for (LivingEntity chainTarget : affectedEntities) {
			if (i == chainAmount) {
				break;
			}
			lightningAttack(chainTarget, attacker, MathHelper.clamp(attackDamage-(attackDamage/2), 0, attackDamage), false, MathHelper.clamp(chainAmount-1, 0, chainAmount), MathHelper.clamp(chainRange-1, 0, chainRange));
			i++;
		}
	}

	public static void fireAttack(LivingEntity target, LivingEntity attacker, float attackDamage) {
		if (target.getActivePotionEffect(Effects.FIRE_RESISTANCE) == null) {
			target.setFire((int) attackDamage);
			target.attackEntityFrom(CDamageSource.causeFireDamage(attacker), attackDamage);
		}
	}

	public static void iceAttack(LivingEntity target, LivingEntity attacker, float attackDamage) {
		setIce(target, (int) attackDamage);
		target.attackEntityFrom(CDamageSource.causeIceDamage(attacker), attackDamage);
	}
	
	public static void setIce(Entity entity, int seconds) {
		if (entity instanceof LivingEntity)
		((LivingEntity)entity).addPotionEffect(new EffectInstance(CEffects.FROSTBITE, seconds * 20, 1, false, false));
	}

	public static void waterAttack(LivingEntity target, LivingEntity attacker, float attackDamage) {
		target.attackEntityFrom(CDamageSource.causeWaterDamage(attacker), attackDamage + 1.0F);
	}

	public static void earthAttack(LivingEntity target, LivingEntity attacker, float attackDamage) {
		target.attackEntityFrom(CDamageSource.causeEarthDamage(attacker), attackDamage + (attackDamage*0.5F));
	}

	public static void airAttack(LivingEntity target, LivingEntity attacker, float attackDamage) {
		target.attackEntityFrom(CDamageSource.causeAirDamage(attacker), attackDamage);
		double xRatio = attacker.getPosX() - target.getPosX();
		double zRatio = attacker.getPosZ() - target.getPosZ();
		target.applyKnockback(0.75F, xRatio, zRatio);
	}
}
