package com.stereowalker.combat.entity.projectile;

import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DaggerEntity extends AbstractThrowableItemEntity {

	public DaggerEntity(EntityType<? extends DaggerEntity> p_i50148_1_, World worldIn) {
		super(p_i50148_1_, worldIn);
	}

	public DaggerEntity(World worldIn, LivingEntity entityIn, ItemStack thrownStackIn) {
		super(CEntityType.DAGGER, worldIn, entityIn, thrownStackIn);
	}

	@OnlyIn(Dist.CLIENT)
	public DaggerEntity(World worldIn, double velocityX, double velocityY, double velocityZ) {
		super(CEntityType.DAGGER, worldIn, velocityY, velocityZ, velocityX);
	}
	
	@Override
	public int boomerangAlternative(Entity entity) {
		if (entity instanceof PlayerEntity) {
			if (PlayerSkills.hasSkill((PlayerEntity)entity, Skills.DAGGER_RETRIEVAL_3)) {
				return 3;
			} else if (PlayerSkills.hasSkill((PlayerEntity)entity, Skills.DAGGER_RETRIEVAL_2)) {
				return 2;
			} else if (PlayerSkills.hasSkill((PlayerEntity)entity, Skills.DAGGER_RETRIEVAL_1)) {
				return 1;
			}
		}
		return super.boomerangAlternative(entity);
	}
}