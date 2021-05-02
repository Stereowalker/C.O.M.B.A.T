package com.stereowalker.combat.state.properties;

import net.minecraft.util.IStringSerializable;

public enum VerticalSlabType implements IStringSerializable {
	   EAST("east"),
	   WEST("west"),
	   NORTH("north"),
	   SOUTH("south"),
	   DOUBLE("double");

	   private final String name;

	   private VerticalSlabType(String name) {
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
