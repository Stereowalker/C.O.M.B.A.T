package com.stereowalker.combat.api.spell;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public interface IExtensionSpell {
	public abstract boolean extensionProgram(LivingEntity caster, @Nullable Entity target, double strength, Vector3d location, Hand hand);
//	public abstract boolean canExtensionExecute();
}
