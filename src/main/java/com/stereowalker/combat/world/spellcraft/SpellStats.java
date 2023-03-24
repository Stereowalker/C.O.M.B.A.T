package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.world.entity.CombatEntityStats;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;


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
	
	public static SpellStats read(CompoundTag nbt) {
		return new SpellStats(SpellUtil.getSpellFromNBT(nbt), nbt.getBoolean("IsKnown"), nbt.getBoolean("IsPrimed"), nbt.getInt("Cooldown"), nbt.getInt("MaxCooldown"), nbt.getInt("TimesCast"));
	}
	
	public CompoundTag write(CompoundTag nbt) {
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
	
	public static boolean getSpellKnowledge(Player player, Spell spell) {
		return CombatEntityStats.getSpellStats(player, spell).isKnown() 
				|| (player != null && player.isCreative()) 
				|| !Combat.MAGIC_CONFIG.enableSpellKnowledge;
	}
	
	public static boolean getSpellPrimed(Player player, Spell spell) {
		return CombatEntityStats.getSpellStats(player, spell).isPrimed();
	}
}
