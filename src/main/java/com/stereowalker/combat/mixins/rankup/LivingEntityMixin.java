package com.stereowalker.combat.mixins.rankup;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stereowalker.old.combat.config.Config;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Shadow @Nullable protected Player lastHurtByPlayer;
	@Shadow protected int getExperienceReward(Player pPlayer) {return 0;}

	/**
	 * Awards the damage blocked by weapon stat whenever a sword is used to block damage
	 */
	@Inject(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V", shift = Shift.AFTER))
	public void hurt_inject2(CallbackInfo ci) {
		//In order to prevent players from getting experience from other players
		if (lastHurtByPlayer != null && !((LivingEntity)(Object)this instanceof Player)) {
			int i = this.getExperienceReward(this.lastHurtByPlayer);

			i *= Math.max((PlayerAttributeLevels.getLevel((LivingEntity)(Object)this))-(PlayerAttributeLevels.getLevel(lastHurtByPlayer)), 1);

			PlayerAttributeLevels.setExperience(lastHurtByPlayer, PlayerAttributeLevels.getExperience(lastHurtByPlayer)+i);
		}
		//Make players take all the experence from players that theyve killed
		if (lastHurtByPlayer != null && ((LivingEntity)(Object)this instanceof Player) && Config.RPG_COMMON.takeXpFromKilledPlayers.get()) {
			PlayerAttributeLevels.setExperience(lastHurtByPlayer, PlayerAttributeLevels.getExperience(lastHurtByPlayer)+PlayerAttributeLevels.getExperience((LivingEntity)(Object)this));
			PlayerAttributeLevels.setExperience((LivingEntity)(Object)this, 0);
		}
	}
}
