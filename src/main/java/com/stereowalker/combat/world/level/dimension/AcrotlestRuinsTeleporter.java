package com.stereowalker.combat.world.level.dimension;
import java.util.Random;
import java.util.function.Function;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.ITeleporter;

public class AcrotlestRuinsTeleporter implements ITeleporter {

	@Override
	public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw,
			Function<Boolean, Entity> repositionEntity) {
		if (entity instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer)entity;
			Random rand = new Random();
			int x = rand.nextInt(1000);
			int z = rand.nextInt(1000);
			double d0 = x;
			double d1 = 255;
			double d2 = z;
			float f = player.getXRot();
			float f1 = player.getYRot();
			currentWorld.getProfiler().push("moving");
			double moveFactor = currentWorld.dimensionType().coordinateScale() / destWorld.dimensionType().coordinateScale();
			d0 *= moveFactor;
			d2 *= moveFactor;

			player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20*30));
			player.moveTo(d0, d1, d2, f1, f);
			currentWorld.getProfiler().pop();
			currentWorld.getProfiler().push("placing");
			double d7 = Math.min(-2.9999872E7D, destWorld.getWorldBorder().getMinX() + 16.0D);
			double d4 = Math.min(-2.9999872E7D, destWorld.getWorldBorder().getMinZ() + 16.0D);
			double d5 = Math.min(2.9999872E7D, destWorld.getWorldBorder().getMaxX() - 16.0D);
			double d6 = Math.min(2.9999872E7D, destWorld.getWorldBorder().getMaxZ() - 16.0D);
			d0 = Mth.clamp(d0, d7, d5);
			d2 = Mth.clamp(d2, d4, d6);
			player.moveTo(d0, d1, d2, f1, f);
			
			currentWorld.getProfiler().pop();
			player.setLevel(destWorld);
			destWorld.addDuringPortalTeleport(player);
			player.connection.teleport(player.getX(), player.getY(), player.getZ(), f1, f);
			return player;
		}
		return entity;
	}
}
