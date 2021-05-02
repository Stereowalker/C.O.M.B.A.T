package com.stereowalker.combat.world.dimension.teleporter;
import java.util.Random;
import java.util.function.Function;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

public class AcrotlestRuinsTeleporter implements ITeleporter {

	@Override
	public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw,
			Function<Boolean, Entity> repositionEntity) {
		if (entity instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)entity;
			Random rand = new Random();
			int x = rand.nextInt(1000);
			int z = rand.nextInt(1000);
			double d0 = x;
			double d1 = 255;
			double d2 = z;
			float f = player.rotationPitch;
			float f1 = player.rotationYaw;
			currentWorld.getProfiler().startSection("moving");
			double moveFactor = currentWorld.getDimensionType().getCoordinateScale() / destWorld.getDimensionType().getCoordinateScale();
			d0 *= moveFactor;
			d2 *= moveFactor;

			player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 20*30));
			player.setLocationAndAngles(d0, d1, d2, f1, f);
			currentWorld.getProfiler().endSection();
			currentWorld.getProfiler().startSection("placing");
			double d7 = Math.min(-2.9999872E7D, destWorld.getWorldBorder().minX() + 16.0D);
			double d4 = Math.min(-2.9999872E7D, destWorld.getWorldBorder().minZ() + 16.0D);
			double d5 = Math.min(2.9999872E7D, destWorld.getWorldBorder().maxX() - 16.0D);
			double d6 = Math.min(2.9999872E7D, destWorld.getWorldBorder().maxZ() - 16.0D);
			d0 = MathHelper.clamp(d0, d7, d5);
			d2 = MathHelper.clamp(d2, d4, d6);
			player.setLocationAndAngles(d0, d1, d2, f1, f);
			
			currentWorld.getProfiler().endSection();
			player.setWorld(destWorld);
			destWorld.addDuringPortalTeleport(player);
			player.connection.setPlayerLocation(player.getPosX(), player.getPosY(), player.getPosZ(), f1, f);
			return player;
		}
		return entity;
	}
}
