package com.stereowalker.rankup.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {
	
	protected AbstractArrowMixin(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
		super(p_37248_, p_37249_);
	}
	
	private boolean hasAppliedSkill = false;
	@Shadow public void setBaseDamage(double pDamage) {}
	@Shadow public double getBaseDamage() {return 0;}

	@Inject(method = "shoot", at = @At(value = "TAIL"))
	public void init_inject(double pX, double pY, double pZ, float pVelocity, float pInaccuracy, CallbackInfo ci) {
		if (!this.hasAppliedSkill && this.getOwner() instanceof LivingEntity && PlayerSkills.hasSkill((LivingEntity) this.getOwner(), Skills.ARCHERS_ELBOW)) {
			this.setBaseDamage(this.getBaseDamage()*1.1D);
			this.hasAppliedSkill = true;
		}
	}
}
