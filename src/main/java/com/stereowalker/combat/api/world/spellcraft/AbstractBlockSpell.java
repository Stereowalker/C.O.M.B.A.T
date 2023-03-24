package com.stereowalker.combat.api.world.spellcraft;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBlockSpell extends Spell {

	public AbstractBlockSpell(SpellCategory category, Rank tier, float cost, int cooldown, int castTime) {
		super(category, tier, CastType.BLOCK, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		if (caster instanceof Player && getRayTraceResult() instanceof BlockHitResult && hand != null) {
			UseOnContext context = new UseOnContext((Player)caster, hand, (BlockHitResult)getRayTraceResult());
			return blockProgram(caster, context, strength).consumesAction();
		}
		return false;
	}
	
	public abstract InteractionResult blockProgram(LivingEntity caster, UseOnContext context, double strength);

}
