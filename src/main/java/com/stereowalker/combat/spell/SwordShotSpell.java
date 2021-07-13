package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.SpellInstance;
import com.stereowalker.combat.entity.projectile.ProjectileSwordEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public class SwordShotSpell extends Spell {

	Item sword;
	public SwordShotSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime, Item sword) {
		super(category, tier, type, cost, cooldown, castTime);
		this.sword = sword;
	}
	
	@Override
	public boolean primingProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		if (!caster.world.isRemote) {
			System.out.println("PRIMED");
			ProjectileSwordEntity proj = new ProjectileSwordEntity(caster.world, caster.getPosX() + 1, caster.getPosY() + 4, caster.getPosZ());
			proj.setShooter(caster);
			proj.setNoGravity(true);
			proj.setSpinMidAir(false);
			proj.setEjectedSword(false);
			proj.setSword(new ItemStack(this.sword));
			proj.setSpell(new SpellInstance(this, strength, location, hand, caster.getUniqueID()));
			caster.world.addEntity(proj);
		}
		return super.primingProgram(caster, strength, location, hand);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		for (ProjectileSwordEntity proj : caster.world.getEntitiesWithinAABB(ProjectileSwordEntity.class, caster.getBoundingBox().grow(30))) {
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
