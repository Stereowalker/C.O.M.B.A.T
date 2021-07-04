package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.entity.CombatEntityStats;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;


public class SpellStats {
	private Spell spell;
	private boolean isKnown;
	private boolean isPrimed;
	private int cooldown;
	private int maxCooldown;
	private int timesCast;
	
	public SpellStats (Spell spell, boolean isKnown, boolean isPrimed, int cooldown, int maxCooldown, int timesCast) {
		this.spell = spell;
		this.cooldown = cooldown;
		this.isKnown = isKnown;
		this.isPrimed = isPrimed;;
		this.maxCooldown = maxCooldown;
		this.timesCast = timesCast;
	}
	
	public static SpellStats read(CompoundNBT nbt) {
		return new SpellStats(SpellUtil.getSpellFromNBT(nbt), nbt.getBoolean("IsKnown"), nbt.getBoolean("IsPrimed"), nbt.getInt("Cooldown"), nbt.getInt("MaxCooldown"), nbt.getInt("TimesCast"));
	}
	
	public CompoundNBT write(CompoundNBT nbt) {
		nbt.putString("Spell", spell.getKey());
		nbt.putBoolean("IsKnown", isKnown);
		nbt.putBoolean("IsPrimed", this.isPrimed);
		nbt.putInt("Cooldown", cooldown);
		nbt.putInt("MaxCooldown", maxCooldown);
		nbt.putInt("TimesCast", timesCast);
		return nbt;
	}

	public Spell getSpell() {
		return spell;
	}

	public boolean isKnown() {
		return isKnown;
	}

	public boolean isPrimed() {
		return isPrimed;
	}

	public int getCooldown() {
		return cooldown;
	}
	
	public int getMaxCooldown() {
		return maxCooldown;
	}
	
	public int getTimesCast() {
		return timesCast;
	}
	
	public float cooldownColmpetion() {
		if (maxCooldown == 0)
			return 1.0f;
		else 
			return 1.0f-((float)cooldown/(float)maxCooldown);
	}
	
	public boolean onCooldown() {
		return cooldown > 0;
	}
	
	public static void setSpellKnown(LivingEntity entity, Spell spell, boolean known) {
		SpellStats current = CombatEntityStats.getSpellStats(entity, spell);
		SpellStats updated = new SpellStats(spell, known, current.isPrimed(), current.getCooldown(), current.getMaxCooldown(), current.getTimesCast());
		CombatEntityStats.setSpellStats(entity, updated);
	}
	
	public static void setSpellPrimed(LivingEntity entity, Spell spell, boolean primed) {
		SpellStats current = CombatEntityStats.getSpellStats(entity, spell);
		SpellStats updated = new SpellStats(spell, current.isKnown(), primed, current.getCooldown(), current.getMaxCooldown(), current.getTimesCast());
		CombatEntityStats.setSpellStats(entity, updated);
	}
	
	public static void setCooldown(LivingEntity entity, Spell spell, int cooldown, boolean preserveMax) {
		SpellStats current = CombatEntityStats.getSpellStats(entity, spell);
		SpellStats updated = new SpellStats(spell, current.isKnown(), current.isPrimed(), cooldown, preserveMax?current.getMaxCooldown():cooldown, current.getTimesCast());
		CombatEntityStats.setSpellStats(entity, updated);
	}
	
	public static void addTimesCast(LivingEntity entity, Spell spell) {
		SpellStats current = CombatEntityStats.getSpellStats(entity, spell);
		SpellStats updated = new SpellStats(spell, current.isKnown(), current.isPrimed(), current.getCooldown(), current.getMaxCooldown(), current.getTimesCast()+1);
		CombatEntityStats.setSpellStats(entity, updated);
	}
	
	public static boolean getSpellKnowledge(PlayerEntity player, Spell spell) {
		return CombatEntityStats.getSpellStats(player, spell).isKnown() 
				|| (player != null && player.isCreative()) 
				|| !Config.MAGIC_COMMON.enableSpellKnowledge.get();
	}
}
