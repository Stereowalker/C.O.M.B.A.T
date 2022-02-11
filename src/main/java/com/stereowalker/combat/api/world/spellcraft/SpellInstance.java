package com.stereowalker.combat.api.world.spellcraft;

import java.util.UUID;

import javax.annotation.Nullable;

import com.stereowalker.combat.util.UUIDS;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.level.CGameRules;
import com.stereowalker.combat.world.spellcraft.SpellStats;
import com.stereowalker.combat.world.spellcraft.Spells;
import com.stereowalker.unionlib.util.NBTHelper;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class SpellInstance {
	private Spell spell;
	private double strength;
	private Vec3 location;
	private InteractionHand hand;
	private UUID casterID;

	public SpellInstance(Spell spellIn, double strengthIn, Vec3 locationIn, InteractionHand handIn, UUID casterIDIn) {
		this.spell = spellIn;
		this.strength = strengthIn;
		this.location = locationIn;
		this.hand = handIn;
		this.casterID = casterIDIn;
	}

	public SpellInstance(Spell spellIn) {
		this(spellIn, 1.0D, Vec3.ZERO, InteractionHand.MAIN_HAND, UUIDS.EMPTY);
	}

	public Spell getSpell() {
		return spell != null ? spell : Spells.NONE;
	}

	public double getStrength() {
		return strength;
	}

	public Vec3 getLocation() {
		return location;
	}

	public InteractionHand getHand() {
		if (hand == null)
			hand = InteractionHand.MAIN_HAND;
		return hand;
	}

	public BlockPos getBlockPosLocation() {
		return new BlockPos(this.getLocation());
	}

	public UUID getCasterID() {
		return casterID;
	}

	public CompoundTag write(CompoundTag nbt) {
		nbt.putString("Spell", this.getSpell().getKey());
		nbt.putDouble("Strength", this.getStrength());
		nbt.put("Location", NBTHelper.newDoubleNBTList(this.getLocation().x, this.getLocation().y, this.getLocation().z));
		nbt.putInt("InteractionHand", this.getHand().ordinal());
		nbt.putUUID("CasterID", this.getCasterID());
		return nbt;
	}

	public static SpellInstance read(CompoundTag nbt) {
		UUID casterID = null;
		if (nbt.hasUUID("CasterID")) {
			casterID = nbt.getUUID("CasterID");
		}
		return new SpellInstance(SpellUtil.getSpellFromName(nbt.getString("Spell")), nbt.getDouble("Strength"), new Vec3(nbt.getList("Location", 6).getDouble(0), nbt.getList("Location", 6).getDouble(1), nbt.getList("Location", 6).getDouble(2)), InteractionHand.values()[nbt.getInt("InteractionHand")], casterID);
	}

	public boolean executeSpell(LivingEntity caster) {
		if (caster != null) {
			if (this.getSpell().canBePrimed() && !CombatEntityStats.getSpellStats(caster, this.getSpell()).isPrimed()) {
				if(this.getSpell().primingProgram(caster, this.getStrength(), this.getLocation(), this.getHand())) {
					SpellStats.setSpellPrimed(caster, this.getSpell(), true);
					return true;
				}
				else {
					return false;
				}
			} 
			else {
				if(this.getSpell().spellProgram(caster, this.getStrength(), this.getLocation(), this.getHand())) {
					if (this.getSpell().canBePrimed()) {
						SpellStats.setSpellPrimed(caster, this.getSpell(), false);
					}
					return true;
				}
				else{
					if (caster.level.isClientSide) {
						if (this.getSpell().getFailedMessage(caster) != null) caster.sendMessage(this.getSpell().getFailedMessage(caster), Util.NIL_UUID);
					}
					return false;
				}
			}
		} else return false;
	}

	public boolean executeExtensionSpell(LivingEntity caster, @Nullable Entity target) {
		if (caster != null) {
			if (this.getSpell() instanceof IExtensionSpell) {
				IExtensionSpell extension = (IExtensionSpell)this.getSpell();
				if (canTargetEntity(caster, target)) {
					return extension.extensionProgram(caster, target, this.getStrength(), this.getLocation(), this.getHand());
				}
			}
		}
		return false;
	}

	public boolean executeCommandSpell(LivingEntity caster) {
		if (this.getSpell().getCastType() != Spell.CastType.RAY && !this.getSpell().isClientSpell()) {
			if (this.getSpell().canBePrimed() && !CombatEntityStats.getSpellStats(caster, this.getSpell()).isPrimed()) {
				if(this.getSpell().primingProgram(caster, this.getStrength(), this.getLocation(), this.getHand())) {
					SpellStats.setSpellPrimed(caster, this.getSpell(), true);
					return true;
				}
				else return false;
			} 
			else {
				if(this.getSpell().spellProgram(caster, this.getStrength(), this.getLocation(), this.getHand())) {
					if (this.getSpell().canBePrimed()) {
						SpellStats.setSpellPrimed(caster, this.getSpell(), false);
					}
					return true;
				}
				else return false;
			}
		}
		return false;
	}

	public boolean canTargetEntity(LivingEntity caster, Entity target) {
		if (this.getSpell() instanceof TargetedSpell) {
			TargetedSpell spell = (TargetedSpell) getSpell();
			if (target.level.getGameRules().getBoolean(CGameRules.DO_MAGIC_FRIENDLY_FIRE)) {
				return true;
			} else {
				boolean flag = false;
				if (target instanceof TamableAnimal) {
					TamableAnimal pet = (TamableAnimal)target;
					if (pet.isOwnedBy(caster)) {
						flag = true;
					}
				}
				else if (target == caster) {
					flag = true;
				} 
				else if (caster.isAlliedTo(target)){
					flag = true;
				}
				else {
					if (target instanceof Player) {
						for (Player ally : CombatEntityStats.getAllies(caster)) {
							if (target == ally) {
								flag = true;
								break;
							}
						}
					}
				}
				return spell.isBeneficialSpell() == flag;
			}
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		String s;
		//	      if (this.amplifier > 0) {
		//	         s = this.getSpell().getDefaultTranslationKey() + " x " + (this.amplifier + 1) + ", Duration: " + this.duration;
		//	      } else {
		s = this.getSpell().getDefaultTranslationKey() + ", Strength: " + this.strength;
		//	      }

		//	      if (this.isSplashPotion) {
		//	         s = s + ", Splash: true";
		//	      }
		//
		//	      if (!this.showParticles) {
		//	         s = s + ", Particles: false";
		//	      }
		//
		//	      if (!this.showIcon) {
		//	         s = s + ", Show Icon: false";
		//	      }

		return s;
	}
}
