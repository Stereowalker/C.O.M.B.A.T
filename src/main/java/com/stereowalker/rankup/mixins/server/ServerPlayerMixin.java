package com.stereowalker.rankup.mixins.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;
import com.stereowalker.combat.stats.CStats;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
	
	public ServerPlayerMixin(Level p_36114_, BlockPos p_36115_, float p_36116_, GameProfile p_36117_) {
		super(p_36114_, p_36115_, p_36116_, p_36117_);
	}

	@Inject(method = "awardKillScore", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getScoreboard()Lnet/minecraft/world/scores/Scoreboard;"))
	public void init_inject(Entity pKilled, int pScoreValue, DamageSource pDamageSource, CallbackInfo ci) {
		if (this.getMainHandItem().getItem() instanceof ProjectileWeaponItem) {
			this.awardStat(CStats.MOBS_KILLED_WITH_BOW);
		}
	}
}
