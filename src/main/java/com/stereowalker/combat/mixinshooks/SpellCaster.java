package com.stereowalker.combat.mixinshooks;

import com.stereowalker.combat.api.world.spellcraft.Spell;

public interface SpellCaster {
	public Spell getLastCastedSpell();
	public int getLastCastedTicks();
	public void setLastCastedSpell(Spell spell);
	public void setLastCastedTicks(int ticks);
}
