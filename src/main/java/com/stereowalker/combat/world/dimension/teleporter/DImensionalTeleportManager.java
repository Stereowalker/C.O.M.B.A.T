package com.stereowalker.combat.world.dimension.teleporter;

import com.stereowalker.combat.world.CDimensionType;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DImensionalTeleportManager {

	public static void teleportToAcrotlestViaRuins(ServerPlayerEntity entity) {
		if(!entity.world.isRemote) {
			ServerWorld dimension = entity.getServerWorld();
			ServerWorld destination = dimension == entity.getServer().getWorld(CDimensionType.CWorld.ACROTLEST) ? entity.getServer().getWorld(World.OVERWORLD) : entity.getServer().getWorld(CDimensionType.CWorld.ACROTLEST);
			if(entity.getRidingEntity() == null && !entity.isBeingRidden() && destination != null) {
				entity.changeDimension(destination, new AcrotlestRuinsTeleporter());
			}
		}
	}
}
