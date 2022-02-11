package com.stereowalker.combat.world.level.dimension;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class DImensionalTeleportManager {

	public static void teleportToAcrotlestViaRuins(ServerPlayer entity) {
		if(!entity.level.isClientSide) {
			ServerLevel dimension = entity.getLevel();
			ServerLevel destination = dimension == entity.getServer().getLevel(CDimensionType.CLevel.ACROTLEST) ? entity.getServer().getLevel(Level.OVERWORLD) : entity.getServer().getLevel(CDimensionType.CLevel.ACROTLEST);
			if(entity.getVehicle() == null && !entity.isVehicle() && destination != null) {
				entity.changeDimension(destination, new AcrotlestRuinsTeleporter());
			}
		}
	}
}
