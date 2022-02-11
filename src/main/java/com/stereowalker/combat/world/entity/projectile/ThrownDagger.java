package com.stereowalker.combat.world.entity.projectile;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ThrownDagger extends AbstractThrownItem {

	public ThrownDagger(EntityType<? extends ThrownDagger> p_i50148_1_, Level worldIn) {
		super(p_i50148_1_, worldIn);
	}

	public ThrownDagger(Level worldIn, LivingEntity entityIn, ItemStack thrownStackIn) {
		super(CEntityType.DAGGER, worldIn, entityIn, thrownStackIn);
	}

	@OnlyIn(Dist.CLIENT)
	public ThrownDagger(Level worldIn, double velocityX, double velocityY, double velocityZ) {
		super(CEntityType.DAGGER, worldIn, velocityY, velocityZ, velocityX);
	}
	
	@Override
	public int boomerangAlternative(Entity entity) {
		if (entity instanceof Player) {
			if (PlayerSkills.hasSkill((Player)entity, Skills.DAGGER_RETRIEVAL_3)) {
				return 3;
			} else if (PlayerSkills.hasSkill((Player)entity, Skills.DAGGER_RETRIEVAL_2)) {
				return 2;
			} else if (PlayerSkills.hasSkill((Player)entity, Skills.DAGGER_RETRIEVAL_1)) {
				return 1;
			}
		}
		return super.boomerangAlternative(entity);
	}
}