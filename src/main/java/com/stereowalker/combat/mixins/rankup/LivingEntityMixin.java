package com.stereowalker.combat.mixins.rankup;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.stereowalker.combat.event.AbominationEvents;
import com.stereowalker.old.combat.config.Config;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Shadow @Nullable protected Player lastHurtByPlayer;
	@Shadow protected int getExperienceReward(Player pPlayer) {return 0;}
	
	/**
	 * Duplicates xp into the player
	 */
	@Redirect(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"))
	public void hurt_inject2(ServerLevel level, Vec3 position, int amount) {
		int amountModified = amount;
		//Modify the xp drp for abominations
		if ((LivingEntity)(Object)this instanceof Monster) {
			amountModified = AbominationEvents.abominationXP((Monster)(Object)this, amount);
		}
		//In order to prevent players from getting experience from other players
		if (lastHurtByPlayer != null && !((LivingEntity)(Object)this instanceof Player)) {
			amountModified *= Math.max((PlayerAttributeLevels.getLevel((LivingEntity)(Object)this))-(PlayerAttributeLevels.getLevel(lastHurtByPlayer)), 1);

			PlayerAttributeLevels.setExperience(lastHurtByPlayer, PlayerAttributeLevels.getExperience(lastHurtByPlayer)+amountModified);
		}
		//Make players take all the experence from players that theyve killed
		if (lastHurtByPlayer != null && ((LivingEntity)(Object)this instanceof Player) && Config.RPG_COMMON.takeXpFromKilledPlayers.get()) {
			PlayerAttributeLevels.setExperience(lastHurtByPlayer, PlayerAttributeLevels.getExperience(lastHurtByPlayer)+PlayerAttributeLevels.getExperience((LivingEntity)(Object)this));
			PlayerAttributeLevels.setExperience((LivingEntity)(Object)this, 0);
		}
		
		ExperienceOrb.award(level, position, amountModified);
	}
}
