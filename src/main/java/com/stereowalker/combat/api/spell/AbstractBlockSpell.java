package com.stereowalker.combat.api.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public abstract class AbstractBlockSpell extends Spell {

	public AbstractBlockSpell(SpellCategory category, Rank tier, float cost, int cooldown, int castTime) {
		super(category, tier, CastType.BLOCK, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		if (caster instanceof PlayerEntity && getRayTraceResult() instanceof BlockRayTraceResult && hand != null) {
			ItemUseContext context = new ItemUseContext((PlayerEntity)caster, hand, (BlockRayTraceResult)getRayTraceResult());
			return blockProgram(caster, context, strength).isSuccessOrConsume();
		}
		return false;
	}
	
	public abstract ActionResultType blockProgram(LivingEntity caster, ItemUseContext context, double strength);

}
