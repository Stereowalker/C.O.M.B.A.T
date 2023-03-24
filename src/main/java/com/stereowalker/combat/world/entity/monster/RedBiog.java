package com.stereowalker.combat.world.entity.monster;

import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class RedBiog extends Biog {

	public RedBiog(EntityType<? extends RedBiog> type, Level worldIn) {
		super(type, worldIn);
	}

	public RedBiog(Level worldIn) {
		this(CEntityType.RED_BIOG, worldIn);
	}

	@Override
	protected void addBehaviourGoals() {
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.MOVEMENT_SPEED, (double)0.5F).add(Attributes.FOLLOW_RANGE, 70.0D).add(Attributes.ARMOR, 4.0D).add(Attributes.ATTACK_DAMAGE, 7.0D);
	}

}
