package com.stereowalker.combat.api.world.spellcraft;

import javax.annotation.Nullable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public interface IExtensionSpell {
	public abstract boolean extensionProgram(LivingEntity caster, @Nullable Entity target, double strength, Vec3 location, InteractionHand hand);
//	public abstract boolean canExtensionExecute();
}
