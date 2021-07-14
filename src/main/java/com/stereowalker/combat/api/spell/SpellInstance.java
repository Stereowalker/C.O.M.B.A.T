package com.stereowalker.combat.api.spell;

import java.util.UUID;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.spell.Spell.CastType;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.spell.SpellStats;
import com.stereowalker.combat.spell.Spells;
import com.stereowalker.combat.util.UUIDS;
import com.stereowalker.combat.world.CGameRules;
import com.stereowalker.unionlib.util.NBTHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class SpellInstance {
	private Spell spell;
	private double strength;
	private Vector3d location;
	private Hand hand;
	private UUID casterID;

	public SpellInstance(Spell spellIn, double strengthIn, Vector3d locationIn, Hand handIn, UUID casterIDIn) {
		this.spell = spellIn;
		this.strength = strengthIn;
		this.location = locationIn;
		this.hand = handIn;
		this.casterID = casterIDIn;
	}

	public SpellInstance(Spell spellIn) {
		this(spellIn, 1.0D, Vector3d.ZERO, Hand.MAIN_HAND, UUIDS.EMPTY);
	}

	public Spell getSpell() {
		return spell != null ? spell : Spells.NONE;
	}

	public double getStrength() {
		return strength;
	}

	public Vector3d getLocation() {
		return location;
	}

	public Hand getHand() {
		if (hand == null)
			hand = Hand.MAIN_HAND;
		return hand;
	}

	public BlockPos getBlockPosLocation() {
		return new BlockPos(this.getLocation());
	}

	public UUID getCasterID() {
		return casterID;
	}

	public CompoundNBT write(CompoundNBT nbt) {
		nbt.putString("Spell", this.getSpell().getKey());
		nbt.putDouble("Strength", this.getStrength());
		nbt.put("Location", NBTHelper.newDoubleNBTList(this.getLocation().getX(), this.getLocation().getY(), this.getLocation().getZ()));
		nbt.putInt("Hand", this.getHand().ordinal());
		nbt.putUniqueId("CasterID", this.getCasterID());
		return nbt;
	}

	public static SpellInstance read(CompoundNBT nbt) {
		UUID casterID = null;
		if (nbt.hasUniqueId("CasterID")) {
			casterID = nbt.getUniqueId("CasterID");
		}
		return new SpellInstance(SpellUtil.getSpellFromName(nbt.getString("Spell")), nbt.getDouble("Strength"), new Vector3d(nbt.getList("Location", 6).getDouble(0), nbt.getList("Location", 6).getDouble(1), nbt.getList("Location", 6).getDouble(2)), Hand.values()[nbt.getInt("Hand")], casterID);
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
					if (caster.world.isRemote) {
						if (this.getSpell().getFailedMessage(caster) != null) caster.sendMessage(this.getSpell().getFailedMessage(caster), Util.DUMMY_UUID);
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
		if (this.getSpell().getCastType() != CastType.RAY && !this.getSpell().isClientSpell()) {
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
			if (target.world.getGameRules().getBoolean(CGameRules.DO_MAGIC_FRIENDLY_FIRE)) {
				return true;
			} else {
				boolean flag = false;
				if (target instanceof TameableEntity) {
					TameableEntity pet = (TameableEntity)target;
					if (pet.isOwner(caster)) {
						flag = true;
					}
				}
				else if (target == caster) {
					flag = true;
				} 
				else if (caster.isOnSameTeam(target)){
					flag = true;
				}
				else {
					if (target instanceof PlayerEntity) {
						for (PlayerEntity ally : CombatEntityStats.getAllies(caster)) {
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
