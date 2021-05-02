package com.stereowalker.combat.state.properties;

import net.minecraft.util.IStringSerializable;

public enum CableConnectionType implements IStringSerializable {
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
	   public String getString() {
	      return this.name;
	   }
	}
