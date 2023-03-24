package com.stereowalker.combat.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum CableConnectionType implements StringRepresentable {
	   RECIEVE("recieve"),
	   TRANSFER("transfer"),
	   NONE("none");

	   private final String name;

	   private CableConnectionType(String name) {
	      this.name = name;
	   }

	   @Override
	   public String toString() {
	      return this.name;
	   }

	   @Override
	   public String getSerializedName() {
	      return this.name;
	   }
	}
