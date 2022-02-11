package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellInstance;
import com.stereowalker.combat.world.entity.projectile.ProjectileSword;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class SwordShotSpell extends Spell {

	Item sword;
	public SwordShotSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime, Item sword) {
		super(category, tier, type, cost, cooldown, castTime);
		this.sword = sword;
	}

	@Override
	public boolean primingProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		if (!caster.level.isClientSide) {
			int num = Mth.clamp(Mth.floor(strength), 1, 10);
			for (int i = 1; i <= num; i++) {
				ProjectileSword proj = new ProjectileSword(caster.level, caster.getX() + 1, caster.getY() + 4, caster.getZ());
				proj.setShooter(caster);
				proj.setNoGravity(true);
				proj.setSpinMidAir(false);
				proj.setEjectedSword(false);
				proj.setSword(new ItemStack(this.sword));
				proj.setSpell(new SpellInstance(this, strength/(double)num, location, hand, caster.getUUID()));
				float mod;
				if (num > 1)
					mod = (float)(i-1)/(float)(num-1);
				else 
					mod = 0;
				proj.setSwordPosition(-(60 + 60*mod));
				caster.level.addFreshEntity(proj);
			}
		}
		return super.primingProgram(caster, strength, location, hand);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		for (ProjectileSword proj : caster.level.getEntitiesOfClass(ProjectileSword.class, caster.getBoundingBox().inflate(30))) {
			if (proj.getShooter() == caster) {
				proj.setNoGravity(false);
				proj.setSpinMidAir(true);
				proj.setEjectedSword(true);
			}
		}
		return true;
	}

	@Override
	public boolean canBePrimed() {
		return true;
	}

}
